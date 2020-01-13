package top.ywlog.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import top.ywlog.o2o.entity.Area;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 14:51
 * Description: AreaService测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest
{
    @Autowired
    private AreaService areaService;
    @Test
    public void areaServiceTest()
    {
        List<Area> list = areaService.list();
        for (Area area : list)
        {
            System.out.println(area);
        }
    }
}
