package com.lx.zfxt.service;

import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.StuSimpleInfo;
import com.lx.zfxt.bean.StuTimeTable;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface loginService {
    /**
     * 获得页面的 cookie和 viewstate
     * @param request
     * @param url
     * @return
     * @throws IOException
     */
    LoginData getViewAndCookie(HttpServletRequest request, String url) throws IOException;

    /**
     * 获取验证码
     * @param ImgCookie
     * @param checkCode
     * @param url
     * @return
     * @throws IOException
     */
    byte[] getBufferedImage(String ImgCookie,String checkCode,String url)throws IOException;

    /**
     * 用户登录
     * @param loginData
     * @param url
     * @return
     * @throws IOException
     */
    StuTimeTable login(LoginData loginData, String url) throws IOException;

    /**
     * 用户登录状态刷新
     * @param loginData
     * @param url
     * @return  name
     * @throws IOException
     */
    String refresh(LoginData loginData, String url) throws IOException;
}
