package com.hansybx.clovedoctorbe.service.admin;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.TradeAdminDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.TradeExtMapper;
import com.hansybx.clovedoctorbe.mapper.TradeMapper;
import com.hansybx.clovedoctorbe.model.TradeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminTradeService {
    @Resource
    TradeExtMapper tradeExtMapper;
    @Resource
    TradeMapper tradeMapper;

    public CommonResult getTradeList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TradeAdminDTO> trades = tradeExtMapper.getTradeList();
        TradeExample example = new TradeExample();
        example.createCriteria();
        Long num = tradeMapper.countByExample(example);

        Map<String, Object> map = new HashMap<>();
        map.put("tradeList", trades);
        map.put("total", num);
        return CommonResponse.Success(map);
    }

    public CommonResult tradeSearch(String keyword, Integer pageNum, Integer pageSize) {
        List<TradeAdminDTO> tradeList = tradeExtMapper.searchTradeList(keyword);
        int num = tradeList.size();
        PageHelper.startPage(pageNum, pageSize);
        List<TradeAdminDTO> trades = tradeExtMapper.searchTradeList(keyword);
        Map<String, Object> map = new HashMap<>();
        map.put("tradeList", trades);
        map.put("total", num);
        return CommonResponse.Success(map);
    }
}
