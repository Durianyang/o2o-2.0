$(function () {
    var listUrl = '/o2o/shop/listProduct';
    var changeStatusUrl = '/o2o/shop/updateProduct';
    var deleteUrl = '/o2o/shop/deleteProduct';
    function getList() {
        // 获取此店铺下商品列表
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var productList = data.rows;
                var tempHtml = '';
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.point
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="update" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="delete" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">删除</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    getList();

    function changeStatusItem(id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function () {
            $.ajax({
                url: changeStatusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！', 500);
                        getList();
                    } else {
                        $.toast('操作失败！', 500);
                    }
                }
            });
        });
    }

    function deleteItem(productId) {
        $.confirm('确定么?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productId: productId
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('删除成功！', 500);
                        getList();
                    } else {
                        $.toast('删除失败！', 500);
                    }
                }
            });
        });
    }


    $('.product-wrap').on('click', 'a', function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/o2o/shop/productOperation?productId='
                + e.currentTarget.dataset.id;
        } else if (target.hasClass('update')) {
            changeStatusItem(e.currentTarget.dataset.id,
                e.currentTarget.dataset.status);
        } else if (target.hasClass('delete')) {
            deleteItem(e.currentTarget.dataset.id);
        }
    });

    $('#new').click(function () {
        window.location.href = '/o2o/shop/productOperation';
    });
});