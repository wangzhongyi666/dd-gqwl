/*<![CDATA[*/
var nodId=0;
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());

    $("#searchbtn").on('click',function(){
        countlist($('#pageSizeInp').val());
    });



});

function countlist(pageSize){
    var title=$("#topictitle").val();
    var type=$("#topictype").val();

        $.ajax({
        url :'/topic/topicCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            title:title,
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
    $("#picid1").val($(e).attr("a1"));
    $("#typename1").val($(e).attr("a2"));
    $("#tsort1").val($(e).attr("a3"));
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
function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getlist(a,$('#pageSizeInp').val())
    }else{
        alert("请输入页数!");
    }
}
function deleteType(e){
    var id = $(e).attr("a1");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/topic/deletetopic.do',
            type : 'POST',
            timeout : 20000,
            data:{
                topid:id
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
//审核
function passtopic(e){
    var id = $(e).attr("a1");
    var ispass = $(e).attr("a3");

    $.ajax({
        url :'/topic/passtopic.do',
        type : 'POST',
        timeout : 20000,
        data:{
            topid:id,
            ispass:ispass
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


/*]]>*/