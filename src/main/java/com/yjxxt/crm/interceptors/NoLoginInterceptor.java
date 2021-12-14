package com.yjxxt.crm.interceptors;

import com.yjxxt.crm.exceptions.NoLoginException;
import com.yjxxt.crm.service.Userservice;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor  extends HandlerInterceptorAdapter {
    @Autowired
    private Userservice userservice;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
       //未登录拦截，抛异常
        Integer userId=LoginUserUtil.releaseUserIdFromCookie(req);
        if(userId==null || userservice.selectByPrimaryKey(userId)==null){
            throw new NoLoginException("用户未登录");
        }
        //放行
        return true;
    }
}






































