package top.ywlog.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ywlog.o2o.entity.ShopCategory;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 16:45
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryServiceTest
{
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void list()
    {
        List<ShopCategory> list = shopCategoryService.list(new ShopCategory());
        for (ShopCategory shopCategory : list)
        {
            System.out.println(shopCategory.getShopCategoryName());
        }

    }
}
