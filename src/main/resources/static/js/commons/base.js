$package('YiYa');
/*<![CDATA[*/
var YiYa={
	/*Json 工具类*/
	isJson:function(str){
		var obj = null; 
		try{
			obj = YiYa.paserJson(str);
		}catch(e){
			return false;
		}
		var result = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
		return result;
	},
	paserJson:function(str){
		return eval("("+str+")");
	},
	/*弹出框*/
	alert:function(title, msg, icon, callback){
        layer.msg(msg, {
            time: 0 //不自动关闭
            ,btn: ['关闭']
            ,yes: function(index){
                layer.close(index);
            }
        });
	},
	progress:function(title,msg){
		 var win = layer.msg(msg, {
             time: 0 //不自动关闭
			 ,btn: ['关闭']
             ,yes: function(index){
                 layer.close(index);
             }
         });
	},
	closeProgress:function(){
        layer.close();
	},
	/*重新登录页面*/
	toLogin:function(){
		window.top.location= urls['msUrl']+"/login.shtml";
	},
	checkLogin:function(data){//检查是否登录超时
		if(data.logoutFlag){
			YiYa.closeProgress();
			YiYa.alert('提示',"登录超时,点击确定重新登录.",'error',YiYa.toLogin);
			return false;
		}
		return true;
	},
	ajaxSubmit:function(form,option){
		form.ajaxSubmit(option);
	},
	ajaxJson: function(url,option,callback){
		$.ajax(url,{
			type:'post',
			 	dataType:'json',
			 	data:option,
			 	success:function(data){
			 		//坚持登录
			 		if(!YiYa.checkLogin(data)){
			 			return false;
			 		}		 	
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			YiYa.closeProgress();
			 			var data = $.parseJSON(response.responseText);			 		
				 		//检查登录
				 		if(!YiYa.checkLogin(data)){
				 			return false;
				 		}else{
					 		YiYa.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			YiYa.alert('提示',"请求出现异常,请联系管理员1",'error');
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
		});
	},
	submitForm:function(form,callback,dataType){
			var option =
			{
			 	type:'post',
			 	dataType: dataType||'json',
			 	success:function(data){
			 		console.log(data+"================================================");
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			YiYa.closeProgress();
			 			
			 			var data = $.parseJSON(response.responseText);
			 			
				 		//检查登录
				 		if(!YiYa.checkLogin(data)){
				 			return false;
				 		}else{
					 		YiYa.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			YiYa.alert('提示',"请求出现异常,请联系管理员",e);
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
			 };
			 YiYa.ajaxSubmit(form,option);
	},
	saveForm:function(form,callback){
		if(form.form('validate')){
			//ajax提交form
			YiYa.submitForm(form,function(data){
				YiYa.closeProgress();
			 	if(data.success){
			 		if(callback){
				       	callback(data);
				    }else{
			       		YiYa.alert('提示','保存成功.','info');
			        } 
		        }else{
		       	   YiYa.alert('提示',data.msg,'error');  
		        }
			});
		 }
	},
	/**
	 * 
	 * @param {} url
	 * @param {} option {id:''} 
	 */
	getById:function(url,option,callback){
		YiYa.progress();
		YiYa.ajaxJson(url,option,function(data){
			YiYa.closeProgress();
			if(data.success){
				if(callback){
			       	callback(data);
			    }
			}else{
				YiYa.alert('提示',data.msg,'error');  
			}
		});
	},
	deleteForm:function(url,option,callback){
		YiYa.progress();
		YiYa.ajaxJson(url,option,function(data){
				YiYa.closeProgress();
				if(data.success){
					if(callback){
				       	callback(data);
				    }
				}else{
					YiYa.alert('提示',result.msg,'error');  
				}
		});
	}
};

/*表单转成json数据*/
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
/*]]>*/