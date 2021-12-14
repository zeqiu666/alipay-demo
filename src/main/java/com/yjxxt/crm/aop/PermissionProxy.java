package com.yjxxt.crm.aop;

import com.yjxxt.crm.annotation.RequiresPermission;
import com.yjxxt.crm.exceptions.NoLoginException;
import com.yjxxt.crm.exceptions.ex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin.javascript.navig.LinkArray;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {
    @Autowired
    private HttpSession session;

    @Around(value = "@annotation(com.yjxxt.crm.annotation.RequiresPermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
     //判断登录
        List<String> permissions= (List<String>) session.getAttribute("permissions");
        if(permissions==null|| permissions.size()==0){
            throw  new NoLoginException("未登录");
        }
        //判断有访问目标
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
      RequiresPermission requiresPermission=  methodSignature.getMethod().getDeclaredAnnotation(RequiresPermission.class);
      if(permissions.contains(requiresPermission.code())){
          throw  new ex("无权限访问");
      }
      Object result=pjp.proceed();
      return result;
    }
}






























































