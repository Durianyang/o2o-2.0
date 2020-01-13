package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2019/12/25 12:35
 * Description: 区域实体类--对应数据库表tb_area
 */
@Data
public class Area implements Serializable
{
    private static final long serialVersionUID = 9199681717711083331L;
    /** 主键ID */
    private Integer areaId;
    /** 区域名称 */
    private String areaName;
    /** 排名权重，越大越优先 */
    private Integer priority;
    /** 创建时间 */
    private Date creatTime;
    /** 更新时间 */
    private Date lastEditTime;
}
