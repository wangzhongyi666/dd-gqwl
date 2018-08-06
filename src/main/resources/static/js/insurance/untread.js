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
    var subTime1=$("#demo5").val();
    var subTime2=$("#demo6").val();
    var flag = false;

    $.ajax({
        url :'/insurance/countinsulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            subTime1:subTime1,
            subTime2:subTime2,
            pageSize:1
        },
        async: false,
        success : function(result) {
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
    var demo=$("#demo").val();
    var insuranceType=$("#insuranceType").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/countquitlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            quitMonth:demo,
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
    var demo=$("#demo").val();
    var insuranceType=$("#insuranceType").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/getquitlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            quitMonth:demo,
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
                        rowlist1[10]=row.integration_diff;
                        rowlist1[11]=row.payment_inte;
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
                        if(result.jid!=null && result.jid!=0){
                            if((result.jid==4 && row.audit==11) || (result.jid==3 && row.audit==21)){
                                rowlist1[16]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.quit_integration+"' " +
                                    "a3='"+row.uid+"' a4='"+row.integration_diff+"' a5='"+row.orderNum+"' a6='"+row.quitType+"' onclick=\"shadboxFun1('qt1',this)\">同意</a>" +
                                    "<a href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.quit_integration+"' a3='"+row.uid+"' a5='"+row.orderNum+"' a6='"+row.quitType+"' onclick=\"shadboxFun1('qt2',this)\" class=\"text-primary ml10\">驳回</a>";
                            }else if(result.jid==4 && row.audit==31){
                                rowlist1[16]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' a3='"+row.uid+"'  a5='"+row.orderNum+"' onclick=\"shadboxFun1('qt3',this)\">确认打款</a>";
                            }else {
                                rowlist1[16]='';
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
                        rowlist1[10]='';
                        rowlist1[11]='';
                        rowlist1[12]='';
                        rowlist1[13]='';
                        rowlist1[14]='';
                        rowlist1[15]='';
                        rowlist1[16]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"audittab");
        }
    });
}
function upQuit(audit){
    var qtType=$("#qtType").val();
    if(qtType==1){
        upQuitAudit(audit);
    }else if(qtType==2){
        upQuitAudit1(audit);
    }else if(qtType==3){
        upQuitAudit2(audit);
    }
}
function upQuitAudit(audit){
    $.ajax({
        url :'/insurance/upQuitAudit.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            id:$("#mid").val(),
            uid:$("#vipid").val(),
            quit_integration:$("#kcjf").val(),
            integration_diff:$("#integration_diff").val(),
            orderNum:$("#orderNumInp").val()
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg,{
                anim: 1,
                btn: ['确定'],
                yes:function(){
                    $(this).parents('.popbox-wrapper').animate({
                        opacity: 'hide',top: '0px'
                    }, "slow");

                    $('.popbox-container').fadeOut();
                    layer.close(layer.index);
                    countquitlist($('#pageSizeInp').val());
                }
            });
        }
    });
}
function upQuitAudit1(audit){

    $.ajax({
        url :'/insurance/upQuitAudit1.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            id:$("#mid").val(),
            uid:$("#vipid").val(),
            orderNum:$("#orderNumInp").val()
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg,{
                anim: 1,
                btn: ['确定'],
                yes:function(){
                    $(this).parents('.popbox-wrapper').animate({
                        opacity: 'hide',top: '0px'
                    }, "slow");

                    $('.popbox-container').fadeOut();
                    layer.close(layer.index);
                    countquitlist($('#pageSizeInp').val());
                }
            });
        }
    });
}

function upQuitAudit2(audit){

    $.ajax({
        url :'/insurance/upQuitAudit1.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            id:$("#mid").val(),
            uid:$("#vipid").val(),
            orderNum:$("#orderNumInp").val()
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg,{
                anim: 1,
                btn: ['确定'],
                yes:function(){
                    $(this).parents('.popbox-wrapper').animate({
                        opacity: 'hide',top: '0px'
                    }, "slow");

                    $('.popbox-container').fadeOut();
                    layer.close(layer.index);
                    countquitlist($('#pageSizeInp').val());
                }
            });
        }
    });
}
/*]]>*/