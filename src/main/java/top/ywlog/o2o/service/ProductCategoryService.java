package top.ywlog.o2o.service;

import top.ywlog.o2o.dto.ProductCategoryExecution;
import top.ywlog.o2o.entity.ProductCategory;
import top.ywlog.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/29 16:20
 * Description:
 */
public interface ProductCategoryService
{
    /**
     * 列出相应商铺的商品分类列表
     *
     * @param shopId 商铺ID
     * @return 商品分类列表
     */
    List<ProductCategory> list(Long shopId);

    /**
     * 批量插入商品分类
     *
     * @param productCategories 待添加分类列表
     * @return top.ywlog.o2o.dto.ProductCategoryExecution
     */
    ProductCategoryExecution batchInsert(List<ProductCategory> productCategories) throws ProductCategoryOperationException;

    /**
     * 将该分类下的所有商品的分类Id置为空,再删除商品分类
     *
     * @param productCategoryId 分类Id
     * @param shopId            分类所属店铺Id
     * @return top.ywlog.o2o.dto.ProductCategoryExecution
     */
    ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId);
}
