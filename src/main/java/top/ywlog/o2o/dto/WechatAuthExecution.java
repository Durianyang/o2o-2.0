package top.ywlog.o2o.dto;

import lombok.Getter;
import lombok.Setter;
import top.ywlog.o2o.entity.WechatAuth;
import top.ywlog.o2o.enums.WechatAuthStateEnum;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/1/4 17:37
 * Description:
 */
@Setter
@Getter
public class WechatAuthExecution
{
    /**
     * 结果状态
     */
    private int state;
    /**
     * 状态标识
     */
    private String stateInfo;
    /**
     * 店铺数量
     */
    private int count;
    /**
     * 操作的shop(增删改时)
     */
    private WechatAuth wechatAuth;
    /**
     * 查询操作的shop列表
     */
    private List<WechatAuth> wechatAuthList;

    /**
     * 店铺操作失败时使用的构造器
     *
     * @param wechatAuthStateEnum 店铺状态枚举
     */
    public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum)
    {
        this.state = wechatAuthStateEnum.getState();
        this.stateInfo = wechatAuthStateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功时的构造器
     *
     * @param wechatAuthStateEnum 店铺状态枚举
     */
    public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum, WechatAuth wechatAuth)
    {
        this.state = wechatAuthStateEnum.getState();
        this.stateInfo = wechatAuthStateEnum.getStateInfo();
        this.wechatAuth = wechatAuth;
    }

    /**
     * 店铺操作成功时的构造器
     *
     * @param wechatAuthStateEnum 店铺状态枚举
     */
    public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum, List<WechatAuth> wechatAuthList)
    {
        this.state = wechatAuthStateEnum.getState();
        this.stateInfo = wechatAuthStateEnum.getStateInfo();
        this.wechatAuthList = wechatAuthList;
    }
}
