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
        url :'/gcolumn/gseocount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            g_title:jname,
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
    var g_name1= $("#g_name").val();
    $.ajax({
        url :'/gcolumn/addgcolumn.do',
        type : 'POST',
        timeout : 20000,
        data:{
            g_name:g_name1
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
            url :'/gcolumn/deletegcolumn.do',
            type : 'POST',
            timeout : 20000,
            data:{
                c_id:id
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

//编辑信息
function updatetype(e) {
    var gname1= $("#g_name1").val();
    $.ajax({
        url :'/gcolumn/updategcolumn.do',
        type : 'POST',
        timeout : 20000,
        data:{
            g_name:gname1,
            c_id:$("#c_id").val()
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
    if($.trim($('#g_name').val()).length == 0) {
        str += '栏目名称没有输入\n';
        $('#g_name').focus();
    }

    // 如果没有错误则提交
    if(str != '') {
        layer.alert(str);
        return false;
    } else {
        saveType();
    }
}

/*]]>*/