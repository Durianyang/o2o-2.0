package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 13:29
 * Description: 商品类
 */
@Data
public class Product implements Serializable
{
    private static final long serialVersionUID = -8999174623319073950L;
    /** 商品ID */
    private Long productId;
    /** 商品名称 */
    private String productName;
    /** 商品描述 */
    private String productDesc;
    /** 商品缩略图地址 */
    private String imgAddr;
    /** 原价 */
    private String normalPrice;
    /** 折扣价 */
    private String promotionPrice;
    /** 显示权重 */
    private Integer priority;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date lastEditTime;
    /** 商品状态 0：下架 1：在售 */
    private Integer enableStatus;
    /** 商品详情图片列表 */
    private List<ProductImg> productImgList;
    /** 所属分类 */
    private ProductCategory productCategory;
    /** 所属商铺 */
    private Shop shop;
    /** 积分 */
    private Integer point;
}
