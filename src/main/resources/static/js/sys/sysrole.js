/*<![CDATA[*/
var nodId=0;
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
    var jname=$("#jname").val();
    $.ajax({
        url :'/sys/roleDataCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            jname:jname,
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
    var jname=$("#jname").val();
    $.ajax({
        url :'/sys/roleDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            jname:jname,
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
                        rowlist1[0]=row.jname;
                        rowlist1[1]=row.jdesc;
                        rowlist1[2]=row.create_time.toString().substring(0,19);
                        rowlist1[3]=row.update_time.toString().substring(0,19);

                        rowlist1[4]="<a href=\"javascript:void(0)\" a1="+row.id+" a2="+row.jname+" onclick=\"getTree(this)\" class=\"text-primary ml10\">编辑</a>"+
                            "<a href=\"javascript:void(0)\" a1="+row.id+" onclick=\"deleteRole(this)\" class=\"text-primary ml10\">删除</a>";
                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';
                        rowlist1[4]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"userDataList");
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
function getTree(e){
    shadboxFun1('add',e);
    $.ajax({
        url :'/sys/getTree.do',
        type : 'POST',
        timeout : 20000,
        data:{
            roleId:$("#roleId").val()
        },
        async: false,
        success : function(result) {
            var tree =result.str;
            $('#tree').treeview({
                data: tree,
                showCheckbox:true,
                levels:0
            });
        }
    });
}

function saveMenu(){
    var roleId=$("#roleId").val();
    var jname=$("#jnameinp").val();
    var a=$('#tree').treeview('getChecked');
    var b= '';
    if(a!=null && a.length>0){
        for(var i=0;i<a.length;i++){
            if(i==a.length-1){
                b+=a[i].menuId;
            }else{
                b+=a[i].menuId+",";
            }

        }
    }
    $.ajax({
        url :'/sys/saveRole.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:roleId,
            jname:jname,
            menustr:b
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            $(this).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }
    });

}
function deleteRole(e){
    var id = $(e).attr("a1");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/sys/deleteRole.do',
            type : 'POST',
            timeout : 20000,
            data:{
                id:id
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);
            }
        });
    }

}
/*]]>*/