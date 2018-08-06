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
    var name=$("#name").val();
    var parentId=$("#parentId").val();
    $.ajax({
        url :'/sys/deptDataCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            dre_type:1,
            parentId:parentId,
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
    var name=$("#name").val();
    var parentId=$("#parentId").val();
    $.ajax({
        url :'/sys/deptDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            dre_type:1,
            parentId:parentId,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            const  rowlist=new Array();
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;datalist.length>i;i++){
                    var row=datalist[i];
                    const rowlist1=new Array();

                    rowlist1[0]=row.name;
                    rowlist1[1]=row.rank;
                    rowlist1[2]=row.createTime;
                    rowlist1[3]="<a href=\"javascript:void(0)\" a1="+row.deptId+" onclick=\"deletsubdept(this)\" class=\"text-primary ml10\">删除</a>";
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"subDeptDataList");
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
function deletsubdept(e){
    layer.confirm('确定删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var deptId = $(e).attr("a1");
        $.ajax({
            url :'/sys/deleteDept.do',
            type : 'POST',
            timeout : 20000,
            data:{
                deptId:deptId
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
    }, function(){

    });
}
function addDept(e){
    var flag = false;
    var deptId = $("#deptSelect").val();
    var bank = $("#bank").val();
    var rank = $("#rank").val();
    if(bank==null||bank==''){
        layer.alert("开户行不能为空！");
        return;
    }else{
        flag = true;
    }
    if(rank==null||rank==''){
        layer.alert("排序不能为空！");
        return;
    }else{
        flag = true;
    }
    if(flag){
        $.ajax({
            url :'/sys/updateDeptByDeptId.do',
            type : 'POST',
            timeout : 20000,
            data:{
                bank:bank,
                dre_type:1,
                rank:rank,
                deptId:deptId
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
/*]]>*/