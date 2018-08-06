$(document).ready(function() {
    $('#pageSizeInp').val(10);
    countViplist($('#pageSizeInp').val());
    $("#searchbtn").live('click',function(){
        countViplist($('#pageSizeInp').val());
    });
    $("#yl_statu").live('change',function(){
        countViplist($('#pageSizeInp').val());
    });
    $("#medical_statu").live('change',function(){
        countViplist($('#pageSizeInp').val());
    });

});
function countViplist(pageSize){
    var name=$("#name").val();
    var yl_statu=$("#yl_statu").val();
    var medical_statu=$("#medical_statu").val();
    $.ajax({
        url :'/vip/countViplist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            yl_statu:yl_statu,
            medical_statu:medical_statu,
            audit:2,
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
                    getViplist(page,pageSize);
                }
            });
        }
    });
}
function getViplist(pageNum,pageSize){
    var name=$("#name").val();
    var yl_statu=$("#yl_statu").val();
    var medical_statu=$("#medical_statu").val();
    $.ajax({
        url :'/vip/getViplist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            audit:2,
            yl_statu:yl_statu,
            medical_statu:medical_statu,
            pageNum:pageNum,
            pageSize:pageSize
        },
        async: false,
        success : function(result) {
            var datalist=result.rows;
            /*<![CDATA[*/
            const  rowlist=new Array();
            if(datalist!=null &&  datalist.length>0){
                for(var i=0;pageSize>i;i++){
                    const rowlist1=new Array();
                    if(i<datalist.length){
                        var row=datalist[i];
                        rowlist1[0]=row.tel;
                        rowlist1[1]=row.name;
                        rowlist1[2]=row.identNum;
                        rowlist1[3]=row.createTime;
                        rowlist1[4]=row.integration;
                        // switch(row.yl_statu)
                        // {
                        //     case 2:
                        //         rowlist1[7]="正常缴纳";
                        //         break;
                        //     case 3:
                        //         rowlist1[7]="断缴";
                        //         break;
                        //     default:
                        //         rowlist1[7]="未缴纳";
                        // }
                        // switch(row.medical_statu)
                        // {
                        //     case 2:
                        //         rowlist1[8]="正常缴纳";
                        //         break;
                        //     case 3:
                        //         rowlist1[8]="断缴";
                        //         break;
                        //     default:
                        //         rowlist1[8]="未缴纳";
                        // }
                        rowlist1[5]="<a class=\"text-primary\" href=\"javascript:void(0)\" a1='"+row.id+"' a2='"+row.identPicUrl1+"' a3='"+row.identPicUrl2+"' onclick=\"shadboxFun1('detail1',this)\">查看身份证照片</a>" +
                            // "<a href=\"javascript:void(0)\" a1='"+row.bankPicUrl+"' onclick=\"shadboxFun1('detail2',this)\" class=\"text-primary ml10\">查看医保银行卡照片</a>" +
                            "<a href=\"javascript:void(0)\" a1='"+row.edu+"' a2='"+row.professional+"' a3='"+row.email+"' a4='"+row.qq+"' a5='"+row.deptName+"' onclick=\"shadboxFun1('detail3',this)\" class=\"text-primary ml10\">个人信息</a>";
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
            createTable(rowlist,"audittab");
            /*]]>*/
        }
    });
}
function goon(){
    var a=$('#pageNumInp').val();
    if(a!=null && a>0){
        getViplist(a,$('#pageSizeInp').val())
    }else{
        layer.alert("请输入页数!");
    }
}