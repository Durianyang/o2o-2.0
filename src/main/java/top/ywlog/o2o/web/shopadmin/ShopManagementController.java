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
import top.ywlog.o2o.dto.ShopExecution;
import top.ywlog.o2o.entity.Area;
import top.ywlog.o2o.entity.PersonInfo;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.entity.ShopCategory;
import top.ywlog.o2o.enums.ShopStateEnum;
import top.ywlog.o2o.exceptions.ShopOperationException;
import top.ywlog.o2o.service.AreaService;
import top.ywlog.o2o.service.ShopCategoryService;
import top.ywlog.o2o.service.ShopService;
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
 * Date: 2019/12/26 14:04
 * Description: 店铺controller
 */
@Controller
@RequestMapping("/shop")
public class ShopManagementController
{
    private final ShopService shopService;
    private final ShopCategoryService shopCategoryService;
    private final AreaService areaService;

    @Autowired
    public ShopManagementController(ShopService shopService, ShopCategoryService shopCategoryService, AreaService areaService)
    {
        this.shopService = shopService;
        this.shopCategoryService = shopCategoryService;
        this.areaService = areaService;
    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<Shop> registerShop(HttpServletRequest request)
    {
        JsonMapResult<Shop> result = new JsonMapResult<>();
        ObjectMapper mapper = new ObjectMapper();
        if (!CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        // 接收并转换相应的参数（店铺信息，图片）
        CommonsMultipartFile shopImg;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (resolver.isMultipart(request))
        {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("上传图片不能为空！");
            return result;
        }
        // 注册店铺
        Shop shop;
        String shopJson = HttpServletRequestUtil.getString(request, "shop");
        try
        {
            shop = mapper.readValue(shopJson, Shop.class);
        } catch (IOException e)
        {
            result.setSuccess(false);
            result.setErrMsg("shop信息转换失败！");
            return result;
        }
        if (shop != null && shopImg != null)
        {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = new ShopExecution();
            try
            {
                ImageHolder imageHolder = new ImageHolder();
                imageHolder.setImage(shopImg.getInputStream());
                imageHolder.setImageName(shopImg.getOriginalFilename());
                se = shopService.addShop(shop, imageHolder);
            } catch (IOException e)
            {
                result.setSuccess(false);
                result.setErrMsg(e.getMessage());
            }
            if (se.getState() == ShopStateEnum.CHECK.getState())
            {
                result.setSuccess(true);
                result.setTotal(se.getCount());
                result.setRow(se.getShop());
                // 该用户能操作的店铺列表
                @SuppressWarnings("unchecked")
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList == null || shopList.size() == 0)
                {
                    // 此时为第一次创建店铺
                    shopList = new ArrayList<>();
                }
                // 新建店铺添加到session中
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList", shopList);

            } else
            {
                result.setSuccess(false);
                result.setErrMsg(se.getStateInfo());
            }
            return result;
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("店铺信息和图片不能为空！");
            return result;
        }
    }


    @RequestMapping(value = "/getShopInitInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInitInfo()
    {
        Map<String, Object> model = new HashMap<>();
        List<ShopCategory> shopCategoryList;
        List<Area> areaList;
        try
        {
            shopCategoryList = shopCategoryService.list(new ShopCategory());
            areaList = areaService.list();
            model.put("shopCategoryList", shopCategoryList);
            model.put("areaList", areaList);
            model.put("success", true);
        } catch (Exception e)
        {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = "/getShopInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInfo(Long shopId)
    {
        Map<String, Object> model = new HashMap<>(3);
        if (shopId > 0)
        {
            try
            {
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = areaService.list();
                model.put("shop", shop);
                model.put("areaList", areaList);
                model.put("success", true);
            } catch (Exception e)
            {
                model.put("success", false);
                model.put("errMsg", e.getMessage());
            }
        } else
        {
            model.put("success", false);
            model.put("errMsg", "店铺ID不存在！ ");
        }
        return model;
    }

    @RequestMapping("/updateShop")
    @ResponseBody
    public JsonMapResult<Shop> updateShop(HttpServletRequest request)
    {
        JsonMapResult<Shop> result = new JsonMapResult<>();
        ObjectMapper mapper = new ObjectMapper();
        if (!CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        // 接收并转换相应的参数（店铺信息，图片）
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (resolver.isMultipart(request))
        {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        // 修改店铺信息
        Shop shop;
        String shopJson = HttpServletRequestUtil.getString(request, "shop");
        try
        {
            shop = mapper.readValue(shopJson, Shop.class);
        } catch (IOException e)
        {
            result.setSuccess(false);
            result.setErrMsg("shop信息转换失败！");
            return result;
        }
        if (shop != null && shop.getShopId() != null)
        {
            ShopExecution se = new ShopExecution();
            try
            {
                if (shopImg == null)
                {
                    se = shopService.updateShop(shop, null);
                } else
                {
                    ImageHolder imageHolder = new ImageHolder();
                    imageHolder.setImage(shopImg.getInputStream());
                    imageHolder.setImageName(shopImg.getOriginalFilename());
                    se = shopService.updateShop(shop, imageHolder);
                }
            } catch (ShopOperationException | IOException e)
            {
                result.setSuccess(false);
                result.setErrMsg("更新失败！");
            }

            if (se.getState() == ShopStateEnum.SUCCESS.getState())
            {
                result.setSuccess(true);
                result.setTotal(se.getCount());
                result.setRow(se.getShop());
            } else
            {
                result.setSuccess(false);
                result.setErrMsg("更新失败！");
            }
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("店铺ID不存在！");
        }

        return result;
    }

    @RequestMapping(value = "/getShopList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(@RequestParam(defaultValue = "1") int pageIndex,
                                           @RequestParam(defaultValue = "100") int pageSize,
                                           HttpServletRequest request)
    {
        Map<String, Object> model = new HashMap<>(3);
        // 测试
//        PersonInfo user = new PersonInfo();
//        user.setUserId(8L);
//        user.setName("测试");
//        request.getSession().setAttribute("user", user);

        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        Shop shopCondition = new Shop();
        shopCondition.setOwner(user);
        try
        {
            ShopExecution se = shopService.listShopPage(shopCondition, pageIndex, pageSize);
            model.put("success", true);
            model.put("shopList", se.getShopList());
            model.put("user", user);
            // 获取店铺列表后，将店铺放入session中
            request.getSession().setAttribute("shopList", se.getShopList());
        } catch (Exception e)
        {
            model.put("success", false);
            model.put("errMsg", e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = "/getShopManagementInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request)
    {
        Map<String, Object> model = new HashMap<>(3);
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0)
        {
            // 当前操作店铺放入session中
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null)
            {
                model.put("redirect", true);
                model.put("url", "/o2o/shop/shopList");
            } else
            {
                Shop currentShop = (Shop) currentShopObj;
                model.put("redirect", false);
                model.put("shopId", currentShop.getShopId());
            }
        } else
        {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            model.put("redirect", false);
        }
        return model;
    }

}
