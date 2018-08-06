/*<![CDATA[*/
var stock = -999;
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


    $("#all").click(function(){
        if($(this).is(':checked')){
            checkboxed("goods_ids");
        }else{
            uncheckboxed("goods_ids");
        }
    });

    $("#all_zs").click(function(){
        if($(this).is(':checked')){
            stock = 0;
            countlist($('#pageSizeInp').val());
        }else{
            stock = -999;
            countlist($('#pageSizeInp').val());
        }
    });

    $("#plcz").change(function(){
        var plcz = $("#plcz").val();
        if(plcz ==''||plcz== 0){
            return;
        }
        //批量上架
        if(plcz==1){
            onShelfAll();
        }else if(plcz==2){//批量下架
            outShelfAll();
        }else if(plcz==3){//批量删除
            deletgoodsAll();
        }
    });
});

function countlist(pageSize){
    var gname=$("#gname").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var state=$("#state").val();
    var take_type=$("#take_type").val();
    $.ajax({
        url :'/goods/getGoodsCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            gname:gname,
            startDate:startDate,
            endDate:endDate,
            state:state,
            take_type:take_type,
            stock:stock,
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
        url :'/goods/getGoodsDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            gname:gname,
            startDate:startDate,
            endDate:endDate,
            state:state,
            take_type:take_type,
            stock:stock,
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
                        rowlist1[0]='<td><input name="goods_ids" type="checkbox" value="'+row.id+'"></td>';
                        rowlist1[1]=row.gname;
                        rowlist1[2]=row.integral;
                        if(row.state!=null){
                            if(row.state==1){
                                rowlist1[3]=row.stock;
                            }else if(row.state==2){
                                rowlist1[3]="--";
                            }
                        }else{
                            rowlist1[3]="--";
                        }
                        if(row.take_type!=null){
                            if(row.take_type==1){
                                rowlist1[4]="用户自提";
                            }else if(row.take_type==2){
                                rowlist1[4]="邮寄";
                            }
                        }else{
                            rowlist1[4]="<li>--</li>";
                        }
                        if(row.state!=null){
                            if(row.state==1){
                                rowlist1[5]="<li>上架</li>";
                            }else if(row.state==2){
                                rowlist1[5]="<li>下架</li>";
                            }
                        }else{
                            rowlist1[5]="<li>--</li>";
                        }
                        rowlist1[6]=row.createTime;
                        if(row.state!=1){
                            rowlist1[7]="<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"onShelf(this)\" class=\"text-primary ml10\">上架</a>"+
                                "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"deletgoods(this)\" class=\"text-primary ml10\">删除</a>"+
                                "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"goodsinfo(this)\" class=\"text-primary ml10\">查看详情</a>"+
                                "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"updategoods(this)\" class=\"text-primary ml10\">修改</a>";
                        }else{
                            rowlist1[7]="<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"outShelf(this)\" class=\"text-primary ml10\">下架</a>"+
                                "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"deletgoods(this)\" class=\"text-primary ml10\">删除</a>"+
                                "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"goodsinfo(this)\" class=\"text-primary ml10\">查看详情</a>"+
                                "<a href=\"javascript:void(0)\" a1='"+row.id+"' onclick=\"updategoods(this)\" class=\"text-primary ml10\">修改</a>";
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
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"goodsDataList");
        }
    });
}

function goon(e){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getlist(a,$('#pageSizeInp').val())
    }else{
        layer.alert("请输入页数!");
    }
}

function onShelf(e){
    var goods_id = $(e).attr("a1");
    layer.confirm('确定上架吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/upAudit.do',
            type : 'POST',
            timeout : 20000,
            data:{
                goods_id:goods_id,
                state:1
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);


                countlist($('#pageSizeInp').val());
            }
        });
    }, function(){
    });
}
function outShelf(e){
    var goods_id = $(e).attr("a1");
    layer.confirm('确定下架吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/upAudit.do',
            type : 'POST',
            timeout : 20000,
            data:{
                goods_id:goods_id,
                state:2
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);


                countlist($('#pageSizeInp').val());
            }
        });
    }, function(){
    });
}


function onShelfAll(){
    var goods_idss = document.getElementsByName("goods_ids");
    var objarray = goods_idss.length;
    var goods_ids = "";
    for (i = 0; i < objarray; i++) {
        if (goods_idss[i].checked == true) {
            goods_ids += goods_idss[i].value + ",";
        }
    }
    layer.confirm('确定批量下架吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/upAudit.do',
            type : 'POST',
            timeout : 20000,
            data:{
                goods_ids:goods_ids,
                state:2
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);


                countlist($('#pageSizeInp').val());
            }
        });
    }, function(){
        return;
    });
}
function outShelfAll(){
    var goods_idss = document.getElementsByName("goods_ids");
    var objarray = goods_idss.length;
    var goods_ids = "";
    for (i = 0; i < objarray; i++) {
        if (goods_idss[i].checked == true) {
            goods_ids += goods_idss[i].value + ",";
        }
    }
    layer.confirm('确定批量下架吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/upAudit.do',
            type : 'POST',
            timeout : 20000,
            data:{
                goods_ids:goods_ids,
                state:2
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);


                countlist($('#pageSizeInp').val());
            }
        });
    }, function(){
        return;
    });
}
function deletgoods(e){
    var goods_id = $(e).attr("a1");
    layer.confirm('确定删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/deletgoods.do',
            type : 'POST',
            timeout : 20000,
            data:{
                goods_id:goods_id
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);


                countlist($('#pageSizeInp').val());
            }
        });
    }, function(){
        return;
    });
}

function deletgoodsAll(){
    var goods_idss = document.getElementsByName("goods_ids");
    var objarray = goods_idss.length;
    var goods_ids = "";
    for (i = 0; i < objarray; i++) {
        if (goods_idss[i].checked == true) {
            goods_ids += goods_idss[i].value + ",";
        }
    }
    layer.confirm('确定删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url :'/goods/deletgoodsAll.do',
            type : 'POST',
            timeout : 20000,
            data:{
                goods_ids:goods_ids
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
function goodsinfo(e){
    var goods_id = $(e).attr("a1");
    window.location.href = "/goods/goodsInfo.do?goods_id="+goods_id;
}

function updategoods(e){
    var goods_id = $(e).attr("a1");
    window.location.href = "/goods/editgoods.do?goods_id="+goods_id;
}
/*]]>*/