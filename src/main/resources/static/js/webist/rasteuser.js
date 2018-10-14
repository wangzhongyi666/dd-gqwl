/*<![CDATA[*/
var nodId=0;
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
    $.ajax({
        url :'/rasteuser/rasteuserCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tel:jname,
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

function edit(e){
    $("#webid").val($(e).attr("a1"));
    shadboxFun('edit');
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
function deleteType(e){
    var id = $(e).attr("a1");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/rasteuser/deleterasteuser.do',
            type : 'POST',
            timeout : 20000,
            data:{
                id:id
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
/*]]>*/