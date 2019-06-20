package com.lx.zfxt.controller;

import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.StuSimpleInfo;
import com.lx.zfxt.bean.StuTimeTable;
import com.lx.zfxt.constant.Constant;
import com.lx.zfxt.service.CheckCodeRecognitionService;

import com.lx.zfxt.service.loginService;
import com.lx.zfxt.utils.BizException;
import com.lx.zfxt.utils.JSONModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/zfxt")
public class LoginController extends BaseController {
    @Autowired
    loginService service;

    @Autowired
    private CheckCodeRecognitionService recognitionService;

    @PostMapping("/login")
   public JSONModel login(HttpServletRequest request, HttpServletResponse response){
        LoginData loginData = new LoginData();

            try {
                loginData = service.getViewAndCookie(request, Constant.uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        //获取验证码
        BufferedImage image = null;
        try {
            byte[]  bufferedImage =  service.getBufferedImage(loginData.getCookie(),Constant.checkCode,Constant.uri);
            //将b作为输入流；
            ByteArrayInputStream in = new ByteArrayInputStream(bufferedImage);
            //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
            image = ImageIO.read(in);


        } catch (IOException e) {
            e.printStackTrace();
        }

        String userName = getNotBlankString(request,"username","学号不为空");
        String password = getNotBlankString(request,"password","密码不为空");

        loginData.setTxtUserName(userName);
        loginData.setTextBox2(password);
        if (image == null){
            throw new  BizException("验证码获取错误");
        }
        loginData.setTxtSecretCode(recognitionService.getCheckCode(image));


        StuTimeTable stuTimeTable = new StuTimeTable();
        try {
            stuTimeTable = service.login(loginData,Constant.uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession();
        session.setAttribute(session.getId(), loginData);

        Map<String,Object> result = new HashMap<>();
        result.put("record",stuTimeTable);
        result.put("sessionId",session.getId());

        return JSONModel.buildSuccess("ok",result);
    }

    /***
     * 刷新用户登录状态（session过期重新获取）
     * @param request
     * @param response
     * @return {"name":"","sessionId":""}
     */
    @PostMapping("/refresh")
    public JSONModel refresh(HttpServletRequest request, HttpServletResponse response){
        LoginData loginData = new LoginData();

        try {
            loginData = service.getViewAndCookie(request, Constant.uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取验证码
        BufferedImage image = null;
        try {
            byte[]  bufferedImage =  service.getBufferedImage(loginData.getCookie(),Constant.checkCode,Constant.uri);
            //将b作为输入流；
            ByteArrayInputStream in = new ByteArrayInputStream(bufferedImage);
            //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
            image = ImageIO.read(in);


        } catch (IOException e) {
            e.printStackTrace();
        }

        String userName = getNotBlankString(request,"username","学号不为空");
        String password = getNotBlankString(request,"password","密码不为空");

        loginData.setTxtUserName(userName);
        loginData.setTextBox2(password);
        if (image == null){
            throw new  BizException("验证码获取错误");
        }
        loginData.setTxtSecretCode(recognitionService.getCheckCode(image));


        String name = null;
        try {
            name = service.refresh(loginData,Constant.uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession();
        session.setAttribute(session.getId(), loginData);

        Map<String,Object> result = new HashMap<>();
        result.put("name",name);
        result.put("sessionId",session.getId());

        return JSONModel.buildSuccess("ok",result);
    }


}
