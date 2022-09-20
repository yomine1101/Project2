package com.practice.springsecondphrasepractice.service;

import aj.org.objectweb.asm.TypeReference;
import com.practice.springsecondphrasepractice.controller.dto.request.Nfa.CreateNfaRequest;
import com.practice.springsecondphrasepractice.controller.dto.request.Nfa.DeleteNfaRequest;
import com.practice.springsecondphrasepractice.controller.dto.request.Nfa.UpdateNfaRequest;
import com.practice.springsecondphrasepractice.controller.dto.response.NfaResponse;
import com.practice.springsecondphrasepractice.controller.dto.response.StatusResponse;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.NfaRepository;
import com.practice.springsecondphrasepractice.model.entriy.Nfa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NfaService {

    @Autowired
    NfaRepository nfaRepository;

    public List<NfaResponse> getAllNfa() throws Exception {
        List<Nfa> responseList = nfaRepository.findAll();
        try {
            if (responseList.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            responseList.stream().filter(nfa -> nfa.getNfaEnable().equals("Y")).collect(Collectors.toList());
            List<NfaResponse> response = nfaResponses(responseList);
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public List<NfaResponse> nfaResponses(List<Nfa> nfaList){
        List<NfaResponse> nfaResponseList = new ArrayList<>();
        for(Nfa nfa: nfaList){
            NfaResponse nfaResponse = new NfaResponse();

            nfaResponse.setUuid(nfa.getNfaUuid());
            nfaResponse.setSubject(nfa.getNfaSubject());
            nfaResponse.setContent(nfa.getNfaContent());
            nfaResponse.setEnable(nfa.getNfaEnable());
            nfaResponse.setCreateTime(nfa.getNfaUTime());

            nfaResponseList.add(nfaResponse);
        }
        return nfaResponseList;
    }

    /* 查詢條件 subject start end*/
    public List<NfaResponse> getSubjectAndStartAndEnd(String subject, String startDate, String endDate) throws Exception{
        try {
            List<Nfa> responseList = new ArrayList<>();

            if(subject != null){
                responseList = nfaRepository.findByNfaSubjectContaining(subject);
            }
            if(startDate != null || endDate != null){
                    responseList = nfaRepository.findByNfaSTime(startDate);
                    responseList.stream().filter(start->start.getNfaETime().equals(endDate)).collect(Collectors.toList());

            }else if(startDate == null && endDate !=null){
                responseList = nfaRepository.findByNfaETime(endDate);
            }else if( startDate != null && endDate == null){
                responseList = nfaRepository.findByNfaSTime(startDate);
            }
            responseList = responseList.stream().filter(type->type.getNfaEnable().equals("Y")).collect(Collectors.toList());
            if(responseList.isEmpty()){
                throw new DataNotFoundException("資料不存在");
            }
            return nfaResponses(responseList);
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }


    /* 新增公告 */
    public StatusResponse createNfa(CreateNfaRequest request) throws Exception{
        try {
            Nfa nfa = new Nfa();
            nfa.setNfaUuid(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS").format(LocalDateTime.now()));
            nfa.setNfaSubject(request.getSubject());
            nfa.setNfaContent(request.getContent());
            nfa.setNfaEnable(request.getEnable());
            nfa.setNfaSTime(request.getStartDate());
            nfa.setNfaETime(request.getEndDate());
            nfa.setNfaUTime(LocalDateTime.now());
            nfaRepository.save(nfa);
            return new StatusResponse("新增成功");
        }catch (Exception e){
            throw new Exception();
        }
    }
    /* 修改公告 */
    public StatusResponse updateNfa(String nfaUuid, UpdateNfaRequest request) throws Exception{
        try {
            Nfa nfa = nfaRepository.findByNfaUuid(nfaUuid);
            List<String> msg = new ArrayList<>();
            if( nfa != null){
                nfa.setNfaSubject(request.getSubject());
                nfa.setNfaContent(request.getContent());
                nfa.setNfaEnable(request.getEnable());
                nfa.setNfaSTime(request.getStartDate());
                nfa.setNfaETime(request.getEndDate());
                nfa.setNfaUTime(LocalDateTime.now());
                nfaRepository.save(nfa);
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
    /* 撤銷公告 */
    public StatusResponse deleteNfa(String nfaUuid, DeleteNfaRequest request) throws Exception{
        try {
            Nfa nfa = nfaRepository.findByNfaUuid(nfaUuid);
            List<String> msg = new ArrayList<>();
            if(nfa != null){
                nfa.setNfaEnable(request.getEnable());
                nfaRepository.save(nfa);
                return new StatusResponse("撤銷成功");
            }msg.add("資料不存在");
            throw new ParamInvalidException(msg);
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }
}
