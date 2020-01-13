package top.ywlog.o2o.web.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.dto.JsonMapResult;
import top.ywlog.o2o.dto.ProductExecution;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.entity.ProductCategory;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.service.ProductCategoryService;
import top.ywlog.o2o.service.ProductService;
import top.ywlog.o2o.service.ShopService;
import top.ywlog.o2o.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Durian
 * Date: 2020/1/2 20:03
 * Description:
 */
@Controller
@RequestMapping("/front")
public class ShopDetailController
{
    private final ShopService shopService;
    private final ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    public ShopDetailController(ProductCategoryService productCategoryService, ShopService shopService)
    {
        this.productCategoryService = productCategoryService;
        this.shopService = shopService;
    }

    @RequestMapping(value = "getShopDetailInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopDetailInfo(Long shopId)
    {
        Map<String, Object> result = new HashMap<>();
        if (shopId != null && shopId > 0)
        {
            Shop shop = shopService.getShopById(shopId);
            List<ProductCategory> productCategoryList = productCategoryService.list(shopId);
            result.put("shop", shop);
            result.put("productCategoryList", productCategoryList);
            result.put("success", true);
        } else
        {
            result.put("success", false);
            result.put("errMsg", "店铺ID不存在！");
        }
        return result;
    }

    @RequestMapping("/listProductByShop")
    @ResponseBody
    public JsonMapResult<Product> listProductByShop(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                    HttpServletRequest request)
    {
        JsonMapResult<Product> result = new JsonMapResult<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1))
        {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            ProductExecution pe = productService.listProduct(productCondition, pageIndex, pageSize);
            result.setSuccess(true);
            result.setTotal(pe.getCount());
            result.setRows(pe.getProductList());
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("店铺ID不存在！");
        }
        return result;
    }

    /**
     * 封装查询参数
     *
     * @param shopId            店铺ID
     * @param productCategoryId 商品分类ID
     * @param productName       商品名称
     * @return Product
     */
    private Product compactProductCondition4Search(long shopId,
                                                   long productCategoryId, String productName)
    {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L)
        {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null)
        {
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }

}
