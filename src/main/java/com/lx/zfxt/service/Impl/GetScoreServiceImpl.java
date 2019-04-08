package com.lx.zfxt.service.Impl;

import com.lx.zfxt.bean.CourseScore;
import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.ScoreTable;
import com.lx.zfxt.constant.Constant;
import com.lx.zfxt.log.HttpClientLog;
import com.lx.zfxt.service.GetScoreService;
import com.lx.zfxt.utils.GetHiddenValue;
import com.lx.zfxt.utils.HtmlTools;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;


@Service
public class GetScoreServiceImpl implements GetScoreService {
    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public  ArrayList<CourseScore> getInfo(LoginData loginData, String xn, String xq, int type) throws IOException {
        String url = Constant.BASE_URL+"/xscj.aspx" +"?xh="+loginData.getTxtUserName()+"&xm="+ URLEncoder.encode(loginData.getName(),"gb2312")+"&gnmkdm=N121605";
        String cookie = "ASP.NET_SessionId="+loginData.getCookie();
        String[] button = {"按学期查询","按学年查询","在校学习成绩查询"};

        String viewState = GetHiddenValue.getViewState(url,cookie,url);

        HttpClient httpClient = new HttpClient();
        PostMethod getScore = new PostMethod(url);
        //每次访问需授权的网址时带上前面的cookie作为通行证
        getScore.setRequestHeader("Cookie", cookie);
        getScore.setRequestHeader("Referer",url);

        NameValuePair[]  nvps = new NameValuePair[6];
        nvps[0] = new NameValuePair("__VIEWSTATE",viewState);
        nvps[1] = new NameValuePair("ddlXN",xn);
        nvps[2] = new NameValuePair("ddlXQ",xq);
        nvps[3] = new NameValuePair("txtQSCJ","0");
        nvps[4] = new NameValuePair("txtZZCJ","100");
        nvps[5] = new NameValuePair("Button1",button[type]);
        getScore.setRequestBody(nvps);

        httpClient.executeMethod(getScore);
       // new HttpClientLog().printResponseLog(getScore);
        String html_schedule = getScore.getResponseBodyAsString();

        ArrayList<CourseScore> arrayList = HtmlTools.getScore(html_schedule);

        return arrayList;
    }
}
