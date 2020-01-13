package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/13 14:01
 * Description: 顾客与店铺的映射
 */
@Data
public class UserShopMap implements Serializable
{
    private static final long serialVersionUID = -6344107632898623764L;

    /** 主键 */
    private Long userShopId;
    /** 创建时间 */
    private Date createTime;
    /** 顾客在本店拥有的积分 */
    private Integer point;
    /** 顾客信息 */
    private PersonInfo user;
    /** 店铺信息 */
    private Shop shop;
}
