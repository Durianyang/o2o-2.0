package top.ywlog.o2o.service;

import top.ywlog.o2o.entity.PersonInfo;

/**
 * Author: Durian
 * Date: 2020/1/4 18:36
 * Description:
 */
public interface PersonInfoService
{
    /**
     * 根据userID查询用户信息
     *
     * @param userId userId
     * @return PersonInfo
     */
    PersonInfo getPersonInfoById(Long userId);
}
