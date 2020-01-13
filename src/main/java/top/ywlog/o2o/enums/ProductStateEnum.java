package top.ywlog.o2o.enums;

import lombok.Getter;

/**
 * Author: Durian
 * Date: 2019/12/30 16:08
 * Description:
 */
@Getter
public enum ProductStateEnum
{
    NULL_PRODUCT(-1001, "商品信息为空"),
    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "操作失败");

    private int state;
    private String stateInfo;

    ProductStateEnum(int state, String stateInfo)
    {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum getStateInfoEnumByState(int state)
    {
        for (ProductStateEnum shopStateEnum : values())
        {
            if (shopStateEnum.getState() == state)
            {
                return shopStateEnum;
            }
        }
        return null;
    }
}
