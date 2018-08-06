/*<![CDATA[*/
$(document).ready(function() {
    laydate.render({
            elem: '#demo'
        });
    laydate.render({
        elem: '#demo2'
    });

    $('#pageSizeInp').val(10);
    countAuditlist($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countAuditlist($('#pageSizeInp').val());
    });
    $("#auditvalue").live('change',function(){
        countAuditlist($('#pageSizeInp').val());
    });
    $("#laydate_table").live('click',function(){
        alert("ddd");
        //getAuditlist();
    });
    $("#ud1").live('mouseover',function(){
        $("#ut1").css("background-color","#e60039");
        $("#ut1").css("color","#FFFFFF");
    });
    $("#ud1").live('mouseout',function(){
        $("#ut1").css("color","#e60039");
        $("#ut1").css("background-color","#FFFFFF");
    });
    $("#ud2").live('mouseover',function(){
        $("#ut2").css("background-color","#e60039");
        $("#ut2").css("color","#FFFFFF");
    });
    $("#ud2").live('mouseout',function(){
        $("#ut2").css("color","#e60039");
        $("#ut2").css("background-color","#FFFFFF");
    });
    $(".pagebtn").live('click',function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getAuditlist($(this).text(),$('#pageSizeInp').val());
    });
});
function countAuditlist(pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var auditvalue=$("#auditvalue").val();
    $.ajax({
        url :'/vip/countAuditlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            subtime1:demo,
            subtime2:demo2,
            audit:auditvalue,
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
                    getAuditlist(page,pageSize);
                }
            });
        }
    });
}
function getAuditlist(pageNum,pageSize){
    var name=$("#name").val();
    var demo=$("#demo").val();
    var demo2=$("#demo2").val();
    var auditvalue=$("#auditvalue").val();
    $.ajax({
        url :'/vip/getAuditlist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            subtime1:demo,
            subtime2:demo2,
            audit:auditvalue,
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
                        rowlist1[0]=row.tel;
                        rowlist1[1]=row.name;
                        rowlist1[2]=row.identNum;
                        rowlist1[3]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.identPicUrl1+"' a3='"+row.identPicUrl2+"' onclick=\"shadboxFun1('detail',this)\">查看详情</a>"
                        rowlist1[4]=row.submitTime;
                        if(row.audit!=null){
                            if(row.audit==1){
                                rowlist1[5]="<li class=\"text-danger\">待审核</li>";
                                rowlist1[6]="<a class=\"text-primary\" a1='"+row.id+"' href=\"javascript:void(0)\" onclick=\"shadboxFun1('tishi1',this)\">通过</a><a class=\"text-primary ml10\" a1='"+row.id+"' href=\"javascript:void(0)\" onclick=\"shadboxFun1('tishi2',this)\">驳回</a>";
                            }else if(row.audit==2){
                                rowlist1[5]="已通过";
                                rowlist1[6]="<span class=\"text-info\" >通过</span><span class=\"text-info ml10\">驳回</span>";
                            }else if(row.audit==3){
                                rowlist1[5]="已驳回";
                                rowlist1[6]="<span class=\"text-info\">通过</span><span class=\"text-info ml10\">驳回</span>";
                            }
                        }else{
                            rowlist1[5]='';
                            rowlist1[6]='';
                        }
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
            createTable(rowlist,"audittab");
        }
    });
}
function quxiaobtn1() {
    $("#tishi1").hide();
}
function quxiaobtn2() {
    $("#tishi2").hide();
}
function upAudit(audit){
    var id=$("#vipid").val();
    $.ajax({
        url :'/vip/updateAuditById.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id,
            audit:audit
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg);
            $("#tishi1").hide();
            $("#tishi2").hide();
            $(this).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            countAuditlist($('#pageSizeInp').val());
        }
    });
}
function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getAuditlist(a,$('#pageSizeInp').val())
    }else{
        layer.alert("请输入页数!");
    }
}
function imgChange(type){
    function getObjectURL(file) {
        var url = null;
        if (window.createObjcectURL != undefined) {
            url = window.createOjcectURL(file);
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
    var path = getObjectURL(document.getElementById("file"+type).files[0]);
    $("#idenimg"+type).attr('src',path);
    $("#idenimg"+type).css('background-size',"cover");
}
function uploadurl(e){
    var filepath1=$("#file1").value;
    var filepath2=$("#file2").value;
    if(filepath1!=null && filepath1!=""){
        var extStart=filepath1.lastIndexOf(".");
        var ext=filepath1.substring(extStart,filepath1.length).toUpperCase();
        if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
            layer.alert("提示","图片格式不正确，请重新上传！", "info");
            return false;
        }
    }
    if(filepath2!=null && filepath2!=""){
        var extStart=filepath2.lastIndexOf(".");
        var ext=filepath2.substring(extStart,filepath2.length).toUpperCase();
        if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
            layer.alert("提示","图片格式不正确，请重新上传！", "info");
            return false;
        }
    }
    var id=$("#vipid").val();
    if($("#imgform").form('validate')){
        $("#imgform").form('submit', {
            url:'/vip/uploadImgUrl.do?id='+id,
            success:function(data){
                var c = jQuery.parseJSON(data);
                layer.alert(c.msg);
                $(e).parents('.popbox-wrapper').animate({
                    opacity: 'hide',top: '0px'
                }, "slow");
                $('.popbox-container').fadeOut();
                countAuditlist($('#pageSizeInp').val());
                /!*$.messager.alert("提示",c.msg, "info"); *!/
            }
        });
    }
}
/*]]>*/