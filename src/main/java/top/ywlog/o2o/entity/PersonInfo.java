package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2019/12/25 12:45
 * Description: 用户信息实体类,后期可做优化，将性别、账号状态和账号类别改为枚举类型
 */
@Data
public class PersonInfo implements Serializable
{
    private static final long serialVersionUID = 2984690066312663457L;
    /** 用户主键Id,用户数量多，使用Long更好 */
    private Long userId;
    /** 用户名称 */
    private String name;
    /** 用户头像 */
    private String profileImg;
    /** 用户邮箱 */
    private String email;
    /** 用户性别 */
    private String gender;
    /** 用户账号状态 0.禁止 1.启用 */
    private Integer enableStatus;
    /** 账号类别 1.顾客 2.店家 3.管理员 */
    private Integer userType;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date lastEditTime;
}
