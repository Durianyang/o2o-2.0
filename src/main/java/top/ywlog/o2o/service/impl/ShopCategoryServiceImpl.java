package top.ywlog.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ywlog.o2o.dao.ShopCategoryDao;
import top.ywlog.o2o.entity.ShopCategory;
import top.ywlog.o2o.exceptions.ShopCategoryOperationException;
import top.ywlog.o2o.service.ShopCategoryService;
import top.ywlog.o2o.util.RedisUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 16:43
 * Description:
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
    private final ShopCategoryDao shopCategoryDao;
    private final RedisUtil redisUtil;

    @Autowired
    public ShopCategoryServiceImpl(ShopCategoryDao shopCategoryDao, RedisUtil redisUtil)
    {
        this.shopCategoryDao = shopCategoryDao;
        this.redisUtil = redisUtil;
    }

    @Override
    public List<ShopCategory> list(ShopCategory shopCategory)
    {
        String key = SHOP_CATEGORY_LIST;
        List<ShopCategory> shopCategoryList;
        ObjectMapper mapper = new ObjectMapper();
        if (shopCategory == null)
        {
            // 若查询条件为空，则列出首页所有大类,即查询一级分类
            key = key + "_allFirstLevel";
        } else if (shopCategory.getParent() != null && shopCategory.getParent().getShopCategoryId() != null)
        {
            key = key + "_parent" + shopCategory.getParent().getShopCategoryId();
        } else
        {
            // 列出全部子分类
            key = key + "_allSecondLevel";
        }
        if (!redisUtil.hasKey(key) || redisUtil.get(key) == null)
        {
            shopCategoryList = shopCategoryDao.list(shopCategory);
            String jsonValue;
            try
            {
                jsonValue = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e)
            {
                LOGGER.error("json转换失败！" + e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
            redisUtil.set(key, jsonValue);
        } else
        {
            String jsonValue = (String) redisUtil.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try
            {
                shopCategoryList = mapper.readValue(jsonValue, javaType);
            } catch (IOException e)
            {
                LOGGER.error("json转换失败！" + e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
