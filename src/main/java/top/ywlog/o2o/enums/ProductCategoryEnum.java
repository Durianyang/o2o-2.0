package top.ywlog.o2o.enums;

import lombok.Getter;

/**
 * Author: Durian
 * Date: 2019/12/29 17:02
 * Description:
 */
@Getter
public enum ProductCategoryEnum
{
    NULL_SHOPID(-1002, "店铺ID为空"),
    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY_LIST(-1003, "分类列表为空");

    private int state;
    private String stateInfo;

    ProductCategoryEnum(int state, String stateInfo)
    {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryEnum getStateInfoByState(int state)
    {
        for (ProductCategoryEnum productCategoryEnum : values())
        {
            if (productCategoryEnum.getState() == state)
            {
                return productCategoryEnum;
            }
        }
        return null;
    }
}
