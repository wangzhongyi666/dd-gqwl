/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());

    $("#searchbtn").on('click',function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });

});

function countlist(pageSize){
    var jname=$("#jname").val();
    var createtime=$("createtime1").val();
    $.ajax({
        url :'/rastemassage/rastemassageCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            createtime:createtime,
            tel:jname,
            state:0,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            $('#box').paging({
                initPageNo: 1, // 初始页码
                totalPages: result.pageCount, //总页数
                totalCount: '合计' + result.allNum + '条数据', // 条目总数
                slideSpeed: 600, // 缓动速度。单位毫秒
                jump: true, //是否支持跳转
                callback: function(page) { // 回调函数
                    getlist(page,pageSize);
                }
            })
        }
    });
}
function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getlist(a,$('#pageSizeInp').val())
    }else{
        alert("请输入页数!");
    }
}


function getTree(e){
    shadboxFun1('add');
    $("#savemassage").show();
    $("#updatemassage").hide();
}
function updateshow(e){
    shadboxFun1('add');
    $("#savemassage").hide();
    $("#updatemassage").show();

    var massage_id = $(e).attr("a1");
    $.ajax({
        url :'/rastemassage/getrastemassage.do',
        type : 'POST',
        timeout : 20000,
        data:{
            massage_id:massage_id
        },
        async: false,
        success : function(result) {
            console.log(result.data)
            $("#qianinp1").val(result.data.logo);
            $("#qianimg1").css('background',"url("+result.data.logo+") center center no-repeat");
            $("#qianimg1").css('background-size',"100% 100%");

            $("#lieinp1").val(result.data.two_bar_codes);
            $("#lieimg1").css('background',"url("+result.data.two_bar_codes+") center center no-repeat");
            $("#lieimg1").css('background-size',"100% 100%");

            $("#address").val(result.data.address);
            $("#tel").val(result.data.tel);
            $("#phone").val(result.data.phone);
            $("#email").val(result.data.email);
            $("#e_mail").val(result.data.e_mail);
            $("#record").val(result.data.record);
            $("#itude").val(result.data.itude);
        }
    });
}
function updateMassage(e){
    if($("#massageform").form('validate')){
        $("#massageform").form('submit', {
            url:'/rastemassage/addrastemassage.do',
            success:function(data){
                var c = jQuery.parseJSON(data);
                if(c.success){
                    layer.alert(c.msg,{
                        anim: 1,
                        btn: ['确定'],
                        yes:function(){
                            window.location.replace("/rastemassage/rastemassage.shtml");
                        }
                    });
                }else{
                    layer.alert(c.msg);
                }
            }
        });
    }
}
function saveType(){
    var name= $("#name").val();
    var tel= $("#tel").val();
    var email= $("#email").val();
    $.ajax({
        url :'/rasteuser/addrasteuser.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            tel:tel,
            email:email
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            $(this).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }
    });

}
function passType(e){
    var id = $(e).attr("a1");
    if(confirm("确认通过吗！")){
        $.ajax({
            url :'/rasteuser/updaterasteuser.do',
            type : 'POST',
            timeout : 20000,
            data:{
                id:id,
                state:1
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);

                if(result.msg=="操作成功！"){
                    countlist($('#pageSizeInp').val());
                }

            }
        });
    }

}
function repassType(e){
    var id = $(e).attr("a1");
    var state = $(e).attr("a2");
    if(confirm("确认驳回吗！")){
        $.ajax({
            url :'/rasteuser/updaterasteuser.do',
            type : 'POST',
            timeout : 20000,
            data:{
                id:id,
                state:state
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);

                if(result.msg=="操作成功！"){
                    countlist($('#pageSizeInp').val());
                }

            }
        });
    }

}
// 验证函数
function formValidate1(e) {
    var str = '';
    // 判断名称
    if($.trim($('#gname1').val()).length == 0) {
        str += '信息名称没有输入\n';
        $('#gname1').focus();
    }
    if($.trim($('#gvalue1').val()).length == 0) {
        str += '信息值没有输入\n';
        $('#gvalue1').focus();
    }


    // 如果没有错误则提交
    if(str != '') {
        layer.alert(str);
        return false;
    } else {
        updatetype();
    }
}

// 验证函数
function formValidate(e) {
    var str = '';
    // 判断名称
    if($.trim($('#name').val()).length == 0) {
        str += '姓名没有输入\n';
        $('#name').focus();
    }

    // 判断名称
    if($.trim($('#tel').val()).length == 0) {
        str += '手机号没有输入\n';
        $('#tel').focus();
    }else{
        if(!checkPhone($('#tel').val())){
            str += "手机号码有误，请重填";
            return;
        }
    }

    // 判断名称
    if($.trim($('#email').val()).length == 0) {

        str += '邮箱没有输入\n';
        $('#email').focus();
    }else{
        if(!checkEmail($('#email').val())){
            str +"请输入正确的手机号";
            return;
        }
    }
    // 如果没有错误则提交
    if(str != '') {
        layer.alert(str);
        return false;
    } else {
        saveType();
    }
}
//验证手机号
function checkPhone(phone){
    if(!(/^1[345678]\d{9}$/.test(phone))){
        return false;
    }else{
        return true;
    }
}
//验证邮箱
function checkEmail(str){
    var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
    if(re.test(str)){
        return true;
    }else{
        return false;
    }
}

function imgChange(a,type,event){
    var a1="lie";
    if(a==1){
        a1="qian";
    }
    $(event).attr("name","file"+a);
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
    var path = "";
    if(a==1){
        path = getObjectURL(document.getElementById("logo").files[0]);
    }else{
        path = getObjectURL(document.getElementById("two_bar_codes").files[0]);
    }
    $("#"+a1+"inp"+type).val(path);
    $("#"+a1+"inp"+type).attr("name",a1+"inp");
    $("#"+a1+"img"+type).css('background',"url("+path+") center center no-repeat");
    $("#"+a1+"img"+type).css('background-size',"100% 100%");
}

function saveMassage(){
    if($("#massageform").form('validate')){
        $("#massageform").form('submit', {
            url:'/rastemassage/addrastemassage.do',
            success:function(data){
                var c = jQuery.parseJSON(data);
                if(c.success){
                    layer.alert(c.msg,{
                        anim: 1,
                        btn: ['确定'],
                        yes:function(){
                            window.location.replace("/rastemassage/rastemassage.shtml");
                        }
                    });
                }else{
                    layer.alert(c.msg);
                }
            }
        });
    }
}
/*]]>*/