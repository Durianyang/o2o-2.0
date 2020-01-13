package top.ywlog.o2o.service;

/**
 * Author: Durian
 * Date: 2020/1/6 21:48
 * Description:
 */
public interface CacheService
{
    /**
     * 根据key的前缀删除匹配该模式下的所有key-value
     * @param keyPrefix key前缀
     */
    void removeFromCache(String keyPrefix);
}
