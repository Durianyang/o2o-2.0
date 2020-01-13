package top.ywlog.o2o.web.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.entity.HeadLine;
import top.ywlog.o2o.entity.ShopCategory;
import top.ywlog.o2o.service.HeadLineService;
import top.ywlog.o2o.service.ShopCategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Durian
 * Date: 2020/1/2 14:52
 * Description: 首页
 */
@Controller
@RequestMapping("/front")
public class MainPageController
{
    private static final String PATH = "front/";

    private final ShopCategoryService shopCategoryService;
    private final HeadLineService headLineService;

    @Autowired
    public MainPageController(HeadLineService headLineService, ShopCategoryService shopCategoryService)
    {
        this.headLineService = headLineService;
        this.shopCategoryService = shopCategoryService;
    }

    @RequestMapping("/{pageName}")
    public String page(@PathVariable String pageName)
    {
        return PATH + pageName;
    }

    /**
     * 初始化首页展示信息
     *
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/listMainPageInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPageInfo()
    {
        Map<String, Object> result = new HashMap<>();
        // 获取店铺一级类别
        try
        {
            List<ShopCategory> shopCategoryList = shopCategoryService.list(null);
            result.put("shopCategoryList", shopCategoryList);
        } catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", "店铺类别获取失败！");
            return result;
        }
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        // 获取可用头条
        try
        {
            List<HeadLine> headLineList = headLineService.listHeadLine(headLine);
            result.put("headLineList", headLineList);
        } catch (Exception e)
        {
            result.put("success", false);
            result.put("errMsg", "获取头条信息失败！");
        }
        result.put("success", true);
        return result;
    }

}
