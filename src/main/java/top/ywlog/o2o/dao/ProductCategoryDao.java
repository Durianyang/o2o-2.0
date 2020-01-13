package top.ywlog.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.ProductCategory;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/29 16:02
 * Description: 商品分类管理
 */
@Repository
public interface ProductCategoryDao
{
    /**
     * 列出相应商铺的商品分类列表
     *
     * @param shopId 商铺ID
     * @return 商品分类列表
     */
    List<ProductCategory> list(Long shopId);

    /**
     * 批量新增商品类别
     *
     * @param productCategories 待添加商品类别
     * @return 批量增加记录数
     */
    int batchInsert(List<ProductCategory> productCategories);

    /**
     * 删除商品分类
     *
     * @param productCategoryId 分类Id
     * @param shopId            分类所属店铺id
     * @return 删除影响记录数
     */
    int deleteProductCategory(@Param("productCategoryId") Long productCategoryId,
                              @Param("shopId") Long shopId);
}
