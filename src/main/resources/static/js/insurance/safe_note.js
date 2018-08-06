/*<![CDATA[*/
var id = "";
$(document).ready(function() {
    getinsulist();
});

function getinsulist(){

    $.ajax({
        url :'/insuranceSet/getSmsTempalelist.do',
        type : 'POST',
        timeout : 20000,
        data:{
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            const  rowlist=new Array();
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;datalist.length>i;i++){

                    var row=datalist[i];
                    const rowlist1=new Array();
                    rowlist1[0]=row.trigger_event;
                    rowlist1[1]=row.template;
                    rowlist1[2]=row.explain;
                    if(row.push_time==1){
                        rowlist1[3]="即时";
                    }else{
                        rowlist1[3]="其他";
                    }
                    if(row.status==1){
                        rowlist1[4]="启用";
                    }else{
                        rowlist1[4]="禁用";
                    }
                    if(row.status!=null && row.status!=0){
                        if(row.status == 1){
                            rowlist1[5]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"shadboxFunDisable(this)\">禁用</a>";
                            if(row.id!=null){
                                if(row.id == 8){
                                    rowlist1[5] += "<a href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.trigger_event+"' " +
                                        "onclick=\"shadboxFunSet1(this)\" class=\"text-primary ml10\">设置</a>";
                                }
                                if(row.id == 9){
                                    rowlist1[5] += "<a href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.trigger_event+"' " +
                                        "onclick=\"shadboxFunSet2(this)\" class=\"text-primary ml10\">设置</a>";
                                }
                            }
                        }else{
                            rowlist1[5]="<span class=\"text-primary ml10\" a1='"+row.id+"' onclick=\"shadboxFunEnable(this)\">启用</span>";
                        }
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"smstemplateList");
        }
    });
}
function sendSms1(e){
    var flag = false;
    var sendMonth = $("#sendMonth").val();
    if(sendMonth==null||sendMonth==""){
        layer.alert("请选择推送内容！")
        flag = false;
        return;
    }else{
        flag = true;
    }
    var sendDay = $("#sendDay").val();
    if(sendDay==null||sendDay==""){
        layer.alert("每月的推送日期不能为空！")
        return;
    }else{
        flag = true;
    }
    var sendTime = $("#sendTime").val();
    if(sendTime==null||sendTime==""){
        layer.alert("每月的推送日期不能为空！")
        return;
    }else{
        flag = true;
    }
    if(flag){
        layer.confirm('确定推送吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url :'/insuranceSet/sendSms1.do',
                type : 'POST',
                timeout : 20000,
                data:{
                    sendMonth:sendMonth,
                    sendDay:sendDay,
                    sendTime:sendTime
                },
                async: false,
                success : function(result) {
                    console.log(result);
                    layer.alert(result.msg);

                    getinsulist();
                }
            });
        }, function(){

        });
        $(e).parents('.popbox-wrapper').animate({
            opacity: 'hide',top: '0px'
        }, "slow");

        $('.popbox-container').fadeOut();
    }
}
function sendSms2(e){
    var flag = false;
    var integral = $("#integral").val();
    if(integral==null||integral==""){
        layer.alert("请选择推送内容！");
        flag = false;
        return;
    }else{
        flag = true;
    }
    if(isIntNum(integral)){
        flag = true;
    }else{
        layer.alert("您输入的积分不合法！");
        return;
    }
    if(flag){
        layer.confirm('确定推送吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                url :'/insuranceSet/sendSms2.do',
                type : 'POST',
                timeout : 20000,
                data:{
                    integral:integral
                },
                async: false,
                success : function(result) {
                    layer.alert(result.msg);

                    getinsulist();
                }
            });
        }, function(){

        });
        $(e).parents('.popbox-wrapper').animate({
            opacity: 'hide',top: '0px'
        }, "slow");

        $('.popbox-container').fadeOut();
    }
}
function shadboxFunDisable(e){
    id = $(e).attr("a1");
    shadboxFun("add4");
}
function shadboxFunEnable(e){
    id = $(e).attr("a1");
    shadboxFun("add3");
}
function shadboxFunSet1(e){
    id = $(e).attr("a1");
    var trigger_event = $(e).attr("a2");
    shadboxFun("add1");
}
function shadboxFunSet2(e){
    id = $(e).attr("a1");
    shadboxFun("add2");
}
function enable(e){
    $.ajax({
        url :'/insuranceSet/enableSms.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id,
            status:1
        },
        async: false,
        success : function(result) {
            console.log(result);
            layer.alert(result.msg);
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            getinsulist();
        }
    });
}

function disable(e){
    $.ajax({
        url :'/insuranceSet/enableSms.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id,
            status:2
        },
        async: false,
        success : function(result) {
            console.log(result);
            layer.alert(result.msg);
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            getinsulist();
        }
    });
}

function isIntNum(val){
    var regPos = / ^\d+$/; // 非负整数
    var regNeg = /^\-[1-9][0-9]*$/; // 负整数
    if(regPos.test(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }
}
/*]]>*/