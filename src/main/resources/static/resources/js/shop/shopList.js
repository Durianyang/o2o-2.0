$(function () {
    getList();

    function getList() {
        $.ajax({
            url: "/o2o/shop/getShopList",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleUser(data.user);
                    handleShopList(data.shopList);
                }
            }
        });
    }

    // 显示用户名
    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function handleShopList(data) {
        var html = '';
        if (data == null) {
            return;
        }
        data.map(function (item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus, item.shopId)
                + '</div></div>';
        });
        $('.shop-wrap').html(html);
    }

    function shopStatus(status) {
        if (status == 0) {
            return "审核中"
        } else if (status == -1) {
            return "非法店铺"
        } else if (status == 1) {
            return "审核通过"
        }
    }

    function goShop(status, shopId) {
        if (status == 1) {
            return '<a href="/o2o/shop/shopManagement?shopId=' + shopId + '">进入</a>';
        }
        return '';
    }

});