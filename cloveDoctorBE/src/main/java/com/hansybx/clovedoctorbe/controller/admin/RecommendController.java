package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.DTO.DrugsDTO;
import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.DTO.SwiperDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.admin.RecommendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/recommend")
public class RecommendController {
    @Resource
    RecommendService recommendService;

    @GetMapping("")
    public CommonResult getRecommendDrugs(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return recommendService.getRecommendDrugs(pageNum, pageSize);
    }

    @PostMapping("/add")
    public CommonResult addRecommendDrug(@RequestBody DrugsDTO[] recommendDrugsDTOs){
        return recommendService.addRecommedDrug(recommendDrugsDTOs);
    }

    @PostMapping("/delete")
    public CommonResult delRecommend(@RequestBody RecommendDrugsDTO recommendDrugsDTO){
        return recommendService.delRecommendDrugs(recommendDrugsDTO.getDrugId());
    }

    @PostMapping("/delete/multi")
    public CommonResult delRecommend(@RequestBody RecommendDrugsDTO[] recommendDrugsDTOS){
        return recommendService.delRecommendDrugsMulti(recommendDrugsDTOS);
    }
}
