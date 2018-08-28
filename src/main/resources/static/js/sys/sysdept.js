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
    $.ajax({
        url :'/sys/deptDataCount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
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
    $.ajax({
        url :'/sys/deptDataList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            const  rowlist=[];
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;pageSize>i;i++){
                    const rowlist1=[];
                    if(i<datalist.length){
                        var row=datalist[i];
                        rowlist1[0]=row.name;
                        rowlist1[1]=row.rank;
                        rowlist1[2]=row.subDeptNames;
                        rowlist1[3]=row.createTime;
                        rowlist1[4]="<a href=\"/sys/sysBusDept.do?deptId="+row.deptId+"&deptName="+row.name+"\" class=\"text-primary ml10\">市级地区("+row.subCount+")</a>";
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
            createTable(rowlist,"deptDataList");
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