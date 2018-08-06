/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countinsulist($('#pageSizeInp').val());
    $("#searchbtn").on('click',function(){
        countinsulist($('#pageSizeInp').val());
    });
    $("#audit").on('change',function(){
        countinsulist($('#pageSizeInp').val());
    });
    $("#insuranceType").on('change',function(){
        countinsulist($('#pageSizeInp').val());
    });
    $("#ratio").on('change',function(){
        countinsulist($('#pageSizeInp').val());
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
            pageSize:1,
            status:$("#status").val()
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
function countinsulist(pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/countinsulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            status:$("#status").val(),
            name:name,
            insuStart1:demo,
            insuEnd1:demo2,
            insuranceType:insuranceType,
            ratio:ratio,
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
                    getinsulist(page,pageSize);
                }
            });
        }
    });
}
function getinsulist(pageNum,pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/getinsulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            status:$("#status").val(),
            name:name,
            insuStart1:demo,
            insuEnd1:demo2,
            insuranceType:insuranceType,
            ratio:ratio,
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
                        rowlist1[4]=row.tel;
                        if(row.insuranceType==1){
                            rowlist1[5]="养老";
                        }else{
                            rowlist1[5]="医疗";
                        }
                        if(row.status==1){
                            rowlist1[6]=row.inseuranceCycle;
                        }else if(row.status==2){
                            rowlist1[6]=row.insuStart+"-"+row.insuEnd;
                        }
                        rowlist1[7]=row.base;
                        rowlist1[8]=row.ratio;
                        rowlist1[9]=row.payment;
                        rowlist1[10]=row.subTime;
                        if(row.audit<31){
                            rowlist1[12]="待审核";
                            switch(row.audit)
                            {
                                case 11:
                                    rowlist1[11]="待审核";
                                    break;
                                case 21:
                                    rowlist1[11]="已审核";
                                    break;
                                case 22:
                                    rowlist1[11]="已驳回";
                                    break;
                                default:
                            }
                        }else if(row.audit==31){
                            rowlist1[12]="已审核";
                            rowlist1[11]="已审核";
                        }else if(row.audit==32){
                            rowlist1[12]="已驳回";
                            rowlist1[11]="已审核";
                        }else{
                            rowlist1[12]="--";
                            rowlist1[11]="--";
                        }
                        if(result.jid!=null && result.jid!=0){
                            if((result.jid==4 && row.audit==11) || (result.jid==3 && row.audit==21)){
                                rowlist1[13]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.payment+"' onclick=\"shadboxFun1('tishi4',this)\">通过</a>" +
                                    "<a href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.payment+"' onclick=\"shadboxFun1('tishi5',this)\" class=\"text-primary ml10\">驳回</a>";
                            }else{
                                rowlist1[13]="<span class=\"text-info\">通过</span><span class=\"text-info ml10\">驳回</span>";
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
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"audittab");
        }
    });
}
function upAudit(audit){
    var payment=$("#payment").val();
    $.ajax({
        url :'/insurance/upAudit.do',
        type : 'POST',
        timeout : 20000,
        data:{
            audit:audit,
            payment:payment,
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
            countinsulist($('#pageSizeInp').val());
        }
    });
}
/*]]>*/