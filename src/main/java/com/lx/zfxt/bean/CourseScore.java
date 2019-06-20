package com.lx.zfxt.bean;

import java.io.Serializable;

public class CourseScore implements Serializable {
    /**
     * 课程代码
     */
    private String classCode;

    /**
     * 课程名称
     */
    private String className;
    /**
     * 卷面分
     */
    private String examScore;
    /**
     * 最终成绩
     */
    private String lastScore;

    /**
     * 是否补考
     */
    private String isMakeup;

    /**
     * 学分
     */
    private String credit;

    /**
     * 构造函数
     */
    public CourseScore(){

    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    public String getLastScore() {
        return lastScore;
    }

    public void setLastScore(String lastScore) {
        this.lastScore = lastScore;
    }

    public String getIsMakeup() {
        return isMakeup;
    }

    public void setIsMakeup(String isMakeup) {
        this.isMakeup = isMakeup;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
