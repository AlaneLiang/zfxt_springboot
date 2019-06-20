package com.lx.zfxt.utils;

/**
 * json工具类
 * @param <T>
 */
public class JSONModel<T> {

    private String code = "000000";

    private String msg;

    private T result;

    public static JSONModel buildSuccess(String msg) {
        JSONModel<String> json = new JSONModel<>();
        json.setMsg(msg);
        json.setResult("");
        return json;
    }

    public static <T> JSONModel buildSuccess(String msg, T t) {
        JSONModel<T> json = new JSONModel<>();
        json.setMsg(msg);
        json.setResult(t);
        return json;
    }
    public static <T> JSONModel buildFailure(String msg) {
        JSONModel<String> json = new JSONModel<>();
        json.setCode("999999");
        json.setMsg(msg);
        json.setResult("");
        return json;
    }
    public static <T> JSONModel buildFailure(String code,String msg) {
        JSONModel<String> json = new JSONModel<>();
        json.setCode(code);
        json.setMsg(msg);
        json.setResult("");
        return json;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JSONModel{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
