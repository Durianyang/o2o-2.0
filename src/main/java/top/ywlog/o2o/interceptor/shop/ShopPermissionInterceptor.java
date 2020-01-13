package top.ywlog.o2o.interceptor.shop;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.ywlog.o2o.entity.Shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/1/12 13:54
 * Description:
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
        if (currentShop != null && shopList != null)
        {
            for (Shop shop : shopList)
            {
                if (Objects.equals(shop.getShopId(), currentShop.getShopId()))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
