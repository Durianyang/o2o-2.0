package top.ywlog.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2019/12/25 13:09
 * Description: 店铺类别实体类,自身包含自身，parent为null时为父级类别
 */
@Getter
@Setter
public class ShopCategory implements Serializable
{
    private static final long serialVersionUID = -6433371543087775326L;
    /** 主键Id */
    private Long shopCategoryId;
    /** 店铺分类名称 */
    private String shopCategoryName;
    /** 店铺分类描述 */
    private String shopCategoryDesc;
    /** 店铺分类图片 */
    private String shopCategoryImg;
    /** 店铺分类权重，越大越优先 */
    private Integer priority;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date lastEditTime;
    /** 父级分类 */
    private ShopCategory parent;
}
