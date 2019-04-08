package com.lx.zfxt.utils;

public class BizException extends RuntimeException {

    public String code = "999999";
    public static String UNKNOWN_ERROR = "未知错误，请稍后刷新重试";

    public BizException() {
        super(UNKNOWN_ERROR);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static BizException buildNeedLogin() {
        return new BizException("100001", "需要登录");
    }
}
