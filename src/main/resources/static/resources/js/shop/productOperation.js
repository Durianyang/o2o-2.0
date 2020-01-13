$(function () {
    var productId = getQueryParam('productId');
    var infoUrl = '/o2o/shop/getProductById?productId=' + productId;
    var categoryUrl = '/o2o/shop/getProductCategoryList';
    var postUrl = '/o2o/shop/updateProduct';
    var isEdit = false;
    if (productId) {
        getInfo(productId);
        isEdit = true;
    } else {
        getCategory();
        postUrl = '/o2o/shop/addProduct';
    }

    function getInfo(id) {
        $.getJSON(infoUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#point').val(product.point);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);

                var optionHtml = '';
                var optionArr = data.productCategoryList;
                var optionSelected;
                if (product.productCategory != null) {
                    optionSelected = product.productCategory.productCategoryId;
                }
                if (!optionSelected) {
                    optionHtml += '<option disabled="disabled" selected="selected">'
                        + '无'
                        + '</option>';
                }
                optionArr.map(function (item, index) {
                    if (optionSelected) {
                        var isSelect = optionSelected === item.productCategoryId ? 'selected'
                            : '';
                    }
                    optionHtml += '<option data-value="'
                        + item.productCategoryId
                        + '"'
                        + isSelect
                        + '>'
                        + item.productCategoryName
                        + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    function getCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.rows;
                var optionHtml = '';
                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    /**
     *  针对商品详情图控件组，若该控件的最后一个元素发生了变化（选则了文件）
     *  且控件总数未达到6个，则生成新的一个人文件上传控件
     */
    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        } else {
            $.toast('最多上传6张详情图片！');
        }
    });

    $('#submit').click(function () {
        $.showIndicator();
        var product = {};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.point = $('#point').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productCategory = {
            productCategoryId: $('#category').find('option').not(
                function () {
                    return !this.selected;
                }).data('value')
        };
        product.productId = productId;

        // 获取缩略图文件流
        var thumbnail = $('#small-img')[0].files[0];
        console.log(thumbnail);
        // 生成表单对象
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        // 遍历商品详情图控件，获取文件流
        $('.detail-img').map(
            function (index, item) {
                // 判断该控件是否选择了文件
                if ($('.detail-img')[index].files.length > 0) {
                    // 将第i个文件流赋值给key为productImg的表单键值对里
                    formData.append('productImg' + index,
                        $('.detail-img')[index].files[0]);
                }
            });
        // 将product的json对象转换成字符串
        formData.append('productStr', JSON.stringify(product));
        // 获取表单中的验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.hideIndicator();
            $.toast('请输入验证码！', 1000);
            return;
        }
        formData.append("verifyCodeInput", verifyCodeActual);
        $.ajax({
            url: postUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.hideIndicator();
                    $.toast('提交成功！', 500);
                    $('#kaptcha_img').click();
                    window.setTimeout("window.location='/o2o/shop/productManagement'", 750);
                } else {
                    $.hideIndicator();
                    $.toast(data.errMsg, 500);
                    $('#kaptcha_img').click();
                }
            }
        });
    });

});