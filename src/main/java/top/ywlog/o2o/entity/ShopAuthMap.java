package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/13 14:05
 * Description: 店铺人员授权
 */
@Data
public class ShopAuthMap implements Serializable
{
    private static final long serialVersionUID = 5952963935858697003L;

    private Long shopAuthId;
    /** 职位名 */
    private String title;
    /** 职位名标识符号 */
    private Integer titleFlag;
    /** 有效状态：0、无效，1:、有效 */
    private Integer enableStatus;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date lastEditTime;
    /** 员工信息  */
    private PersonInfo employee;
    /** 店铺信息 */
    private Shop shop;
}
