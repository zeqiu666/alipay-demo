layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

        form.on("submit(addOrUpdateRole)",function (obj) {
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});

            var url=ctx+"/role/save";
            if($("input[name=id]").val()){
                url=ctx+"/role/update";
            }
            $.ajax(url,obj.field,function (result) {
                if(result.code==200){
                setTimeout(function () {

                        top.layer.close(index);
                        layer.msg("添加OK",{icon:6});
                        top.layer.closeAll("iframe");
                        parent.location.reload();
                },500)
                }else {
                    layer.msg(result.msg,{icon: 5});
                }

            },"json");
            return false;
        })
    /*取消*/
        $("#closeBtn").click(function () {
         var index=parent.layer.getFrameIndex(window.name);
         parent.layer.close(index);
        });
});














































































