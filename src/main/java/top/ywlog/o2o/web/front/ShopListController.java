package top.ywlog.o2o.web.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.dto.ShopExecution;
import top.ywlog.o2o.entity.Area;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.entity.ShopCategory;
import top.ywlog.o2o.service.AreaService;
import top.ywlog.o2o.service.ShopCategoryService;
import top.ywlog.o2o.service.ShopService;
import top.ywlog.o2o.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Durian
 * Date: 2020/1/2 17:40
 * Description: 店铺前端展示控制器
 */
@Controller
@RequestMapping("/front")
public class ShopListController
{
    private final ShopService shopService;
    private final AreaService areaService;
    private final ShopCategoryService shopCategoryService;

    @Autowired
    public ShopListController(ShopCategoryService shopCategoryService,
                              AreaService areaService,
                              ShopService shopService)
    {
        this.shopCategoryService = shopCategoryService;
        this.areaService = areaService;
        this.shopService = shopService;
    }

    /**
     * 按条件返回店铺列表的查询信息 area shopCategory
     *
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/listShopCondition", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShopCondition(HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1)
        {
            try
            {
                // 如果parentId存在,取出该一级分类下的二级分类列表
                ShopCategory condition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                condition.setParent(parent);
                shopCategoryList = shopCategoryService.list(condition);
            } catch (Exception e)
            {
                result.put("success", false);
                result.put("errMsg", "获取店铺分类失败!");
            }
        } else
        {
            // 如果parentId不存在，此时是选择全部商品
            try
            {
                shopCategoryList = shopCategoryService.list(null);
            } catch (Exception e)
            {
                result.put("success", false);
                result.put("errMsg", "获取店铺分类失败!");
            }
        }
        result.put("shopCategoryList", shopCategoryList);
        try
        {
            List<Area> areaList = areaService.list();
            result.put("areaList", areaList);
        } catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", "区域信息获取失败!");
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(value = "/listShop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShop(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpServletRequest request)
    {
        Map<String, Object> modelMap = new HashMap<>();
        if ((pageIndex > -1) && (pageSize > -1))
        {
            // 获取一级类别
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            // 获取二级类别
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            // 获取区域信息
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            // 模糊名字查询
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            // 封装查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            ShopExecution se;
            se = shopService.listShopPage(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else
        {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }

        return modelMap;
    }

    /**
     * 封装查询参数
     * @param parentId 一级分类Id
     * @param shopCategoryId 分类Id
     * @param areaId 区域ID
     * @param shopName 店铺名称
     * @return ShopCondition
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName)
    {
        Shop shopCondition = new Shop();
        if (parentId != -1L)
        {
            ShopCategory child = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            child.setParent(parentCategory);
            shopCondition.setShopCategory(child);
        }
        if (shopCategoryId != -1L)
        {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L)
        {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null)
        {
            shopCondition.setShopName(shopName);
        }
        // 店铺必须是合法的
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
