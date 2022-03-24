package com.hansybx.clovedoctorbe.service.admin;

import com.hansybx.clovedoctorbe.DTO.SwiperDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.SwiperMapper;
import com.hansybx.clovedoctorbe.model.Swiper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SwiperService {
    @Resource
    SwiperMapper swiperMapper;
    public CommonResult addSwiper(SwiperDTO swiperDTO){
        Swiper swiper = new Swiper();
        swiper.setAddTime(swiperDTO.getAddTime());
        swiper.setDrugId(swiperDTO.getDrugId());
        swiper.setImgUrl(swiperDTO.getImgUrl());
        swiper.setSortId(swiperDTO.getSortId());
        swiperMapper.insert(swiper);
        return CommonResponse.Success();
    }
}
