$(function () {
    var loading = false;
    var maxItems = 100;
    var pageSize = 5;

    var listUrl = '/o2o/front/listProductByShop';

    var pageNum = 1;
    var shopId = getQueryParam('shopId');
    var productCategoryId = '';
    var productName = '';

    var searchDivUrl = '/o2o/front/getShopDetailInfo?shopId=' + shopId;

    function getSearchDivData() {
        var url = searchDivUrl;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.shopImg);
                $('#shop-update-time').html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').append(shop.shopDesc);
                $('#shop-addr').append(shop.shopAddr);
                $('#shop-phone').append(shop.phone);

                var productCategoryList = data.productCategoryList;
                var html = '';
                html += '<a href="#" class="button" data-product-search-id="">全部商品</a>';
                productCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-product-search-id='
                        + item.productCategoryId
                        + '>'
                        + item.productCategoryName
                        + '</a>';
                });
                $('#shopdetail-button-div').html(html);
            }
        });
    }

    getSearchDivData();

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success && data.total > 0) {
                maxItems = data.total;
                var html = '';
                if (data.rows == null) {
                    return;
                }
                data.rows.map(function (item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + getContextPath() + item.imgAddr + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                }
                // 页码+1，加载
                pageNum += 1;
                // 加载结束，可以继续加载了
                loading = false;
                // 刷新页面
                $.refreshScroller();
            } else {
                // 删除加载提示符
                $('.infinite-scroll-preloader').remove();
                var html = '<div align="center">暂无商品</div>';
                $('.list-div').html(html);
            }
        });
    }

    // 预览部分数据
    addItems(pageSize, pageNum);

    // 下滑屏幕自动分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function (e) {
            productCategoryId = e.target.dataset.productSearchId;
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                productCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            $('.list-div').empty();
            pageNum = 1;
            addItems(pageSize, pageNum);
        });

    $('.list-div').on(
        'click',
        '.card',
        function (e) {
            var productId = e.currentTarget.dataset.productId;
            window.location.href = '/o2o/front/productDetail?productId='
                + productId;
        });

    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });
    $.init();
});
