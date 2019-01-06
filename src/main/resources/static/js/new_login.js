$package('YiYa.login');
YiYa.login = function(){
	return {
		toLogin:function(){			
			try{
				var form = $("#loginForm");
                	layer.load();
					YiYa.submitForm(form,function(data){
                        layer.closeAll('loading');
						if(data.msg=="101"){
							window.location= "${msUrl}/guangqi/main.shtml";
				        }else{
							if(data.success){
                                window.location= "${msUrl}/guangqi/main.shtml";
                            }else{
                                layer.alert('登录出错', {icon: 5});
                            }
						}
					});


			}catch(e){
                layer.alert('登录出错', {icon: 5});
			}
			return false;
		},
		init:function(){
			if(window.top != window.self){
				window.top.location =  window.self.location;
			}
			
			var form = $("#loginForm");
			form.submit(YiYa.login.toLogin);
		}
	}
}();

$(function(){
	YiYa.login.init();
});		

$(function(){
	$("#btnLogin").live('click', function() {
		$("#loginForm").submit(YiYa.login.toLogin());
	});
	
		$("#pwd").bind('keyup', function(event) {
			if (event.keyCode == "13") {
				$("#loginForm").submit(YiYa.login.toLogin());
			}
		});
	
});	