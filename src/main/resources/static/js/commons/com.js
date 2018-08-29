//�����ʾ����
$(function(){
		$('.sopen').hide();
		$('.sclose').click(
			function(){
				$(this).hide();
				$(".rightbox").css({'width':'100%'});
				$(".side").hide();
				$(".sopen").show();
			});
});
$(function(){
		$('.sopen').click(
			function(){
				$(this).hide();
				$(".side").show();
				$(".rightbox").css({'width':'80%'});
				$(".sclose").show();
			})	
});
//�������
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


//��������
function shadboxFun(objId){
	var obj = "#"+objId;  //����ID
	
	$('.popbox-container').fadeIn();
	$('.popbox-overlay').css({
		"height": $(document).height() + 'px'
	});
	
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
		var version = $.browser.version;
		
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
	});
	
	$('.popbox-overlay').click(function(){
		$(this).siblings('.popbox-wrapper').animate({
			opacity: 'hide',top: '0px'
		}, "slow");
		
		$('.popbox-container').fadeOut();				
	})
}
