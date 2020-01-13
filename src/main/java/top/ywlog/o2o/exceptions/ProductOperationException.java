package top.ywlog.o2o.exceptions;

/**
 * Author: Durian
 * Date: 2019/12/30 16:13
 * Description:
 */
public class ProductOperationException extends RuntimeException
{
    private static final long serialVersionUID = 1581651211025765712L;

    public ProductOperationException(String msg)
    {
        super(msg);
    }
}
