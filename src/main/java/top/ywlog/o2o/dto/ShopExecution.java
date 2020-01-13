package top.ywlog.o2o.dto;

import lombok.Getter;
import lombok.Setter;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.enums.ShopStateEnum;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 12:28
 * Description:
 */
@Getter
@Setter
public class ShopExecution
{
    /** 结果状态 */
    private int state;
    /** 状态标识 */
    private String stateInfo;
    /** 店铺数量 */
    private int count;
    /** 操作的shop(增删改时) */
    private Shop shop;
    /** 查询操作的shop列表 */
    private List<Shop> shopList;

    public ShopExecution(){}

    /**
     * 店铺操作失败时使用的构造器
     * @param shopStateEnum 店铺状态枚举
     */
    public ShopExecution(ShopStateEnum shopStateEnum)
    {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功时的构造器
     * @param shopStateEnum 店铺状态枚举
     */
    public ShopExecution(ShopStateEnum shopStateEnum, Shop shop)
    {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 店铺操作成功时的构造器
     * @param shopStateEnum 店铺状态枚举
     */
    public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shopList)
    {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;
    }
}
