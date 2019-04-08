package com.lx.zfxt.service;

import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.PersonInfoDetail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface InfoDeatilService {
   /**
    * 获取用户详细信息
    * @param loginData
    * @return
    * @throws IOException
    */
   PersonInfoDetail getInfo(LoginData loginData) throws IOException;
}
