package com.lx.zfxt.controller;

import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.PersonInfoDetail;
import com.lx.zfxt.service.InfoDeatilService;
import com.lx.zfxt.utils.JSONModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/zfxt")
public class InfoDetailController extends BaseController {

    @Autowired
    private InfoDeatilService infoDeatilService;

    @GetMapping("/info")
    public JSONModel info (HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("sessionId");
        LoginData loginData = (LoginData) request.getSession().getAttribute(sessionId);
        PersonInfoDetail infoDetail = infoDeatilService.getInfo(loginData);

        return JSONModel.buildSuccess("ok",infoDetail);
    }
}
