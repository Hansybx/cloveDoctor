package com.hansybx.clovedoctorbe.controller.user;

import com.alipay.api.AlipayApiException;
import com.hansybx.clovedoctorbe.DTO.DrugTradeDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.user.TradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class TradeController {
    @Resource
    TradeService tradeService;

    @PostMapping("/trade")
    public CommonResult trade(@RequestBody DrugTradeDTO drugTradeDTO) throws AlipayApiException {
        return tradeService.Trade(drugTradeDTO);
    }
}
