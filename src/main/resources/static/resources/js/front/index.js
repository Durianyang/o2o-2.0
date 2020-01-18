$(function () {
    var url = '/o2o/front/listMainPageInfo';

    $.getJSON(url, function (data) {
        if (data.success) {
            // 获取头条列表
            var headLineList = data.headLineList;
            var swiperHtml = '';
            // 遍历头条列表
            headLineList.map(function (item, index) {
                swiperHtml += ''
                    + '<div class="swiper-slide img-wrap">'
                    + '<img class="banner-img" src="' + getContextPath() + item.lineImg + '" alt="' + item.lineName + '">'
                    + '</div>';
            });
            $('.swiper-wrapper').html(swiperHtml);
            $(".swiper-container").swiper({
                // 轮播三秒
                autoplay: 3000,
                // 鼠标放上后是否停止轮播
                autoplayDisableOnInteraction: true
            });
            // 获取店铺一级分类
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            // 遍历分类列表
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                    + '<div class="col-50 shop-classify" data-category=' + item.shopCategoryId + '>'
                    + '<div class="word">'
                    + '<p class="shop-title">' + '<b>' + item.shopCategoryName + '</b>' + '</p>'
                    // + '<p class="shop-desc">' + item.shopCategoryDesc + '</p>'
                    + '</div>'
                    + '<div class="shop-classify-img-warp">'
                    + '<img class="shop-img" src="' + getContextPath() + item.shopCategoryImg + '">'
                    + '</div>'
                    + '</div>';
            });
            $('.row').html(categoryHtml);
        }
    });

    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });

    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        window.location.href = '/o2o/front/shopList?parentId=' + shopCategoryId;
    });

});
