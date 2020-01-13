package top.ywlog.o2o.web.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.dto.JsonMapResult;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.service.ProductService;

/**
 * Author: Durian
 * Date: 2020/1/11 16:04
 * Description:
 */
@Controller
@RequestMapping("/front")
public class ProductDetailController
{
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getProductDetail", method = RequestMethod.GET)
    @ResponseBody
    public JsonMapResult<Product> getProductDetail(Long productId)
    {
        JsonMapResult<Product> result = new JsonMapResult<>();
        Product product = productService.getProductById(productId);
        if (product != null && product.getProductId() != null)
        {
            result.setRow(product);
            result.setSuccess(true);
        } else
        {
            result.setSuccess(false);
        }
        return result;
    }
}
