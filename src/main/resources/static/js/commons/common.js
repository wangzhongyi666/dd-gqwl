//视频左侧显示隐藏
$(function(){
    $('.sclose').click(
        function(){
            $(this).hide();
            $("#ifWd").css({'width':'100%'});
            $(".side").hide();
            $("#ifWd").contents().find(".sopen").show();
        });
})
$(function(){
    $('.sopen').click(
        function(){
            var heigh= $("#tabs" , parent.document).width()-230;
            $(this).hide();
            $(".side" , parent.document).show();
            $("#ifWd" , parent.document).css({'width':heigh+'px'});
            $(".sclose" , parent.document).show();
        })
})
//视频左侧下拉
$(function(){
	$('.navlist dd').hide();
	$('.navlist dt.on').nextUntil('dt').show();
	$('.navlist dt').click(function(){
		if($(this).nextUntil('dt').css('display')=='none'){
			$('i').attr('class','up');
			$(this).find('i').attr('class','down');
			$(this).nextUntil('dt').slideDown(100);
			$(this).siblings('dt').nextUntil('dt').slideUp(100);	
		}else{
			
			$(this).find('i').attr('class','up');
			$(this).nextUntil('dt').slideUp(100);	
		}
	})
});

//覆盖整体
function shadboxFun(objId){
    var obj = "#"+objId;  //对象ID
    $('.popbox-container').fadeIn();
    $('.popbox-overlay').css({
        "height": $(document).height() + 'px'
    })

    var _height = $(obj).height();
    var _width = $(obj).width();

    //获取对象距离窗口上部和左部的距离
    var _top = ($(window).height() - _height)/2 + "px";
    var _left = ($(window).width() - _width)/2 + 'px';
    $(obj).css({"left":_left});

    $(obj).animate({
        opacity: 'show', top: _top
    }, "200",function(){
        var userAgent = window.navigator.userAgent.toLowerCase();
        var version = $.support.version;
        if(version!="6.0"){//如果是非IE6
            $(obj).css({
                "top": ($(window).height() - _height)/2 + 'px'
            });
        }
        if(version=="6.0"){//如果IE6
            $(obj).css({
                "top": ($(window).height() - _height)/2 + $(window).scrollTop() + 'px'
            });
        }
    });

    $('.popbox-close').click(function(){
        $(this).parents('.popbox-wrapper').animate({
            opacity: 'hide',top: '0px'
        }, "slow");

        $('.popbox-container').fadeOut();
    })

    $('.popbox-overlay').click(function(){
        $(this).siblings('.popbox-wrapper').animate({
            opacity: 'hide',top: '0px'
        }, "slow");

        $('.popbox-container').fadeOut();
    })
}
function shadboxFun1(objId,event){
    var obj = "#"+objId;  //����ID

    $('.popbox-container').fadeIn();
    $('.popbox-overlay').css({
        "height": $(document).height() + 'px'
    })

    var _height = $(obj).height();
    var _width = $(obj).width();

    //��ȡ������봰���ϲ����󲿵ľ���
    var _top = ($(window).height() - _height)/2 + "px";
    var _left = ($(window).width() - _width)/2 + 'px';
    $(obj).css({"left":_left});

    $(obj).animate({
        opacity: 'show', top: _top
    }, "200",function(){
        var userAgent = window.navigator.userAgent.toLowerCase();
        var version = $.support.version;

        if(version!="6.0"){//����Ƿ�IE6
            $(obj).css({
                "top": ($(window).height() - _height)/2 + 'px'
            });
        }
        if(version=="6.0"){//���IE6
            $(obj).css({
                "top": ($(window).height() - _height)/2 + $(window).scrollTop() + 'px'
            });
        }
    });

    $('.popbox-close').click(function(){
        $(this).parents('.popbox-wrapper').animate({
            opacity: 'hide',top: '0px'
        }, "slow");

        $('.popbox-container').fadeOut();
    })

    $('.popbox-overlay').click(function(){
        $(this).siblings('.popbox-wrapper').animate({
            opacity: 'hide',top: '0px'
        }, "slow");

        $('.popbox-container').fadeOut();
    })
    if(objId=='detail' || objId=='detail1'){
        var name = $(event).parent().parent().find("td").eq(1).text();
        var number = $(event).parent().parent().find("td").eq(2).text();
        $("#selname").text(name);
        $("#selnum").text(number);
        var mid=$(event).attr("a1");
        var idenimg1=$(event).attr("a2");
        var idenimg2=$(event).attr("a3");
        $("#vipid").val(mid);
        $("#idenimg1").attr('src',idenimg1);
        $("#idenimg2").attr('src',idenimg2);
    }else if(objId=='tishi1' || objId=='tishi2'|| objId=='tishi3' ){
        var mid=$(event).attr("a1");
        $("#vipid").val(mid);
    }else if(objId=='tishi4' || objId=='tishi5' ){
        var mid=$(event).attr("a1");
        var payment=$(event).attr("a2");
        $("#vipid").val(mid);
        $("#payment").val(payment);
    }else if(objId=='detail2'){
        var selbank = $(event).parent().parent().find("td").eq(3).text();
        var selbankNum = $(event).parent().parent().find("td").eq(4).text();
        var img3 = $(event).attr("a1");
        $("#selbank").text(selbank);
        $("#selbankNum").text(selbankNum);
        $("#img3").attr("src",img3);
    }else if(objId=='detail3'){
        var seledu = $(event).attr("a1");
        var selprofessional = $(event).attr("a2");
        var selemail = $(event).attr("a3");
        var selqq = $(event).attr("a4");
        var seldeptName=$(event).attr("a5");
        if(seledu!=null && seledu.length>0 && seledu!='null' ){
            $("#seledu").text(seledu);
        }
        if(selprofessional!=null && selprofessional.length>0 && selprofessional!='null'){
            $("#selprofessional").text(selprofessional);
        }
        if(selemail!=null && selemail.length>0 && selemail!='null'){
            $("#selemail").text(selemail);
        }
        if(selqq!=null && selqq.length>0 && selqq!='null'){
            $("#selqq").text(selqq);
        }
        if(seldeptName!=null && seldeptName.length>0 && seldeptName!='null'){
            $("#seldeptName").text(seldeptName);
        }
    }else if(objId=='info1'){
        var mid=$(event).attr("a1");
        var idenimg1=$(event).attr("a2");
        var idenimg2=$(event).attr("a3");
        $("#vipid").val(mid);
        var imgurl="";
        if(idenimg1!=null && idenimg1.length>0){
            imgurl+=idenimg1;
            if(idenimg2!=null && idenimg2.length>0){
                imgurl+=";"+idenimg2;
            }
        }else if(idenimg2!=null && idenimg2.length>0){
            imgurl+=idenimg2;
        }
        var ary=imgurl.split(";");
        if(ary !=null && ary.length>0){
            var imgstr="";
            for(var i=0;i<ary.length;i++){
                imgstr+="<li class='text-center'><img style='width: 300px;height: 210px;' src='"+ary[i]+"'/></li>";
            }
        }
        $("#imgul").append(imgstr);
    }else if(objId=='qt3'){
        var mid=$(event).attr("a1");
        var vipid=$(event).attr("a3");
        var orderNumInp=$(event).attr("a5");
        $("#mid").val(mid);
        $("#vipid").val(vipid);
        $("#orderNumInp").val(orderNumInp);
    }else if(objId=='qt2'){
        var mid=$(event).attr("a1");
        var vipid=$(event).attr("a3");
        var orderNumInp=$(event).attr("a5");
        var quitType=$(event).attr("a6");
        $("#mid").val(mid);
        $("#vipid").val(vipid);
        $("#orderNumInp").val(orderNumInp);
        $("#qtType").val(quitType);
        var quit_integration=$(event).attr("a2");
        $("#kcjf").val(quit_integration);
    }else if(objId=='qt1'){
        var mid=$(event).attr("a1");
        var quit_integration=$(event).attr("a2");
        var vipid=$(event).attr("a3");
        var integration_diff=$(event).attr("a4");
        var orderNumInp=$(event).attr("a5");
        var quitType=$(event).attr("a6");
        $("#mid").val(mid);
        $("#vipid").val(vipid);
        $("#kcjf").val(quit_integration);
        $("#integration_diff").val(integration_diff);
        $("#orderNumInp").val(orderNumInp);
        $("#qtType").val(quitType);
    }else if(objId=='add'){
        var roleId=$(event).attr("a1");
        $("#roleId").val(roleId);
        var jnameinp=$(event).attr("a2");
        $("#jnameinp").val(jnameinp);
    }else if(objId=='deptadd'){
        var parentId=$("#parentId").val();
        $.ajax({
            url :'/sys/getDeptByParentId.do',
            type : 'POST',
            timeout : 20000,
            data:{
                parentId:parentId
            },
            async: false,
            success : function(result) {
                $("#deptSelect").empty();
                var datalist=result.data;
                if(datalist!=null && datalist.length>0){
                    var selDom = $("#deptSelect");
                    for(var i=0;i<datalist.length;i++){
                        selDom.append("<option value='"+datalist[i].deptId+"'>"+datalist[i].name+"</option>");
                    }
                }
            }
        });
    }
}
//动态加载table
function createTable(datalist,id){
    var t=document.getElementById(id);     //获取Table
    var length= t.rows.length;             //获得Table下的行数
    if(length!=0){                      //如果有行，则清空
        for(var i=length-1;i>=0;i--)
        {
            t.deleteRow(i);
        }
    }
    for(var i=0;datalist.length>i;i++){
        var r = t.insertRow(t.rows.length);
        for(var z=0;datalist[i].length>z;z++){
            var c = r.insertCell();
            c.innerHTML = datalist[i][z];
        }
    }
    var tr=document.getElementsByTagName("tr");
    for(var i=0;i<tr.length;i++) {
        tr[i].style.background=tr[i].rowIndex % 2==0?"rgb(238,241,247)":"white";
    }
    var iframeWin = parent.document.getElementById("ifWd").contentWindow || parent.document.getElementById("ifWd").contentDocument.parentWindow;
    if (iframeWin.document.body) {
        parent.document.getElementById("ifWd").height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
    }
    //document.getElementById(id).appendChild(t);
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function qx(e) {
    $(".popbox-container").parents('.popbox-wrapper').animate({
        opacity: 'hide',top: '0px'
    }, "slow");

    $('.popbox-container').fadeOut();
}
//判断该用户是否有btnUrl功能权限
function is_purview(btn,btnUrl,btnsUrl,msUrl){
    var btns = btn.split(msUrl);
    var btnsU = btns[1]+"";

    var flag = false;
    $.each(btnsUrl,function (index,val) {
        alert("1"+index);
        if(btnsU==index){
            $.each(val,function (i,v) {
                if(btnUrl==v){
                    flag = true;
                    return flag;
                }
            });
        }
    });
    return flag;
}