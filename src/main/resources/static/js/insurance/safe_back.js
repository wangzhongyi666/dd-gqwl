/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countquitlist($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countquitlist($('#pageSizeInp').val());
    });
    $("#audit").live('change',function(){
        countquitlist($('#pageSizeInp').val());
    });
    $("#insuranceType").live('change',function(){
        countquitlist($('#pageSizeInp').val());
    });
});

function checkDate(e){
    var subTime1=$("#subTime3").val();
    var subTime2=$("#subTime4").val();
    var flag = false;

    $.ajax({
        url :'/insurance/countquitlist.do',
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
function countquitlist(pageSize){
    var name=$("#name").val();
    var subTime1=$("#subTime1").val();
    var subTime2=$("#subTime2").val();
    var insuranceType=$("#insuranceType").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/countquitlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            subTime1:subTime1,
            subTime2:subTime2,
            insuranceType:insuranceType,
            audit:audit,
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
                    getquitlist(page,pageSize);
                }
            });
        }
    });
}
function getquitlist(pageNum,pageSize){
    var name=$("#name").val();
    var subTime1=$("#subTime1").val();
    var subTime2=$("#subTime2").val();
    var insuranceType=$("#insuranceType").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/getquitlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            subTime1:subTime1,
            subTime2:subTime2,
            insuranceType:insuranceType,
            audit:audit,
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
                        rowlist1[1]=row.insuranceNum;
                        rowlist1[2]=row.name;
                        rowlist1[3]=row.identNum;
                        if(row.insuranceType==1){
                            rowlist1[4]="养老";
                        }else{
                            rowlist1[4]="医疗";
                        }
                        rowlist1[5]=row.quitMonth;
                        rowlist1[6]=row.extendMonth;
                        rowlist1[7]=row.monthNum;
                        rowlist1[8]=row.quit_payment;
                        rowlist1[9]=row.quit_integration;

                        if(row.integration_diff!=null&&row.integration_diff!=""){
                            rowlist1[10]=row.integration_diff;
                        }else{
                            rowlist1[10]="--";
                        }
                        if(row.payment_inte!=null&&row.payment_inte!=""){
                            rowlist1[11]=row.payment_inte;
                        }else{
                            rowlist1[11]="--";
                        }
                        rowlist1[12]=row.pra_payment;
                        rowlist1[13]=row.subTime;
                        if(row.audit<31){
                            rowlist1[15]="待审核";
                            switch(row.audit)
                            {
                                case 11:
                                    rowlist1[14]="待审核";
                                    break;
                                case 21:
                                    rowlist1[14]="已同意";
                                    break;
                                case 22:
                                    rowlist1[14]="已驳回";
                                    break;
                                default:
                            }
                        }else if(row.audit==31){
                            rowlist1[15]="已同意";
                            rowlist1[14]="已同意";
                        }else if(row.audit==32){
                            rowlist1[15]="已驳回";
                            rowlist1[14]="已同意";
                        }
                        else if(row.audit==41){
                            rowlist1[15]="已同意";
                            rowlist1[14]="确认打款";
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
                        rowlist1[13]='';
                        rowlist1[14]='';
                        rowlist1[15]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"audittab");
        }
    });
}
/*]]>*/