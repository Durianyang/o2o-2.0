package top.ywlog.o2o.dao;

import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.PersonInfo;

/**
 * Author: Durian
 * Date: 2020/1/4 16:50
 * Description:
 */
@Repository
public interface PersonInfoDao
{
    /**
     * 根据用户Id查询用户信息
     *
     * @param userId userId
     * @return PersonInfo
     */
    PersonInfo getPersonInfoById(Long userId);

    /**
     * 添加用户信息
     *
     * @param personInfo 待添加的用户信息
     * @return 添加数
     */
    int insertPersonInfo(PersonInfo personInfo);
}
