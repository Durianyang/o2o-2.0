package top.ywlog.o2o.service;

import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.ShopExecution;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.exceptions.ShopOperationException;

/**
 * Author: Durian
 * Date: 2019/12/26 12:43
 * Description: 店铺操作接口
 */
public interface ShopService
{
    /**
     * 添加店铺
     *
     * @param shop               添加的店铺信息
     * @param shopImgInputStream 添加的店铺图片
     * @return top.ywlog.o2o.dto.ShopExecution
     */
    ShopExecution addShop(Shop shop, ImageHolder shopImgInputStream) throws ShopOperationException;

    /**
     * 根据店铺ID查询店铺信息
     *
     * @param shopId 店铺ID
     * @return 店铺信息
     */
    Shop getShopById(Long shopId);

    /**
     * 更新店铺信息
     *
     * @param shop               更新的店铺信息
     * @param shopImgInputStream 更新的店铺图片
     * @return top.ywlog.o2o.dto.ShopExecution
     */
    ShopExecution updateShop(Shop shop, ImageHolder shopImgInputStream) throws ShopOperationException;

    /**
     * 按条件分页查询Shop
     *
     * @param shopCondition 查询条件
     * @param pageIndex     查询页数
     * @param pageSize      每页大小
     * @return top.ywlog.o2o.dto.ShopExecution
     */
    ShopExecution listShopPage(Shop shopCondition, int pageIndex, int pageSize);
}
