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

    $("#all").on('click',function(){
        if($(this).is(':checked')){
            checkboxed("ordeem_code_ids");
        }else{
            uncheckboxed("ordeem_code_ids");
        }
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
                        rowlist1[0]='<td><input name="ordeem_code_ids" type="checkbox" value="'+row.id+'"></td>';
                        rowlist1[1]=row.goods_name;
                        rowlist1[2]=row.redeem_code;
                        if(row.status==1){
                            rowlist1[3]='未兑换'
                        }else if(row.status==2){
                            rowlist1[3]='已兑换'
                        }else if(row.status==3){
                            rowlist1[3]='已核销'
                        }else if(row.status==4){
                            rowlist1[3]='已过期'
                        }
                        rowlist1[4]=row.term_of_validity_end;
                        rowlist1[5]=row.create_time;
                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                        rowlist1[5]='';
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

function deletRedeemCodeAll(){
    var ordeem_code_idss = document.getElementsByName("ordeem_code_ids");
    var objarray = ordeem_code_idss.length;
    if(objarray==0){
        layer.alert("请选择兑换码！");
        return;
    }
    var ordeem_code_ids = "";
    for (i = 0; i < objarray; i++) {
        if (ordeem_code_idss[i].checked == true) {
            ordeem_code_ids += ordeem_code_idss[i].value + ",";
        }
    }
    layer.confirm('确定删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/deletRedeemCodeAll.do',
            type : 'POST',
            timeout : 20000,
            data:{
                ordeem_code_ids:ordeem_code_ids
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);
                countlist($('#pageSizeInp').val());
            }
        });
    }, function(){
        countlist($('#pageSizeInp').val());
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