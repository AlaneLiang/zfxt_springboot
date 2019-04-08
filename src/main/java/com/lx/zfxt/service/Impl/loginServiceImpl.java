package com.lx.zfxt.service.Impl;

import com.lx.zfxt.bean.Course;
import com.lx.zfxt.bean.LoginData;

import com.lx.zfxt.bean.StuSimpleInfo;
import com.lx.zfxt.bean.StuTimeTable;
import com.lx.zfxt.constant.Constant;
import com.lx.zfxt.log.HttpClientLog;
import com.lx.zfxt.service.loginService;
import com.lx.zfxt.utils.BizException;
import com.lx.zfxt.utils.HtmlTools;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

@Service
public class loginServiceImpl implements loginService {

    private  HttpClientLog log = new HttpClientLog();

    /***
     * 获取页面的cookie 和 viewstate
     * @param request
     * @param url
     * @return LoginData
     * @throws IOException
     */
    @Override
    public LoginData getViewAndCookie(HttpServletRequest request,String url) throws  IOException {
        LoginData loginData = new LoginData();


        String NET_SessionId = null;

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);

        getMethod.addRequestHeader("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
        getMethod.addRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; GWX:QUALIFIED; Shuame)");
        getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
        getMethod.addRequestHeader("Host", "210.45.175.14");
        getMethod.addRequestHeader("DNT", "1");
        getMethod.addRequestHeader("Connection", "Keep-Alive");
        getMethod.addRequestHeader("Pragma", "no-cache");
        log.printRequestHeadersLog(getMethod);

        httpClient.executeMethod(getMethod);

        log.printResponseLog(getMethod);
        String content;
        BufferedReader bufr = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(),"utf-8"));
        while((content=bufr.readLine())!=null)
        {
            if(content.contains("__VIEWSTATE"))
            {
                //value="dDwyODE2NTM0OTg7Oz7I2Cct+u86RN/NdUhLZSrpUl6ZsQ=="
                String result = content.substring(content.indexOf("value=\"")+7,
                        content.lastIndexOf("\""));
                content = result;
                loginData.setViewState(result);
                break;
            }
        }

        NET_SessionId = getMethod.getResponseHeader("Set-Cookie").getValue();
        NET_SessionId = NET_SessionId.substring(NET_SessionId.indexOf("=")+1,NET_SessionId.indexOf(";"));

        loginData.setCookie(NET_SessionId);
        System.out.println("获取到请求参数为："+NET_SessionId);

        request.getSession().setAttribute("loginData", loginData);

        return loginData;

    }

    @Override
    public byte[] getBufferedImage(String ImgCookie, String checkCode, String url) throws IOException {
//        System.out.println("============================================");
//        System.out.println("收到请求参数为：" + ImgCookie);
//        System.out.println("================准备用此参数去获取验证码图片===================");

        HttpClient httpClient = new HttpClient();
        GetMethod getMethodImg = new GetMethod(checkCode);
        getMethodImg.addRequestHeader("Referer", url);
        getMethodImg.addRequestHeader("Cookie", "ASP.NET_SessionId=" + ImgCookie);

        //  new HttpClientLog().printRequestHeadersLog(getMethodImg);

        httpClient.executeMethod(getMethodImg);
        byte[] img = getMethodImg.getResponseBody();
        return img;
    }

    /**
     * 登录后返回用户信息和课表
     * @param loginData
     * @param url
     * @return  StuTimeTable
     * @throws IOException
     */
    @Override
    public StuTimeTable login(LoginData loginData,String url) throws IOException{
       String returnLocal = null;
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);

        NameValuePair[]  nvps = new NameValuePair[9];

        nvps[0] = new NameValuePair("__VIEWSTATE",loginData.getViewState());
        nvps[1] = new NameValuePair("Button1","");
        nvps[2] = new NameValuePair("hidPdrs","");
        nvps[3] = new NameValuePair("hidsc","");
        nvps[4] = new NameValuePair("lbLanguage","");
        nvps[5] = new NameValuePair("RadioButtonList1","学生");
        nvps[6] = new NameValuePair("TextBox2",loginData.getTextBox2());
        nvps[7] = new NameValuePair("txtSecretCode",loginData.getTxtSecretCode());
        nvps[8] = new NameValuePair("txtUserName",loginData.getTxtUserName());

        post.setRequestBody(nvps);
        System.out.println("Cookie:"+loginData.getCookie());
        post.addRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());

      //  new HttpClientLog().printRequestHeadersLog(post);

        httpClient.executeMethod(post);

        // new HttpClientLog().printResponseHeadersLog(post);

        BufferedReader bufr = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
        String line = null;
        while((line=bufr.readLine())!=null)
        {
            if(line.contains("验证码不正确！"))
            {
                throw new BizException("对不起，您输入的验证码有误哦");

            }
        }
        System.out.println(post.getResponseBodyAsString());
        //    /xs_main.aspx?xh=13101010115
        Header header = post.getResponseHeader("Location");
        if(header==null)
        {
            throw new BizException("登录失败!!");

        }
        returnLocal=header.getValue();
        if(!returnLocal.contains("xs_main.aspx"))
        {
            System.out.println("不正确的返回===》Location:  "+returnLocal);
            String errorInfo = post.getResponseBodyAsString();
            post.releaseConnection();
            throw new BizException(errorInfo);
        }
        System.out.println("Location: "+returnLocal);
        post.releaseConnection();


        GetMethod get = new GetMethod(Constant.BASE_URL+returnLocal);
        get.addRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());

        //new HttpClientLog().printRequestHeadersLog(get);

        httpClient.executeMethod(get);
        String html = get.getResponseBodyAsString();
        //jsoup 处理html标签
        Document docMain = Jsoup.parse(html);
        Elements nameHtml = docMain.select("span#xhxm");
        String  name = nameHtml.text().replace("同学","");

        loginData.setName(name);

        PostMethod postMethod3 = new PostMethod(Constant.BASE_URL+"/xskbcx.aspx" +"?xh="+loginData.getTxtUserName()+"&xm="+URLEncoder.encode(loginData.getName(),"gb2312")+"&gnmkdm=N121603");


        //每次访问需授权的网址时带上前面的cookie作为通行证
        postMethod3.setRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());
        postMethod3.setRequestHeader("Referer",Constant.BASE_URL+"/xs_main.aspx"+"?xh="+loginData.getTxtUserName());

        httpClient.executeMethod(postMethod3);

       // new HttpClientLog().printResponseLog(postMethod3);

        String html_schedule = postMethod3.getResponseBodyAsString();
        StuSimpleInfo stuSimpleInfo = HtmlTools.getCourseStuInfo(html_schedule);
        ArrayList<Course> courses = HtmlTools.getCourseList(html_schedule);

        StuTimeTable stuTimeTable = new StuTimeTable();
        stuTimeTable.setInfo(stuSimpleInfo);
        stuTimeTable.setCourseArrayList(courses);

        return stuTimeTable;
    }

    /***
     * 用户登录状态刷新（session过期后调用）
     * @param loginData
     * @param url
     * @return  String name
     * @throws IOException
     */
    @Override
    public String refresh(LoginData loginData, String url) throws IOException {

        String returnLocal = null;
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);

        NameValuePair[]  nvps = new NameValuePair[9];

        nvps[0] = new NameValuePair("__VIEWSTATE",loginData.getViewState());
        nvps[1] = new NameValuePair("Button1","");
        nvps[2] = new NameValuePair("hidPdrs","");
        nvps[3] = new NameValuePair("hidsc","");
        nvps[4] = new NameValuePair("lbLanguage","");
        nvps[5] = new NameValuePair("RadioButtonList1","学生");
        nvps[6] = new NameValuePair("TextBox2",loginData.getTextBox2());
        nvps[7] = new NameValuePair("txtSecretCode",loginData.getTxtSecretCode());
        nvps[8] = new NameValuePair("txtUserName",loginData.getTxtUserName());

        post.setRequestBody(nvps);
        System.out.println("Cookie:"+loginData.getCookie());
        post.addRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());

        //  new HttpClientLog().printRequestHeadersLog(post);

        httpClient.executeMethod(post);

        // new HttpClientLog().printResponseHeadersLog(post);

        BufferedReader bufr = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
        String line = null;
        while((line=bufr.readLine())!=null)
        {
            if(line.contains("验证码不正确！"))
            {
                throw new BizException("对不起，您输入的验证码有误哦");

            }
        }
        System.out.println(post.getResponseBodyAsString());
        //    /xs_main.aspx?xh=13101010115
        Header header = post.getResponseHeader("Location");
        if(header==null)
        {
            throw new BizException("登录失败!!");

        }
        returnLocal=header.getValue();
        if(!returnLocal.contains("xs_main.aspx"))
        {
            System.out.println("不正确的返回===》Location:  "+returnLocal);
            String errorInfo = post.getResponseBodyAsString();
            post.releaseConnection();
            throw new BizException(errorInfo);
        }
        System.out.println("Location: "+returnLocal);
        post.releaseConnection();


        GetMethod get = new GetMethod(Constant.BASE_URL+returnLocal);
        get.addRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());

        //new HttpClientLog().printRequestHeadersLog(get);

        httpClient.executeMethod(get);
        String html = get.getResponseBodyAsString();
        //jsoup 处理html标签
        Document docMain = Jsoup.parse(html);
        Elements nameHtml = docMain.select("span#xhxm");
        String  name = nameHtml.text().replace("同学","");

        loginData.setName(name);

        return name;
    }


}
