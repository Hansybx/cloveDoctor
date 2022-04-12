package com.hansybx.clovedoctorbe.DTO;

import com.hansybx.clovedoctorbe.model.Trade;


public class TradeAdminDTO extends Trade {
    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}
