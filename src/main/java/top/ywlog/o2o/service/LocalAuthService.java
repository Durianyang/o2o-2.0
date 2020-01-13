package top.ywlog.o2o.service;

import top.ywlog.o2o.dto.LocalAuthExecution;
import top.ywlog.o2o.entity.LocalAuth;
import top.ywlog.o2o.exceptions.LocalAuthOperationException;

import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/11 17:02
 * Description:
 */
public interface LocalAuthService
{
    /**
     * 根据用户名查询账号
     *
     * @param username 用户名
     * @return 账号
     */
    LocalAuth getLocalAuthByUserName(String username);

    /**
     * 根据账号密码查询对应的本地账号
     *
     * @param username 用户名称
     * @param password 用户密码
     * @return 本地账号
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String username, String password);

    /**
     * 根据用户Id查询对应账号
     *
     * @param userId 用户Id
     * @return 本地账号
     */
    LocalAuth getLocalAuthByUserId(Long userId);

    /**
     * 绑定微信，生成专属账号
     *
     * @param localAuth 待添加的账号信息
     * @return 主键id
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

    /**
     * 修改账号密码
     *
     * @param userId       用户id
     * @param password     旧密码
     * @param newPassword  新密码
     * @param lastEditTime 更新时间
     * @return 影响数
     */
    LocalAuthExecution updateLocalAuthPassword(Long userId, String password, String newPassword, Date lastEditTime)
            throws LocalAuthOperationException;
}
