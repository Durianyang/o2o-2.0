package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/13 13:53
 * Description: 顾客与消费的商品映射关系
 */
@Data
public class UserProductMap implements Serializable
{
    private static final long serialVersionUID = -5855242539086499559L;

    /** 主键 */
    private Long userProductId;
    /** 创建时间 */
    private Date createTime;
    /** 兑现商品所需积分 */
    private Integer point;
    /** 顾客信息 */
    private PersonInfo user;
    /** 商品信息 */
    private Product product;
    /** 店铺信息 */
    private Shop shop;
    /** 操作员信息 */
    private PersonInfo operator;
}
