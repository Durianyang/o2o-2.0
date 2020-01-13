package top.ywlog.o2o.dao;

import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.ProductImg;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/30 15:38
 * Description: 商品图片Dao接口
 */
@Repository
public interface ProductImgDao
{
    /**
     * 批量添加商品图片
     *
     * @param productImgList 待添加的图片
     * @return 添加图片数量
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     *
     * @param productId 产品Id
     * @return 删除记录数
     */
    int deleteProductImgByProductId(Long productId);

    /**
     * 获取指定商品下的所有详情图
     *
     * @param productId 商品ID
     * @return List<ProductImg>
     */
    List<ProductImg> listProductImg(Long productId);
}
