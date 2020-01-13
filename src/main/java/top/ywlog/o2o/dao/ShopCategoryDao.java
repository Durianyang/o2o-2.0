package top.ywlog.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.ShopCategory;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 16:25
 * Description:
 */
@Repository
public interface ShopCategoryDao
{
    /**
     * 查询所有可选店铺子分类或查询某个特定分类下的所有子分类
     * 新增：可查询父分类
     *
     * @param shopCategory 查询条件
     * @return 分类列表
     */
    List<ShopCategory> list(@Param("shopCategoryCondition") ShopCategory shopCategory);
}
