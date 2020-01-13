package top.ywlog.o2o.service;

import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.ProductExecution;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/30 16:06
 * Description:
 */
public interface ProductService
{
    /**
     * @param product        商品信息
     * @param thumbnail      缩略图
     * @param productImgList 详情图
     * @return top.ywlog.o2o.dto.ProductExecution
     * @throws ProductOperationException 商品操作异常
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail,
                                List<ImageHolder> productImgList) throws ProductOperationException;

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
     * @param thumbnail      缩略图
     * @param productImgList 详情图
     * @param product        更新的product信息
     * @return top.ywlog.o2o.dto.ProductExecution
     */
    ProductExecution updateProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 查寻所有符合条件的商品列表
     *
     * @param productCondition 查询条件
     * @param pageIndex        查询起始页
     * @param pageSize         每页大小
     * @return top.ywlog.o2o.dto.ProductExecution
     */
    ProductExecution listProduct(Product productCondition, int pageIndex, int pageSize);

    /**
     * 按照商品id删除商品
     *
     * @param productId 商品ID
     * @return top.ywlog.o2o.dto.ProductExecution
     */
    ProductExecution deleteProductById(Long productId) throws ProductOperationException;
}
