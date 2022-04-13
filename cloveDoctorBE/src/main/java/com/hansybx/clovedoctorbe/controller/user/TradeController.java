package com.hansybx.clovedoctorbe.controller.user;

import com.alipay.api.AlipayApiException;
import com.hansybx.clovedoctorbe.DTO.DrugTradeDTO;
import com.hansybx.clovedoctorbe.DTO.TradeItemDTO;
import com.hansybx.clovedoctorbe.Exception.StockNotFullException;
import com.hansybx.clovedoctorbe.common.CommonResponse;
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
    public CommonResult trade(@RequestBody DrugTradeDTO drugTradeDTO) throws AlipayApiException, StockNotFullException {
        try{
            return tradeService.Trade(drugTradeDTO);
        }catch (StockNotFullException e){
            return CommonResponse.Fail(e.getMessage());
        }
    }

    @GetMapping("/trade")
    public CommonResult getTradeList(@RequestParam Integer userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return tradeService.getTradeList(userId, pageNum, pageSize);
    }

    @PostMapping("/trade/del")
    public CommonResult tradeDel(@RequestBody TradeItemDTO tradeItemDTO) {
        return tradeService.delTrade(tradeItemDTO);
    }
}
