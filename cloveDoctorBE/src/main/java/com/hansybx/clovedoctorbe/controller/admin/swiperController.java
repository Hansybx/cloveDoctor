package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.DTO.SwiperDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.model.Swiper;
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
    public CommonResult addSwiper(@RequestBody SwiperDTO swiperDTO) {
        return swiperService.addSwiper(swiperDTO);
    }

    @GetMapping("/swiper")
    public CommonResult getSwiperListPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return swiperService.getSwiperListPage(pageNum, pageSize);
    }

    @PostMapping("/swiper/update")
    public CommonResult updateSwiper(@RequestBody Swiper swiperDTO) {
        return swiperService.updateSwiper(swiperDTO);
    }

    @PostMapping("/swiper/del")
    public CommonResult swiperDel(@RequestBody SwiperDTO swiperDTO) {
        return swiperService.delSwiper(swiperDTO);
    }

    @PostMapping("/swiper/del/multi")
    public CommonResult swiperDelMulti(@RequestBody SwiperDTO[] swiperDTOs) {
        return swiperService.delSwiperMulti(swiperDTOs);
    }
}
