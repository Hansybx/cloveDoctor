package com.hansybx.clovedoctorbe.controller;

import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.CommonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CommonController {
    @Resource
    CommonService commonService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/recommend")
    public CommonResult getHomeRecommend(){
        return commonService.getHomeRecommend();
    }
}
