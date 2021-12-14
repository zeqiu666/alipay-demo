package com.yjxxt.crm;

import com.alibaba.fastjson.JSON;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class NoAuthException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
      //未登录异常
//        if(ex instanceof NoLoginException){
//            ModelAndView mav=new ModelAndView("redirect:/index");
//            return mav;
//        }
        ModelAndView mav=new ModelAndView("error");
        mav.addObject("code", 400);
        mav.addObject("msg", "系统异常，请稍后再试...");
      if(handler instanceof HandlerMethod){
          HandlerMethod handlerMethod=(HandlerMethod) handler;
          ResponseBody responseBody=handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
         if(responseBody==null){
             if(ex instanceof ParamsException){
                 ParamsException pe=(ParamsException) ex;
                 mav.addObject("code",pe.getCode());
                 mav.addObject("msg",pe.getMsg());
             }
             return mav;
         }else {
             ResultInfo resultInfo=new ResultInfo();
             resultInfo.setCode(300);
             resultInfo.setMsg("参数异常了");
             if(ex instanceof ParamsException){
                 ParamsException pe=(ParamsException) ex;
                 resultInfo.setCode(pe.getCode());
                 resultInfo.setMsg(pe.getMsg());
             }
             resp.setContentType("application/json;charset=utf-8");
             PrintWriter pw= null;
             try {
                 pw = resp.getWriter();
                 pw.write(JSON.toJSONString(resultInfo));
             } catch (IOException e) {
                 e.printStackTrace();
             }finally {
                 if(pw!=null){
                     pw.close();
                 }
             }
            return null;
         }
      }

      return mav;
    }
}














































