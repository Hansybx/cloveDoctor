package com.hansybx.clovedoctorbe.service.admin;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.SwiperDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.SwiperMapper;
import com.hansybx.clovedoctorbe.model.Swiper;
import com.hansybx.clovedoctorbe.model.SwiperExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class SwiperService {
    @Resource
    SwiperMapper swiperMapper;

    public CommonResult addSwiper(SwiperDTO swiperDTO) {
        SwiperExample swiperExample = new SwiperExample();
        swiperExample.createCriteria().andDrugIdEqualTo(swiperDTO.getDrugId());
        if (swiperMapper.countByExample(swiperExample) > 0) {
            return CommonResponse.Fail("该药品已经配置过了");
        }
        Swiper swiper = new Swiper();
        swiper.setAddTime(swiperDTO.getAddTime());
        swiper.setDrugId(swiperDTO.getDrugId());
        swiper.setImgUrl(swiperDTO.getImgUrl());
        swiper.setSortId(swiperDTO.getSortId());
        swiperMapper.insert(swiper);
        return CommonResponse.Success();
    }

    public CommonResult getSwiperListPage(Integer pageNum, Integer pageSize) {
        String orderBy = "sortId desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);

        SwiperExample swiperExample = new SwiperExample();
        swiperExample.createCriteria();
        List<Swiper> swiperList = swiperMapper.selectByExample(swiperExample);
        Long totalNum = swiperMapper.countByExample(swiperExample);

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalNum", totalNum);
        map.put("swiperList", swiperList);
        return CommonResponse.Success(map);
    }

    public CommonResult updateSwiper(Swiper swiperDTO) {
        SwiperExample swiperExample = new SwiperExample();
        swiperExample.createCriteria().andIdEqualTo(swiperDTO.getId());
        swiperMapper.updateByExample(swiperDTO, swiperExample);
        return CommonResponse.Success();
    }

    public CommonResult delSwiper(SwiperDTO swiperDTO) {
        SwiperExample swiperExample = new SwiperExample();
        swiperExample.createCriteria().andIdEqualTo(swiperDTO.getId());
        swiperMapper.deleteByExample(swiperExample);
        return CommonResponse.Success();
    }

    public CommonResult delSwiperMulti(SwiperDTO[] swiperDTOs) {
        for (SwiperDTO swiperDTO : swiperDTOs) {
            swiperMapper.deleteByPrimaryKey(swiperDTO.getId());
        }
        return CommonResponse.Success();
    }

}
