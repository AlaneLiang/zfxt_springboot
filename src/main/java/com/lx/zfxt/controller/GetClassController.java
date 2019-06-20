package com.lx.zfxt.controller;

import com.lx.zfxt.bean.Course;
import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.service.ClassService;
import com.lx.zfxt.utils.JSONModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;


@RestController
@RequestMapping("/zfxt")
public class GetClassController extends BaseController {

    @Autowired
    ClassService classService;

    @GetMapping("/class")
    public JSONModel getClass (HttpServletRequest request) throws IOException {
        String xnd = getString(request,"xnd");//传入学年 2016-2017
        String xqd = getString(request,"xqd");//传入学期  （1或2）
        String sessionId = request.getHeader("sessionId");
        LoginData loginData = (LoginData) request.getSession().getAttribute(sessionId);
        ArrayList<Course> result = classService.getSchedule(loginData,xnd,xqd);

         return JSONModel.buildSuccess("ok",result);
    }
}
