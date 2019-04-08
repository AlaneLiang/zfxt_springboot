package com.lx.zfxt.service.Impl;

import com.lx.zfxt.bean.Course;
import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.constant.Constant;
import com.lx.zfxt.log.HttpClientLog;
import com.lx.zfxt.service.ClassService;
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
public class ClassServiceImpl implements ClassService {

    public Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public ArrayList<Course> getSchedule(LoginData loginData, String xnd, String xqd) throws IOException {
        String url = Constant.BASE_URL+"/xskbcx.aspx" +"?xh="+loginData.getTxtUserName()+"&xm="+URLEncoder.encode(loginData.getName(),"gb2312")+"&gnmkdm=N121603";
        String cookie = "ASP.NET_SessionId="+loginData.getCookie();
        String referer = Constant.BASE_URL+"/xs_main.aspx"+"?xh="+loginData.getTxtUserName();

        String viewState = GetHiddenValue.getViewState(url,cookie,referer);

        HttpClient httpClient = new HttpClient();
        PostMethod getSchedule = new PostMethod(url);
        //每次访问需授权的网址时带上前面的cookie作为通行证
        getSchedule.setRequestHeader("Cookie", cookie);
        getSchedule.setRequestHeader("Referer",referer);

        NameValuePair[]  nvps = new NameValuePair[5];
        nvps[0] = new NameValuePair("__EVENTTARGET","xnd");
        nvps[1] = new NameValuePair("__EVENTARGUMENT","");
        nvps[2] = new NameValuePair("__VIEWSTATE",viewState);
        nvps[3] = new NameValuePair("xnd",xnd);
        nvps[4] = new NameValuePair("xqd",xqd);
        getSchedule.setRequestBody(nvps);

        httpClient.executeMethod(getSchedule);
      //  new HttpClientLog().printResponseLog(getSchedule);
        String html_schedule = getSchedule.getResponseBodyAsString();

        ArrayList<Course> list = HtmlTools.getCourseList(html_schedule);

        return list;




    }
}
