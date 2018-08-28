/*<![CDATA[*/
var audit = 101;
var insuranceType = 1;
$(document).ready(function() {

    $('#pageSizeInp').val(5);
    countlist($('#pageSizeInp').val());
    $(".pagebtn").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });

    $(".yj_active").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        if($(this).text()=="已缴月份"){
            audit = 101;
        }else{
            audit = 102;
        }
        $("#yjDateList").html("");
        countlist($('#pageSizeInp').val());
    });
});

function countlist(pageSize){
    insuranceType = $("#insuranceType").val();
    $.ajax({
        url :'/handApp/check_detailCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            insuranceType:insuranceType,
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
    insuranceType = $("#insuranceType").val();
    $.ajax({
        url :'/handApp/check_detailList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            insuranceType:insuranceType,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            console.log(datalist);
            const  rowlist=[];
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;datalist.length>i;i++){
                    var row=datalist[i];
                    const rowlist1=[];
                    if(row.status==1){
                        rowlist1[0]=row.inseuranceCycle;
                    }else{
                        rowlist1[0]=row.insuStart+"-"+row.insuEnd;
                    }
                    rowlist1[1]=row.base;
                    rowlist1[2]=row.payment;

                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"yjDateList");
        }
    });
}
/*]]>*/