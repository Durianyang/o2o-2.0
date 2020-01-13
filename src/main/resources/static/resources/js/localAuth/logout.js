$(function () {
    $('#log-out').click(function () {
        // 清除session
        $.ajax({
            url: '/o2o/local/logout',
            type: 'get',
            async: false,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var userType = $('#log-out').attr("userType");
                    // 清除成功后退出到登陆界面
                    window.location.href = '/o2o/local/login?userType=' + userType;
                }
            },
            error: function (data, error) {
                alert(error);
            }
        })
    });
});