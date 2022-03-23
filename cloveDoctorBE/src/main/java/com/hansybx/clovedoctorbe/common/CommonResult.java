package com.hansybx.clovedoctorbe.common;

import lombok.Data;

@Data
public class CommonResult {
    private Integer code;
    private String message;
    private Object data;

    public CommonResult setCode(ResultCode resultCode){
        this.code = resultCode.code;
        return this;
    }
    public CommonResult setMessage(String message){
        this.message = message;
        return this;
    }

    public CommonResult setData(Object data){
        this.data = data;
        return this;
    }

}