$(document).ready(function() {
    getCountPayment();
});
function getCountPayment(){
    $.ajax({
        url :'/finance/getCountPayment.do',
        type : 'POST',
        timeout : 20000,
        data:{},
        async: false,
        success : function(result) {
            $("#ml").text(result.ml+"元");
            $("#my").text(result.my+"元");
            $("#mz").text(result.mz+"元");
            $("#yl").text(result.yl+"元");
            $("#yy").text(result.yy+"元");
            $("#yz").text(result.yz+"元");
            $("#ql").text(result.ql+"元");
            $("#qy").text(result.qy+"元");
            $("#qz").text(result.qz+"元");
            $("#ye").text(result.ye);
            $("#xf").text(result.xf);
            $("#th").text(result.th);
            $("#zf").text(result.zf);
            $("#mlq").text(result.mlq+"元");
            $("#myq").text(result.myq+"元");
            $("#mzq").text(result.mzq+"元");
            $("#qlq").text(result.qlq+"元");
            $("#qyq").text(result.qyq+"元");
            $("#qzq").text(result.qzq+"元");
            $("#pq").text(result.pq+"元");
            $("#lcz").text(result.lcz+"元");
            $("#ycz").text(result.ycz+"元");
            $("#zcz").text(result.zcz+"元");
        }
    });
}