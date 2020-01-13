package top.ywlog.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.ywlog.o2o.dao.LocalAuthDao;
import top.ywlog.o2o.dto.LocalAuthExecution;
import top.ywlog.o2o.entity.LocalAuth;
import top.ywlog.o2o.enums.LocalAuthEnum;
import top.ywlog.o2o.exceptions.LocalAuthOperationException;
import top.ywlog.o2o.service.LocalAuthService;
import top.ywlog.o2o.util.MD5Util;

import java.util.Date;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/1/11 17:02
 * Description:
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService
{
    private final LocalAuthDao localAuthDao;

    @Autowired
    public LocalAuthServiceImpl(LocalAuthDao localAuthDao)
    {
        this.localAuthDao = localAuthDao;
    }

    @Override
    public LocalAuth getLocalAuthByUserName(String username)
    {
        return localAuthDao.getLocalAuthByUserName(username);
    }

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String username, String password)
    {
        return localAuthDao.getLocalAuthByUserNameAndPwd(username, MD5Util.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(Long userId)
    {
        return localAuthDao.getLocalAuthByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException
    {
        // 空值判断
        if (localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null
                || localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null)
        {
            return new LocalAuthExecution(LocalAuthEnum.NULL_AUTH_INFO);
        }
        // 查询此用户是否已绑定过平台账号
        LocalAuth tempAuth = localAuthDao.getLocalAuthByUserId(localAuth.getPersonInfo().getUserId());
        if (tempAuth != null)
        {
            return new LocalAuthExecution(LocalAuthEnum.ONLY_ONE_ACCOUNT);
        }
        // 如果之前没有绑定，则创建一个账号与该用户绑定
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        localAuth.setPassword(MD5Util.getMd5(localAuth.getPassword()));
        int count = localAuthDao.insertLocalAuth(localAuth);
        if (count <= 0)
        {
            throw new LocalAuthOperationException("账号绑定失败！");
        } else
        {
            return new LocalAuthExecution(LocalAuthEnum.SUCCESS, localAuth, count);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LocalAuthExecution updateLocalAuthPassword(Long userId, String password, String newPassword, Date lastEditTime) throws LocalAuthOperationException
    {
        // 非空判断
        if (userId != null && password != null && newPassword != null)
        {
            if (Objects.equals(password, newPassword))
            {
                return new LocalAuthExecution(LocalAuthEnum.SAME_PASSWORD);
            }
            try
            {
                // 更新密码
                int count = localAuthDao.updateLocalAuthPassword(userId, MD5Util.getMd5(password),
                        MD5Util.getMd5(newPassword), lastEditTime);
                if (count <= 0)
                {
                    throw new LocalAuthOperationException("更新密码失败！");
                }
                return new LocalAuthExecution(LocalAuthEnum.SUCCESS);
            } catch (LocalAuthOperationException e)
            {
                throw new LocalAuthOperationException("更新密码失败！" + e.getMessage());
            }
        } else
        {
            return new LocalAuthExecution(LocalAuthEnum.NULL_AUTH_INFO);
        }
    }
}
