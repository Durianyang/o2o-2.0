package top.ywlog.o2o.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/1/13 14:03
 * Description: 商品销量统计
 */
@Data
public class ProductSellDaily implements Serializable
{
    private static final long serialVersionUID = 988059130041017533L;

    /** 哪天的销量, 精确到天 */
    private Date time;
    /** 总销量 */
    private Integer total;
    /** 商品信息 */
    private Product product;
    /** 店铺信息 */
    private Shop shop;
}
