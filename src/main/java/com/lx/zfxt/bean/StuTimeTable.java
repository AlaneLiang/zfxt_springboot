package com.lx.zfxt.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StuTimeTable implements Serializable {

    private StuSimpleInfo info;

    private ArrayList<Course> courseArrayList;

    public StuSimpleInfo getInfo() {
        return info;
    }

    public void setInfo(StuSimpleInfo info) {
        this.info = info;
    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }

    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }
}
