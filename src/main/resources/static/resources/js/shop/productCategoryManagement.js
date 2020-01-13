$(function () {
    var listUrl = '/o2o/shop/getProductCategoryList';
    var addUrl = '/o2o/shop/addProductCategory';
    var deleteUrl = '/o2o/shop/removeProductCategory';
    getList();

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var dataList = data.rows;
                $('.category-wrap').html('');
                var tempHtml = '';
                dataList.map(function (value, index) {
                    tempHtml += ''
                        + '<div class="row row-product-category now">'
                        + '<div class="col-33 product-category-name">'
                        + value.productCategoryName
                        + '</div>'
                        + '<div class="col-33">'
                        + value.priority
                        + '</div>'
                        + '<div class="col-33"><a href="#" class="button delete" data-id="'
                        + value.productCategoryId
                        + '">删除</a></div>' + '</div>';
                });
                $('.category-wrap').append(tempHtml);
            }
        });
    }

    $('#new').click(function () {
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-33"><input class="category-input category" type="text" placeholder="输入分类名"></div>'
            + '<div class="col-33"><input class="category-input priority" type="number" placeholder="输入优先级"></div>'
            + '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $('.category-wrap').append(tempHtml);
    });

    $('#submit').click(function () {
        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function (index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(productCategoryList),
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！', 500);
                    // 成功后显示新的分类列表
                    getList();
                } else {
                    $.toast('提交失败！' + data.errMsg, 1000);
                }
            }
        });
    });

    // 删除已提交的分类
    $('.category-wrap').on('click', '.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm('确定么?', function () {
                $.ajax({
                    url: deleteUrl,
                    type: 'GET',
                    data: {
                        productCategoryId: target.dataset.id
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.toast('删除成功！', 500);
                            getList();
                        } else {
                            $.toast('删除失败！' + data.errMsg, 500);
                        }
                    }
                });
            });
        });

    // 删除还未提交的分类
    $('.category-wrap').on('click', '.row-product-category.temp .delete',
        function (e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();
        });

});