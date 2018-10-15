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
        url :'/newstype/newstypecount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            s_name:jname,
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
    $("#newstypeid1").val($(e).attr("a1"));
    $("#newstypename1").val($(e).attr("a2"));
    $("#n_sort1").val($(e).attr("a3"));
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
    var newstypename=$("#newstypename").val();
    var n_sort=$("#n_sort").val();
    $.ajax({
        url :'/newstype/addnewstype.do',
        type : 'POST',
        timeout : 20000,
        data:{
            newstypename:newstypename,
            n_sort:n_sort
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
            url :'/newstype/deletenewstype.do',
            type : 'POST',
            timeout : 20000,
            data:{
                newstypeid:id
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
function updatetype() {
    var newstypeid= $("#newstypeid1").val();
    var typename=$("#newstypename1").val();
    var n_sort=$("#n_sort1").val();
    $.ajax({
        url :'/newstype/updatenewstype.do',
        type : 'POST',
        timeout : 20000,
        data:{
            newstypeid:newstypeid,
            newstypename:typename,
            n_sort:n_sort
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
    if($.trim($('#newstypename1').val()).length == 0) {
        str += '分类名称没有输入\n';
        $('#typename1').focus();
    }
    if($.trim($('#n_sort1').val()).length == 0) {
        str += '排序没有输入\n';
        $('#n_sort1').focus();
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
    if($.trim($('#newstypename').val()).length == 0) {
        str += '分类名称没有输入\n';
        $('#newstypename').focus();
    }
    if($.trim($('#n_sort').val()).length == 0) {
        str += '排序没有输入\n';
        $('#n_sort').focus();
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