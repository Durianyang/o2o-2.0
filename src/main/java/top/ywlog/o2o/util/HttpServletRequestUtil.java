package top.ywlog.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Durian
 * Date: 2019/12/26 14:15
 * Description: 处理请求参数
 */
public class HttpServletRequestUtil
{
    public static int getInt(HttpServletRequest request, String name)
    {

        try
        {
            return Integer.decode(request.getParameter(name));
        } catch (Exception e)
        {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String name)
    {

        try
        {
            return Long.valueOf(request.getParameter(name));
        } catch (Exception e)
        {
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request, String name)
    {

        try
        {
            return Double.valueOf(request.getParameter(name));
        } catch (Exception e)
        {
            return -1d;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request, String name)
    {

        try
        {
            return Boolean.valueOf(request.getParameter(name));
        } catch (Exception e)
        {
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String name)
    {
        try
        {
            String result = request.getParameter(name);
            if (result != null)
            {
                result = result.trim();
            }
            if ("".equals(result))
                result = null;
            return result;
        } catch (Exception e)
        {
            return null;
        }

    }
}
