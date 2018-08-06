/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countUserlist($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countUserlist($('#pageSizeInp').val());
    });
    $("#insuranceType").live('change',function(){
        countUserlist($('#pageSizeInp').val());
    });
    $("#ratio").live('change',function(){
        countUserlist($('#pageSizeInp').val());
    });
});
function checkDate(e){
    var subTime1=$("#demo5").val();
    var subTime2=$("#demo6").val();
    var flag = false;

    $.ajax({
        url :'/insurance/countUserlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            subTime1:subTime1,
            subTime2:subTime2,
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

function countUserlist(pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    $.ajax({
        url :'/insurance/countUserlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            subTime1:demo,
            subTime2:demo2,
            insuranceType:insuranceType,
            ratio:ratio,
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
                    getUserlist(page,pageSize);
                }
            });
        }
    });
}
function getUserlist(pageNum,pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    $.ajax({
        url :'/insurance/getUserlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            subTime1:demo,
            subTime2:demo2,
            insuranceType:insuranceType,
            ratio:ratio,
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
                        rowlist1[0]=row.insuranceNum;
                        rowlist1[1]=row.name;
                        rowlist1[2]=row.identNum;
                        rowlist1[3]=row.tel;
                        if(row.insuranceType==1){
                            rowlist1[4]="养老";
                        }else{
                            rowlist1[4]="医疗";
                        }
                        if(row.status==1){
                            rowlist1[5]=row.inseuranceCycle;
                        }else{
                            rowlist1[5]=row.insuStart+"-"+row.insuEnd;
                        }

                        rowlist1[6]=row.base;
                        rowlist1[7]=row.ratio;
                        rowlist1[8]=row.payment;
                        rowlist1[9]=row.auditTime;
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
                    }

                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"audittab");
        }
    });
}
/*]]>*/