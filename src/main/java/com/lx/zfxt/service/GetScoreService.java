package com.lx.zfxt.service;

import com.lx.zfxt.bean.CourseScore;
import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.bean.ScoreTable;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface GetScoreService {
    /**
     * 获取成绩
     * @param loginData
     * @param xn 学期
     * @param xq 学年
     * @param type 查询种类
     * @return
     * @throws IOException
     */
    ArrayList<CourseScore> getInfo(LoginData loginData, String xn, String xq, int type) throws IOException;
}
