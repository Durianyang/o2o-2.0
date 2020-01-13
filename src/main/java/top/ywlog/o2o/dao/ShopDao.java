package top.ywlog.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.Shop;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 20:56
 * Description: 店铺dao
 */
@Repository
public interface ShopDao
{
    /**
     * 新增店铺
     *
     * @param shop 新增店铺信息
     * @return int 插入影响行数
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop 更新信息
     * @return int 更新影响行数
     */
    int updateShop(Shop shop);

    /**
     * 根据店铺ID查询店铺信息
     *
     * @param shopId 店铺ID
     * @return 店铺信息
     */
    Shop getShopById(Long shopId);


    /**
     * 分页查询店铺列表，可查询条件：店铺名（模糊），店铺状态，店铺类别，区域ID，owner
     *
     * @param shopCondition 查询条件
     * @param rowIndex      从第几行开始
     * @param pageSize      每页大小
     * @return 每页的List<Shop>
     */
    List<Shop> listShopPage(@Param("shopCondition") Shop shopCondition,
                            @Param("rowIndex") int rowIndex,
                            @Param("pageSize") int pageSize);


    /**
     * 查询符合条件的shop总数
     *
     * @param shopCondition 查询条件
     * @return shop总行数
     */
    int shopCount(@Param("shopCondition") Shop shopCondition);
}
