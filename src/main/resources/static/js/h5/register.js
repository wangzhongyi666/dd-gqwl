/*<![CDATA[*/
$(document).ready(function() {
});
function vipregister(){
    var tel=$("#tel").val();
    var pwd2=$("#pwd2").val();
    var pwd=$("#pwd").val();
    var pwd2=$("#pwd2").val();
    if(pwd!=pwd2){
        layer.alert("两次密码不一致");
        return;
    }
    $.ajax({
        url :'/handApp/vipregister.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tel:tel,
            pwd:pwd
        },
        async: false,
        success : function(result) {

        }
    });
}
/*]]>*/