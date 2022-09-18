package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.entriy.Bud;
import com.practice.springsecondphrasepractice.service.BudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/bud")
public class BudController {

    @Autowired
    private BudService budService;


    @GetMapping()
    public List<Bud> getAllBuds() throws DataNotFoundException {
        try {
            return budService.getAllBud();
        } catch (Exception e) {
            throw new DataNotFoundException("資料不存在");
        }
    }

    @GetMapping("/{budYmd}")
    public Bud getBudByBudYmd(@PathVariable String budYmd) throws ParamInvalidException {
        try {
            return budService.getBudByBudYmd(budYmd);

        } catch (Exception e) {
            List<String> errMsgs = new ArrayList<>();
            errMsgs.add("格式錯誤");
            throw new ParamInvalidException(errMsgs);
        }
    }

}
