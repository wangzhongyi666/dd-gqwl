/*<![CDATA[*/
$(function() {
    laydate.render({
        elem: '#demo',
        type: 'month',
        format: 'yyyy/MM',
        show: false
    });
    laydate.render({
        elem: '#demo2'
        , type: 'month'
        , format: 'yyyy/MM'
        , show: false
    });
    $("#sheng").change(function(){
        var pid = $("#sheng").val();
        if(pid =='' || pid ==0){
            $("#shi").empty();
            $("#shi").append("<option value='0'>请选择</option>");
            return;
        }
        $.ajax({
            url:'/handApp/selectPRDept.do',
            async:false,
            type:'post',
            data:{parentId:pid},
            success:function(data){
                var datalist=data.data;
                var t2 = $("#shi").empty();
                t2.append("<option value='0'>请选择</option>");
                for ( var i = 0; i < datalist.length; i++) {
                    t2.append("<option value='"+datalist[i].deptId+"'>"+ datalist[i].name+"</option>");
                }
            }
        })
    });

    $("#shi").change(function(){
        var pid = $("#shi").val();
        if(pid =='' || pid ==0){
            $("#qu").empty();
            $("#qu").append("<option value='0'>请选择</option>");
            return;
        }
        $.ajax({
            url:'/handApp/selectPRDept.do',
            async:false,
            type:'post',
            data:{parentId:pid},
            success:function(data){
                var datalist=data.data;
                var t2 = $("#qu").empty();
                t2.append("<option value='0'>请选择</option>");
                for ( var i = 0; i < datalist.length; i++) {
                    t2.append("<option value='"+datalist[i].deptId+"'>"+ datalist[i].name+"</option>");
                }
            }
        })
    });
    $("#ylsheng").change(function(){
        var pid = $("#ylsheng").val();
        if(pid ==''){
            return;
        }

        $.ajax({
            url:'/sys/deptSubDataList.do',
            async:false,
            type:'post',
            data:{parentId:pid},
            success:function(data){
                var t2 = $("#ylshi").empty();

                for ( var k = 0; k < data.rows.length; k++) {
                    t2.append("<option value='"+data.rows[k].deptId+"'>"+ data.rows[k].name+"</option>");
                    if(data.rows[k].deptId==$("#ylsheng").val()){
                        console.log(pid);
                        $.ajax({
                            url:'/sys/deptSubDataList.do',
                            async:false,
                            type:'post',
                            data:{parentId:data.rows[k].deptId},
                            success:function(data1){

                                var t3 = $("#ylqu").empty();
                                for ( var o = 0; o < data1.rows.length; o++) {
                                    t3.append("<option value='"+data1.rows[o].deptId+"'>"+ data1.rows[o].name+"</option>");
                                }
                            }
                        })
                    }
                }
            }
        })
    });
    $("#ylshi").change(function(){
        var pid = $("#ylshi").val();
        if(pid ==''){
            return;
        }

        $.ajax({
            url:'/sys/deptSubDataList.do',
            async:false,
            type:'post',
            data:{parentId:pid},
            success:function(data){
                var t3 = $("#ylqu").empty();
                for ( var i = 0; i < data.rows.length; i++) {
                    t3.append("<option value='"+data.rows[i].deptId+"'>"+ data.rows[i].name+"</option>");
                }
            }
        })
    });


    $(".btn1").click(function(){
        $(".cur").removeClass("cur");
        $(this).addClass("cur");
    });
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
function printp(){
    // $.ajax({
    //     url :'/print/setInsurance.do',
    //     type : 'POST',
    //     timeout : 20000,
    //     data:{
    //         fileName:"http://192.168.1.159:8080/aptitude/201805111023010292.jpg",
    //         count:1
    //     },
    //     async: false,
    //     success : function(result) {
    //         layer.alert(result.msg());
    //     }
    // });

    $("#identPicUrl3").attr('src',$("#identPicUrl1")[0].src);
    $("#identPicUrl4").attr('src',$("#identPicUrl2")[0].src);
    $("#printul1").show();
    $("#printul1").jqprint({
        debug: false,//如果是true则可以显示iframe查看效果，默认是false
        importCSS: true,//true表示引进原来的页面的css，默认是true。
        printContainer: true,//表示如果原来选择的对象必须被纳入打印，默认是true。
        operaSupport: true///表示如果插件也必须支持歌opera浏览器，默认是true。
    });
    $("#printul1").hide();
}

function checkDate(e){
    var subStartTime=$("#subStartTime1").val();
    var subEndTime=$("#subEndTime1").val();
    var flag = false;

    $.ajax({
        url :'/insuranceSet/countInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            subStartTime:subStartTime,
            subEndTime:subEndTime,
            pageSize:1
        },
        async: false,
        success : function(result) {
            console.log(result.allNum);
            if(result.allNum>0){
                $(e).parents('.popbox-wrapper').animate({
                    opacity: 'hide',top: '0px'
                }, "slow");
                $('.popbox-container').fadeOut();
                flag = true;
            }else{
                layer.alert("您选择的该时间段内没有数据，请重新选择。");
                flag = false;
            }
        }
    });
    return flag;
}

function countlist(pageSize){
    var name=$("#name").val();
    var insuStart=$("#insuStart").val();
    var insuEnd=$("#insuEnd").val();
    var insuranceType=$("#insuranceType").val();
    var subStartTime=$("#subStartTime").val();
    var subEndTime=$("#subEndTime").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    var insuranceNature=$("#insuranceNature").val();

    $.ajax({
        url :'/insuranceSet/countInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            insuStart:insuStart,
            insuEnd:insuEnd,
            insuranceType:insuranceType,
            subStartTime:subStartTime,
            subEndTime:subEndTime,
            ratio:ratio,
            audit:audit,
            insuranceNature:insuranceNature,
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
    var insuStart=$("#insuStart").val();
    var insuEnd=$("#insuEnd").val();
    var insuranceType=$("#insuranceType").val();
    var subStartTime=$("#subStartTime").val();
    var subEndTime=$("#subEndTime").val();
    var ratio=$("#ratio").val();
    var audit=$("#audit").val();
    var insuranceNature=$("#insuranceNature").val();
    $.ajax({
        url :'/insuranceSet/dataInsList.do',
        type : 'POST',
        timeout : 20000,
        data:{
            name:name,
            insuStart:insuStart,
            insuEnd:insuEnd,
            insuranceType:insuranceType,
            subStartTime:subStartTime,
            subEndTime:subEndTime,
            ratio:ratio,
            audit:audit,
            insuranceNature:insuranceNature,
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
                        rowlist1[0]=row.orderNum;
                        rowlist1[1]=row.insuranceNum;
                        rowlist1[2]=row.name;
                        rowlist1[3]=row.identNum;
                        rowlist1[4]=row.tel;
                        if(row.insuranceType!=null){
                            if(row.insuranceType==1){
                                rowlist1[5]="养老保险";
                            }else if(row.insuranceType==2){
                                rowlist1[5]="医疗保险";
                            }
                        }
                        rowlist1[6]=row.insuStart+'-'+row.insuEnd;
                        rowlist1[7]=row.base;
                        rowlist1[8]=row.ratio;
                        rowlist1[9]=row.payment;
                        if(row.insuranceNature!=null){
                            if(row.insuranceNature==1){
                                rowlist1[10]="初次参保";
                            }else if(row.insuranceNature==2){
                                rowlist1[10]="参保续接";
                            }
                        }else{
                            rowlist1[10]="<li>--</li>";
                        }
                        rowlist1[11]=row.firstTime;
                        rowlist1[12]=row.registered;
                        if(row.audit!=null){
                            if(row.audit==1){
                                rowlist1[13]="<li class=\"text-danger\">待审核</li>";
                            }else if(row.audit==6){
                                rowlist1[13]="已审核";
                            }else if(row.audit==2){
                                rowlist1[13]="保单调整待审核";
                            }else if(row.audit==7){
                                rowlist1[13]="退保审核中";
                            }else if(row.audit==8){
                                rowlist1[13]="已退保";
                            }
                        }
                        rowlist1[14]=row.subTime;
                        rowlist1[15]=row.remarks;
                        if(row.audit!=null){
                            // if(row.audit==1){
                            //     rowlist1[16]="<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.name+"' a3='"+row.identNum+"' a4='"+row.identPicUrl1+"' " +
                            //         "a5='"+row.identPicUrl2+"' href=\"javascript:void(0)\" onclick=\"showIdentPic(this)\">身份证照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            //         "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.bank+"' a3='"+row.bankPicUrl+"' a4='"+row.bankNum+"' href=\"javascript:void(0)\" " +
                            //         "onclick=\"showBankPic(this)\">医保卡照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            //         "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuStart+"' a3='"+row.insuEnd+"' href=\"javascript:void(0)\" " +
                            //         "onclick=\"updateIns(this)\">修改周期</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            //         "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.remarks+"' href=\"javascript:void(0)\" onclick=\"addRemarks(this)\">添加备注</a><br/>" +
                            //         "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuranceNature+"' a3='"+row.insuranceType+"' a4='"+row.insuranceNum+"' " +
                            //         "a5='"+row.firstTime+"' a6='"+row.registered+"' a7='"+row.deptId+"' a8='"+row.uid+"' a9='"+row.bank+"' a10='"+row.bankNum+"' " +
                            //         "a11='"+row.city+"' a12='"+row.province+"' href=\"javascript:void(0)\" onclick=\"insertInfoFun(this)\">信息录入</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            //         "<a class=\"text-primary\" a1='"+row.id+"' href=\"javascript:void(0)\" onclick=\"passshow(this)\">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            //         "<a class=\"text-primary\" a1='"+row.id+"' href=\"javascript:void(0)\" onclick=\"refuseshow(this)\">拒绝</a>";
                            // }else{
                                if(row.audit !=null && row.audit==1){
                                    rowlist1[16]="<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.name+"' a3='"+row.identNum+"' a4='"+row.identPicUrl1+"' a5='"+row.identPicUrl2+"' " +
                                        "href=\"javascript:void(0)\" onclick=\"showIdentPic(this)\">身份证照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.bank+"' a4='"+row.bankNum+"' a3='"+row.medicalUrl+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"showBankPic(this)\">医保卡照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.remarks+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"addRemarks(this)\">添加备注</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuranceNature+"' a3='"+row.insuranceType+"' a4='"+row.insuranceNum+"' " +
                                        "a5='"+row.firstTime+"' a6='"+row.registered+"' a7='"+row.deptId+"' a8='"+row.uid+"' a9='"+row.bank+"' a10='"+row.bankNum+"' " +
                                        "a11='"+row.city+"' a12='"+row.province+"' href=\"javascript:void(0)\" onclick=\"insertInfoFun(this)\">信息录入</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuStart+"' a3='"+row.insuEnd+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"passshow(this)\">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuStart+"' a3='"+row.insuEnd+"' a4='"+row.insuranceType+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"updateIns(this)\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuStart+"' a3='"+row.insuEnd+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"refuseshow(this)\">退保</a>";
                                }else{
                                    rowlist1[16]="<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.name+"' a3='"+row.identNum+"' a4='"+row.identPicUrl1+"' a5='"+row.identPicUrl2+"' " +
                                        "href=\"javascript:void(0)\" onclick=\"showIdentPic(this)\">身份证照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.bank+"' a4='"+row.bankNum+"' a3='"+row.medicalUrl+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"showBankPic(this)\">医保卡照片</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.remarks+"' href=\"javascript:void(0)\" " +
                                        "onclick=\"addRemarks(this)\">添加备注</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                        "<a class=\"text-primary\" a1='"+row.id+"' a2='"+row.insuranceNature+"' a3='"+row.insuranceType+"' a4='"+row.insuranceNum+"' " +
                                        "a5='"+row.firstTime+"' a6='"+row.registered+"' a7='"+row.deptId+"' a8='"+row.uid+"' a9='"+row.bank+"' a10='"+row.bankNum+"' " +
                                        "a11='"+row.city+"' a12='"+row.province+"' href=\"javascript:void(0)\" onclick=\"insertInfoFun(this)\">信息录入</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                                }
                            // }
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
                        rowlist1[12]='';

                        rowlist1[13]='';
                        rowlist1[14]='';
                        rowlist1[15]='';
                        rowlist1[16]='';
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

function showIdentPic(e){
    var objId = 'detail1';
    shadboxFun(objId);
    var name = $(e).attr("a2");
    if(name==null||name==""||name=="null"){
        name = "--";
    }
    var identNum = $(e).attr("a3");
    if(identNum==null||identNum==""||identNum=="null"){
        identNum = "--";
    }
    var identPicUrl1 = $(e).attr("a4");
    if(identPicUrl1==null||identPicUrl1==""||identPicUrl1=="null"){
        identPicUrl1 = "--";
    }
    var identPicUrl2 = $(e).attr("a5");
    if(identPicUrl2==null||identPicUrl2==""||identPicUrl2=="null"){
        identPicUrl2 = "--";
    }
    $("#name1").text(name);
    $("#identNum").text(identNum);
    $("#identPicUrl1").attr('src',identPicUrl1);
    $("#identPicUrl2").attr('src',identPicUrl2);
    // $("#identPicUrl1").attr('src',"http://192.168.1.159:8080/aptitude/201805111023010292.jpg");
    // $("#identPicUrl2").attr('src',"http://192.168.1.159:8080/aptitude/201805111023010292.jpg");
}

function showBankPic(e){
    var objId = 'detail2';
    shadboxFun(objId);
    var bank = $(e).attr("a2");
    if(bank==null||bank==""||bank=="null"){
        bank = "--";
    }
    var bankNum1 = $(e).attr("a4");
    if(bankNum1==null||bankNum1==""||bankNum1=="null"){
        bankNum1 = "--";
    }
    var bankPicUrl = $(e).attr("a3");
    if(bankPicUrl==null||bankPicUrl==""||bankPicUrl=="null"){
        bankPicUrl = "--";
    }
    $("#bank1").text(bank);
    $("#bankNum1").text(bankNum1);
    $("#bankPicUrl").attr('src',bankPicUrl);
}

function updateIns(e){
    var id = $(e).attr("a1");
    $("#updateIns_id").val(id);
    var objId = 'time';
    shadboxFun(objId);
    var insuStart = $(e).attr("a2")==null||$(e).attr("a2")=="null"?"":$(e).attr("a2");
    var insuEnd = $(e).attr("a3")==null||$(e).attr("a3")=="null"?"":$(e).attr("a3");

    $("#insuStart1").val(insuStart);
    $("#insuEnd1").val(insuEnd);

    // 拆分年月日
    insuStart = insuStart.split('/');

    // 得到月数
    insuStart = parseInt(insuStart[0]) * 12 + parseInt(insuStart[1]);

    // 拆分年月日
    insuEnd = insuEnd.split('/');
    // 得到月数
    insuEnd = parseInt(insuEnd[0]) * 12 + parseInt(insuEnd[1]);
    var m = Math.abs(insuStart - insuEnd);
    $("#months").text(m+1);



    if($(e).attr("a4")==1||$(e).attr("a4")=="1"){

        laydate.render(startDate);
        laydate.render(endDate);
        console.log($("#yl_insuStart_S").val());
        console.log($("#yl_insuStart_E").val());
        var startDate= laydate.render({//渲染开始时间选择
            elem: '#insuStart1'//通过id绑定html中插入的start
            ,type: 'month'
            ,format:'yyyy/MM'
            ,min:$("#yl_insuStart_S").val()
            ,max:$("#yl_insuStart_E").val()//设置一个默认最大值
            ,done: function (value,date) {
                date.month = date.month !== 1 ? date.month - 1 : (date.year--, 12);//月份修正
                endDate.config.min = date;//开始日选好后，重置结束日的最小日期
                endDate.config.value = value;//将结束日的初始值设定为开始日
            }
        });
        var endDate= laydate.render({//渲染结束时间选择
            elem: '#insuEnd1',//通过id绑定html中插入的end
            type: 'month',
            format:'yyyy/MM',
            min:$("#yl_insuEnd_S").val(),//设置min默认最小值
            max:$("#yl_insuEnd_E").val(),
            done: function (value, dates) {

            }
        });
        endDate.config.min = startDate.config.max;
    }else{
        laydate.render(startDate);
        laydate.render(endDate);
        console.log($("#yl_insuStart_S").val());
        console.log($("#yl_insuStart_E").val());
        var startDate= laydate.render({//渲染开始时间选择
            elem: '#insuStart1'//通过id绑定html中插入的start
            ,type: 'month'
            ,format:'yyyy/MM'
            ,min:$("#yb_insuStart_S").val()
            ,max:$("#yb_insuStart_E").val()//设置一个默认最大值
            ,done: function (value,date) {
                date.month = date.month !== 1 ? date.month - 1 : (date.year--, 12);//月份修正
                endDate.config.min = date;//开始日选好后，重置结束日的最小日期
                endDate.config.value = value;//将结束日的初始值设定为开始日
            }
        });
        var endDate= laydate.render({//渲染结束时间选择
            elem: '#insuEnd1',//通过id绑定html中插入的end
            type: 'month',
            format:'yyyy/MM',
            min:$("#yb_insuEnd_S").val(),//设置min默认最小值
            max:$("#yb_insuEnd_E").val(),
            done: function (value, dates) {

            }
        });
        endDate.config.min = startDate.config.max;
    }
}

function addRemarks(e){
    var id = $(e).attr("a1");
    $("#resmarks_ins_id").val(id);
    $("#mark").val("");
    $("#remarks").text($(e).attr("a2")==null||$(e).attr("a2")=="null"?"":$(e).attr("a2"));
    shadboxFun('mark');
}
function insertInfoFun(e){

    var id = $(e).attr("a1");

    var insuranceNature=$(e).attr("a2")==null||$(e).attr("a2")=="null"?"":$(e).attr("a2");
    var insuranceType=$(e).attr("a3")==null||$(e).attr("a3")=="null"?"":$(e).attr("a3");
    var insuranceNum=$(e).attr("a4")==null||$(e).attr("a4")=="null"?"":$(e).attr("a4");
    var firstTime=$(e).attr("a5")==null||$(e).attr("a5")=="null"?"":$(e).attr("a5");
    var registered=$(e).attr("a6")==null||$(e).attr("a6")=="null"?"":$(e).attr("a6");
    var deptId=$(e).attr("a7")==null||$(e).attr("a7")=="null"?"":$(e).attr("a7");

    var uid=$(e).attr("a8")==null||$(e).attr("a8")=="null"?"":$(e).attr("a8");

    var bank=$(e).attr("a9")==null||$(e).attr("a9")=="null"?"":$(e).attr("a9");
    var bankNum=$(e).attr("a10")==null||$(e).attr("a10")=="null"?"":$(e).attr("a10");
    var city=$(e).attr("a11")==null||$(e).attr("a11")=="null"?"":$(e).attr("a11");
    var province=$(e).attr("a12")==null||$(e).attr("a12")=="null"?"":$(e).attr("a12");

    $("#uid").val(uid);
    var objId = 'yluru';

    if(insuranceType!=null){
        if(insuranceType==2){
            objId = 'lluru';
            shadboxFun(objId);

            $("#bank").val(bank==null||bank=="null"?"":bank);
            $("#bankNum").val(bankNum==null||bankNum=="null"?"":bankNum);
            $("#firstTime1").val(firstTime==null||firstTime=="null"?"":firstTime);
            $("#ylregistered").val(registered==null||registered=="null"?"":registered);

            var count=$("#ylsheng option").length;
            console.log("ylsheng======="+count)
            for(var i=0;i<count;i++){
                if($("#ylsheng").get(0).options[i].value == province){
                    $("#ylsheng").get(0).options[i].selected = true;
                    break;
                }
            }
            count=$("#ylshi option").length;
            console.log("ylshi======="+count)
            for(var i=0;i<count;i++){
                if($("#ylshi").get(0).options[i].value == city){
                    $("#ylshi").get(0).options[i].selected = true;
                    break;
                }
            }
            count=$("#ylqu option").length;
            console.log("ylqu====="+count)
            for(var i=0;i<count;i++){
                if($("#ylqu").get(0).options[i].value == deptId){
                    $("#ylqu").get(0).options[i].selected = true;
                    break;
                }
            }
            $("#yiliao_ins_id").val(id);
            if(insuranceNature==1){
                $("#yliao_f").removeClass("cur");
                $("#yliao_nf").removeClass("cur");//不选中
                $("#yliao_f").addClass("cur");//选中
            }else{
                $("#yliao_f").removeClass("cur");
                $("#yliao_nf").removeClass("cur");//不选中
                $("#yliao_nf").addClass("cur");//选中

            }
            selectPRDept(0,"ylsheng");
            $("#ylsheng").val(province);
            selectPRDept(province,"ylshi");
            $("#ylshi").val(city);
            selectPRDept(city,"ylqu");
            $("#ylqu").val(deptId);
        }else if(insuranceType==1){
            shadboxFun(objId);
            $("#yanglao_ins_id").val(id);
            $("#insuranceNum").val(insuranceNum==null||insuranceNum=="null"?"":insuranceNum);
            $("#firstTime").val(firstTime==null||firstTime=="null"?"":firstTime);
            $("#registered").val(registered==null||registered=="null"?"":registered);
            var count=$("#sheng option").length;
            console.log("sheng====="+count)
            for(var i=0;i<count;i++){
                if($("#sheng").get(0).options[i].value == province){
                    $("#sheng").get(0).options[i].selected = true;
                    break;
                }
            }
            count=$("#shi option").length;
            console.log("shi====="+count)
            for(var i=0;i<count;i++){
                if($("#shi").get(0).options[i].value == city){
                    $("#shi").get(0).options[i].selected = true;
                    break;
                }
            }
            count=$("#qu option").length;
            console.log("qu====="+count)
            for(var i=0;i<count;i++){
                if($("#qu").get(0).options[i].value == deptId){
                    $("#qu").get(0).options[i].selected = true;
                    break;
                }
            }
            if(insuranceNature==1){
                $("#ylao_f").removeClass("cur");
                $("#ylao_nf").removeClass("cur");//不选中
                $("#ylao_f").addClass("cur");//选中

            }else{
                $("#ylao_f").removeClass("cur");
                $("#ylao_nf").removeClass("cur");//不选中
                $("#ylao_nf").addClass("cur");//选中

            }
            selectPRDept(0,"sheng");
            $("#sheng").val(province);
            selectPRDept(province,"shi");
            $("#shi").val(city);
            selectPRDept(city,"qu");
            $("#qu").val(deptId);
        }
    }

}
function selectPRDept(parentId,seId){
    $.ajax({
        url :'/handApp/selectPRDept.do',
        type : 'POST',
        timeout : 20000,
        data:{
            parentId:parentId
        },
        async: false,
        success : function(result) {
            var datalist=result.data;
            if(datalist!=null && datalist.length>0){
                for(var i=0;i<datalist.length;i++){
                    $("#"+seId).append("<option value='"+datalist[i].deptId+"'>"+datalist[i].name+"</option>");
                }
            }
        }
    });
}

function updateInsurance(e){
    var id = $("#yanglao_ins_id").val();
    var insuranceNum = $("#insuranceNum").val();
    if(insuranceNum==null||insuranceNum==""){
        layer.alert("社保号不能为空！");
        return;
    }
    var orderNumCh_zn = $(".cur").val();
    var insuranceNature = "";
    if(orderNumCh_zn=='初次参保'){
        insuranceNature = 1;
    }else if(orderNumCh_zn=='参保续接'){
        insuranceNature = 2;
    }
    if(insuranceNature==null||insuranceNature==""){
        layer.alert("参保类型不能为空！");
        return;
    }
    var firstTime = $("#firstTime").val();
    if(firstTime==null||firstTime==""){
        layer.alert("首次参保时间不能为空！");
        return;
    }
    var sheng = $("#sheng").val();
    if(sheng==null||sheng==""){
        layer.alert("省份不能为空！");
        return;
    }
    var shi = $("#shi").val();
    if(shi==null||shi==""){
        layer.alert("市不能为空！");
        return;
    }
    var qu = $("#qu").val();
    if(qu==null||qu==""){
        layer.alert("区不能为空！");
        return;
    }
    if(!checkRate(sheng)){
        return;
    }
    if(!checkRate(shi)){
        return;
    }
    if(!checkRate(qu)){
        return;
    }
    var registered = $("#registered").val();
    var uid=$("#uid").val();
    $.ajax({
        url :'/insuranceSet/updateInsInfo.do',
        type : 'POST',
        timeout : 20000,
        data:{
            insuranceNum:insuranceNum,
            insuranceNature:insuranceNature,
            firstTime:firstTime,
            province:sheng,
            city:shi,
            deptId:qu,
            registered:registered,
            id:id,
            uid:uid,
            updateType:2
        },
        async: false,
        success : function(result) {
            if(result.success){
                layer.alert("录入成功！");
            }else{
                layer.alert("录入失败！");
            }
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");
            $('.popbox-container').fadeOut();
            console.log(e)
            countlist($('#pageSizeInp').val());
        }
    });
}
function updateyiliaoInsurance(e){
    var id = $("#yiliao_ins_id").val();
    var bankNum = $("#bankNum").val();
    if(bankNum==null||bankNum==""){
        layer.alert("医保银行卡号不能为空！");
        return;
    }
    var bank = $("#bank").val();
    if(bank==null||bank==""){
        layer.alert("开户行不能为空！");
        return;
    }
    var orderNumCh_zn = $(".cur").val();
    var insuranceNature = "";
    if(orderNumCh_zn=='初次参保'){
        insuranceNature = 1;
    }else if(orderNumCh_zn=='参保续接'){
        insuranceNature = 2;
    }
    if(insuranceNature==null||insuranceNature==""){
        layer.alert("参保类型不能为空！");
        return;
    }
    var firstTime1 = $("#firstTime1").val();
    if(firstTime1==null||firstTime1==""){
        layer.alert("首次参保时间不能为空！");
        return;
    }
    var sheng = $("#ylsheng").val();
    if(sheng==null||sheng==""){
        layer.alert("省份不能为空！");
        return;
    }
    var shi = $("#ylshi").val();
    if(shi==null||shi==""){
        layer.alert("市不能为空！");
        return;
    }
    var qu = $("#ylqu").val();
    if(qu==null||qu==""){
        layer.alert("区不能为空！");
        return;
    }
    if(!checkRate(sheng)){
        return;
    }
    if(!checkRate(shi)){
        return;
    }
    if(!checkRate(qu)){
        return;
    }
    var registered = $("#ylregistered").val();
    var uid=$("#uid").val();
    $.ajax({
        url :'/insuranceSet/updateInsInfo.do',
        type : 'POST',
        timeout : 20000,
        data:{
            bankNum:bankNum,
            bank:bank,
            firstTime:firstTime1,
            province:sheng,
            city:shi,
            deptId:qu,
            registered:registered,
            id:id,
            uid:uid,
            updateType:2
        },
        async: false,
        success : function(result) {
            if(result.success){
                var filepath=$("#medicalUrl").val();
                if(filepath!=null && filepath!=""){
                    var extStart=filepath.lastIndexOf(".");
                    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
                    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
                        layer.alert("提示","图片格式不正确，请重新上传！", "info");
                        return false;
                    }
                }
                if($("#imgurl").form('validate')){
                    $("#imgurl").form('submit', {
                        url:'/insuranceSet/uploadImgUrl.do?uid='+uid+'&id='+id,
                        success:function(data){
                            var c = jQuery.parseJSON(data);

                            /!*$.messager.alert("提示",c.msg, "info"); *!/
                        }
                    });
                }

                layer.alert("修改成功！");


            }else{
                layer.alert("修改失败！");
            }
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }
    });
}
//
function updateStartEnd(e){
    var id = $("#updateIns_id").val();
    var insuStart1 = $("#insuStart1").val();
    var insuEnd1 = $("#insuEnd1").val();

    $.ajax({
        url :'/insuranceSet/updateInsurance.do',
        type : 'POST',
        timeout : 20000,
        data:{
            insuStart:insuStart1,
            insuEnd:insuEnd1,
            id:id,
            updateType:3
        },
        async: false,
        success : function(result) {
            layer.alert(result.msg)
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();

            countlist($('#pageSizeInp').val());
        }
    });
}
function qxtime(e){
    $(e).parents('.popbox-wrapper').animate({
        opacity: 'hide',top: '0px'
    }, "slow");

    $('.popbox-container').fadeOut();
}
function updateRemarks(e){

    var id = $("#resmarks_ins_id").val();
    var remarks = $("#remarks").val();
    $.ajax({
        url :'/insuranceSet/updateRemarks.do',
        type : 'POST',
        timeout : 20000,
        data:{
            remarks:remarks,
            id:id
        },
        async: false,
        success : function(result) {
            if(result.success){
                layer.alert("修改成功！");
            }else{
                layer.alert("修改失败！");
            }
           $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }
    });
}
//打开通过页面
function passshow(e){
    shadboxFun('tishi1');
    var id = $(e).attr("a1");
    $("#pass_ins_id").val(id);
}
//打开拒绝页面
function refuseshow(e){
    shadboxFun('tishi2');
    var id = $(e).attr("a1");
    $("#pass_ins_id").val(id);
}

function enterPass(e){
    var insId = $("#pass_ins_id").val();
    $.ajax({
        url:'/insuranceSet/auditPassIns.do',
        type:'POST',
        timeout:20000,
        data:{
            id:insId,
            audit:6
        },
        async: false,
        success : function(result) {
            if(result.success){
                layer.alert("操作成功！");
            }else{
                layer.alert("操作失败！");
            }
            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            countlist($('#pageSizeInp').val());
        }

    });
}
function enterRefuse(e){
    var insId = $("#pass_ins_id").val();
    $.ajax({
        url:'/insuranceSet/auditPassIns.do',
        type:'POST',
        timeout:20000,
        data:{
            id:insId,
            audit:7
        },
        async: false,
        success : function(result) {
            if(result.success){
                layer.alert("操作成功！");
            }else{
                layer.alert("操作失败！");
            }

            $(e).parents('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            $('.popbox-container').fadeOut();
            $(this).siblings('.popbox-wrapper').animate({
                opacity: 'hide',top: '0px'
            }, "slow");

            countlist($('#pageSizeInp').val());
        }

    });
}

function uploadurl(){
    var filepath=$("#medicalUrl").value;
    if(filepath!=null && filepath!=""){
        var extStart=filepath.lastIndexOf(".");
        var ext=filepath.substring(extStart,filepath.length).toUpperCase();
        if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
            $.messager.alert("提示","图片格式不正确，请重新上传！", "info");
            return false;
        }
    }
    var uid=$("#uid").val();
    if($("#imgform").form('validate')){
        $("#imgform").form('submit', {
            url:'/insuranceSet/uploadImgUrl.do?uid='+uid,
            success:function(data){
                var c = jQuery.parseJSON(data);
                layer.alert(c.msg);

                /!*$.messager.alert("提示",c.msg, "info"); *!/
            }
        });
    }
}

function checkRate(nubmer) {
    var re = /^\+?[1-9][0-9]*$/;//判断字符串是否为数字//判断正整数/[1−9]+[0−9]∗]∗/
    if (!re.test(nubmer)) {
        layer.alert("请选择地区");
        return false;
    }else{
        return true;
    }
}
/*]]>*/