package top.ywlog.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.LocalAuth;

import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/11 16:21
 * Description:
 */
@Repository
public interface LocalAuthDao
{
    /**
     * 根据用户名查询账号
     *
     * @param username 用户名
     * @return 账号
     */
    LocalAuth getLocalAuthByUserName(@Param("username") String username);

    /**
     * 根据账号密码查询对应的本地账号
     *
     * @param username 用户名称
     * @param password 用户密码
     * @return 本地账号
     */
    LocalAuth getLocalAuthByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户Id查询对应账号
     *
     * @param userId 用户Id
     * @return 本地账号
     */
    LocalAuth getLocalAuthByUserId(@Param("userId") Long userId);

    /**
     * 添加本地账号
     *
     * @param localAuth 待添加的账号信息
     * @return 主键id
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 修改账号密码
     *
     * @param userId       用户id
     * @param password     旧密码
     * @param newPassword  新密码
     * @param lastEditTime 更新时间
     * @return 影响数
     */
    int updateLocalAuthPassword(@Param("userId") Long userId, @Param("password") String password,
                                @Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEditTime);

}
