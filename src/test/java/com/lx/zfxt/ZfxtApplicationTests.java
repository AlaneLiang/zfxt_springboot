package com.lx.zfxt;


import com.lx.zfxt.constant.Constant;
import com.lx.zfxt.service.loginService;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ZfxtApplicationTests {

     @Autowired
    loginService service;
    @Test
    public void contextLoads() {
    }
}

