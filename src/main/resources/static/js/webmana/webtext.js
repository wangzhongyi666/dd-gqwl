/*<![CDATA[*/
$(document).ready(function() {

});
function imgChange(type){
    function getObjectURL(file) {
        var url = null;
        if (window.createObjcectURL != undefined) {
            url = window.createOjcectURL(file);
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
    var path = getObjectURL(document.getElementById("file"+type).files[0]);
    $("#img"+type).css('background',"url("+path+") center center no-repeat");
    $("#img"+type).css('background-size',"100% 100%");
    $("#pa"+type).attr("href",path);
}
function saveoffice(){
    if($("#imgform").form('validate')){
        $("#imgform").form('submit', {
            url:'/web/addText.do',
            success:function(data){
                var c = jQuery.parseJSON(data);
                layer.alert(c.msg, {
                    // skin: 'layui-layer-molv',
                    //closeBtn: 1,
                    anim: 1,
                    btn: ['确定'],
                    //icon: 6,
                    yes:function(){
                        window.location.replace("/web/webtext.do");
                    }
                });
                /*$.messager.alert("提示",c.msg, "info");*/
            }
        });
    }
}
/*]]>*/