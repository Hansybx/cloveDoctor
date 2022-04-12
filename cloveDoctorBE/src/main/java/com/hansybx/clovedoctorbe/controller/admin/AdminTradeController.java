package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.admin.AdminTradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/trade")
public class AdminTradeController {
    @Resource
    AdminTradeService adminTradeService;

    @GetMapping("")
    public CommonResult getTradeList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminTradeService.getTradeList(pageNum, pageSize);
    }

    @GetMapping("/search")
    public CommonResult searchTradeList(@RequestParam String keyword, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminTradeService.tradeSearch(keyword, pageNum, pageSize);
    }
}
