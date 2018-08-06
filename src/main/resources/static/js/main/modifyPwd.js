$.validator.setDefaults({
    submitHandler: function() {
        layer.confirm('确定修改吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){

            var pwdForm = $("#pwdForm");
            if(pwdForm.form('validate')){
                YiYa.saveForm(pwdForm,function(data){
                    layer.alert(data.msg);
                    pwdForm.resetForm();
                });
            }
        }, function(){

        });
    }
});
$().ready(function() {
// 在键盘按下并释放及提交后验证提交表单
    $("#pwdForm").validate({
        rules: {
            password: {
                required: true,
                minlength: 5
            },
            confirm_password: {
                required: true,
                minlength: 5,
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母"
            },
            confirm_password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母",
                equalTo: "两次密码输入不一致"
            }
        }
    });
});