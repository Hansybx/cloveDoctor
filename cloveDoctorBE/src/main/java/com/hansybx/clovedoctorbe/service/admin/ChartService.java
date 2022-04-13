package com.hansybx.clovedoctorbe.service.admin;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.DrugsMapper;
import com.hansybx.clovedoctorbe.mapper.UserMapper;
import com.hansybx.clovedoctorbe.model.Drugs;
import com.hansybx.clovedoctorbe.model.DrugsExample;
import com.hansybx.clovedoctorbe.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {
    @Resource
    DrugsMapper drugsMapper;

    @Resource
    UserMapper userMapper;

    public CommonResult getChartData() {
        List<Drugs> drugList = getDrugData();
        Map<String, Long> map = getUserData();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("drugData", drugList);
        resMap.put("userData", map);
        return CommonResponse.Success(resMap);
    }


    private List<Drugs> getDrugData() {
        int top5 = 5;
        String orderBy = "sold DESC";
        PageHelper.startPage(1, top5, orderBy);
        DrugsExample example = new DrugsExample();
        return drugsMapper.selectByExample(example);
    }

    private Map<String, Long> getUserData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String str = sdf.format(d);
        UserExample example = new UserExample();
        Long total = userMapper.countByExample(example);
        example.createCriteria().andRegisterDateLike('%' + str + '%');
        Long today = userMapper.countByExample(example);
        Map<String, Long> map = new HashMap<>();
        map.put("today", today);
        map.put("before", total - today);
        return map;
    }
}
