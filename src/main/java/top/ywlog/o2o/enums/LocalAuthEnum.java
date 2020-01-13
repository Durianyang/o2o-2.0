package top.ywlog.o2o.enums;

import lombok.Getter;

/**
 * Author: Durian
 * Date: 2020/1/11 17:05
 * Description:
 */
@Getter
public enum LocalAuthEnum
{
    LOGINFAIL(-1, "密码或帐号输入有误"),
    SUCCESS(0, "操作成功"),
    NULL_AUTH_INFO(-1006,"注册信息为空"),
    SAME_PASSWORD(-1002, "新密码与原始密码一致"),
    ONLY_ONE_ACCOUNT(-1007,"最多只能绑定一个本地帐号");

    private int state;

    private String stateInfo;

    LocalAuthEnum(int state, String stateInfo)
    {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static LocalAuthEnum getLocalAuthStateEnumByState(int index)
    {
        for (LocalAuthEnum state : values())
        {
            if (state.getState() == index)
            {
                return state;
            }
        }
        return null;
    }
}
