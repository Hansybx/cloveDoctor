package com.hansybx.clovedoctorbe.service.admin;

import com.hansybx.clovedoctorbe.DTO.DrugsDTO;
import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import org.springframework.stereotype.Service;


public interface RecommendService {
    CommonResult addRecommedDrug(DrugsDTO[] recommendDrugsDTOs);
    CommonResult getRecommendDrugs(Integer pageNum, Integer pageSize);
    CommonResult delRecommendDrugs(Integer drugId);
    CommonResult delRecommendDrugsMulti(RecommendDrugsDTO[] recommendDrugsDTOS);
}
