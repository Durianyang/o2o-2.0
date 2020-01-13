package top.ywlog.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ywlog.o2o.service.CacheService;
import top.ywlog.o2o.util.RedisUtil;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Author: Durian
 * Date: 2020/1/6 21:49
 * Description:
 */
@Service
public class CacheServiceImpl implements CacheService
{
    @Resource
    private RedisUtil redisUtil;

    @Override
    public void removeFromCache(String keyPrefix)
    {
        Set<String> keySet = redisUtil.keys(keyPrefix + "*");
        for (String key : keySet)
        {
            redisUtil.del(key);
        }
    }
}
