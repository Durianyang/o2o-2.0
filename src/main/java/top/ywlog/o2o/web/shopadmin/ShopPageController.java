package top.ywlog.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author: Durian
 * Date: 2019/12/26 15:41
 * Description: 店铺页面跳转控制器
 */
@Controller
@RequestMapping(value = "/shop", method = RequestMethod.GET)
public class ShopPageController
{
    private static final String PATH = "shop/";

    @RequestMapping("/{pageName}")
    public String page(@PathVariable String pageName)
    {
        return PATH + pageName;
    }
}
