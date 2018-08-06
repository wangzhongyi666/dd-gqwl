/*<![CDATA[*/
$(document).ready(function() {

    $(".btn1").on('click',function(){
        $(".cur").removeClass("cur");
        $(this).addClass("cur");
    });
   /* $('#pageSizeInp').val(3);
    countlist($('#pageSizeInp').val());
    $("#searchbtn").on('click',function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });*/
});

function setInsurance(){

    var ylao_id = $("#ylao_id").val();
    var yiliao_id = $("#yiliao_id").val();
    var expends_base=$("#expends_base").val();
    var result = "";
    result = validateMoney(expends_base);
    if (result != "Y") {
        layer.alert(result);
        return false;
    }
    var expends_amounts = "";

    $("input[name='expends_amount']").each(function(key,value){
        if($(value).val()!=null&&$(value).val()!=''){
            result = validateMoney($(value).val());
            expends_amounts += $(value).val()+",";
        }
    })

    var expends_scales = "";

    $("em[name='expends_scale']").each(function(key,value){
            expends_scales += $(value).text()+",";

    })
    if (result != "Y") {
        layer.alert(result);
        return false;
    }

    var state="";
    state = $(".cur").val();
    if(state=='开启'){
        state = 1;
    }else{
        state = 2;
    }
    var effect_start_time=$("#effect_start_time").val();

    var effect_end_time=$("#effect_end_time").val();
    var expends_base1=$("#expends_base1").val();
    result = validateMoney(expends_base1);
    if (result != "Y") {
        layer.alert(result);
        return false;
    }
    var expends_amount5=$("#expends_amount5").val();
    result = validateMoney(expends_amount5);
    if (result != "Y") {
        layer.alert(result);
        return false;
    }
    console.log(expends_amounts);

    var adjustment_time_start = $("#adjustment_time_start").val();
    if(adjustment_time_start==null||adjustment_time_start==""){
        layer.alert("基数调整月份不能为空！")
        return;
    }

    var yb_adjustment_time_start = $("#yb_adjustment_time_start").val();
    if(yb_adjustment_time_start==null||yb_adjustment_time_start==""){
        layer.alert("基数调整月份不能为空！")
        return;
    }
    $.ajax({
        url :'/insuranceSet/setInsurance.do',
        type : 'POST',
        timeout : 20000,
        data:{
            expends_base:expends_base,
            expends_amounts:expends_amounts,
            expends_scales:expends_scales,
            state:state,
            effect_start_time:effect_start_time,
            effect_end_time:effect_end_time,
            expends_base1 : expends_base1,
            expends_amount5:expends_amount5,
            ylao_id:ylao_id,
            yiliao_id:yiliao_id,
            adjustment_time_start:adjustment_time_start,
            yb_adjustment_time_start:yb_adjustment_time_start
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            window.location.href="/insuranceSet/insuranceSet.do";
        }
    });
}
/**
 * 金额校验
 * @param money
 * @returns {*}
 */
function validateMoney(money) {
    var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    if (reg.test(money)) {
        return "Y";
    }
    return "请输入正确的金额,且最多两位小数!";
}
/*]]>*/