package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.controller.dto.request.Prod.CreateProdRequest;
import com.practice.springsecondphrasepractice.controller.dto.request.Prod.DeleteProdRequest;
import com.practice.springsecondphrasepractice.controller.dto.request.Prod.UpdateProdRequest;
import com.practice.springsecondphrasepractice.controller.dto.response.StatusResponse;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.ProdRepository;
import com.practice.springsecondphrasepractice.model.entriy.Prod;
import lombok.NoArgsConstructor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.StandardServletAsyncWebRequest;
import org.w3c.dom.ls.LSInput;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.print.PrinterAbortException;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdService {

    @Autowired
    ProdRepository prodRepository;

    public List<Prod> getAllProd(){
        return prodRepository.findAll();
    }

    /* 根據類別查詢已啟用的商品 */
    public List<Prod> getByProdKind(String prodKind) throws Exception{
        List<Prod> response = prodRepository.findByProdIdStartingWith(prodKind);
        try{
            if(response.isEmpty()){
                throw new DataNotFoundException("資料不存在");
            }
            response.stream().filter(enable->enable.getProdEnable().equals("Y")).collect(Collectors.toList());
            return response;
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }

    }

    /* 根據幣別查詢已啟用商品 */

    public List<Prod> getByProdCcy(String prodCcy) throws Exception{
        List<Prod> response = prodRepository.findByProdCcyStartingWith(prodCcy);
        try {
            if(response.isEmpty()){
                throw new DataNotFoundException("資料不存在");
            }
            response.stream().filter(enable->enable.getProdEnable().equals("Y")).collect(Collectors.toList());
            return response;
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    /* 查詢單一商品 */
    public Prod getByProdId(String prodId){
        return this.prodRepository.findByProdId(prodId);
    }

    /* 新增商品 */
    public StatusResponse createProd(CreateProdRequest request) throws Exception {
        try {
//            String prodIdByKindAndCcy = request.getProdKind() + "_" + request.getProdCcy();
//            Prod prodId = this.prodRepository.findByProdId(prodIdByKindAndCcy);
            Prod prod = new Prod();
                prod.setProdId(request.getProdKind() + "_" + request.getProdCcy());
                prod.setProdKind(request.getProdKind());
                prod.setProdName(request.getProdName());
                prod.setProdEname(request.getProdEname());
                prod.setProdEnable(request.getProdEnable());
                prod.setProdCcy(request.getProdCcy());
                prod.setProdITime(LocalDateTime.now());
                prod.setProdUTime(LocalDateTime.now());
                prodRepository.save(prod);
                return new StatusResponse("新增成功");

        } catch (Exception e) {
            throw new Exception();
        }
    }

    /* 修改商品 */
    public StatusResponse updateProd(String prodId, UpdateProdRequest request) throws Exception{
        try {
            Prod prod = prodRepository.findByProdId(prodId);
            List<String> msg = new ArrayList<>();
            if(prod != null){
                prod.setProdName(request.getProdName());
                prod.setProdEname(request.getProdEname());
                prod.setProdEnable(request.getProdEnable());
                prodRepository.save(prod);
                return new StatusResponse("異動成功");
            }
            msg.add("資料不存在");
            throw new ParamInvalidException(msg);
        }catch (Exception e){
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    /* 註銷商品 */
    public StatusResponse deleteProd(String prodId, DeleteProdRequest request) throws Exception {
        try {
            Prod prod = prodRepository.findByProdId(prodId);
            List<String> msg = new ArrayList<>();
            if (prod != null) {
                prod.setProdEnable(request.getProdEnable());
                prod.setProdUTime(LocalDateTime.now());
                prodRepository.save(prod);
                return new StatusResponse("註銷成功");
            }
            msg.add("資料不存在");
//            throw new DataNotFoundException("刪除失敗");
            throw new ParamInvalidException(msg);
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }
}
