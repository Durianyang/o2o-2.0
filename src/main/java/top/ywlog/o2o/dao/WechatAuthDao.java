package top.ywlog.o2o.dao;

import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.WechatAuth;

/**
 * Author: Durian
 * Date: 2020/1/4 16:53
 * Description:
 */
@Repository
public interface WechatAuthDao
{
    /**
     * 根据openId查询对应本平台的微信账号
     *
     * @param openId openId
     * @return top.ywlog.o2o.entity.WechatAuth
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     * 添加对应本平台的微信账号
     *
     * @param wechatAuth 待添加的微信账号信息
     * @return 添加数
     */
    int insertWechatAuth(WechatAuth wechatAuth);
}
