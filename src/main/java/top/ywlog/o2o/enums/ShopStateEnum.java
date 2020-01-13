package top.ywlog.o2o.enums;

import lombok.Getter;

/**
 * Author: Durian
 * Date: 2019/12/26 12:30
 * Description: 商铺状态枚举类
 */
@Getter
public enum ShopStateEnum
{
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "系统错误"),
    NULL_SHOPID(-1002, "店铺ID为空"),
    NULL_SHOP(-1003, "店铺信息为空");


    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo)
    {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum getShopStateEnumByState(int state)
    {
        for (ShopStateEnum shopStateEnum : values())
        {
            if (shopStateEnum.getState() == state)
            {
                return shopStateEnum;
            }
        }
        return null;
    }
}
