package com.hansybx.clovedoctorbe.service;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.DrugsExtMapper;
import com.hansybx.clovedoctorbe.mapper.DrugsMapper;
import com.hansybx.clovedoctorbe.mapper.RecommendDrugsExtMapper;
import com.hansybx.clovedoctorbe.mapper.SwiperMapper;
import com.hansybx.clovedoctorbe.model.Drugs;
import com.hansybx.clovedoctorbe.model.DrugsExample;
import com.hansybx.clovedoctorbe.model.Swiper;
import com.hansybx.clovedoctorbe.model.SwiperExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class CommonService {
    @Resource
    RecommendDrugsExtMapper recommendDrugsExtMapper;

    @Resource
    DrugsMapper drugsMapper;
    @Resource
    DrugsExtMapper drugsExtMapper;

    @Resource
    SwiperMapper swiperMapper;

    public CommonResult getHomeRecommend() {
        // 只要前八个或更少
        Integer pageNum = 0;
        Integer pageSize = 8;
        List<RecommendDrugsDTO> drugsDTOList = recommendDrugsExtMapper.selectForHome(pageNum, pageSize);
        return CommonResponse.Success(drugsDTOList);
    }

    public CommonResult getDrugInfo(Integer drugId) {
        Drugs drugs = drugsMapper.selectByPrimaryKey(drugId);
        return CommonResponse.Success(drugs);
    }

    public CommonResult getSwiper() {
        String orderBy = "sortId desc";
        PageHelper.startPage(1, 5, orderBy);

        SwiperExample swiperExample = new SwiperExample();
        swiperExample.createCriteria();
        List<Swiper> swiperList = swiperMapper.selectByExample(swiperExample);
        return CommonResponse.Success(swiperList);
    }

    public Long drugNums(){
        DrugsExample drugsExample = new DrugsExample();
        drugsExample.createCriteria();
        return drugsMapper.countByExample(drugsExample);
    }

    public CommonResult searchName(String keyword, Integer status, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Drugs> drugsList = drugsExtMapper.searchName(keyword, status);

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalNum", drugNums());
        map.put("drugList", drugsList);
        return CommonResponse.Success(map);
    }
}
