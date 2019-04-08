package com.lx.zfxt.service.Impl;

import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.PersonInfoDetail;
import com.lx.zfxt.constant.Constant;
import com.lx.zfxt.log.HttpClientLog;
import com.lx.zfxt.service.InfoDeatilService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.net.URLEncoder;

@Service
public class InfoDetailServiceImpl implements InfoDeatilService {


    @Override
    public PersonInfoDetail getInfo(LoginData loginData) throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getInfo = new GetMethod(Constant.BASE_URL+"/xsgrxx.aspx" +"?xh="+loginData.getTxtUserName()+"&xm="+ URLEncoder.encode(loginData.getName(),"gb2312")+"&gnmkdm=N121501");


        //每次访问需授权的网址时带上前面的cookie作为通行证
        getInfo.setRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());
        getInfo.setRequestHeader("Referer",Constant.BASE_URL+"/xs_main.aspx"+"?xh="+loginData.getTxtUserName());
        httpClient.executeMethod(getInfo);
       // new HttpClientLog().printResponseLog(getInfo);

        String html_schedule = getInfo.getResponseBodyAsString();
        Document doc = Jsoup.parse(html_schedule);
        Elements studentCode_html = doc.select("span#xh");
        Elements studentName_html = doc.select("span#xm");
        Elements studentSex_html = doc.select("span#lbl_xb");
        Elements studyDate_html = doc.select("span#lbl_rxrq");
        Elements graduateSchoolName_html = doc.select("span#lbl_byzx");
        Elements hometown_html = doc.select("span#lbl_jg");
        Elements graduateSchoolLocation_html = doc.select("span#lbl_lydq");
        Elements graduateSchoolProvince_html = doc.select("span#lbl_lys");
        Elements examCode_html = doc.select("span#lbl_zkzh");
        Elements IdNumber_html = doc.select("span#lbl_sfzh");
        Elements studyLevel_html = doc.select("span#lbl_CC");
        Elements institution_html = doc.select("span#lbl_xy");
        Elements major_html = doc.select("span#lbl_zymc");
        Elements classroom_html = doc.select("span#lbl_xzb");
        Elements studyYear_html = doc.select("span#lbl_dqszj");

        PersonInfoDetail personInfoDetail = new PersonInfoDetail();
        personInfoDetail.setName(studentName_html.text());
        personInfoDetail.setStudentCode(studentCode_html.text());
        personInfoDetail.setSex(studentSex_html.text());
        personInfoDetail.setStudyDate(studyDate_html.text());
        personInfoDetail.setGraduateSchoolName(graduateSchoolName_html.text());
        personInfoDetail.setHometown(hometown_html.text());
        personInfoDetail.setGraduateSchoolLocation(graduateSchoolLocation_html.text());
        personInfoDetail.setGraduateSchoolProvince(graduateSchoolProvince_html.text());
        personInfoDetail.setExamCode(examCode_html.text());
        personInfoDetail.setIdNumber(IdNumber_html.text());
        personInfoDetail.setStudyLevel(studyLevel_html.text());
        personInfoDetail.setInstitution(institution_html.text());
        personInfoDetail.setMajor(major_html.text());
        personInfoDetail.setClassroom(classroom_html.text());
        personInfoDetail.setStudyYear(studyYear_html.text());


        return personInfoDetail;
    }
}
