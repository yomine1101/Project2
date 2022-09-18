package com.practice.springsecondphrasepractice.service;


import com.practice.springsecondphrasepractice.controller.dto.request.BudRequest;
import com.practice.springsecondphrasepractice.model.entriy.Bud;
import com.practice.springsecondphrasepractice.model.BudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

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

    public String checkBudYmd(BudRequest request){
        SimpleDateFormat budYmdDataFormat = new SimpleDateFormat("yyyyMMdd");
        if(request.getBudYmd() != budYmdDataFormat.format(request.getBudYmd())){
            return null;
        }
        return null;
    }
}
