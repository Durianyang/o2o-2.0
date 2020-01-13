package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/13 13:42
 * Description: 奖品实体类
 */
@Data
public class Award implements Serializable
{
    private static final long serialVersionUID = -2098031591057440440L;

    private Long awardId;
    private String awardName;
    private String awardDesc;
    private String awardImg;
    /** 兑换所需积分 */
    private Integer point;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;
    /** 所属店铺ID */
    private Long shopId;
}
