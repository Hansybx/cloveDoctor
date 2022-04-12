package com.hansybx.clovedoctorbe.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.DrugTradeDTO;
import com.hansybx.clovedoctorbe.DTO.DrugTradeListDTO;
import com.hansybx.clovedoctorbe.DTO.TradeItemDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.DrugsMapper;
import com.hansybx.clovedoctorbe.mapper.TradeMapper;
import com.hansybx.clovedoctorbe.model.Drugs;
import com.hansybx.clovedoctorbe.model.DrugsExample;
import com.hansybx.clovedoctorbe.model.Trade;
import com.hansybx.clovedoctorbe.model.TradeExample;
import com.hansybx.clovedoctorbe.service.user.TradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TradeServiceImpl implements TradeService {
    private static final String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCMj/Q723nOi4nrOepH4OtKQvAb1swp5KjSC9nobQksWJmo2rtFXsgY+I1x3eXUbST/lKyH7ArUQVS4d+T2wby5k6kvh2+sXYQG7WA/jX7UqKuh+XuFa+WkyxM4/m2WyMGSP97BETP55lYkyITf+IkaxQV6hMWCZn+svXPN3qavT9y22CRPNAdw5vi1xBLcSuOixkxt5ue9JANVDr3Yo3XH6xEdhy8Y6zycKlQT3gX15Bp1ZDNC2jejSovWqkaj1jNzS1UdMImtrof+UJ5wpAYN5G791Fwiv9Gk5g6eDOJ8vtJmCj1zm2O9GTewbfqL5/2gdTHKvLIT0mfyzPCLQ4rAgMBAAECggEAcc4GplWDlejvS1MCNH0NlqEYWp978G2p6fxBI67fTmuXfew+nPp34/orU6MF0EllNS/LjVPRpCtwIeC6k/2Sd2QFPk/IpFVuw+nUn+qVuRekiDbWL2TZVR9US+nC7zl+xhGs8BTtKWZiTmBeGvlf7UGEDxvKjyrZZx97aiQukW63XjzQgiaL24OOpsT2ClBNurrZCAapFWu+UreJGRMuCkUbkeMFRZBS/ASKDc2rwzbUx+e59Bs2syBVgFk8J9Eat+MhNCdcxc1M9l5VauamnzU0ppQQfGciZZQG+MpxFcJu/zrR8k9agehw36aiAYFjsMFTUWGZ5SV6wlyRt/JXUQKBgQDC0YyQ/HSreE7tzDN/fjyvCNn5m1eEfnqcndiTUW+A1KbRdABW76v7ncqNTTQp/1F++9OvsW8B0L3PpdCKoo2TVKCVvuIDzNyD/W3y3920oOBASrQ0sUWwqE2Fc6+wTeCW2V5vS8kuHkV+pvHIw3KXN43D6T76UlDLqOEilRkWRQKBgQCrFWOEFv05PQEpIY2xEdFJpQ0nsJ4YMvJcDFRts/Zcl7UKlNWou2FhNl7QU7B8E2rssV4rc5+SYch8nQLkBWKnFwfs1+WOKcI/DmAJNkHWX1OVNVdZ6gDOQMsZKcms0gnF6dltGXnDiE0S2h8YnL6Q6zClClZrtUQpQSl87z9RrwKBgFgoKsuQ8dqF3nGM7eDLZvptrvVcbgXuld05MD+5ohNSJwqwy1aLre92P6ohPXGjr2Ue33Opfun7Y2kLR4r0c8wvh1s7vVqq4T5497/nvZN3GQL+yvztGvQNtMamyuGk2/Rm+Gy0hpzv5dKiCNcoWjpDUUtNyMnNZfJCwV28ylVhAoGBAItcY/EwbNuYVv6zR2Fq2yn7WgjvtQiPT58tqASo6NEp/x58eML2yG9Qw9E9qvCXPK1MGiKZD+5E2vSvTbCCtobXmuwSPfsdBw8oXZCDcwP2KPbNObeTh057tzVrNc4iVjWytRtK+kYOHIJzzYrV++G+w6RdFGKuiLR2UR5lIRYfAoGAFKviOpK72fVz8+5aeYti8V6YbZxiIaYlciJjrIt5Uv7n6X9gkRsVj+qOQBC82etxleDtGRT0LiLvt9kG9uS9bOaF6kJoormbAixMDJIpXLlaOaWBJLDhJ/dOushmhyg7ZzxSFKiSGVze7zcMC5zBX+3uYRTPZcjx20KCvuibPi8=";
    private static final String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgjI/0O9t5zouJ6znqR+DrSkLwG9bMKeSo0gvZ6G0JLFiZqNq7RV7IGPiNcd3l1G0k/5Ssh+wK1EFUuHfk9sG8uZOpL4dvrF2EBu1gP41+1Kirofl7hWvlpMsTOP5tlsjBkj/ewREz+eZWJMiE3/iJGsUFeoTFgmZ/rL1zzd6mr0/cttgkTzQHcOb4tcQS3ErjosZMbebnvSQDVQ692KN1x+sRHYcvGOs8nCpUE94F9eQadWQzQto3o0qL1qpGo9Yzc0tVHTCJra6H/lCecKQGDeRu/dRcIr/RpOYOngzifL7SZgo9c5tjvRk3sG36i+f9oHUxyryyE9Jn8szwi0OKwIDAQAB";
    private static final String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    private static final String APPID = "2021000119657043";
    private static final int EXIST = 1;
    private static final int DELETED = 0;

    @Resource
    TradeMapper tradeMapper;

    @Resource
    DrugsMapper drugsMapper;

    private AlipayConfig alipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(serverUrl);
        alipayConfig.setAppId(APPID);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }

    @Override
    public CommonResult Trade(DrugTradeDTO drugTradeDTO) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(drugTradeDTO.getOutTradeNo());
        model.setTotalAmount(drugTradeDTO.getTotalAmount().toString());
        model.setSubject(drugTradeDTO.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl("");
        request.setReturnUrl(drugTradeDTO.getReturnUrl());
        request.setBizModel(model);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            addToTrade(drugTradeDTO);
            drugStockChange(drugTradeDTO.getDrugList());
            return CommonResponse.Success(response);
        }

        return CommonResponse.Fail("预创建失败");
    }

    @Override
    public CommonResult getTradeList(Integer userId, Integer pageNum, Integer pageSize) {
        String orderBy = "tradeDate desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);

        TradeExample example = new TradeExample();
        example.createCriteria().andUserIdEqualTo(userId).andTradeStatusEqualTo(EXIST);
        List<Trade> trades = tradeMapper.selectByExample(example);
        Long num = tradeMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("tradeList", trades);
        map.put("total", num);
        return CommonResponse.Success(map);
    }

    @Override
    public CommonResult delTrade(TradeItemDTO tradeItemDTO) {
        TradeExample example = new TradeExample();
        example.createCriteria()
                .andIdEqualTo(tradeItemDTO.getId())
                .andUserIdEqualTo(tradeItemDTO.getUserId());

        tradeItemDTO.setTradeStatus(DELETED);
        int res = tradeMapper.updateByExampleSelective(tradeItemDTO, example);
        if(res >0) return CommonResponse.Success("删除成功","");
        return CommonResponse.Fail("删除失败");
    }

    public void addToTrade(DrugTradeDTO drugTradeDTO) {
        Trade trade = new Trade();
        trade.setUserId(drugTradeDTO.getUserId());
        trade.setTradeNo(drugTradeDTO.getOutTradeNo());
        trade.setTotalAmout(drugTradeDTO.getTotalAmount());
        trade.setTradeStatus(EXIST);   // 设置为0是逻辑删除
        trade.setTradeInfo(JSON.toJSONString(drugTradeDTO.getDrugList()));
        trade.setTradeDate(drugTradeDTO.getTradeDate());
        tradeMapper.insert(trade);
    }

    public void drugStockChange(DrugTradeListDTO[] drugList) {
        for (DrugTradeListDTO drugItem : drugList) {
            DrugsExample drugsExample = new DrugsExample();
            drugsExample.createCriteria()
                    .andIdEqualTo(drugItem.getDrugId())
                    .andDrugNameEqualTo(drugItem.getDrugName());
            Drugs drug = drugsMapper.selectByPrimaryKey(drugItem.getDrugId());
            int curNum = drug.getStock() - drugItem.getDrugNum();
            drug.setStock(curNum);
            if (curNum == 0) drug.setStatus(0);
            drugsMapper.updateByExample(drug, drugsExample);
        }
    }
}
