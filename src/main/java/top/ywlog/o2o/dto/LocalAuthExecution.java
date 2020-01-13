package top.ywlog.o2o.dto;

import lombok.Getter;
import lombok.Setter;
import top.ywlog.o2o.entity.LocalAuth;
import top.ywlog.o2o.enums.LocalAuthEnum;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/1/11 17:04
 * Description:
 */
@Getter
@Setter
public class LocalAuthExecution
{
    /** 结果状态 */
    private int state;
    /** 状态标识 */
    private String stateInfo;
    /** 店铺数量 */
    private int count;
    /** 操作的分类(增删改时) */
    private LocalAuth localAuth;
    /** 查询操作 */
    private List<LocalAuth> localAuthList;

    public LocalAuthExecution(){}

    /**
     * 失败时调用
     * @param state 状态枚举类
     */
    public LocalAuthExecution(LocalAuthEnum state)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }

    public LocalAuthExecution(LocalAuthEnum state, List<LocalAuth> localAuthList, int count)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.localAuthList = localAuthList;
        this.count = count;
    }

    public LocalAuthExecution(LocalAuthEnum state, int count)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.count = count;
    }

    public LocalAuthExecution(LocalAuthEnum state, LocalAuth localAuth, int count)
    {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.count = count;
    }
}
