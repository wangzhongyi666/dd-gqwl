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
    var createtime=$("createtime1").val();
    $.ajax({
        url :'/sugges/suggestionscount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            createtime:createtime,
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
        getlist1(a,$('#pageSizeInp').val())
    }else{
        alert("请输入页数!");
    }
}


function deleteType(e){
    var suggestion_id = $(e).attr("a1");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/sugges/deletesugges.do',
            type : 'POST',
            timeout : 20000,
            data:{
                suggestion_id:suggestion_id
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

function showSugges(e){
    shadboxFun("show");
    var suggestion_id = $(e).attr("a1");
    $.ajax({
        url :'/sugges/getsugges.do',
        type : 'POST',
        timeout : 20000,
        data:{
            suggestion_id:suggestion_id
        },
        async: false,
        success : function(result) {
            if(result.data!=null){
                $("#suggestion").val(result.data.suggestion);
            }
        }
    });
}
function postExcelFile(params, url) { //params是post请求需要的参数，url是请求url地址
    var form = document.createElement("form");
    form.style.display = 'none';
    form.action = url;
    form.method = "post";
    document.body.appendChild(form);

    for(var key in params){
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = key;
        input.value = params[key];
        form.appendChild(input);
    }

    form.submit();
    form.remove();
}
//点击导出按钮导出excel表格
function exportSugges() {
    var params = { createtime :$("#createtime1").val()};
    postExcelFile(params, "/sugges/exportSugges.do");
}
/*]]>*/