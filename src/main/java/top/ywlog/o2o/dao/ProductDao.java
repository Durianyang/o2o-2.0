package top.ywlog.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.Product;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/30 15:37
 * Description: 商品类dao接口
 */
@Repository
public interface ProductDao
{
    /**
     * 新增商品
     *
     * @param product 待添加的商品
     * @return 影响记录数
     */
    int insertProduct(Product product);

    /**
     * 根据productId获取Product
     *
     * @param productId 产品ID
     * @return product
     */
    Product getProductById(Long productId);

    /**
     * 更新product
     *
     * @param product 待更新的product
     * @return 更新记录数
     */
    int updateProduct(Product product);

    /**
     * 查询所有商品列表（可按条件查询）
     *
     * @param productCondition 查询条件
     * @param rowIndex         查询行
     * @param pageSize         每页行数
     * @return List<Product>
     */
    List<Product> listProduct(@Param("productCondition") Product productCondition,
                              @Param("rowIndex") int rowIndex,
                              @Param("pageSize") int pageSize);

    /**
     * 按条件查寻商品总数
     *
     * @param productCondition 查询条件
     * @return 商品总数
     */
    int listProductCount(@Param("productCondition") Product productCondition);

    /**
     * 按照ID删除商品
     *
     * @param productId 商品ID
     * @return 删除记录数
     */
    int deleteProductById(Long productId);

    /**
     * 将某个分类下商品的分类Id置为null
     *
     * @param productCategoryId 商品分类ID
     * @return 更新记录
     */
    int updateProductCategoryToNull(Long productCategoryId);
}
