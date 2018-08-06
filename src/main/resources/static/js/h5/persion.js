/*<![CDATA[*/
$(document).ready(function() {
    // $("#tabs",parent.document).css("height",$(".info").innerHeight());
    /* var nowTime=new Date();
     $("#insuStart").text(nowTime.Format("yyyy/MM"));

     $(".con").on('click',function(){
         $(".con").removeClass("cur1");
         $(".con2").removeClass('cur');
         $(this).addClass("cur1");
         $("#customTime").hide();
         $("#customTime").val("");
     });

     $(".con1").on('click',function(){
         $(".con2").removeClass("cur");
         $(this).addClass("cur");
         $("#customTime").hide();
         $("#customTime").val("");
     });*/

     $(".con3").on('click',function(){
         $(".con3").removeClass("cur2");
         $(this).addClass("cur2");
         if($(this).text()=="60%"){
            $("#tishi_1").show();
         }else{
             $("#tishi_1").hide();
         }
     });
    //执行一个laydate实例
    /*lay('#custom').on('click', function(e) {
        if(!$("#custom").hasClass("cur")){
            $("#customTime").show();
            $(".con1").removeClass('cur');
            $(".con").removeClass('cur1');
            $("#custom").addClass('cur');
            laydate.render({
                elem: '#customTime'
                , type: 'month'
                , format: 'yyyy/MM'
                , range: true
                , show: true
            });
        }else{
            $("#customTime").hide();
            $("#customTime").val("");
        }
    });*/
});
function sumbmit(){
    var monthli=$("#monthli").text();
    var monar=monthli.split("-");
    //var insuStart = $("#insuStart").text();
    //var insuEnd = $(".cur1").text();
    var insuStart = $("#insuStart").val();
    var insuEnd = $("#insuEnd").val();
    //var customTime = $("#customTime").val();
    var ratio = $(".cur2").text().replace("%","");
    var base = $("#base").text();
    var flag = false;
    /*if(customTime != null && customTime != ''){
        var customs = customTime.split("-");
        insuStart = customs[0];
        insuEnd = customs[1];
    }*/
    if(insuStart==null||insuStart==""){
        layer.alert("开始时间不能为空！");
        flag = true;
        return;
    }else if(monar!=null && monar.length>0 && insuStart<monar[0] && ratio=="60"){
        layer.alert("您的参保月份不在缴费基数60%的规定参保月份之内，请重新选择！");
        flag = true;
        return;
    }
    if(insuEnd==null||insuEnd==""){
        layer.alert("结束时间不能为空！");
        flag = true;
        return;
    }else if(monar!=null && monar.length>0 && insuEnd>monar[1] && ratio=="60"){
        layer.alert("您的参保月份不在缴费基数60%的规定参保月份之内，请重新选择！");
        flag = true;
        return;
    }
    if(ratio==null||ratio==""){
        layer.alert("利率不能为空！");
        flag = true;
        return;
    }
    if(base==null||base==""){
        layer.alert("基数不能为空！");
        flag = true;
        return;
    }

    var beginDate = insuStart+"/01";
    var endDate = insuEnd+"/01";
    var d1 = new Date(beginDate);
    var d2 = new Date(endDate);

    if(beginDate!=""&&endDate!=""&&d1>d2)
    {
        layer.alert("开始时间不能大于结束时间！");
        return false;
    }
    window.location.href="/handApp/yldetail.shtml?insuStart="+insuStart+"&insuEnd="+insuEnd+"&ratio="+ratio+"&base="+base;

/*    if(!flag){"&customTime="+customTime+
        $.ajax({
            url :'/handApp/addOrder.do',
            type : 'POST',
            timeout : 20000,
            async: false,
            data:{
                insuStart:insuStart,
                insuEnd:insuEnd,
                customTime:customTime,
                ratio:ratio,
                base:base
            },
            async: false,
            success : function(result) {
                alert(result.result_msg);

            }
        });
    }*/

}
/*]]>*/