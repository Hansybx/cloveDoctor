package com.hansybx.clovedoctorbe.common;

public class CommonResponse {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    // 只返回状态
    public static CommonResult Success() {
        return new CommonResult().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    //正常返回结果
    public static CommonResult Success(Object data) {
        return new CommonResult().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
    }
    // 没有数据但是请求正常
    public static CommonResult Success(String msg, Object data) {
        return new CommonResult().setCode(ResultCode.SUCCESS).setMessage(msg).setData(data);
    }

    // 请求失败
    public static CommonResult Fail(String msg) {
        return new CommonResult().setCode(ResultCode.FAIL).setMessage(msg);
    }

}
