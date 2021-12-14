package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.bean.User;
import com.yjxxt.crm.exceptions.ParamsException;
import com.yjxxt.crm.model.UserModel;
import com.yjxxt.crm.query.UserQuery;
import com.yjxxt.crm.service.Userservice;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private Userservice userservice;

    @RequestMapping("toPasswordPage")
    public  String updatePwd(){
        return "user/password";
    }
    @RequestMapping("index")
    public  String index(){
        return "user/user";
    }
    @RequestMapping("addOrUpdatePage")
    public  String addOrUpdatePage(Integer id, Model model){
        if(id!=null){
           User user= userservice.selectByPrimaryKey(id);
           model.addAttribute("user",user);
        }
        return "user/add_update";
    }
    @RequestMapping("toSettingPage")
    public String setting(HttpServletRequest req){
        //获取用户ID
        int userId=LoginUserUtil.releaseUserIdFromCookie(req);
        //调用方法
        User user=userservice.selectByPrimaryKey(userId);
        req.setAttribute("user",user);
        //转发
        return "user/setting";
    }
    @RequestMapping("login")
    @ResponseBody
    public ResultInfo say(User user){
        ResultInfo resultInfo=new ResultInfo();

            UserModel userModel=userservice.userLogin(user.getUserName(),user.getUserPwd());
            resultInfo.setResult(userModel);
        return resultInfo;
    }
    @RequestMapping("setting")
    @ResponseBody
    public ResultInfo sayUpdate(User user){
        ResultInfo resultInfo=new ResultInfo();
        //修改信息
       userservice.updateByPrimaryKeySelective(user);
       //返回目标数据对象
        return resultInfo;
    }
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(User user){
        //修改信息
        userservice.addUser(user);
        //返回目标数据对象
        return success("用户添加OK");
    }
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(User user){
        //修改信息
        userservice.changeUser(user);
        //返回目标数据对象
        return success("用户修改OK");
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer[] ids){
        //修改信息
        userservice.removeUserIds(ids);
        //返回目标数据对象
        return success("批量删除用户OK");
    }


    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updatePwd(HttpServletRequest req,String oldPassword,String newPassword,String confirmPwd){
        ResultInfo resultInfo=new ResultInfo();
        int userId=LoginUserUtil.releaseUserIdFromCookie(req);
        userservice.changeUserPwd(userId,oldPassword,newPassword,confirmPwd);
        return resultInfo;
    }

    @RequestMapping("sales")
    @ResponseBody
    public  List<Map<String,Object>>  findSales(){
      List<Map<String,Object>> list= userservice.querySales();
      return list;
    }
    @RequestMapping("list")
    @ResponseBody
    public  Map<String,Object>  list(UserQuery userQuery){
      return   userservice.findUserByParams(userQuery);

    }
}






















































