package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2019/12/25 13:03
 * Description: 头条实体类
 */
@Data
public class HeadLine implements Serializable
{
    private static final long serialVersionUID = -3714883809804342363L;
    /** 主键Id */
    private Long lineId;
    /** 头条名称 */
    private String lineName;
    /** 头条链接 */
    private String lineLink;
    /** 头条图片 */
    private String lineImg;
    /** 头条权重，越大越优先 */
    private Integer priority;
    /** 头条状态 0：禁用 1：启用 */
    private Integer enableStatus;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date lastEditTime;
}
