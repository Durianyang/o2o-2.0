package top.ywlog.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: Durian
 * Date: 2020/1/4 15:28
 * Description: 微信用户实体类
 */
@Data
public class WechatUser implements Serializable
{
    private static final long serialVersionUID = -8387201013836019375L;

    /** openId,标识该公众号下面的该用户的唯一Id */
    @JsonProperty("openid")
    private String openId;
    /** 用户昵称 */
    @JsonProperty("nickname")
    private String nickName;
    /** 性别 */
    @JsonProperty("sex")
    private int sex;
    /** 省份 */
    @JsonProperty("province")
    private String province;
    /** 城市 */
    @JsonProperty("city")
    private String city;
    /** 区 */
    @JsonProperty("country")
    private String country;
    /** 头像图片地址 */
    @JsonProperty("headimgurl")
    private String headimgurl;
    /** 语言 */
    @JsonProperty("language")
    private String language;
    /** 用户权限，这里没什么作用 */
    @JsonProperty("privilege")
    private String[] privilege;
}
