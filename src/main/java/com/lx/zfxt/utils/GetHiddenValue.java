package com.lx.zfxt.utils;

import com.lx.zfxt.log.HttpClientLog;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import java.io.IOException;

/**
 * 获取正方系统post方法需带的_ViewState值
 */

public class GetHiddenValue {

    public static  String getViewState(String url,String cookie,String referer) throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getScore = new GetMethod(url);

        getScore.setRequestHeader("Cookie", cookie);
        getScore.setRequestHeader("Referer",referer);

        httpClient.executeMethod(getScore);

        String html = getScore.getResponseBodyAsString();
        String viewstate = Jsoup.parse(html).select("input[name=__VIEWSTATE]")
                .val();

        return viewstate;
    }
}
