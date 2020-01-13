package top.ywlog.o2o.dto;

import lombok.Getter;
import lombok.Setter;
import top.ywlog.o2o.entity.ProductCategory;
import top.ywlog.o2o.enums.ProductCategoryEnum;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/30 13:41
 * Description:
 */
@Getter
@Setter
public class ProductCategoryExecution
{
    /** 结果状态 */
    private int state;
    /** 状态标识 */
    private String stateInfo;
    /** 店铺数量 */
    private int count;
    /** 操作的分类(增删改时) */
    private ProductCategory productCategory;
    /** 查询操作的分类列表 */
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution(){}

    /**
     * 失败时调用
     * @param state 状态枚举类
     */
    public ProductCategoryExecution(ProductCategoryEnum state)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }

    public ProductCategoryExecution(ProductCategoryEnum state, List<ProductCategory> productCategories, int count)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.productCategoryList = productCategories;
        this.count = count;
    }

    public ProductCategoryExecution(ProductCategoryEnum state, int count)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.count = count;
    }


}
