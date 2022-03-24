package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.DTO.SwiperDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.admin.SwiperService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class swiperController {
    @Resource
    private SwiperService swiperService;

    @PostMapping("/swiper")
    public CommonResult addSwiper(@RequestBody SwiperDTO swiperDTO){
        return swiperService.addSwiper(swiperDTO);
    }
}
