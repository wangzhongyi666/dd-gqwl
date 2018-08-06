/*<![CDATA[*/
var rankTime = 1;
$(document).ready(function() {
    $('#pageSizeInp').val(16);
    countlist($('#pageSizeInp').val());
    $(".pagebtn").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });
    $(".sort").on('click',function(){
        if(rankTime==1){
            rankTime = 2;
            $("#rankImg").css("background-image","url(/static/images/h5/shengxu.png)");
        }else{
            rankTime = 1;
            $("#rankImg").css("background-image","url(/static/images/h5/jiangxu.png)");
        }
        countlist($('#pageSizeInp').val());
    });
});

function countlist(pageSize){

    $.ajax({
        url :'/handApp/goodsDataCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            rankTime:rankTime,
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

    $.ajax({
        url :'/handApp/goodsDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            rankTime:rankTime,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            var html = "";
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;datalist.length>i;i++){
                    var row=datalist[i];

                    html += "<div class='jf_list'>" +
                                "<a href='javascript:void();' a1='"+row.id+"' a2='"+row.gname+"' a3='"+row.integral+"' " +
                                "a4='"+row.stock+"' a5='"+row.term_of_validity_start+"' a6='"+row.term_of_validity_end+"' " +
                                " a7='"+row.info+"' a8='"+row.statement+"' onclick='goodsInfo(this)'>" +
                                   "<p class='pic'><img class='img-responsive' src='"+row.imgUrl+"'></p>"+
                                   "<p class='txt'>"+row.gname+"</p>"+
                                "</a>"+
                                "<ul>"+
                                    "<li>剩余数量：<span class='text-danger'>"+row.stock+"</span></li>"+
                                    "<li>兑换积分：<span class='text-danger'>"+row.integral+"</span></li>"+
                                "</ul>"+
                            "</div>";

                }
            }
            $("#goodsDataList").html(html);
            var height = 0;
            if(datalist.length!=0){
                if(datalist.length/4<1){
                    height = 1
                }else{
                    if(datalist%4==0){
                        height = datalist.length / 4;
                    }else{
                        height = datalist.length / 4+1;
                    }
                }
            }
            $("#tabs",parent.document).css("height",height*360);
            $("#ifWd",parent.document).css("height",height*360);
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
function goodsInfo(e){
    var id = $(e).attr("a1");
    var gname = $(e).attr("a2");//商品名称
    var integral = $(e).attr("a3");//积分
    var stock = $(e).attr("a4");//库存
    var term_of_validity_start = $(e).attr("a5");//有效期开始时间
    var term_of_validity_end = $(e).attr("a6");//有效期结束时间
    var info = $(e).attr("a7");//商品详情
    var statement = $(e).attr("a8");//重要声明

    window.location.href = "/handApp/goodsInfo.shtml?id="+id;

}
/*]]>*/