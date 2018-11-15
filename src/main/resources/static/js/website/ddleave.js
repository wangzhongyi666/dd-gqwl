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
    var createtime=$("createtime1").val();
    $.ajax({
        url :'/ddleave/ddleaveCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            createtime:createtime,
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
        getlist1(a,$('#pageSizeInp').val())
    }else{
        alert("请输入页数!");
    }
}


function getTree(e){
    shadboxFun1('add');


}

function deleteType(e){
    var leave_id = $(e).attr("a1");
    var state = $(e).attr("a2");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/ddleave/deleteddleave.do',
            type : 'POST',
            timeout : 20000,
            data:{
                leave_id:leave_id,
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
function showLeave(e){
    shadboxFun("show");
    var leave_id = $(e).attr("a1");
    $.ajax({
        url :'/ddleave/getddleave.do',
        type : 'POST',
        timeout : 20000,
        data:{
            leave_id:leave_id
        },
        async: false,
        success : function(result) {
            if(result.data!=null){
               $("#leave").val(result.data.leave);
            }
        }
    });
}
/*]]>*/