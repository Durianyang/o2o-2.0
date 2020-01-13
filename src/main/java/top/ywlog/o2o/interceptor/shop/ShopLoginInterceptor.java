package top.ywlog.o2o.interceptor.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.ywlog.o2o.entity.PersonInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Author: Durian
 * Date: 2020/1/12 13:39
 * Description:
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopLoginInterceptor.class);
    /**
     * 操作前拦截器
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @return 拦截结果
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Object user = request.getSession().getAttribute("user");
        if (user != null)
        {
            // 若用户信息不为空
            PersonInfo personInfo = (PersonInfo)user;
            if (personInfo.getUserId() != null && personInfo.getUserId() > 0 && personInfo.getEnableStatus() == 1)
            {
                LOGGER.debug("用户" + personInfo.getName() + "已登录！");
                return true;
            }
        }
        String userType = "2";
        StringBuffer requestUrl = request.getRequestURL();
        // 测试
        if (requestUrl.toString().contains("/o2o/shop/"))
        {
            LOGGER.debug("操作页面在店铺管理系统！");
            userType = "2";
        } else if (requestUrl.toString().contains("/o2o/front/"))
        {
            LOGGER.debug("操作页面在店铺前端展示系统！");
            userType = "1";
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open('" + request.getContextPath() + "/local/login?userType=" + userType + "', '_self')");
        out.println("</script>");
        out.println("</html>");
        LOGGER.debug("用户未登录，跳转登录页面中...");
        return false;
    }
}
