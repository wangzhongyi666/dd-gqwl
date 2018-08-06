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

function checkDate(e){
    var startDate1=$("#startDate1").val();
    var endDate1=$("#endDate1").val();
    var flag = false;

    $.ajax({
        url :'/goods/getGoodsOrderCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            startDate1:startDate1,
            endDate1:endDate1,
            pageSize:1
        },
        async: false,
        success : function(result) {
            console.log(result.allNum)
            if(result.allNum>0){
                $(e).parents('.popbox-wrapper').animate({
                    opacity: 'hide',top: '0px'
                }, "slow");

                $('.popbox-container').fadeOut();
                flag = true;
            }else{
                layer.alert("您选择的该时间段内没有数据，请重新选择。");
                flag = false;
            }
        }
    });
    return flag;
}

function countlist(pageSize){
    var goods_nn=$("#goods_nn").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var write_off_state=$("#write_off_state").val();
    $.ajax({
        url :'/goods/getGoodsOrderCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            goods_nn:goods_nn,
            startDate:startDate,
            endDate:endDate,
            startTime:startTime,
            endTime:endTime,
            take_type:1,
            write_off_state:write_off_state,
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
    var goods_nn=$("#goods_nn").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var write_off_state=$("#write_off_state").val();
    $.ajax({
        url :'/goods/getGoodsOrderDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            goods_nn:goods_nn,
            startDate:startDate,
            endDate:endDate,
            startTime:startTime,
            endTime:endTime,
            take_type:1,
            write_off_state:write_off_state,
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
                        rowlist1[0]=row.order_number;
                        rowlist1[1]=row.redeem_code;
                        rowlist1[2]=row.goods_name;
                        rowlist1[3]=row.nums;
                        rowlist1[4]=row.integral;
                        rowlist1[5]=row.exchange_number;
                        rowlist1[6]=row.exchange_time;
                        rowlist1[7]=row.write_off_time;
                        if(row.write_off_state!=null){
                            if(row.write_off_state==1){
                                rowlist1[8]="未使用";
                            }else if(row.write_off_state==2){
                                rowlist1[8]="已使用";
                            }else{
                                rowlist1[8]="--";
                            }
                        }else{
                            rowlist1[8]="--";
                        }

                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                        rowlist1[5]='';
                        rowlist1[6]='';
                        rowlist1[7]='';
                        rowlist1[8]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"goodsOrderDataList");
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
/*]]>*/