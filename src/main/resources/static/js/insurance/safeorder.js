/*<![CDATA[*/
$(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());
    $("#searchbtn").click(function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").click(function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });
});

function countlist(pageSize){
    var name=$("#name").val();
    var insuStart=$("#insuStart").val();
    var insuEnd=$("#insuEnd").val();
    var insuranceType=$("#insuranceType").val();
    var subStartTime=$("#subStartTime").val();
    var subEndTime=$("#subEndTime").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    var insuranceNature=$("#insuranceNature").val();

    $.ajax({
        url :'/insuranceSet/countInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tname:name,
            insuStart:insuStart,
            insuEnd:insuEnd,
            insuranceType:insuranceType,
            subStartTime:subStartTime,
            subEndTime:subEndTime,
            ratio:ratio,
            audit:audit,
            audit1:201,
            insuranceNature:insuranceNature,
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
    var name=$("#name").val();
    var insuStart=$("#insuStart").val();
    var insuEnd=$("#insuEnd").val();
    var insuranceType=$("#insuranceType").val();
    var subStartTime=$("#subStartTime").val();
    var subEndTime=$("#subEndTime").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    var insuranceNature=$("#insuranceNature").val();
    $.ajax({
        url :'/insuranceSet/dataInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tname:name,
            insuStart:insuStart,
            insuEnd:insuEnd,
            insuranceType:insuranceType,
            subStartTime:subStartTime,
            subEndTime:subEndTime,
            ratio:ratio,
            audit:audit,
            audit1:201,
            insuranceNature:insuranceNature,
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
                        if(row.insuranceType!=null){
                            if(row.insuranceType==1){
                                rowlist1[5]="养老保险";
                            }else if(row.insuranceType==2){
                                rowlist1[5]="医疗保险";
                            }
                        }
                        rowlist1[6]=row.insuStart+'-'+row.insuEnd;
                        rowlist1[7]=row.base;
                        rowlist1[8]=row.ratio;
                        rowlist1[9]=row.payment;
                        rowlist1[10]=row.payment;
                        if(row.insuranceNature!=null){
                            if(row.insuranceNature==1){
                                rowlist1[11]="初次参保";
                            }else if(row.insuranceNature==2){
                                rowlist1[11]="参保续接";
                            }else{
                                rowlist1[11]="--";
                            }
                        }else{
                            rowlist1[11]="--";
                        }

                        if(row.audit!=null){//审核状态 1 待审核  2待缴费 3 已拒绝 5 已过期 6参保中 7退保审核中 8 已退保 9已完成 10已取消
                            if(row.audit==2){
                                rowlist1[12]="<li class=\"text-danger\">待缴费</li>";
                            }else if(row.audit==5){
                                rowlist1[12]="已过期";
                            }else if(row.audit==6){
                                rowlist1[12]="参保中";
                            }else if(row.audit==7){
                                rowlist1[12]="退保审核中";
                            }else if(row.audit==8){
                                rowlist1[12]="已退保";
                            }else if(row.audit==9){
                                rowlist1[12]="已完成";
                            }else{
                                rowlist1[12]="--";
                            }
                        }
                        rowlist1[13]=row.auditTime;
                        if(row.audit!=null){
                            if(row.audit==6){
                                rowlist1[14]="<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.name+"' a3='"+row.identNum+"' a4='"+row.identPicUrl1+"' " +
                                    " a5='"+row.identPicUrl2+"' href=\"javascript:void(0)\" onclick=\"showIdentPic(this)\">身份证照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                    "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.bank+"' a3='"+row.medicalUrl+"' a4='"+row.bankNum+"' href=\"javascript:void(0)\" " +
                                    "onclick=\"showBankPic(this)\">医保卡照片</a>&nbsp;&nbsp;&nbsp;&nbsp; "+
                                    "<a class=\"text-primary\" a1='"+row.id+"' a4='"+row.orderNum+"' href=\"javascript:void(0)\" onclick=\"quitOrderShow(this)\">退保</a><br/> ";
                            }else{
                                rowlist1[14]="<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.name+"' a3='"+row.identNum+"' a4='"+row.identPicUrl1+"'" +
                                    " a5='"+row.identPicUrl2+"' href=\"javascript:void(0)\" onclick=\"showIdentPic(this)\">身份证照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                    "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.bank+"' a3='"+row.medicalUrl+"' a4='"+row.bankNum+"' href=\"javascript:void(0)\" " +
                                    "onclick=\"showBankPic(this)\">医保卡照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" ;
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
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"insuranceDateList");
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

function showIdentPic(e){
    var objId = 'detail1';
    shadboxFun(objId);
    var name = $(e).attr("a2")==null||$(e).attr("a2")=="null"?"--":$(e).attr("a2");
    var identNum = $(e).attr("a3")==null||$(e).attr("a3")=="null"?"--":$(e).attr("a3");
    var identPicUrl1 = $(e).attr("a4")==null||$(e).attr("a4")=="null"?"--":$(e).attr("a4");
    var identPicUrl2 = $(e).attr("a5")==null||$(e).attr("a5")=="null"?"--":$(e).attr("a5");

    $("#name1").text(name);
    $("#identNum").text(identNum);
    $("#identPicUrl1").attr('src',identPicUrl1);
    $("#identPicUrl2").attr('src',identPicUrl2);
}

function showBankPic(e){
    var objId = 'detail2';
    shadboxFun(objId);
    var bank = $(e).attr("a2")==null||$(e).attr("a2")=="null"?"--":$(e).attr("a2");
    var bankNum = $(e).attr("a4")==null||$(e).attr("a4")=="null"?"--":$(e).attr("a4");
    var bankPicUrl = $(e).attr("a3")==null||$(e).attr("a3")=="null"?"--":$(e).attr("a3");

    $("#bank").text(bank);
    $("#bankNum").text(bankNum);
    $("#bankPicUrl").attr('src',bankPicUrl);
}

function checkDate(e){
    var subStartTime=$("#subStartTime1").val();
    var subEndTime=$("#subEndTime1").val();
    var flag = false;

    $.ajax({
        url :'/insuranceSet/countInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            subStartTime:subStartTime,
            subEndTime:subEndTime,
            pageSize:1
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

function tuibao(e){
    var objId = 'detail3';
    shadboxFun(objId);
    var id = $("#id").val();
    var orderNum = $("#orderNum").val();
    $.ajax({
        url :'/insuranceSet/tuibao.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id,
            orderNum:orderNum
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg)
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }
    });

}
function quitOrderShow(e){
    var objId = 'detail3';
    var orderNum = $(e).attr("a4");
    $("#orderNum").val(orderNum);
    var id = $(e).attr("a1");
    $("#id").val(id);
    $.ajax({
        url :'/insuranceSet/quitOrder.do',
        type : 'POST',

        timeout : 20000,
        data:{
            orderNum:orderNum
        },
        async: false,
        success : function(data) {
            console.log(data);
                shadboxFun(objId);

                if(data.is_have==1){
                    $(".info").show();
                    $("#syjf").text(data.syjf);
                    $("#cejf").text(data.cejf);
                    $("#stjf").text(data.stjf);
                    $("#tkjf").text(data.tkjf);
                }
                $("#ytje").text(data.ytje);
                $("#ytjf").text(data.ytjf);

        }
    });
}

function printp(){
    $("#identPicUrl3").attr('src',$("#identPicUrl1")[0].src);
    $("#identPicUrl4").attr('src',$("#identPicUrl2")[0].src);
    $("#printul1").show();
    $("#printul1").jqprint({
        debug: false,//如果是true则可以显示iframe查看效果，默认是false
        importCSS: true,//true表示引进原来的页面的css，默认是true。
        printContainer: true,//表示如果原来选择的对象必须被纳入打印，默认是true。
        operaSupport: true///表示如果插件也必须支持歌opera浏览器，默认是true。
    });
    $("#printul1").hide();
}
/*]]>*/