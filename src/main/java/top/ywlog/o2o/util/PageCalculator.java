package top.ywlog.o2o.util;

/**
 * Author: Durian
 * Date: 2019/12/27 17:59
 * Description: 分页工具类
 */
public class PageCalculator
{
    public static int calculatorRowIndex(int pageIndex, int pageSize)
    {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
