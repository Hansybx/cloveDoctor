package com.hansybx.clovedoctorbe.service.admin.impl;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.DrugsDTO;
import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.RecommendDrugsExtMapper;
import com.hansybx.clovedoctorbe.mapper.RecommendDrugsMapper;
import com.hansybx.clovedoctorbe.model.RecommendDrugs;
import com.hansybx.clovedoctorbe.model.RecommendDrugsExample;
import com.hansybx.clovedoctorbe.service.admin.RecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Resource
    RecommendDrugsExtMapper recommendDrugsExtMapper;
    @Resource
    RecommendDrugsMapper recommendDrugsMapper;

    @Override
    public CommonResult addRecommedDrug(DrugsDTO[] recommendDrugsDTOs) {
        for(DrugsDTO recommendDrugsDTO:recommendDrugsDTOs){
            RecommendDrugs drugs = new RecommendDrugs();
            drugs.setDrugId(recommendDrugsDTO.getId());
            drugs.setAddDate(new Date());
            RecommendDrugsDTO example = recommendDrugsExtMapper.selectRecommondDrugByDrugId(recommendDrugsDTO.getId());
            if (example != null) {
                return CommonResponse.Fail("该药品已添加");
            }
            recommendDrugsMapper.insert(drugs);
        }
        return CommonResponse.Success();
    }

    @Override
    public CommonResult getRecommendDrugs(Integer pageNum, Integer pageSize) {
        List<RecommendDrugsDTO> recommendDrugsDTOS = recommendDrugsExtMapper.selectAll(pageNum-1, pageSize);
        if (recommendDrugsDTOS.size() > 0) {
            Map<String,Object> map = new HashMap<>();
            map.put("recommendList",recommendDrugsDTOS);
            map.put("totalNum",recommendDrugsMapper.countByExample(new RecommendDrugsExample()));
            return CommonResponse.Success(map);
        } else {
            return CommonResponse.Success("没有推荐药品", null);
        }
    }

    @Override
    public CommonResult delRecommendDrugs(Integer drugId) {
        int res = recommendDrugsExtMapper.deleteItem(drugId);
        if (res > 0) return CommonResponse.Success();
        return CommonResponse.Fail("该药品删除失败");
    }

    @Override
    public CommonResult delRecommendDrugsMulti(RecommendDrugsDTO[] recommendDrugsDTOS) {
        for (RecommendDrugsDTO recommendDrugsDTO : recommendDrugsDTOS) {
            int res = recommendDrugsExtMapper.deleteItem(recommendDrugsDTO.getDrugId());
            if (res <= 0) return CommonResponse.Fail("部分药品删除失败");
        }
        return CommonResponse.Success();
    }
}
