$(function () {
    // 登录
    var loginUrl = '/o2o/local/login';
    // 获取userType，该页面跳转过来是在param上携带了userType
    var userType = getQueryParam("userType");
    // 登录次数,三次失败后开启验证码
    var loginCount = 0;

    $('#submit').click(function () {
        // 获取输入账号
        var username = $('#username').val();
        // 获取输入密码
        var password = $('#password').val();
        // 获取验证码信息
        var verifyCode = $('#j_kaptcha').val();
        // 是否需要验证
        var needVerify = false;
        // 如果三次登录失败
        if (loginCount >= 3) {
            if (!verifyCode) {
                $.toast('请输入验证码！', 1000);
                return;
            } else {
                needVerify = true;
            }
        }

        // 访问后台登录验证
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: 'post',
            dataType: 'json',
            data: {
                username: username,
                password: password,
                verifyCodeInput: verifyCode,
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    $.toast('登录成功，跳转中...', 500);
                    if (userType ==1) {
                        window.location.href = '/o2o/front/index';
                    } else {
                        window.location.href = '/o2o/shop/shopList';
                    }
                } else {
                    $.toast('登录失败，' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        // 显示验证码区域
                        $('#verifyPart').removeAttr("hidden");
                    }
                }
            }
        })
    });
});