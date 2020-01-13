package top.ywlog.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.ShopExecution;
import top.ywlog.o2o.entity.Area;
import top.ywlog.o2o.entity.PersonInfo;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.entity.ShopCategory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OptionalDataException;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 13:15
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest
{
    @Autowired
    private ShopService shopService;
    @Test
    public void addShopTest() throws FileNotFoundException
    {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(11L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(13L);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setAdvice("无");
        shop.setPhone("123456");
        shop.setPriority(100);
        shop.setShopAddr("店铺service测试");
        shop.setShopDesc("店铺service测试");
        shop.setShopName("店铺service测试");
        File shopImg = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\190337-1574852617ea0a.jpg");
        ImageHolder imageHolder = new ImageHolder();
        imageHolder.setImage(new FileInputStream(shopImg));
        imageHolder.setImageName(shopImg.getName());
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
        System.out.println(shopExecution.getStateInfo());
    }

    @Test
    public void updateShopTest() throws FileNotFoundException
    {
        Shop shop = shopService.getShopById(68L);
        shop.setShopName("茶颜悦色");
        File shopImg = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\212516-15666531161ade.jpg");
        ImageHolder imageHolder = new ImageHolder();
        imageHolder.setImage(new FileInputStream(shopImg));
        imageHolder.setImageName(shopImg.getName());
        ShopExecution shopExecution = shopService.updateShop(shop, imageHolder);
        System.out.println(shopExecution.getStateInfo());
    }

    @Test
    public void listShopPageTest() throws OptionalDataException
    {
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(22L);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution listShopPage = shopService.listShopPage(shopCondition, 1, 10);
        List<Shop> shopList = listShopPage.getShopList();
        for (Shop shop : shopList)
        {
            System.out.println("shop = " + shop);
        }
    }

}
