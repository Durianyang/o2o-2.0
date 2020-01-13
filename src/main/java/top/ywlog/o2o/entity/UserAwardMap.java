package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/13 13:47
 * Description: 顾客与奖品的映射实体类
 */
@Data
public class UserAwardMap implements Serializable
{
    private static final long serialVersionUID = 4436719546807719588L;

    /** 主键 */
    private Long userAwardId;
    /** 创建时间 */
    private Date createTime;
    /** 使用状态：1、已兑换， 0、未兑换 */
    private Integer useStatus;
    /** 领取奖品消耗的积分 */
    private Integer point;
    /** 顾客信息 */
    private PersonInfo user;
    /** 奖品信息 */
    private Award award;
    /** 店铺信息 */
    private Shop shop;
    /** 操作员信息 */
    private PersonInfo operator;
}
