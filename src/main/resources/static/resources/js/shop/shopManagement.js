$(function () {
    var shopId = getQueryParam('shopId');
    var shopInfoUrl = '/o2o/shop/getShopManagementInfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $('#shopInfo').attr('href', '/o2o/shop/shopOperation?shopId=' + shopId);
        }
    })
});