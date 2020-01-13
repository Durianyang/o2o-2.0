$(function () {
    // 绑定账号的url
    var bindUrl = '/o2o/local/bindLocalAuth';
    // userType=1则为前端展示系统，其余为店家管理系统
    var userType = getQueryParam("userType");
    $('#submit').click(function () {
        $.showIndicator();
        // 获取输入的账号
        var username = $('#username').val();
        // 获取输入的密码
        var password = $('#password').val();
        // 获取输入的验证码
        var verifyCode = $('#j_kaptcha').val();
        // var needVerify = false;
        if (username == null || username === "" || password == null || password === "") {
            $.hideIndicator();
            $.toast('账号密码不能为空！！', 1000);
            return;
        }
        if (!verifyCode) {
            $.hideIndicator();
            $.toast('请输入验证码！', 1000);
            return;
        }
        // 访问后台
        $.ajax({
            url: bindUrl,
            async: false,
            cache: false,
            type: 'post',
            dataType: 'json',
            data: {
                username: username,
                password: password,
                verifyCodeInput: verifyCode
            },
            success:function (data) {
                if (data.success) {
                    $.hideIndicator();
                    $.toast('绑定成功！', 500);
                    if (userType == 1) {
                        // 回到前端展示首页
                        window.setTimeout("window.location = '/o2o/front/index'", 750);
                    } else {
                        window.setTimeout("window.location = '/o2o/shop/shopList'", 750);
                    }
                } else {
                    $.hideIndicator();
                    $.toast(data.errMsg, 1000);
                    $('#kaptcha_img').click();
                }
            }
        })
    });
});