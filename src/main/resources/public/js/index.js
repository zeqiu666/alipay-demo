layui.use(['form','jquery','jquery_cookie','layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    
   //监听提交
    form.on('submit(login)', function(data){
        //layer.msg(JSON.stringify(data.field));
        var fieldData=data.field;
        if(fieldData.username=='undefinded' || fieldData.username==''){
            layer.msg("用户名不能为空");
            return ;
        }
        if(fieldData.password=='undefinded' || fieldData.password==''){
            layer.msg("密码不能为空");
            return ;
        }
        //发送ajax
        $.ajax({
            type:"post",
            url:ctx+"/user/login",
            data:{
                "userName":fieldData.username,
                "userPwd":fieldData.password
            },
            dataType:"json",
            success:function (msg) {
              if(msg.code==200){
                  //layer.msg("登录成功了",{icon:5});

                  layer.msg("登录成功了",function () {
                  //将用户是数据储存到Cookie
                      $.cookie("userIdStr",msg.result.userIdStr);
                      $.cookie("userName",msg.result.userName);
                      $.cookie("trueName",msg.result.trueName);
                      //判断是否选中
                     if($("input[type='checkbox']").is(":checked")){
                          $.cookie("userIdStr",msg.result.userIdStr,{expires:7});
                          $.cookie("userName",msg.result.userName,{expires:7});
                          $.cookie("trueName",msg.result.trueName,{expires:7});
                      }
                      //跳转
                      window.location.href=ctx+"/main";
                  });
              }else {
                  layer.msg(msg.msg);
              }
            }
        });
        //取消默认行为
        return false;
    });
});
































































