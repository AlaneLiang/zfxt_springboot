package com.lx.zfxt.bean;

import java.io.Serializable;

public class PersonInfoDetail implements Serializable {
    /**
     * 用户名
     */
    private String name;
    /**
     * 学号
     */
    private String studentCode;
    /**
     * 性别
     */
    private String sex;
    /**
     * 入学日期
     */
    private String studyDate;
    /**
     * 毕业学校名
     */
    private String graduateSchoolName;
    /**
     * 籍贯
     */
    private String hometown;
    /**
     * 生源地
     */
    private String graduateSchoolLocation;
    /**
     * 生源省份
     */
    private String graduateSchoolProvince;
    /**
     * 准考证号
     */
    private String examCode;
    /**
     * 身份证号
     */
    private String IdNumber;
    /**
     * 学历层次
     */
    private String studyLevel;
    /**
     * 学院
     */
    private String  institution;
    /**
     * 专业
     */
    private String major;
    /**
     * 行政班
     */
    private  String classroom;
    /**
     * 届
     */
    private String studyYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public String getGraduateSchoolName() {
        return graduateSchoolName;
    }

    public void setGraduateSchoolName(String graduateSchoolName) {
        this.graduateSchoolName = graduateSchoolName;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getGraduateSchoolLocation() {
        return graduateSchoolLocation;
    }

    public void setGraduateSchoolLocation(String graduateSchoolLocation) {
        this.graduateSchoolLocation = graduateSchoolLocation;
    }

    public String getGraduateSchoolProvince() {
        return graduateSchoolProvince;
    }

    public void setGraduateSchoolProvince(String graduateSchoolProvince) {
        this.graduateSchoolProvince = graduateSchoolProvince;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
}
