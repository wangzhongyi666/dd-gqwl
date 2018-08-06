/*<![CDATA[*/
$(function() {
    $('#pageSizeInp').val(10);
    countlist($('#pageSizeInp').val());
    $("#searchbtn").click(function(){
        countlist($('#pageSizeInp').val());
    });
    $(".pagebtn").click(function(){
        $(".active").removeClass("active");
        $(this).parent().addClass("active");
        getlist($(this).text(),$('#pageSizeInp').val());
    });
});

function countlist(pageSize){
    var tname=$("#tname").val();
    var insuStart1=$("#insuStart1").val();
    var insuEnd1=$("#insuEnd1").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/countinsulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tname:tname,
            insuStart1:insuStart1,
            insuEnd1:insuEnd1,
            insuranceType:insuranceType,
            ratio:ratio,
            audit:audit,
            status:2,
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
    var tname=$("#tname").val();
    var insuStart1=$("#insuStart1").val();
    var insuEnd1=$("#insuEnd1").val();
    var insuranceType=$("#insuranceType").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    $.ajax({
        url :'/insurance/getinsulist.do',
        type : 'POST',
        timeout : 20000,
        data:{
            tname:tname,
            insuStart1:insuStart1,
            insuEnd1:insuEnd1,
            insuranceType:insuranceType,
            ratio:ratio,
            audit:audit,
            status:2,
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
                        rowlist1[0]="<input value='"+row.id+"' type=\"checkbox\"/>";
                        rowlist1[1]=row.orderNum;
                        rowlist1[2]=row.insuranceNum;
                        rowlist1[3]=row.name;
                        rowlist1[4]=row.identNum;
                        rowlist1[5]=row.tel;
                        if(row.insuranceType!=null){
                            if(row.insuranceType==1){
                                rowlist1[6]="养老保险";
                            }else if(row.insuranceType==2){
                                rowlist1[6]="医疗保险";
                            }
                        }
                        rowlist1[7]=row.insuStart+"-"+row.insuEnd;
                        rowlist1[8]=row.base;
                        rowlist1[9]=row.ratio;
                        rowlist1[10]=row.unitPrice;

                        if(row.audit!=null){//审核状态:(11:待财务审核;21:财务审核通过(待城市经理审核);22:财务驳回;31:城市经理审核通过;32:城市经理驳回;41：未缴纳)
                            if(row.audit==11){
                                rowlist1[11]="<li class=\"text-danger\">待缴纳</li>";
                            }else if(row.audit==11){
                                rowlist1[11]="待财务审核";
                            }else if(row.audit==21){
                                rowlist1[11]="待城市经理审核";
                            }else if(row.audit==22){
                                rowlist1[11]="财务驳回";
                            }else if(row.audit==31){
                                rowlist1[11]="已缴纳";
                            }else if(row.audit==32){
                                rowlist1[11]="城市经理驳回";
                            }
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
                        rowlist1[8]='';
                        rowlist1[9]='';
                        rowlist1[10]='';
                        rowlist1[11]='';
                    }
                    rowlist[i]=rowlist1;
                }
            }
            createTable(rowlist,"insuranceDateList");
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

function tuibao(e){
    var objId = 'detail3';
    shadboxFun(objId);
    var id = $(e).attr("a1");
    $.ajax({
        url :'/insuranceSet/countInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            id:id
        },
        async: false,
        success : function(result) {

        }
    });
}

/*]]>*/