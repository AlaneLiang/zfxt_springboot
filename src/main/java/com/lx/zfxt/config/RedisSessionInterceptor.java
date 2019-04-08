package com.lx.zfxt.config;


import com.lx.zfxt.bean.LoginData;
import com.lx.zfxt.utils.JSONModel;
import com.lx.zfxt.utils.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//拦截登录失效的请求
public class RedisSessionInterceptor implements HandlerInterceptor
{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        //无论访问的地址是不是正确的，都进行登录验证，登录成功后的访问再进行分发，404的访问自然会进入到错误控制器中
        String sessionId = request.getHeader("sessionId");

            try
            {
                //验证当前请求的session是否是已登录的session
                LoginData loginData = (LoginData) request.getSession().getAttribute(sessionId);
                if (loginData != null )
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        outError(response);
        return false;
    }

    private void outError(HttpServletResponse response)
    {
        JSONModel json = new JSONModel();
        json.setMsg("登录失效");
        json.setCode("999990");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String resultJson = JacksonUtils.toJson(json);

        try
        {
            response.getWriter().print(resultJson);
    }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }
}
