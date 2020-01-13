// 表单验证优化
$(function () {
    var shopId = getQueryParam('shopId');
    var isEdit = shopId ? true : false;
    var initUrl = "/o2o/shop/getShopInitInfo";
    var registerShopUrl = "/o2o/shop/registerShop";
    var shopInfoUrl = "/o2o/shop/getShopInfo?shopId=" + shopId;
    var editShopUrl = "/o2o/shop/updateShop";
    if (!isEdit) {
        $('#shopTitle').val("店铺注册");
        getShopInitInfo();
    } else {
        $('#shopTitle').val("店铺更新");
        getShopInfo(shopId);
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                // 分类信息不允许修改
                var shopCategory = '<option data-id="' + shop.shopCategory.shopCategoryId + '"selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    if (shop.area.areaId == item.areaId) {
                        tempAreaHtml += '<option data-id ="' + item.areaId + '" selected="selected">'
                            + item.areaName + '</option>';
                    } else {
                        tempAreaHtml += '<option data-id ="' + item.areaId + '">'
                            + item.areaName + '</option>';
                    }

                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled');
                $('#shop-area').html(tempAreaHtml);
            }
        });
    }

    // 获取分类 和 区域信息
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id ="' + item.shopCategoryId + '">'
                        + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id ="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#shop-area').html(tempAreaHtml);
            }
        });
    }

    $('#submit').click(function () {
        $.showIndicator();
        var shop = {};
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#shop-area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shop', JSON.stringify(shop));
        var verifyCode = $('#j_kaptcha').val();
        if (!verifyCode) {
            $.hideIndicator();
            $.toast('请输入验证码!', 1000);
            return;
        }
        formData.append('verifyCodeInput', verifyCode);
        $.ajax({
            url: (isEdit ? editShopUrl : registerShopUrl),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.hideIndicator();
                    $.toast('提交成功!');
                    if (isEdit) {
                        window.setTimeout("window.location='/o2o/shop/shopManagement?shopId=" + data.row.shopId + "'", 750);
                    } else if (!isEdit) {
                        window.setTimeout("window.location='/o2o/shop/shopList'", 750);
                    }
                } else {
                    $.hideIndicator();
                    $.toast(data.errMsg, 500);
                }
                // 点击提交将改变验证码
                $('#kaptcha_img').click();
            }
        });
    });
});
