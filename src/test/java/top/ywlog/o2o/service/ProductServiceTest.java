package top.ywlog.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.ProductExecution;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.entity.ProductCategory;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.enums.ProductStateEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2019/12/30 22:28
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest
{
    @Autowired
    private ProductService productService;

    @Test
    public void addProductTest() throws FileNotFoundException
    {
        Product p = new Product();
        Shop shop = new Shop();
        shop.setShopId(20L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(11L);
        p.setShop(shop);
        p.setProductCategory(pc);
        p.setProductName("测试商品");
        p.setProductDesc("测试商品");
        p.setPriority(1);
        p.setCreateTime(new Date());
        p.setLastEditTime(new Date());
        p.setEnableStatus(1);
        // 创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\190337-1574852617ea0a.jpg");
        InputStream in = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder();
        imageHolder.setImageName(thumbnailFile.getName());
        imageHolder.setImage(in);
        // 创建两个商品详情图文件流
        File productImg1 = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\190337-1574852617ea0a.jpg");
        InputStream in1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\212516-15666531161ade.jpg");
        InputStream in2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>(2);
        productImgList.add(new ImageHolder(in1, productImg1.getName()));
        productImgList.add(new ImageHolder(in2, productImg2.getName()));
        ProductExecution productExecution = productService.addProduct(p, imageHolder, productImgList);
        System.out.println("productExecution = " + productExecution.getStateInfo());
    }

    @Test
    public void updateProductTest() throws FileNotFoundException
    {
        Product p = new Product();
        p.setProductId(26L);
        Shop shop = new Shop();
        shop.setShopId(15L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(35L);
        p.setShop(shop);
        p.setProductCategory(pc);
        p.setProductName("正式商品");
        p.setProductDesc("正式商品");
        p.setPriority(1);
        p.setCreateTime(new Date());
        p.setLastEditTime(new Date());
        p.setEnableStatus(1);
        // 创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\212516-15666531161ade.jpg");
        InputStream in = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder();
        imageHolder.setImageName(thumbnailFile.getName());
        imageHolder.setImage(in);
        // 创建两个商品详情图文件流
        File productImg1 = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\212516-15666531161ade.jpg");
        InputStream in1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:\\Users\\Durian\\Pictures\\Saved Pictures\\212516-15666531161ade.jpg");
        InputStream in2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>(2);
        productImgList.add(new ImageHolder(in1, productImg1.getName()));
        productImgList.add(new ImageHolder(in2, productImg2.getName()));
        ProductExecution pe = productService.updateProduct(p, imageHolder, productImgList);
        assert Objects.equals(ProductStateEnum.SUCCESS.getStateInfo(), pe.getStateInfo());
    }

    @Test
    public void getProductByIdTest()
    {
        System.out.println(productService.getProductById(30L));
    }

    @Test
    public void deleteTest()
    {
        ProductExecution productExecution = productService.deleteProductById(17L);
        System.out.println(productExecution.getStateInfo());
    }
}
