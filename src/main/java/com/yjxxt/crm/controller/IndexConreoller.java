package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.bean.User;
import com.yjxxt.crm.service.PermissionService;
import com.yjxxt.crm.service.Userservice;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexConreoller extends BaseController {

    @Autowired
    private Userservice userservice;
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("main")
    public String main(HttpServletRequest req) {
        //获取当前用户的信息
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //根据用户的id查询用户信息
        User user = userservice.selectByPrimaryKey(userId);
        //存储
        req.setAttribute("user",user);
        //将用户的权限码存到Session
        List<String> permissions = permissionService.queryUserHasRoleHasPermissions(userId);
        for (String code: permissions) {
            System.out.println(code+"权限码");
        }
        //将用户的权限存到session作用域
        req.getSession().setAttribute("permissions",permissions);
        //转发
        return "main";
    }
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }


}















































