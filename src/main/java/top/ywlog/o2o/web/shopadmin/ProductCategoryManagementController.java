package top.ywlog.o2o.web.shopadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.dto.JsonMapResult;
import top.ywlog.o2o.dto.ProductCategoryExecution;
import top.ywlog.o2o.entity.ProductCategory;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.enums.ProductCategoryEnum;
import top.ywlog.o2o.exceptions.ProductCategoryOperationException;
import top.ywlog.o2o.service.ProductCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/29 16:45
 * Description: 店铺分类控制器
 */
@Controller
@RequestMapping("/shop")
public class ProductCategoryManagementController
{
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryManagementController(ProductCategoryService productCategoryService)
    {
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/getProductCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public JsonMapResult<ProductCategory> getProductCategoryList(HttpServletRequest request)
    {
        JsonMapResult<ProductCategory> result = new JsonMapResult<>();
        /* 单独测试用
        Shop shop = new Shop();
        shop.setShopId(20L);
        request.getSession().setAttribute("currentShop", shop);
        */

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() > 0)
        {
            List<ProductCategory> list = productCategoryService.list(currentShop.getShopId());
            result.setTotal(list.size());
            result.setRows(list);
            result.setSuccess(true);
        } else
        {
            result.setErrMsg(ProductCategoryEnum.INNER_ERROR.getStateInfo());
        }
        return result;
    }

    @RequestMapping(value = "/addProductCategory", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<ProductCategory> batchAddProductCategory(@RequestBody List<ProductCategory> productCategories,
                                                                  HttpServletRequest request)
    {
        JsonMapResult<ProductCategory> result = new JsonMapResult<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (productCategories != null && productCategories.size() > 0)
        {
            for (ProductCategory productCategory : productCategories)
            {
                productCategory.setShopId(currentShop.getShopId());
                productCategory.setCreateTime(new Date());
            }
            try
            {
                ProductCategoryExecution pe = productCategoryService.batchInsert(productCategories);
                if (pe.getState() == ProductCategoryEnum.SUCCESS.getState())
                {
                    result.setSuccess(true);
                    result.setRows(pe.getProductCategoryList());
                    result.setTotal(pe.getCount());
                }
            } catch (ProductCategoryOperationException e)
            {
                result.setSuccess(false);
                result.setErrMsg(e.toString());
                return result;
            }

        } else
        {
            result.setSuccess(false);
            result.setErrMsg("请至少添加一个商品类别！");
        }
        return result;
    }

    @RequestMapping(value = "/removeProductCategory", method = RequestMethod.GET)
    @ResponseBody
    public JsonMapResult<ProductCategory> deleteProductCategory(Long productCategoryId, HttpServletRequest request)
    {
        JsonMapResult<ProductCategory> result = new JsonMapResult<>();
        if (productCategoryId != null && productCategoryId > 0)
        {
            try
            {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryEnum.SUCCESS.getState())
                {
                    result.setSuccess(true);
                    result.setTotal(pe.getCount());
                } else
                {
                    result.setSuccess(false);
                    result.setErrMsg(pe.getStateInfo());
                }

            } catch (Exception e)
            {
                result.setSuccess(false);
                result.setErrMsg("删除失败！");
            }
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("商品ID不存在！");
        }
        return result;
    }
}
