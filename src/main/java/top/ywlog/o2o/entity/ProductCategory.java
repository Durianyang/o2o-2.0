package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2019/12/25 13:24
 * Description: 商品分类
 */
@Data
public class ProductCategory implements Serializable
{
    private static final long serialVersionUID = 4527804663033514951L;
    /** 主键Id */
    private Long productCategoryId;
    /** 关联外键Id */
    private Long shopId;
    /** 分类名称 */
    private String productCategoryName;
    /** 分类权重 */
    private Integer priority;
    /** 创建时间 */
    private Date createTime;
}
