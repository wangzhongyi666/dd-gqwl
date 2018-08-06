/*<![CDATA[*/
$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countWebInfo($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countWebInfo($('#pageSizeInp').val());
    });
});
function countWebInfo(pageSize){
    var title=$("#title").val();
    $.ajax({
        url :'/web/countWebInfo.do',
        type : 'POST',
        timeout : 20000,
        data:{
            title:title,
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
                    getwebinfolist(page,pageSize);
                }
            });
        }
    });
}
function getwebinfolist(pageNum,pageSize){
    var title=$("#title").val();
    $.ajax({
        url :'/web/getwebinfolist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            title:title,
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
                        rowlist1[0]=row.title;
                        if(row.cont!=null && row.cont!=''){
                            rowlist1[1]='<h style="width:230px;margin:auto;display: -webkit-box;-webkit-line-clamp: 2; -webkit-box-orient: vertical;overflow: hidden;">'+row.cont+'</h>'
                        }else{
                            rowlist1[1]=row.cont;
                        }
                        rowlist1[2]="<a href=\"javascript:void(0)\" a1='"+datalist[i].id+"' a2='"+datalist[i].picurl2+"' a3='"+datalist[i].picurl3+"' onclick=\"shadboxFun1('info1',this)\" class=\"text-primary\">查看图片</a>"
                        rowlist1[3]=row.releTime;
                        rowlist1[4]='<a class="text-primary" href="/web/webadd.do?id='+row.id+
                            '&title='+row.title+'&cont='+row.cont+'&picurl1='+row.picurl1+'&picurl2='+row.picurl2+'&picurl3='+row.picurl3+
                            '">编辑</a><a href="javascript:void(0)" a1="'+row.id+'" class="text-primary ml10" onclick="shadboxFun1(\'tishi1\',this)">删除</a>';
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
            createTable(rowlist,"audittab");
        }
    });
}
function deletinfo(){
    var id=$("#vipid").val();
    $.ajax({
        url :'/web/deletinfo.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            $("#tishi1").hide();
            $(this).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            countWebInfo($('#pageSizeInp').val());
        }
    });
}
/*]]>*/