/*<![CDATA[*/
$(document).ready(function() {
    if($("#take_type").val()==2){
        if($("#check_address").val()=="true"){
            $("#alist").show();
           // $("#take_type1").hide();
            $("#tabs",parent.document).css("height",$(".info").innerHeight());
        }else{
            $("#add_address").show();
            $("#ex_integrals").hide();
            $("#tabs",parent.document).css("height",$(".info").innerHeight());
        }
    }else{
        //$("#take_type2").hide();
        $("#add_address").hide();
        $("#alist").hide();
    }
});
function exchengeJifen(){
    $.ajax({
        url :'/handApp/hasPayPwd.do',
        type : 'POST',
        timeout : 20000,
        data:{
        },
        async: false,
        success : function(result) {
            if(result.flag){
                shadboxFun('tishi2');
            }else{
                shadboxFun('tishi1')
            }
        }

    });
}
function enterExchange(e){
    var take_type = $("#take_type").val();
    var order_number = $("#order_number").text();
    var goods_name = $("#goods_name").text();
    var nums = $("#nums").text();
    var integral = $("#integral").text();
    var address_id = $("#address_id").text();
    var address = $("#address").text();
    var tel = $("#tel").text();
    var name = $("#name").text();
    var address_id = $("#address_id").text();
    var pay_pwd = $("#pay_pwd").val();
    var goods_id = $("#goods_id").val()
    //判断剩余积分是否足够购买该商品
    $.ajax({
        url :'/handApp/enterExchange.do',
        type : 'POST',
        timeout : 20000,
        data:{
            take_type : take_type,
            order_number:order_number,
            goods_name:goods_name,
            nums:nums,
            integral:integral,
            onsignee_name:name,
            deliver_goods_tel:tel,
            address_id:address_id,
            pay_pwd:pay_pwd,
            goods_id:goods_id,
            deliver_goods_address:address
        },
        async: false,
        success : function(result) {

            if(result.result_code=="1"){
                   window.location.href = "/handApp/paySuccess.shtml?order_number="+order_number
            }else{
                   layer.alert(result.result_msg);
            }


            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
        }

    });
    //if(take_type == 2){
    /*   $.ajax({
           url :'/handApp/enterExchange.do',
           type : 'POST',
           timeout : 20000,
           data:{
               take_type : take_type,
               order_number:order_number,
               goods_name:goods_name,
               nums:nums,
               integral:integral,
               onsignee_name:name,
               deliver_goods_tel:tel,
               address_id:address_id,
               pay_pwd:pay_pwd
           },
           async: false,
           success : function(result) {
               if(result.result_code=="1"){
                   window.location.href = "/handApp/paySuccess.shtml?order_number="+order_number
               }else{
                   layer.alert(result.result_msg);
               }
               $(e).parents('.popbox-wrapper').animate({
                   opacity: 'hide',top: '0px'
               }, "slow");

               $('.popbox-container').fadeOut();
           }

       });
   }else{

   }*/

}
function change(){
    var address_id = $("#address_id").text();
    $.ajax({
        url :'/handApp/addresslist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:address_id
        },
        async: false,
        success : function(result) {
            if(result.size==1){
                $("#add_address").show();
            }
        }

    });
}
/*]]>*/