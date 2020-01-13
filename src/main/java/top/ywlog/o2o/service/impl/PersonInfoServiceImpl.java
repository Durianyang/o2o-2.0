package top.ywlog.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ywlog.o2o.dao.PersonInfoDao;
import top.ywlog.o2o.entity.PersonInfo;
import top.ywlog.o2o.service.PersonInfoService;

/**
 * Author: Durian
 * Date: 2020/1/4 18:37
 * Description:
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService
{
    private final PersonInfoDao personInfoDao;

    @Autowired
    public PersonInfoServiceImpl(PersonInfoDao personInfoDao)
    {
        this.personInfoDao = personInfoDao;
    }

    @Override
    public PersonInfo getPersonInfoById(Long userId)
    {
        return personInfoDao.getPersonInfoById(userId);
    }
}
