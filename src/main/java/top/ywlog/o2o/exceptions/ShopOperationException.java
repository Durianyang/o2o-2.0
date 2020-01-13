package top.ywlog.o2o.exceptions;

/**
 * Author: Durian
 * Date: 2019/12/26 13:10
 * Description: 店铺操作异常
 */
public class ShopOperationException extends RuntimeException
{
    private static final long serialVersionUID = 5933886874231206769L;

    public ShopOperationException(String msg)
    {
        super(msg);
    }
}
