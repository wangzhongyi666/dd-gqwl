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
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var flag = false;

    $.ajax({
        url :'/goods/getGoodsOrderCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            startDate:startDate,
            endDate:endDate,
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
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var write_off_state=$("#write_off_state").val();
    var startDate1=$("#startDate1").val();
    var endDate1=$("#endDate1").val();
    $.ajax({
        url :'/goods/getGoodsOrderCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            goods_nn:goods_nn,
            startTime:startTime,
            endTime:endTime,
            startDate:startDate1,
            endDate:endDate1,
            write_off_state:write_off_state,
            take_type:2,
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
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var write_off_state=$("#write_off_state").val();
    var startDate1=$("#startDate1").val();
    var endDate1=$("#endDate1").val();
    $.ajax({
        url :'/goods/getGoodsOrderDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            goods_nn:goods_nn,
            startTime:startTime,
            endTime:endTime,
            startDate:startDate1,
            endDate:endDate1,
            write_off_state:write_off_state,
            take_type:2,
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
                        rowlist1[1]=row.goods_number;
                        rowlist1[2]=row.exchange_number;
                        rowlist1[3]=row.integral;
                        rowlist1[4]=row.goods_name;
                        rowlist1[5]=row.nums;
                        rowlist1[6]=row.onsignee_name;
                        rowlist1[7]=row.deliver_goods_tel;
                        rowlist1[8]=row.deliver_goods_address;
                        if(row.write_off_state!=null){
                            if(row.write_off_state==1){
                                rowlist1[9]="<li>未发货</li>";
                            }else if(row.write_off_state==2){
                                rowlist1[9]="<li>已发货</li>";
                            }
                        }else{
                            rowlist1[9]='';
                        }
                        rowlist1[10]=row.exchange_time;
                        rowlist1[11]=row.write_off_time;
                        if(row.write_off_state!=null){
                            if(row.write_off_state==1){
                                rowlist1[12]="<a class=\"text-primary\" a1='"+row.id+"' href=\"javascript:void(0);\" onclick=\"deliverGoods(this)\">发货</a>";
                            }else if(row.write_off_state==2){
                                rowlist1[12]="<a class=\"text-primary\" a1='"+row.id+"' href=\"javascript:void(0);\" onclick=\"deliverGoodsInfo(this)\">查看详情</a>";
                            }else{
                                rowlist1[12]='';
                            }
                        }else{
                            rowlist1[12]='';
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
                        rowlist1[9]='';
                        rowlist1[10]='';
                        rowlist1[11]='';
                        rowlist1[12]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"dataList");
        }
    });
}

function deliverGoods(e){
    shadboxFun('send1');
    var order_id = $(e).attr("a1");
    $("#order_id").val(order_id);
}

function sendGodds(e){
    var flag = false;
    var order_id = $("#order_id").val();
    if(order_id==null||order_id==""){
        flag = true;
        return ;
    }

    var waybill_number = $("#waybill_number").val();
    if(waybill_number==null||waybill_number==""){
        flag = true;
        return ;
    }

    var courier_firm = $("#courier_firm").val();
    if(courier_firm==null||courier_firm==""){
        flag = true;
        return ;
    }
    if(!flag){
        $.ajax({
            url :'/goods/sendGoods.do',
            type : 'POST',
            timeout : 20000,
            data:{
                waybill_number:waybill_number,
                courier_firm:courier_firm,
                order_id:order_id
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);
                $(e).parents('.popbox-wrapper').animate({
                    opacity: 'hide',top: '0px'
                }, "slow");

                $('.popbox-container').fadeOut();
                countlist($('#pageSizeInp').val());
            }

        });
    }

}

function deliverGoodsInfo(e){
    shadboxFun('send2');
    var order_id = $(e).attr("a1");
    $("#order_id").val(order_id);
    sendGoddsInfo(e);
}



function sendGoddsInfo(e){
    var flag = false;
    var order_id = $("#order_id").val();
    if(order_id==null||order_id==""){
        flag = true;
        return ;
    }

    if(!flag){
        $.ajax({
            url :'/goods/sendGoodsInfo.do',
            type : 'POST',
            timeout : 20000,
            data:{
                order_id:order_id
            },
            async: false,
            success : function(result) {
                if(result.goodsOrder==null){
                    $("#courier_firm1").text("--");
                    $("#waybill_number1").text("--");
                }else{
                    $("#courier_firm1").text(result.goodsOrder.waybill_number);
                    $("#waybill_number1").text(result.goodsOrder.courier_firm);
                }
            }

        });
    }

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