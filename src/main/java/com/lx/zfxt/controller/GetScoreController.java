package com.lx.zfxt.controller;

import com.lx.zfxt.bean.CourseScore;
import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.ScoreTable;
import com.lx.zfxt.service.GetScoreService;
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
public class GetScoreController extends BaseController {

    @Autowired
    private GetScoreService getScoreService;

    @GetMapping("/score")
    public JSONModel getScore (HttpServletRequest request) throws IOException {
        String xn = null;//学年
        String xq = null;//学期
        int type = 2;//具体定义方式见对应serviceImpl

        xn = getString(request,"xn");
        xq = getString(request,"xq");
        try {
            type = getInt(request,"type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sessionId = request.getHeader("sessionId");
        LoginData loginData = (LoginData) request.getSession().getAttribute(sessionId);
        ArrayList<CourseScore> result = getScoreService.getInfo(loginData,xn,xq,type);

        return JSONModel.buildSuccess("ok",result);
    }
}
