/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countOrderlist($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countOrderlist($('#pageSizeInp').val());
    });
});
function countOrderlist(pageSize){
    var name=$("#name").val();
    var rechargeTime1 =$("#demo").val();
    var rechargeTime2 =$("#demo2").val();
    var insuStart =$("#demo3").val();
    var insuEnd =$("#demo4").val();
    $.ajax({
        url :'/finance/countOrderlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            rechargeTime1:rechargeTime1,
            rechargeTime2:rechargeTime2,
            insuStart:insuStart,
            insuEnd:insuEnd,
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
                    getOrderlist(page,pageSize);
                }
            });
        }
    });
}
function getOrderlist(pageNum,pageSize){
    var name=$("#name").val();
    var rechargeTime1 =$("#demo").val();
    var rechargeTime2 =$("#demo2").val();
    var insuStart =$("#demo3").val();
    var insuEnd =$("#demo4").val();
    $.ajax({
        url :'/finance/getOrderlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            rechargeTime1:rechargeTime1,
            rechargeTime2:rechargeTime2,
            insuStart:insuStart,
            insuEnd:insuEnd,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            const  rowlist=new Array();
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;pageSize>i;i++){
                    const rowlist1=new Array();
                    if(i<datalist.length){
                        var row=datalist[i];
                        rowlist1[0]=row.orderNum;
                        rowlist1[1]=row.name;
                        rowlist1[2]=row.tel;
                        if(row.insuranceType==1){
                            rowlist1[3]="养老保险";
                        }else{
                            rowlist1[3]="医疗保险";
                        }
                        rowlist1[4]=row.insuStart+"-"+row.insuEnd;
                        rowlist1[5]=row.payment;
                        rowlist1[6]=row.payment * 0.006;
                        rowlist1[7]=row.rechargeTime;
                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                        rowlist1[5]='';
                        rowlist1[6]='';
                        rowlist1[7]='';
                    }

                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"audittab");
        }
    });
}
/*]]>*/