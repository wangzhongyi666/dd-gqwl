/*<![CDATA[*/
$(document).ready(function() {
});
function tologin(){
    var tel=$("#tel").val();
    var pwd=$("#pwd").val();
    $.ajax({
        url :'/handApp/tologin.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tel:tel,
            pwd:pwd
        },
        async: false,
        success : function(result) {
            layer.alert(result.result_msg, {
                // skin: 'layui-layer-molv',
                closeBtn: 1,
                anim: 1,
                btn: ['确定'],
                //icon: 6,
                yes:function(){
                    if(result.result_code==1){
                        $(location).attr("href","main_h5.shtml");
                    }else{
                           layer.close(layer.index);
                    }
                }
            });

        }
    });
}
/*]]>*/