layui.use(['form','jquery','jquery_cookie','layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on('submit(saveBtn)', function(data) {
     var fieldData=data.field;
     //发送
        $.ajax({
            type:"post",
            url:ctx+"/user/updatePwd",
            data:{
                "oldPassword":fieldData.old_password,
                "newPassword":fieldData.new_password,
                "confirmPwd":fieldData.again_password
            },
            dataType:"json",
            success:function (data) {
             if(data.code==200){
                layer.msg("修改密码成功了,系统三秒钟会退出",function () {
                   //清空cookie信息
                    $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                    $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                    $.removeCookie("trueName",{domain:"localhost",path:"/crm"});
                    //跳转页面
                    window.parent.location.href=ctx+"/index";
                });
             }else {
                 layer.msg(data.msg);
             }
            }
        })

     return false;
    });
});
































































