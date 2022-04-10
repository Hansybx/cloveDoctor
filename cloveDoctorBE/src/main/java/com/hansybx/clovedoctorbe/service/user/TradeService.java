package com.hansybx.clovedoctorbe.service.user;

import com.alipay.api.AlipayApiException;
import com.hansybx.clovedoctorbe.DTO.DrugTradeDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;

// 结算相关
public interface TradeService {
    CommonResult Trade(DrugTradeDTO drugTradeDTO) throws AlipayApiException;
}
