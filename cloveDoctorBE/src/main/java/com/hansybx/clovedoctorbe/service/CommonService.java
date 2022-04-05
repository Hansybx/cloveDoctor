package com.hansybx.clovedoctorbe.service;

import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.RecommendDrugsExtMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonService {
    @Resource
    RecommendDrugsExtMapper recommendDrugsExtMapper;

    public CommonResult getHomeRecommend() {
        // 只要前八个或更少
        Integer pageNum = 0;
        Integer pageSize = 8;
        List<RecommendDrugsDTO> drugsDTOList = recommendDrugsExtMapper.selectForHome(pageNum, pageSize);
        return CommonResponse.Success(drugsDTOList);
    }
}
