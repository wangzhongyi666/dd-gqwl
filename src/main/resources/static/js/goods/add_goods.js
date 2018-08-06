/*<![CDATA[*/
$(document).ready(function() {
    $('input[type=radio][name=take_type]').change(function() {
        if (this.value == 1) {
            $("#stocks").hide();
        }
        else if (this.value == 2) {
            $("#stocks").show();
        }
    });
});

function quxiao(){
    window.location.replace("/goods/mangoods.do");
}
function addGoods(){
    if($("#imgform").form('validate')){
        var flag = true;
        if($('#file1').val().length==0 && $('#file2').val().length==0 && $('#file3').val().length==0 && $('#file4').val().length==0){
            layer.alert("请选择图片！");
            flag = false;
            return ;
        }
        // if($('#file2').val().length==0){
        //     layer.alert("请选择图片！");
        //     flag = false;
        //     return ;
        // }
        // if($('#file3').val().length==0){
        //     layer.alert("请选择图片！");
        //     flag = false;
        //     return ;
        // }
        // if($('#file4').val().length==0){
        //     layer.alert("请选择图片！");
        //     flag = false;
        //     return ;
        // }

        // var number = $("#number").val();
        // if(number==null||number==""){
        //     layer.alert("商品编号不能为空！");
        //     flag = false;
        //     return;
        // }
        var gname = $("#gname").val();
        if(gname==null||gname==""){
            layer.alert("商品名称不能为空！");
            flag = false;
            return;
        }
        var integral = $("#integral").val();
        if(integral==null||integral==0){
            layer.alert("积分不能为空！");
            flag = false;
            return;
        }
        var info = $("#info").val();
        if(info==null||info==""){
            layer.alert("商品详情不能为空！");
            flag = false;
            return;
        }

        var exchange = $("input[name='exchange']:checked").val();

        var take_type = $("input[name='take_type']:checked").val();

        var stock = $("#stock").val();
        if(take_type==2){
            if(stock==null||stock==0||stock==""||stock==undefined){
                layer.alert("选择邮寄方式库存不能为空！");
                flag = false;
                return ;
            }
        }
        if(flag){
            $("#imgform").form('submit', {
                url:'/goods/addGoods.do?exchange='+exchange+'&take_type='+take_type,
                success:function(data){
                    var c = jQuery.parseJSON(data);
                    console.log(c.success)
                    if(c.success){
                        layer.alert(c.msg, {
                            // skin: 'layui-layer-molv',
                            //closeBtn: 1,
                            anim: 1,
                            btn: ['确定'],
                            //icon: 6,
                            yes:function(){
                                window.location.replace("/goods/mangoods.do");
                            }
                        });

                    }else{
                        layer.alert(c.msg);
                    }

                    /*$.messager.alert("提示",c.msg, "info");*/
                }
            });
        }
    }
}

/*]]>*/