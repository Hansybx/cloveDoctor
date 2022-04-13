package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.admin.ChartService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/chart")
public class ChartController {
    @Resource
    ChartService chartService;

    @GetMapping("")
    public CommonResult getChartData(){
        return chartService.getChartData();
    }
}
