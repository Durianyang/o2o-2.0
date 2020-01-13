$(function () {
    var productId = getQueryParam('productId');
    var productUrl = '/o2o/front/getProductDetail?productId='
        + productId;

    $.getJSON(productUrl, function (data) {
        if (data.success) {
            var product = data.row;
            $('#product-img').attr('src', product.imgAddr);
            $('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
            $('#product-name').text(product.productName);
            $('#product-desc').append(product.productDesc);
            if (product.normalPrice != null && product.normalPrice.length !== 0 &&
                product.promotionPrice != null && product.promotionPrice.length !== 0) {
                $('#price').removeAttr("hidden");
                $('#normal-price').html('<s>￥' + product.normalPrice + '</s>');
                $('#promotion-price').html("￥" + product.promotionPrice);
            } else if (product.normalPrice != null || product.promotionPrice === undefined) {
                $('#price').removeAttr("hidden");
                $('#normal-price').html("￥" + product.normalPrice);
            }
            if (product.point != 0 && product.point != null) {
                $('#product-point').text("购买可获得" + product.point + "积分");
            } else {
                $('#product-point').text("购买该商品无积分奖励");
            }

            var imgListHtml = '';
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div> <img src="'
                    + getContextPath() + item.imgAddr + '"/></div>';
            });
            // // 生成购买商品的二维码供商家扫描
            // imgListHtml += '<div> <img src="/o2o/front/generateqrcode4product?productId='
            //     + product.productId + '"/></div>';
            // $('#imgList').html(imgListHtml);
        } else {
            $.toast("商品信息加载失败！", 1000);
        }
    });
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });
    $.init();
});
