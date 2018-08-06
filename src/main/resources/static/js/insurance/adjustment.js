/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countadjulist($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countadjulist($('#pageSizeInp').val());
    });
    $("#audit").live('change',function(){
        countadjulist($('#pageSizeInp').val());
    });
    $("#insuranceType").live('change',function(){
        countadjulist($('#pageSizeInp').val());
    });
    $("#ratio").live('change',function(){
        countadjulist($('#pageSizeInp').val());
    });
});

function checkDate(e){
    var subTime1=$("#demo5").val();
    var subTime2=$("#demo6").val();
    var flag = false;
    $.ajax({
        url :'/insurance/countadjulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            subTime1:subTime1,
            subTime2:subTime2,
            pageSize:1,
            audit:1
        },
        async: false,
        success : function(result) {
            console.log(result.allNum);
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

function countadjulist(pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    $.ajax({
        url :'/insurance/countadjulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            status:$("#status").val(),
            name:name,
            insuStart1:demo,
            insuEnd1:demo2,
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
                    getadjulist(page,pageSize);
                }
            });
        }
    });
}
function getadjulist(pageNum,pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    $.ajax({
        url :'/insurance/getadjulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            status:$("#status").val(),
            name:name,
            insuStart1:demo,
            insuEnd1:demo2,
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
                        rowlist1[0]=row.orderNum;

                        rowlist1[1]=row.name;

                        if(row.insuranceType==1){
                            rowlist1[2]="养老";
                        }else{
                            rowlist1[2]="医疗";
                        }
                        rowlist1[3]=row.oldStart+"-"+row.oldEnd;
                        rowlist1[4]=row.insuStart+"-"+row.insuEnd;
                        rowlist1[5]=(row.oldPayment - row.payment)<=0||row.monthPayment<=0?0:((row.oldPayment - row.payment)/row.monthPayment);
                        rowlist1[6]=(row.oldPayment - row.payment)<0?0:(row.oldPayment - row.payment);
                        rowlist1[7]=row.subTime;
                        if(row.audit==1){
                            rowlist1[8]="待审核";
                        }else if(row.audit==2){
                            rowlist1[8]="待城市经理审核";
                        }else if(row.audit==6){
                            rowlist1[8]="参保中";
                        }else if(row.audit==7){
                            rowlist1[8]="退保审核中";
                        }else if(row.audit==8){
                            rowlist1[8]="已退保";
                        }else if(row.audit==9){
                            rowlist1[8]="已完成";
                        }
                        if(result.jid!=null && result.jid!=0){
                            if((result.jid==3 && row.audit==2)){
                                rowlist1[9]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.payment+"' onclick=\"shadboxFun1('tishi4',this)\">通过</a>" +
                                    "<a href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.payment+"' onclick=\"shadboxFun1('tishi5',this)\" class=\"text-primary ml10\">驳回</a>";
                            }else{
                                rowlist1[9]="<span class=\"text-info\">通过</span><span class=\"text-info ml10\">驳回</span>";
                            }
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
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"audittab");
        }
    });
}
function upOrderAudit(audit){
    $.ajax({
        url :'/insurance/upOrderAudit.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            id:$("#vipid").val()
        },
        async: false,
        success : function(result) {
            // layer.alert(result.msg);
            $("#tishi4").hide();
            $("#tishi5").hide();
            $(this).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            countadjulist($('#pageSizeInp').val());
        }
    });
}
/*]]>*/