package com.lx.zfxt.service;

import com.lx.zfxt.bean.Course;
import com.lx.zfxt.bean.LoginData;

import java.io.IOException;
import java.util.ArrayList;

public interface ClassService {
    /**
     * 获取课表
     * @param loginData
     * @param xnd
     * @param xqd
     * @throws IOException
     */
    ArrayList<Course> getSchedule(LoginData loginData, String xnd, String xqd) throws IOException;
}
