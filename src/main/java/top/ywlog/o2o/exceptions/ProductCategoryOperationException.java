package top.ywlog.o2o.exceptions;

/**
 * Author: Durian
 * Date: 2019/12/30 13:49
 * Description:
 */
public class ProductCategoryOperationException extends RuntimeException
{
    private static final long serialVersionUID = -2416537185406117425L;

    public ProductCategoryOperationException(String msg)
    {
        super(msg);
    }
}
