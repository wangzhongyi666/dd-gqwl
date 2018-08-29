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

// 验证手机号
function isPhoneNo(phone) {
    var pattern = /^1[3456789]\d{9}$/;
    return pattern.test(phone);
}

// 验证函数
function formValidate(e) {
    var str = '';
    // 判断名称
    if($.trim($('#email').val()).length == 0) {
        str += '名称没有输入\n';
        $('#email').focus();
    }

    if($.trim($('#pwd').val()).length == 0) {
        str += '名称没有输入\n';
        $('#pwd').focus();
    }else if($.trim($('#pwd').val()).length < 5){
        str += '密码长度不能小于6\n';
        $('#pwd').focus();
    }

    // 判断地区
    if($.trim($('#deptId').val()).length == 0) {
        str += '请选择地区\n';
        $('#deptId').focus();
    }

    // 判断角色
    if($.trim($('#jid').val()).length == 0) {
        str += '请选择角色\n';
        $('#jid').focus();
    }

    // 判断名称
    if($.trim($('#email').val()).length == 0) {
        str += '名称没有输入\n';
        $('#email').focus();
    }
    // 判断手机号码
    if ($.trim($('#tel').val()).length == 0) {
        str += '手机号没有输入\n';
        $('#tel').focus();
    } else {
        if(isPhoneNo($.trim($('#tel').val()) == false)) {
            str += '手机号码不正确\n';
            $('#tel').focus();
        }
    }

    // 如果没有错误则提交
    if(str != '') {
        layer.alert(str);
        return false;
    } else {
        addUser(e);
    }
}
function qx(e) {
    $(".popbox-container").parents('.popbox-wrapper').animate({
        opacity: 'hide',top: '0px'
    }, "slow");

    $('.popbox-container').fadeOut();
}
function addUser(e){

    $.ajax({
        url :'/sys/adduser.do',
        type : 'POST',
        timeout : 20000,
        data:{
            email:$("#email").val(),
            pwd:$("#pwd").val(),
            deptId:$("#deptId").val(),
            tel:$("#tel").val(),
            jid:$("#jid").val(),
            nickName:$("#nickName").val(),
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            if(result.success){
                $(".popbox-container").parents('.popbox-wrapper').animate({
                    opacity: 'hide',top: '0px'
                }, "slow");

                $('.popbox-container').fadeOut();
            }
            countlist($('#pageSizeInp').val());
        }
    });
}
function countlist(pageSize){
    var ename=$("#ename").val();
    $.ajax({
        url :'/sys/userCountDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            ename:ename,
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
function getlist(pageNum,pageSize){
    var ename=$("#ename").val();
    $.ajax({
        url :'/sys/userDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            ename:ename,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            const  rowlist=[];
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;pageSize>i;i++){
                    const rowlist1=[];
                    if(i<datalist.length){
                        var row=datalist[i];
                        rowlist1[0]=row.email;
                        rowlist1[1]=row.nickName;
                        rowlist1[2]=row.tel;
                        rowlist1[3]=row.deptName;
                        rowlist1[4]=row.roleName;
                        if(row.state!=null){
                            if(row.state==0){
                                rowlist1[5]="<li>正常</li>";
                            }else if(row.state==1){
                                rowlist1[5]="<li>锁定</li>";
                            }
                        }
                        rowlist1[6]="<a href='/flexiblebe/changepwd.shtml' class=\"text-primary ml10\">重置密码</a>"+
                            "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"shadboxFunDel(this)\" class=\"text-primary ml10\">删除</a>";
                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                        rowlist1[5]='';
                        rowlist1[6]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"userDataList");
        }
    });
}

function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getlist(a,$('#pageSizeInp').val())
    }else{
        layer.alert("请输入页数!");
    }
}
function shadboxFunDel(e){
   var id = $(e).attr("a1");
//询问框

    layer.confirm('是否确定删除？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        enable(id);
    }, function(){

    });
}
function enable(id){
    $.ajax({
        url :'/sys/delUser.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id
        },
        async: false,
        success : function(result) {
            console.log(result);
            layer.alert(result.msg);

            countlist($('#pageSizeInp').val());
        }
    });
}
/*]]>*/