package com.hansybx.clovedoctorbe.service;

import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.DrugsMapper;
import com.hansybx.clovedoctorbe.mapper.RecommendDrugsExtMapper;
import com.hansybx.clovedoctorbe.model.Drugs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonService {
    @Resource
    RecommendDrugsExtMapper recommendDrugsExtMapper;

    @Resource
    DrugsMapper drugsMapper;

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
}
