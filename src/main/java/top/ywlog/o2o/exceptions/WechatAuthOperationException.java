package top.ywlog.o2o.exceptions;

/**
 * Author: Durian
 * Date: 2020/1/4 17:36
 * Description:
 */
public class WechatAuthOperationException extends RuntimeException
{
    private static final long serialVersionUID = -5576599319601565009L;

    public WechatAuthOperationException(String msg)
    {
        super(msg);
    }
}
