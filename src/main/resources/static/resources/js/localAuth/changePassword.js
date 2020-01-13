$(function () {
    // 修改平台密码的controller url
    var updateUrl = '/o2o/local/updateLocalPwd';
    // 获取userType
    var userType = getQueryParam("userType");

    $('#back').click(function () {
        if (userType == 2) {
            window.location.href = '/o2o/shop/shopList';
        } else if (userType == 1){
            window.location.href = '/o2o/front/index';
        }
    });

    $('#submit').click(function () {
        var password = $('#password').val();
        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();
        if (newPassword !== confirmPassword) {
            $.toast('两次输入密码不一致！');
            return;
        }
        // 添加表单数据
        var formDate = new FormData();
        formDate.append('password', password);
        formDate.append('newPassword', newPassword);
        formDate.append('confirmPassword', confirmPassword);
        // 获取验证码
        var verifyCode = $('#j_kaptcha').val();
        if (!verifyCode) {
            $.toast('请输入验证码！');
            return;
        }
        formDate.append('verifyCodeInput', verifyCode);
        $.showIndicator();
        $.ajax({
            url: updateUrl,
            contentType: false,
            processData: false,
            cache: false,
            type: 'post',
            data: formDate,
            success: function (data) {
                $.hideIndicator();
                if (data.success) {
                    $.toast('修改成功！', 500);
                    if (userType == 1) {
                        window.setTimeout("window.location='/o2o/front/index'", 500);
                    } else {
                        window.setTimeout("window.location='/o2o/shop/shopList'", 500);
                    }
                } else {
                    $.toast('修改失败，' + data.errMsg, 500);
                    $('#kaptcha_img').click();
                }
            }
        })
    });

});
