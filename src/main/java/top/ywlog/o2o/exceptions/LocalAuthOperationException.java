package top.ywlog.o2o.exceptions;

/**
 * Author: Durian
 * Date: 2020/1/11 17:09
 * Description:
 */
public class LocalAuthOperationException extends RuntimeException
{
    private static final long serialVersionUID = 8625955485638777264L;

    public LocalAuthOperationException(String msg)
    {
        super(msg);
    }
}
