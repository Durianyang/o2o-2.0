package top.ywlog.o2o.dto;

import lombok.Getter;
import lombok.Setter;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.enums.ProductStateEnum;
import top.ywlog.o2o.enums.ShopStateEnum;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/30 16:07
 * Description:
 */
@Getter
@Setter
public class ProductExecution
{
    /** 结果状态 */
    private int state;
    /** 状态标识 */
    private String stateInfo;
    /** 店品数量 */
    private int count;
    /** 操作的shop(增删改时) */
    private Product product;
    /** 查询操作的shop列表 */
    private List<Product> productList;

    public ProductExecution(){}

    /**
     * 店铺操作失败时使用的构造器
     * @param productStateEnum 店铺状态枚举
     */
    public ProductExecution(ProductStateEnum productStateEnum)
    {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功时的构造器
     * @param productStateEnum 店铺状态枚举
     */
    public ProductExecution(ProductStateEnum productStateEnum, Product product)
    {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.product = product;
    }

    /**
     * 店铺操作成功时的构造器
     * @param shopStateEnum 店铺状态枚举
     */
    public ProductExecution(ShopStateEnum shopStateEnum, List<Product> productList)
    {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.productList = productList;
    }
}
