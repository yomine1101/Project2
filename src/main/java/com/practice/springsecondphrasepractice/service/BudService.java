package com.practice.springsecondphrasepractice.service;


import com.practice.springsecondphrasepractice.controller.dto.request.Bud.CreateBudRequest;
import com.practice.springsecondphrasepractice.controller.dto.request.Bud.UpdateBudRequest;
import com.practice.springsecondphrasepractice.controller.dto.response.StatusResponse;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.entriy.Bud;
import com.practice.springsecondphrasepractice.model.BudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BudService {

    @Autowired
    BudRepository budRepository;


    public List<Bud> getAllBud() {
        return this.budRepository.findAll();
    }

    public Bud getBudByBudYmd(String budYmd) {
        return this.budRepository.findByBudYmd(budYmd);
    }



    /* 查詢區間內是營業日的日期 */
    public List<Bud> getStartAndEnd(String startDate, String endDate) throws Exception{
        try{
            List<Bud> response = budRepository.findByBudYmdBetween(startDate, endDate);
            if (response.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            return response;

        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    /* 查詢該年度內為營業日的日期 */
    public List<Bud> getYear(String year) throws Exception{
        List<Bud> response = budRepository.findByBudYmdStartingWith(year);
        try{
            if(response.isEmpty()){
                throw new DataNotFoundException("資料不存在");
            }
            return response.stream().filter(type->type.getBudType().equals("Y")).collect(Collectors.toList());

        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    /* 查詢前一營業日、後一營業日 */
    public Map getPrevAndNext(String year) throws Exception{
        try{
            List<Bud> budPrevYmd = budRepository.getPrevYmd(year);
            List<Bud> budNextYmd = budRepository.getNextYmd(year);
            if(budPrevYmd.isEmpty()||budNextYmd.isEmpty()){
                throw new DataNotFoundException("資料不存在");
            }
            LinkedHashMap responses = new LinkedHashMap();
            responses.put("budYmd", year);
            responses.put("budPrevYmd", budPrevYmd.get(0).getBudYmd());
            responses.put("budNextYmd", budNextYmd.get(0).getBudYmd());
            return responses;
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    public StatusResponse createBud(CreateBudRequest request) throws Exception {
        try {
            Bud budYmd = budRepository.findByBudYmd(request.getBudYmd());
            Bud bud = new Bud();
            List<String> msg = new ArrayList<>();
            if (budYmd == null) {
                bud.setBudYmd(request.getBudYmd());
                bud.setBudType(request.getBudType());
                bud.setBudUTime(LocalDateTime.now());
                budRepository.save(bud);
                return new StatusResponse("新增成功");
            }
            msg.add("資料已存在");
            throw new ParamInvalidException(msg);
        } catch (Exception e) {
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    public StatusResponse updateBud(String budYmd, UpdateBudRequest request) throws Exception{
        try {
            Bud bud = budRepository.findByBudYmd(budYmd);
            List<String> msg = new ArrayList<>();

            if (bud != null) {
                bud.setBudType(request.getBudType());
                bud.setBudUTime(LocalDateTime.now());
                budRepository.save(bud);
                return new StatusResponse("異動成功");
            }
            msg.add("資料不存在");
            throw new ParamInvalidException(msg);

        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }

    }

}
