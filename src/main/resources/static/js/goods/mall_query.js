/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());
    $("#searchbtn").on('click',function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").on('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });
});

function checkDate(e){
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var flag = false;

    $.ajax({
        url :'/goods/getGoodsQueryCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            startDate:startDate,
            endDate:endDate,
            pageSize:1
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

function countlist(pageSize){
    var gname=$("#gname").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var state=$("#state").val();
    var take_type=$("#take_type").val();
    $.ajax({
        url :'/goods/getGoodsQueryCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            gname:gname,
            startDate:startDate,
            endDate:endDate,
            state:state,
            take_type:take_type,
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
    var gname=$("#gname").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var state=$("#state").val();
    var take_type=$("#take_type").val();
    $.ajax({
        url :'/goods/getGoodsQueryDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            gname:gname,
            startDate:startDate,
            endDate:endDate,
            state:state,
            take_type:take_type,
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
                        rowlist1[0]=row.gname;
                        rowlist1[1]=row.integral;
                        rowlist1[2]=row.exchanged_nums;
                        rowlist1[3]=row.not_exchange_nums;
                        rowlist1[4]=row.pick_up_nums;
                        rowlist1[5]=row.mail_nums;
                        rowlist1[6]=row.writed_off_nums;
                        rowlist1[7]=row.write_off_nums;
                        rowlist1[8]=row.write_off_nums;
                        rowlist1[9]=row.write_off_nums;
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
            createTable(rowlist,"goodsQueryDataList");
        }
    });
}

function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getlist(a,$('#pageSizeInp').val())
    }else{
        alert("请输入页数!");
    }
}
/*]]>*/