package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.Bud.CreateBudRequest;
import com.practice.springsecondphrasepractice.controller.dto.request.Bud.UpdateBudRequest;
import com.practice.springsecondphrasepractice.controller.dto.response.StatusResponse;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.entriy.Bud;
import com.practice.springsecondphrasepractice.service.BudService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@Validated
@RequestMapping("/api/v1/bud")
public class BudController {

    @Autowired
    private BudService budService;

    @GetMapping()
    public List<Bud> getAllBuds(@RequestParam(required = false) @Pattern(regexp = "^[?=\\d]{4}", message = "年度 格式錯誤")
                                        String year,
                                @RequestParam(required = false) @Pattern(regexp = "^[?=\\d]{8}", message = "起始日期 格式錯誤")
                                        String startDate,
                                @RequestParam(required = false) @Pattern(regexp = "^[?=\\d]{8}", message = "結束日期 格式錯誤")
                                        String endDate) throws Exception {
        try {
            List<Bud> response;
//        List<Bud> getYearData = this.budService.getYear(year);
            List<String> msg = new ArrayList<>();
            if (year == null) {
                if (startDate == null && endDate == null) {
                    response = this.budService.getAllBud();
                    return response;
                } else {
                     assert startDate != null;
                    if (Integer.parseInt(startDate) > Integer.parseInt(endDate)) {
                        msg.add("起始日期 不能大於 結束日期");
                        throw new ParamInvalidException(msg);
                    } else {
                        response = this.budService.getStartAndEnd(startDate, endDate);
                        response.stream().filter(type->type.getBudType().equals("Y")).collect(Collectors.toList());
                    }
                }
            } else {
                response = this.budService.getYear(year);
            }
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    /* 查詢單一 */
    @GetMapping("/{budYmd}")
    public Bud getBudByBudYmd(@PathVariable @NotEmpty(message = "Id不得為空") @Pattern(regexp = "^[(?=\\d)]{8}", message = "日期 格式錯誤") String budYmd) throws DataNotFoundException {

        Bud findBudYmd = budService.getBudByBudYmd(budYmd);
        if (findBudYmd == null) {
            throw new DataNotFoundException("資料不存在");
        }
        return findBudYmd;
    }
    /* 查詢前一營業日、後一營業日 */
    @GetMapping("/business/{budYmd}")
    public Map getPrevAndNext(@PathVariable @Pattern(regexp = "^[(?=\\d)]{8}", message = "日期 格式錯誤")
                              String budYmd) throws Exception{
        try {
            return budService.getPrevAndNext(budYmd);

        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    /* 新增資料 */
    @PostMapping
    public StatusResponse createBud(@RequestBody @Validated CreateBudRequest request) throws Exception{
        try{
            return budService.createBud(request);
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    /* 更新 */
    @PutMapping("/{budYmd}")
    public StatusResponse updateBud(@PathVariable String budYmd, @RequestBody @Validated UpdateBudRequest request) throws Exception{
        try{
            return budService.updateBud(budYmd, request);
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

}
