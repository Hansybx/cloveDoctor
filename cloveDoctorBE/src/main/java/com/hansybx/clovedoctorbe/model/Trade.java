package com.hansybx.clovedoctorbe.model;

import java.math.BigDecimal;

public class Trade {
    private Integer id;

    private Integer userId;

    private BigDecimal totalAmout;

    private String tradeNo;

    private String tradeStatus;

    private String tradeInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(BigDecimal totalAmout) {
        this.totalAmout = totalAmout;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }

    public String getTradeInfo() {
        return tradeInfo;
    }

    public void setTradeInfo(String tradeInfo) {
        this.tradeInfo = tradeInfo == null ? null : tradeInfo.trim();
    }
}