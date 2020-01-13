package top.ywlog.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.JsonMapResult;
import top.ywlog.o2o.dto.ProductExecution;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.entity.ProductCategory;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.enums.ProductStateEnum;
import top.ywlog.o2o.exceptions.ProductOperationException;
import top.ywlog.o2o.service.ProductCategoryService;
import top.ywlog.o2o.service.ProductService;
import top.ywlog.o2o.util.CodeUtil;
import top.ywlog.o2o.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Durian
 * Date: 2019/12/30 22:43
 * Description: 商品操作控制器
 */
@Controller
@RequestMapping("/shop")
public class ProductController
{
    /**
     * 支持详情图片上传的最大张数
     */
    private static final int MAX_IMAGE_COUNT = 6;

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService)
    {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<Product> addProduct(HttpServletRequest request)
    {
        // 判断验证码正误
        JsonMapResult<Product> result = new JsonMapResult<>();
        if (!CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        // 接收前端参数的变量初始化
        ObjectMapper mapper = new ObjectMapper();
        Product product;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ImageHolder thumbnail;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try
        {
            // 若请求中存在文件流，则取出相关文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request))
            {
                thumbnail = getImageHolder(request, productImgList);
            } else
            {
                result.setSuccess(false);
                result.setErrMsg("上传图片不能为空!");
                return result;
            }
        } catch (Exception e)
        {
            result.setSuccess(false);
            result.setErrMsg("添加图片失败！");
            return result;
        }
        try
        {
            // 获取product实体对象
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e)
        {
            result.setSuccess(false);
            result.setErrMsg("商品信息转换失败!");
            return result;
        }
        if (product != null && productImgList.size() > 0)
        {
            try
            {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState())
                {
                    result.setSuccess(true);
                    result.setRow(product);
                } else
                {
                    result.setSuccess(false);
                    result.setErrMsg(pe.getStateInfo());
                }
            } catch (ProductOperationException e)
            {
                result.setSuccess(false);
                result.setErrMsg("商品添加失败！");
                return result;
            }

        } else
        {
            result.setSuccess(false);
            result.setErrMsg("请输入商品信息！");
        }
        return result;
    }

    @RequestMapping(value = "/getProductById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(Long productId)
    {
        Map<String, Object> result = new HashMap<>(3);
        // id合法性判断
        if (productId != null)
        {
            Product product = productService.getProductById(productId);
            // 获取该店铺下的商品类别列表
            List<ProductCategory> categoryList = productCategoryService.list(product.getShop().getShopId());
            result.put("success", true);
            result.put("product", product);
            result.put("productCategoryList", categoryList);
        } else
        {
            result.put("success", false);
            result.put("errMsg", "商品ID不存在!");
        }
        return result;
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<Product> updateProduct(HttpServletRequest request)
    {
        /*判断是商品编时调用还是下架商品时调用, 若为前者需验证码判断, true：需要验证 false：不需要*/
        boolean changeStatus = HttpServletRequestUtil.getBoolean(request, "changeStatus");
        // 判断验证码正误
        JsonMapResult<Product> result = new JsonMapResult<>();
        if (changeStatus && !CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 若存在文件流
        try
        {
            if (multipartResolver.isMultipart(request))
            {
                thumbnail = getImageHolder(request, productImgList);
            }
        } catch (IOException e)
        {
            result.setSuccess(false);
            result.setErrMsg("商品图片更新失败！");
            return result;
        }
        try
        {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e)
        {
            result.setSuccess(false);
            result.setErrMsg("");
            return result;
        }
        if (product != null)
        {
            try
            {
                // 从session中获取当前店铺
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 更新数据
                ProductExecution pe = productService.updateProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState())
                {
                    result.setSuccess(true);
                } else
                {
                    result.setSuccess(false);
                    result.setErrMsg(pe.getStateInfo());
                }
            } catch (RuntimeException e)
            {
                result.setSuccess(false);
                result.setErrMsg("更新失败!");
                return result;
            }

        } else
        {
            result.setSuccess(false);
            result.setErrMsg("请输入商品信息");
        }
        return result;
    }

    @RequestMapping(value = "/listProduct", method = RequestMethod.GET)
    @ResponseBody
    public JsonMapResult<Product> listProduct(@RequestParam(defaultValue = "0") int pageIndex,
                                              @RequestParam(defaultValue = "100") int pageSize,
                                              HttpServletRequest request)
    {
        JsonMapResult<Product> result = new JsonMapResult<>();
        ProductExecution pe;
        // 获取查询条件
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null && currentShop.getShopId() > 0)
        {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            int enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
            // 设置查询条件
            Product productCondition = getProductCondition(currentShop.getShopId(), productCategoryId, productName, enableStatus);
            pe = productService.listProduct(productCondition, pageIndex, pageSize);
            if (pe.getStateInfo().equals(ProductStateEnum.SUCCESS.getStateInfo()))
            {
                result.setSuccess(true);
                result.setRows(pe.getProductList());
                result.setTotal(pe.getCount());
            } else
            {
                result.setSuccess(false);
                result.setErrMsg(pe.getStateInfo());
            }
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("店铺ID为空！");
        }
        return result;
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<Product> deleteProductById(Long productId)
    {
        JsonMapResult<Product> result = new JsonMapResult<>();
        ProductExecution pe = productService.deleteProductById(productId);
        if (pe.getStateInfo().equals(ProductStateEnum.SUCCESS.getStateInfo()))
        {
            result.setSuccess(true);
            result.setTotal(pe.getCount());
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("删除失败！");
        }
        return result;
    }

    /**
     * ======================= private method========================
     */

    private ImageHolder getImageHolder(HttpServletRequest request, List<ImageHolder> productImgList) throws IOException
    {
        ImageHolder thumbnail = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null)
        {
            thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
        }
        // 取出缩略图构建List<ImageHolder>
        for (int i = 0; i < MAX_IMAGE_COUNT; i++)
        {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
                    .getFile("productImg" + i);
            if (productImgFile != null)
            {
                // 若不为空时，加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(),
                        productImgFile.getOriginalFilename());
                productImgList.add(productImg);
            }
        }
        return thumbnail;
    }

    /**
     * 封装查询条件
     *
     * @param shopId            shopId
     * @param productCategoryId productCategoryId
     * @param productName       productName
     * @param enableStatus      enableStatus
     * @return Product
     */
    private Product getProductCondition(Long shopId, Long productCategoryId, String productName, Integer enableStatus)
    {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 若有指定类别条件，则添加进去
        if (productCategoryId != null && productCategoryId != -1L)
        {
            ProductCategory pc = new ProductCategory();
            pc.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(pc);
        }
        // 若有商品名
        if (productName != null)
        {
            productCondition.setProductName(productName);
        }
        // 若有商品状态
        if (enableStatus != -1)
        {
            productCondition.setEnableStatus(enableStatus);
        }
        return productCondition;
    }
}
