package com.hansybx.clovedoctorbe.service.admin;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.DrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.DrugsExtMapper;
import com.hansybx.clovedoctorbe.mapper.DrugsMapper;
import com.hansybx.clovedoctorbe.model.Drugs;
import com.hansybx.clovedoctorbe.model.DrugsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DrugsService {
    @Resource
    DrugsMapper drugsMapper;

    @Resource
    DrugsExtMapper drugsExtMapper;

    //    public CommonResult (DrugsDTO drugsDTO){
//        return CommonResponse.Success();
//    }
    public Long drugNums() {
        DrugsExample drugsExample = new DrugsExample();
        drugsExample.createCriteria();
        return drugsMapper.countByExample(drugsExample);
    }

    public CommonResult addDrug(DrugsDTO drugsDTO) {
        Drugs drugs = new Drugs();
        drugs.setDrugImg(drugsDTO.getDrugImg());
        drugs.setDrugName(drugsDTO.getDrugName());
        drugs.setPrice(drugsDTO.getPrice());
        drugs.setStatus(drugsDTO.getStatus());
        drugs.setStock(drugsDTO.getStock());
        drugs.setUpdateTime(drugsDTO.getUpdateTime());
        drugs.setSold(0);
        int num = drugsMapper.insert(drugs);
        if (num > 0) {
            return CommonResponse.Success();
        }
        return CommonResponse.Fail("该药品添加失败");
    }

    public CommonResult getDrugs(Integer pageNum, Integer pageSize) {
        String orderBy = "updateTime asc";
        PageHelper.startPage(pageNum, pageSize, orderBy);

        DrugsExample drugsExample = new DrugsExample();
        drugsExample.createCriteria().andStatusEqualTo(1);
        List<Drugs> drugsList = drugsMapper.selectByExample(drugsExample);
        Long totalNum = drugsMapper.countByExample(drugsExample);

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalNum", totalNum);
        map.put("drugList", drugsList);
        return CommonResponse.Success(map);
    }

    public CommonResult changeStatus(Drugs drugsDTO) {
        DrugsExample drugsExample = new DrugsExample();
        drugsExample.createCriteria().andIdEqualTo(drugsDTO.getId());
        int newStatus = -1;
        if (drugsDTO.getStatus() == 1) {
            newStatus = 0;
        } else {
            newStatus = 1;
        }
        drugsDTO.setStatus(newStatus);
        int res = drugsMapper.updateByExample(drugsDTO, drugsExample);
        if (res > 0) {
            return CommonResponse.Success();
        }
        return CommonResponse.Fail("状态改变失败");
    }

    public CommonResult drugDelSingle(DrugsDTO drugsDTO) {
        int res = drugsMapper.deleteByPrimaryKey(drugsDTO.getId());
        if (res > 0) {
            return CommonResponse.Success("药品删除成功");
        }
        return CommonResponse.Fail("药品删除失败");
    }

    public CommonResult drugDelSingle(DrugsDTO[] drugsDTOs) {
        for (DrugsDTO drugsDTO : drugsDTOs) {
            int res = drugsMapper.deleteByPrimaryKey(drugsDTO.getId());
            if (res <= 0) {
                return CommonResponse.Fail("药品删除失败");
            }
        }
        return CommonResponse.Success("药品删除成功");
    }

    public CommonResult drugUpdate(Drugs drugsDTO) {
        DrugsExample drugsExample = new DrugsExample();
        drugsExample.createCriteria().andIdEqualTo(drugsDTO.getId());
        Drugs drugs = drugsMapper.selectByPrimaryKey(drugsDTO.getId());
        drugsDTO.setSold(drugs.getSold());
        if (drugsDTO.getStock() > 0) drugsDTO.setStatus(1);
        int res = drugsMapper.updateByExample(drugsDTO, drugsExample);
        if (res > 0) {
            return CommonResponse.Success();
        }
        return CommonResponse.Fail("更新失败");
    }

    public CommonResult drugSearch(String keyword, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Drugs> drugsList = drugsExtMapper.search(keyword, status);

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalNum", drugNums());
        map.put("drugList", drugsList);
        return CommonResponse.Success(map);
    }

    public CommonResult drugSearch(String keyword) {
        List<Drugs> drugsList = drugsExtMapper.searchUnuse(keyword);
        return CommonResponse.Success(drugsList);
    }
}
