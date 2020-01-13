package top.ywlog.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: Durian
 * Date: 2020/1/13 20:31
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest
{
    @Autowired
    private CacheService cacheService;

    @Test
    public void test()
    {
        cacheService.removeFromCache("shopCategoryList");
    }
}
