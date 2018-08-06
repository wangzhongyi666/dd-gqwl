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

function countlist(pageSize){
    var goods_name=$("#goods_name").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var status=$("#status").val();
    $.ajax({
        url :'/goods/getCodeCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            goods_name:goods_name,
            startDate:startDate,
            endDate:endDate,
            status:status,
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
    var goods_name=$("#goods_name").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var status=$("#status").val();
    $.ajax({
        url :'/goods/getCodeDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            goods_name:goods_name,
            startDate:startDate,
            endDate:endDate,
            status:status,
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
                        rowlist1[0]=row.goods_name;
                        rowlist1[1]=row.redeem_code;
                        rowlist1[2]=row.shop_name;
                        if(row.status==null){
                            rowlist1[3]="--";
                        }else{
                            if(row.status==1){
                                rowlist1[3]="未核销";
                            }else if(row.status==2){
                                rowlist1[3]="已核销";
                            }else if(row.status==3){
                                rowlist1[3]="已核销";
                            }else if(row.status==4){
                                rowlist1[3]="已过期";
                            }
                        }
                        rowlist1[4]=row.write_off_time;
                        rowlist1[5]=row.status_zh;
                        rowlist1[6]=row.create_time;
                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                        rowlist1[5]='';
                        rowlist1[6]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"dataList");
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
/*]]>*/