package top.ywlog.o2o.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ywlog.o2o.entity.Area;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 14:31
 * Description: AreaDao测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest
{
    @Autowired
    private AreaDao areaDao;

    @Test
    public void areaDaoListTest()
    {
        List<Area> list = areaDao.list();
        for (Area area : list)
        {
            System.out.println(area);
        }
    }
}
