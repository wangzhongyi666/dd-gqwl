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
        url :'/pictype/pictypecount.do',
        type : 'POST',
        timeout : 20000,
        data:{
            typename:jname,
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
    var url = '/pictype/updatetype.do';
    var purview = isPurview(url);
    $.ajax({
        url :"/pictype/pictypeDataList.do",
        type : 'POST',
        timeout : 20000,
        data:{
            typename:jname,
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
                        rowlist1[0]=row.pictypeid;
                        rowlist1[1]=row.typename;
                        rowlist1[2]=row.tsort;

                        if(purview){
                            rowlist1[3]="<a href=\"javascript:void(0)\" a1="+row.pictypeid+" a2="+row.typename+" " +
                                "a3="+row.tsort+" onclick=\"edit(this);\" class=\"text-primary ml10\">编辑</a>"+
                                "<a href=\"javascript:void(0)\" a1="+row.pictypeid+" onclick=\"deleteType(this)\" " +
                                "class=\"text-primary ml10\">删除</a>";
                        }else{
                            rowlist1[3]="<a href=\"javascript:void(0)\" class=\"text-primary ml10\">编1辑</a>"+
                                "<a href=\"javascript:void(0)\" class=\"text-primary ml10\">删1除</a>";
                        }

                    }else{
                        rowlist1[0]='';
                        rowlist1[1]='';
                        rowlist1[2]='';
                        rowlist1[3]='';

                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"userDataList");
        }
    });
}
function edit(e){
    $("#pictypeid1").val($(e).attr("a1"));
    $("#typename1").val($(e).attr("a2"));
    $("#tsort1").val($(e).attr("a3"));
    shadboxFun('edit');
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
    shadboxFun1('add');


}

function saveType(){
    var typename=$("#typename").val();
    var tsort=$("#tsort").val();
    $.ajax({
        url :'/pictype/addtype.do',
        type : 'POST',
        timeout : 20000,
        data:{
            typename:typename,
            tsort:tsort
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
function deleteType(e){
    var id = $(e).attr("a1");
    if(confirm("确认删除吗！")){
        $.ajax({
            url :'/pictype/deleteptype.do',
            type : 'POST',
            timeout : 20000,
            data:{
                pictypeid:id
            },
            async: false,
            success : function(result) {
                layer.alert(result.msg);

                if(result.msg=="操作成功！"){
                    countlist($('#pageSizeInp').val());

                }

            }
        });
    }

}

//编辑信息
function updatetype() {
    var typename=$("#typename1").val();
    var tsort=$("#tsort1").val();
    var pictypeid=$("#pictypeid1").val();
    $.ajax({
        url :'/pictype/updatetype.do',
        type : 'POST',
        timeout : 20000,
        data:{
            pictypeid:pictypeid,
            typename:typename,
            tsort:tsort
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

// 验证函数
function formValidate1(e) {
    var str = '';
    // 判断名称
    if($.trim($('#typename1').val()).length == 0) {
        str += '分类名称没有输入\n';
        $('#typename1').focus();
    }

    if($.trim($('#tsort1').val()).length == 0) {
        str += '排序没有输入\n';
        $('#tsort1').focus();
    }
    // 如果没有错误则提交
    if(str != '') {
        layer.alert(str);
        return false;
    } else {
        updatetype();
    }
}


// 验证函数
function formValidate(e) {
    var str = '';
    // 判断名称
    if($.trim($('#typename').val()).length == 0) {
        str += '分类名称没有输入\n';
        $('#typename').focus();
    }

    if($.trim($('#tsort').val()).length == 0) {
        str += '排序没有输入\n';
        $('#tsort').focus();
    }
    // 如果没有错误则提交
    if(str != '') {
        layer.alert(str);
        return false;
    } else {
        saveType();
    }
}

/*]]>*/