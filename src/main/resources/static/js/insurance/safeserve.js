/*<![CDATA[*/

$(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());
    $("#searchbtn").on("click",function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").on("click",function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });
    $("#all").on("click",function(){
        if($(this).is(':checked')){
            checkboxed("order_info_id");
        }else{
            uncheckboxed("order_info_id");
        }
    });
});
function checkDate(e){
    var subTime1=$("#demo5").val();
    var subTime2=$("#demo6").val();
    var flag = false;

    $.ajax({
        url :'/insurance/countinsulist1.do',
        type : 'POST',
        timeout : 20000,
        data:{
            subTime1:subTime1,
            subTime2:subTime2,
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
function countlist(pageSize){
    var tname=$("#tname").val();
    var insuStart1=$("#insuStart1").val();
    var insuEnd1=$("#insuEnd1").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    var rank=$("#rank").val();
    $.ajax({
        url :'/insurance/countinsulist1.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tname:tname,
            insuStart1:insuStart1,
            insuEnd1:insuEnd1,
            insuranceType:insuranceType,
            ratio:ratio,
            audit:audit,
            rank:rank,
            status:$("#state").val(),
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
    var tname=$("#tname").val();
    var insuStart1=$("#insuStart1").val();
    var insuEnd1=$("#insuEnd1").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    var rank=$("#rank").val();
    $.ajax({
        url :'/insurance/getinsulist1.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tname:tname,
            insuStart1:insuStart1,
            insuEnd1:insuEnd1,
            insuranceType:insuranceType,
            ratio:ratio,
            audit:audit,
            rank:rank,
            status:$("#state").val(),
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
                        rowlist1[0]="<input value='"+row.id+"' name='order_info_id' type=\"checkbox\"/>";
                        rowlist1[1]=row.orderNum;
                        rowlist1[2]=row.insuranceNum;
                        rowlist1[3]=row.name;
                        rowlist1[4]=row.identNum;
                        rowlist1[5]=row.tel;
                        if(row.insuranceType!=null){
                            if(row.insuranceType==1){
                                rowlist1[6]="养老保险";
                            }else if(row.insuranceType==2){
                                rowlist1[6]="医疗保险";
                            }
                        }
                        if($("#state").val()=="1"){
                            rowlist1[7]=row.inseuranceCycle;
                        }else{
                            rowlist1[7]=row.insuStart+'-'+row.insuEnd;
                        }

                        rowlist1[8]=row.base;
                        rowlist1[9]=row.ratio;
                        rowlist1[10]=row.payment;
//审核状态:(1:待缴纳;11:待财务审核;21:财务审核通过(待城市经理审核);22:财务驳回;31:城市经理审核通过;32:城市经理驳回;)
                        if(row.audit!=null){//审核状态:(11:待财务审核;21:财务审核通过(待城市经理审核);22:财务驳回;31:城市经理审核通过;32:城市经理驳回;41：未缴纳)
                            if(row.audit==1){

                                rowlist1[11]="<li class=\"text-danger\">未缴纳</li>";
                            }else if(row.audit==11){
                                rowlist1[11]="审核中";
                            }else if(row.audit==21){
                                rowlist1[11]="审核中";
                            }else if(row.audit==22){
                                rowlist1[11]="已驳回";
                            }else if(row.audit==31){
                                rowlist1[11]="已缴纳";
                            }else if(row.audit==32){
                                rowlist1[11]="已驳回";
                            }else if(row.audit==41){
                                rowlist1[11]="已缴纳";
                            }else{
                                rowlist1[11]="--";
                            }
                            if(row.isquit==1){
                                rowlist1[11]="已退保";
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

function tuibao(e){
    var objId = 'detail3';
    shadboxFun(objId);
    var id = $(e).attr("a1");
}

function enterjn(e){
    var order_info_ids = document.getElementsByName("order_info_id");
    var objarray = order_info_ids.length;
    var info_ids = "";
    for (i = 0; i < objarray; i++) {
        if (order_info_ids[i].checked == true) {
            info_ids += order_info_ids[i].value + ",";
        }
    }
    if(info_ids==""){
        layer.alert("请选择！");
    }else{
        $.ajax({
            url :'/insuranceSet/payOrder.do',
            type : 'POST',
            timeout : 20000,
            data:{
                info_ids:info_ids
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
function enterjnAll(e){

        $.ajax({
            url :'/insuranceSet/payOrderAll.do',
            type : 'POST',
            timeout : 20000,
            data:{
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
function yjjnshow(){
    $.ajax({
        url :'/insuranceSet/payOrderAllShow.do',
        type : 'POST',
        timeout : 20000,
        data:{
        },
        async: false,
        success : function(result) {
            if(result.result==1){
                layer.alert(result.msg);
                return;
            }else{
                shadboxFun('tishi2');
                $("#ny").text(result.ny);
                $("#ny1").text(result.ny1);
                $("#count").text(result.count);
                $("#ylcount").text(result.ylcount);
                $("#ybcount").text(result.ybcount);
            }
        }
    });

}

function checkboxed(objName){
    var objNameList=document.getElementsByName(objName);

    if(null!=objNameList){
        for(var i=0;i<objNameList.length;i++){
            objNameList[i].checked="checked";
        }
    }
}

function uncheckboxed(objName){
    var objNameList=document.getElementsByName(objName);

    if(null!=objNameList){
        for(var i=0;i<objNameList.length;i++){
            objNameList[i].checked="";
        }
    }
}
/*]]>*/