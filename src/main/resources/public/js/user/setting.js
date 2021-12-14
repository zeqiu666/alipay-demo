layui.use(['form','jquery','jquery_cookie','layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on("submit(saveBtn)",function (data) {

        //发送ajax
        $.ajax({
            type:"post",
            url:ctx+"/user/setting",
            data:{
                userName:data.field.username,
                phone:data.field.phone,
                email:data.field.email,
                trueName:data.field.trueName,
                id:data.field.id
            },
            dataType:"json",
            success:function (msg) {
                if(msg.code==200){
                    layer.msg("保存成功了",function () {
                        //清空cookie
                     $.removeCookie("userIdStr",{domain:"location",path:"/crm"});
                     $.removeCookie("userName",{domain:"location",path:"/crm"});
                     $.removeCookie("trueName",{domain:"location",path:"/crm"});
                      window.parent.location.href=ctx+"/index";
                    });
                }else {
                  layer.msg(msg,msg);
                }
            }
        });
    });

});










































