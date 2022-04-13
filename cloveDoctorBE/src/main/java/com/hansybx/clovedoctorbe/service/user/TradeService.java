package com.hansybx.clovedoctorbe.service.user;

import com.alipay.api.AlipayApiException;
import com.hansybx.clovedoctorbe.DTO.DrugTradeDTO;
import com.hansybx.clovedoctorbe.DTO.TradeItemDTO;
import com.hansybx.clovedoctorbe.Exception.StockNotFullException;
import com.hansybx.clovedoctorbe.common.CommonResult;

// 结算相关
public interface TradeService {
    CommonResult Trade(DrugTradeDTO drugTradeDTO) throws AlipayApiException, StockNotFullException;

    CommonResult getTradeList(Integer userId, Integer pageNum, Integer pageSize);

    CommonResult delTrade(TradeItemDTO tradeItemDTO);
}
