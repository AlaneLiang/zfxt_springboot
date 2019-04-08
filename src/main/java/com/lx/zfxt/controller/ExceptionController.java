package com.lx.zfxt.controller;

import com.lx.zfxt.utils.BizException;
import com.lx.zfxt.utils.JSONModel;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController extends  BaseController {

    /**
     * 全局错误处理
     */
    @ExceptionHandler
    public JSONModel<String> error(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof BizException) {
            BizException be = (BizException) e;
            return JSONModel.buildFailure(be.code, be.getMessage());
        }
        return JSONModel.buildFailure("服务器忙，请稍后刷新重试");
    }
}
