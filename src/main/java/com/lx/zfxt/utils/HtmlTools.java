package com.lx.zfxt.utils;
import com.lx.zfxt.bean.Course;
import com.lx.zfxt.bean.CourseScore;
import com.lx.zfxt.bean.StuSimpleInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTools {

    /**
     * 查找课表部分的HTML字符串
     * @param html HTML文档
     * @return 课表的HTML串
     */
    private static String findCourseTableHtml(String html){
        String res="";
        String tar="<table id=\"Table1\" class=\"blacktab\" bordercolor=\"Black\" border=\"0\" width=\"100%\">";
        String pattern="<table id=\"Table1\" class=\"blacktab\" bordercolor=\"Black\" border=\"0\" width=\"100%\">([\\S\\s]+?)</table>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(0);
            res=res.substring(res.indexOf(tar)+tar.length(),res.lastIndexOf("</table>")).trim();
        }else{
            System.out.println(html);
            System.out.println("什么都没有");
        }
        return res;
    }

    /**
     * 获取当前学年度选项
     * @param html
     * @return
     */
    public static String getCurrXnd(String html){
        //<option selected="selected" value="2016-2017">2016-2017</option>

        String res="";
        String pattern="<option selected=\"selected\" value=\"((\\d\\d+)-(\\d\\d+))\">(.*?)</option>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(1);
        }
        return res.trim();

    }


    /**
     * 获取当前学期
     * @param html
     * @return
     */
    public static String getCurrXqd(String html){
        //	<option selected="selected" value="1">1</option>

        String res="";
        String pattern="<option selected=\"selected\" value=\"\\d\">(.*?)</option>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(1);
        }
        return res.trim();

    }

    /**
     * 获取学生简要信息
     * @param html HTML文档
     * @return 返回学生简要信息数组
     */
    public static StuSimpleInfo getCourseStuInfo(String html){
        String[] info=null;
        Map<String,String> map=new HashMap<>();
        String res="";
        String pattern="<TR class=\"trbg1\">"
                + "([\\s\\S]*?)<TD>([\\s\\S]*?)"
                + "<span id=\"Labe(.+?)\">([\\s\\S]+?)</span>(\\|([\\s\\S]*?)<span id=\"Labe(.+?)\">([\\s\\S]+?)</span>)*";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(0);
            info=res.split("\\|");
            pattern="<span id=\"Labe(.+?)\">([\\s\\S]+?)：([\\s\\S]+?)</span>";
            p=Pattern.compile(pattern);
            String[] key={"id","name","department","major","classNum"};
            for(int i=0;i<info.length;i++){
                m=p.matcher(info[i]);
                if(m.find()){
                    map.put(key[i],m.group(3));
                }else{
                    map.put(key[i],"");
                }
            }
        }
        System.out.println("--------"+map.toString());
        return BeanFactor.createStuSimpleInfo(map);
    }

    /**
     * 在HTML文档中提取课表
     * @param html HTML文档
     * @return 返回课表
     */
    public static ArrayList<Course> getCourseList(String html){

        String courseTableHtml=findCourseTableHtml(html);//找到课表部分的HTML
        ArrayList<Course> courses=new ArrayList<Course>();

        String[] rows=courseTableHtml.split("</tr><tr>");//按上课时间分隔HTML
        for(int i=2;i<rows.length;i+=2){

            String r=rows[i];
            String[] cols=r.split("</td><td([\\S\\s]*?)>");//按星期几分隔HTML

            int j=1;
            if(i==2||i==6||i==10){
                j=2;
            }

            int x=1;
            for(;j<cols.length;x++,j++){
                String c=cols[j];
                String[] info=c.split("<br>");
                if(info[0].contains("&nbsp;")) continue;
                String[] tem=new String[4];
                int t=0;

                for(int k=0;k<info.length;k++){
                    String item=info[k].trim();
                    if(item.equals("")){
                        //处理同一时间不同周数的课程
                        t=0;
                        tem=new String[4];
                        continue;
                    }
                    tem[t++]=item;
                    if(t==4){
                        Course course= BeanFactor.createCourse(tem, i-1, x);
                        courses.add(course);
                    }
                }

            }

        }

        return courses;
    }

    /**
     * 在html文档中获取成绩表
     * @param html HTML文档
     * @return 成绩表对象
     */
    public static  ArrayList<CourseScore> getScore(String html){
        String res="";
        String pattern="<table class=\"datelist\" cellspacing=\"0\" cellpadding=\"3\" border=\"0\" id=\"DataGrid1\" width=\"100%\">([\\s\\S]+?)</table>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);
        if(m.find()){
            res=m.group(1);

        }
        ArrayList<CourseScore> list=findScore(res);

        return list;
    }

    /**
     * 获取成绩的列表
     * @param html HTML文档
     * @return 成绩的列表对象
     */
    public static ArrayList<CourseScore> findScore(String html){



        ArrayList<CourseScore> list=new ArrayList<>();
        Map<String ,String> map=new HashMap<>();
        String res="";

        String pattern="<tr([\\s\\S]*?)>([\\s\\S]*?)</tr>";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(html);

        m.find();

        String pattern2="<td>([\\s\\S]*?)</td>";
        Pattern p2=Pattern.compile(pattern2);
        Matcher m2;
        while(m.find()){
            res=m.group(0).trim();
            m2=p2.matcher(res);
            int i=0;
            while(m2.find()){
                res=m2.group(1).trim();
                i++;
                switch (i){
                    case 1:{
                        map.put("classCode",res);
                        break;
                    }
                    case 2:{
                        map.put("className",res);
                        break;
                    }
                    case 4:{
                        map.put("examScore",res);
                        break;
                    }
                    case 5:{
                        map.put("lastScore",res);
                        break;
                    }

                    case 9:{
                        map.put("credit",res);
                        break;
                    }
                    case 10:{
                        map.put("isMakeup",res);
                        break;
                    }
                    default:{
                        continue;
                    }
                }
            }
            CourseScore cs=BeanFactor.createCourseScore(map);
            list.add(0,cs);
        }
        return  list;
    }












}
