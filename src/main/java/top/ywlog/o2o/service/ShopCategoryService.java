package top.ywlog.o2o.service;

import top.ywlog.o2o.entity.ShopCategory;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 16:37
 * Description:
 */
public interface ShopCategoryService
{
    String SHOP_CATEGORY_LIST = "shopCategoryList";

    /**
     * 查询所有shop子分类或某个父分类下的子分类
     * 新增：可查询父分类
     *
     * @param shopCategory 父类别
     * @return List<ShopCategory>
     */
    List<ShopCategory> list(ShopCategory shopCategory);
}
