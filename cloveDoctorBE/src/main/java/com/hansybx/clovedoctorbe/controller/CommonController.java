package com.hansybx.clovedoctorbe.controller;

import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.CommonService;
import com.hansybx.clovedoctorbe.service.admin.DrugsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CommonController {
    @Resource
    CommonService commonService;
    @Resource
    DrugsService drugsService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/recommend")
    public CommonResult getHomeRecommend() {
        return commonService.getHomeRecommend();
    }

    @GetMapping("/drug")
    public CommonResult getDrugs(@RequestParam Integer drugId) {
        return commonService.getDrugInfo(drugId);
    }

    @GetMapping("/swiper")
    public CommonResult getHomeSwiper() {
        return commonService.getSwiper();
    }

    @GetMapping("/drugs")
    public CommonResult getDrugs(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return drugsService.getDrugs(pageNum, pageSize);
    }

    @GetMapping("/drugs/search")
    public CommonResult drugSearch(@RequestParam String keyword, @RequestParam Integer status, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return commonService.searchName(keyword, status, pageNum, pageSize);
    }
}
