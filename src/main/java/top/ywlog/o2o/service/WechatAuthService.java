package top.ywlog.o2o.service;

import top.ywlog.o2o.dto.WechatAuthExecution;
import top.ywlog.o2o.entity.WechatAuth;
import top.ywlog.o2o.exceptions.WechatAuthOperationException;

/**
 * Author: Durian
 * Date: 2020/1/4 17:34
 * Description:
 */
public interface WechatAuthService
{
    /**
     * 根据openId查询对应本平台的微信账号
     *
     * @param openId openId
     * @return top.ywlog.o2o.entity.WechatAuth
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     * 注册本平台微信账号
     *
     * @param wechatAuth 微信账号
     * @return top.ywlog.o2o.dto.WechatAuthExecution
     * @throws WechatAuthOperationException top.ywlog.o2o.exceptions.WechatAuthOperationException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;
}
