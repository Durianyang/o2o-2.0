package top.ywlog.o2o.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.ywlog.o2o.dao.ShopDao;
import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.ShopExecution;
import top.ywlog.o2o.entity.Shop;
import top.ywlog.o2o.enums.ShopStateEnum;
import top.ywlog.o2o.exceptions.ShopOperationException;
import top.ywlog.o2o.service.ShopService;
import top.ywlog.o2o.util.ImageUtil;
import top.ywlog.o2o.util.PageCalculator;
import top.ywlog.o2o.util.PathUtil;

import java.util.Date;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/26 12:44
 * Description:
 */
@Service
public class ShopServiceImpl implements ShopService
{
    private final ShopDao shopDao;

    @Autowired
    public ShopServiceImpl(ShopDao shopDao)
    {
        this.shopDao = shopDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ShopExecution addShop(Shop shop, ImageHolder shopImgInfo) throws ShopOperationException
    {
        int effectedNum;
        // 还可以对店铺的区域等信息判断
        if (shop == null)
        {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try
        {
            // 设置店铺状态
            shop.setCreateTime(new Date());
            // 设置创建时间
            shop.setLastEditTime(new Date());
            shop.setEnableStatus(0);
            // shop信息插入数据库中
            effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0)
            {
                throw new ShopOperationException("店铺添加失败！");
            } else
            {
                if (shopImgInfo.getImage() != null)
                {
                    try
                    {
                        // 存储图片
                        addShopImg(shop, shopImgInfo);
                    } catch (Exception e)
                    {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    // 更新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0)
                    {
                        throw new ShopOperationException("更新店铺图片地址失败！");
                    }
                }
            }
        } catch (Exception e)
        {
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        // 操作成功返回调用的构造器
        ShopExecution shopExecution = new ShopExecution(ShopStateEnum.CHECK, shop);
        shopExecution.setCount(effectedNum);
        return shopExecution;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Shop getShopById(Long shopId)
    {
        return shopDao.getShopById(shopId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ShopExecution updateShop(Shop shop, ImageHolder shopImgInfo) throws ShopOperationException
    {
        int effectedNum;
        if (shop == null || shop.getShopId() == null)
        {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else
        {
            try
            {
                // 判断是否需要处理图片
                if (shopImgInfo.getImage() != null && !StringUtils.isEmpty(shopImgInfo.getImageName()))
                {
                    // 获取未更新的shop对象
                    Shop tempShop = shopDao.getShopById(shop.getShopId());
                    // 删除原来的图片
                    if (tempShop.getShopImg() != null)
                    {
                        ImageUtil.deleteImgFile(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImgInfo);
                }
                // 更新店铺信息
                shop.setLastEditTime(new Date());
                effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0)
                {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else
                {
                    shop = shopDao.getShopById(shop.getShopId());
                    ShopExecution shopExecution = new ShopExecution(ShopStateEnum.SUCCESS, shop);
                    shopExecution.setCount(effectedNum);
                    shopExecution.setState(ShopStateEnum.SUCCESS.getState());
                    return shopExecution;
                }
            } catch (Exception e)
            {
                throw new ShopOperationException("更新店铺信息失败!" + e.getMessage());
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ShopExecution listShopPage(Shop shopCondition, int pageIndex, int pageSize)
{
    int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
    List<Shop> shopList = shopDao.listShopPage(shopCondition, rowIndex, pageSize);
    int count = shopDao.shopCount(shopCondition);
    ShopExecution shopExecution = new ShopExecution();
    if (shopList != null && shopList.size() != 0)
    {
        shopExecution.setShopList(shopList);
        shopExecution.setCount(count);
        shopExecution.setState(ShopStateEnum.SUCCESS.getState());
        shopExecution.setStateInfo(ShopStateEnum.SUCCESS.getStateInfo());
    } else
    {
        shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        shopExecution.setStateInfo(ShopStateEnum.INNER_ERROR.getStateInfo());
    }
    return shopExecution;
}

    /**
     * 存储店铺图片
     *
     * @param shop        店铺信息
     * @param shopImgInfo 店铺图片
     */
    private void addShopImg(Shop shop, ImageHolder shopImgInfo)
    {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInfo, dest);
        shop.setShopImg(shopImgAddr);
    }
}
