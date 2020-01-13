package top.ywlog.o2o.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.ywlog.o2o.dao.PersonInfoDao;
import top.ywlog.o2o.dao.WechatAuthDao;
import top.ywlog.o2o.dto.WechatAuthExecution;
import top.ywlog.o2o.entity.PersonInfo;
import top.ywlog.o2o.entity.WechatAuth;
import top.ywlog.o2o.enums.WechatAuthStateEnum;
import top.ywlog.o2o.exceptions.WechatAuthOperationException;
import top.ywlog.o2o.service.WechatAuthService;

import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/4 17:42
 * Description:
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatAuthServiceImpl.class);

    private final WechatAuthDao wechatAuthDao;
    private final PersonInfoDao personInfoDao;

    @Autowired
    public WechatAuthServiceImpl(PersonInfoDao personInfoDao, WechatAuthDao wechatAuthDao)
    {
        this.personInfoDao = personInfoDao;
        this.wechatAuthDao = wechatAuthDao;
    }

    @Override
    public WechatAuth getWechatAuthByOpenId(String openId)
    {
        return wechatAuthDao.getWechatAuthByOpenId(openId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException
    {
        // 空值判断
        if (wechatAuth != null && wechatAuth.getOpenId() != null)
        {
            // 设置默认值
            wechatAuth.setCreateTime(new Date());
            // 如果微信账号携带用户信息并且用户ID为空，则认为该用户第一次进入该平台(且通过微信登录),此时自动创建用户信息
            if (wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null)
            {
                try
                {
                    wechatAuth.getPersonInfo().setCreateTime(new Date());
                    wechatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = wechatAuth.getPersonInfo();
                    long count = personInfoDao.insertPersonInfo(personInfo);
                    if (count <= 0)
                    {
                        throw new WechatAuthOperationException("添加用户信息失败！");
                    }
                } catch (WechatAuthOperationException e)
                {
                    LOGGER.error("insert PersonInfo Error: " + e.toString());
                    throw new WechatAuthOperationException("insert PersonInfo Error: " + e.getMessage());
                }
            }
            // 创建属于本平台的微信账号
            try
            {

                int count = wechatAuthDao.insertWechatAuth(wechatAuth);
                if (count <= 0)
                {
                    throw new WechatAuthOperationException("账号创建失败！");
                } else
                {
                    return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
                }
            } catch (WechatAuthOperationException e)
            {
                LOGGER.error("insert WechatAuth Error: " + e.toString());
                throw new WechatAuthOperationException("insert WechatAuth Error:"+ e.getMessage());
            }

        } else
        {
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
