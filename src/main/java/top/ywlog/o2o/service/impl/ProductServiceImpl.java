package top.ywlog.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.ywlog.o2o.dao.ProductDao;
import top.ywlog.o2o.dao.ProductImgDao;
import top.ywlog.o2o.dto.ImageHolder;
import top.ywlog.o2o.dto.ProductExecution;
import top.ywlog.o2o.entity.Product;
import top.ywlog.o2o.entity.ProductImg;
import top.ywlog.o2o.enums.ProductStateEnum;
import top.ywlog.o2o.exceptions.ProductOperationException;
import top.ywlog.o2o.service.ProductService;
import top.ywlog.o2o.util.ImageUtil;
import top.ywlog.o2o.util.PageCalculator;
import top.ywlog.o2o.util.PathUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/30 16:17
 * Description: 商品处理业务类
 */
@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductDao productDao;
    private final ProductImgDao productImgDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductImgDao productImgDao)
    {
        this.productDao = productDao;
        this.productImgDao = productImgDao;
    }

    /**
     * 1、处理缩略图，获取缩略图相对路径并赋值给product
     * 2、往数据库插入商品数据
     * 3、集合productId批量处理商品详情图
     * 4、将商品详情图列表插入tb_product_img中
     *
     * @param product        商品信息
     * @param thumbnail      缩略图
     * @param productImgList 详情图
     * @return top.ywlog.o2o.dto.ProductExecution
     * @throws ProductOperationException top.ywlog.o2o.exceptions.ProductException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws ProductOperationException
    {
        // 空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null)
        {
            // 设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 商品状态默认设置为上架状态
            product.setEnableStatus(1);
            // 判断是否添加商品缩略图
            if (thumbnail != null)
            {
                addThumbnail(product, thumbnail);
            }
            try
            {
                // 创建商品信息
                int count = productDao.insertProduct(product);
                if (count <= 0)
                {
                    throw new ProductOperationException("添加商品失败！");
                }
            } catch (ProductOperationException e)
            {
                throw new ProductOperationException("添加商品失败：" + e.toString());
            }
            // 商品详情图片不为空则添加
            if (productImgList != null && productImgList.size() > 0)
            {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else
        {
            return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
        }
    }

    @Override
    public Product getProductById(Long productId)
    {
        return productDao.getProductById(productId);
    }

    /**
     * 更新商品信息
     * 1、处理缩略图
     * 2、处理详情图
     * 3、更新商品信息
     *
     * @param product              商品信息
     * @param thumbnail            缩略图
     * @param productImgHolderList 详情图
     * @return top.ywlog.o2o.dto.ProductExecution
     * @throws ProductOperationException top.ywlog.o2o.exceptions.ProductException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductExecution updateProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
    {
        // 空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null)
        {
            // 设置默认属性
            product.setLastEditTime(new Date());
            // 判断是否更新商品缩略图
            if (thumbnail != null)
            {
                // 先获取原有图片信息
                Product oldProduct = productDao.getProductById(product.getProductId());
                if (oldProduct.getImgAddr() != null)
                {
                    ImageUtil.deleteImgFile(oldProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            // 更新详情图
            if (productImgHolderList != null && productImgHolderList.size() > 0)
            {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgHolderList);
            }
            try
            {
                // 更新商品信息
                int count = productDao.updateProduct(product);
                if (count <= 0)
                {
                    throw new ProductOperationException("更新商品信息失败！");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (ProductOperationException e)
            {
                throw new ProductOperationException("更新商品信息失败：" + e.toString());
            }
        } else
        {
            return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
        }
    }

    @Override
    public ProductExecution listProduct(Product productCondition, int pageIndex, int pageSize)
    {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.listProduct(productCondition, rowIndex, pageSize);
        int count = productDao.listProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        if (productList != null && productList.size() > 0)
        {
            pe.setState(ProductStateEnum.SUCCESS.getState());
            pe.setStateInfo(ProductStateEnum.SUCCESS.getStateInfo());
            pe.setProductList(productList);
            pe.setCount(count);
        } else
        {
            pe.setState(ProductStateEnum.INNER_ERROR.getState());
            pe.setStateInfo(ProductStateEnum.INNER_ERROR.getStateInfo());
        }
        return pe;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductExecution deleteProductById(Long productId)
    {
        ProductExecution pe = new ProductExecution();
        if (productId != null && productId > 0)
        {
            try
            {
                int count = productDao.deleteProductById(productId);
                if (count > 0)
                {
                    pe.setState(ProductStateEnum.SUCCESS.getState());
                    pe.setStateInfo(ProductStateEnum.SUCCESS.getStateInfo());
                    pe.setCount(count);
                } else
                {
                    pe.setState(ProductStateEnum.INNER_ERROR.getState());
                    pe.setStateInfo(ProductStateEnum.INNER_ERROR.getStateInfo());
                }
            } catch (Exception e)
            {
                pe.setState(ProductStateEnum.INNER_ERROR.getState());
                pe.setStateInfo(ProductStateEnum.INNER_ERROR.getStateInfo());
                throw new ProductOperationException("删除失败！" + e.getMessage());
            }
        }
        return pe;
    }

    /**
     * 删除某个商品下的所有详情图
     *
     * @param productId 商品ID
     */
    private void deleteProductImgList(Long productId)
    {
        // 根据productId获取原来图片
        List<ProductImg> productImgList = productImgDao.listProductImg(productId);
        // 删除原来的图片文件
        for (ProductImg productImg : productImgList)
        {
            ImageUtil.deleteImgFile(productImg.getImgAddr());
        }
        // 删除数据库中图片的记录
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 添加商品详情图片列表
     *
     * @param product              商品信息
     * @param productImgHolderList 商品详情图列表
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList)
    {
        // 获取图片存储路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        // productImgList已经判空
        List<ProductImg> productImgList = new ArrayList<>();
        // 遍历图片，并添加进productImg实体类中
        for (ImageHolder imageHolder : productImgHolderList)
        {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        // 如果确定有图片添加，则执行批量添加操作
        if (productImgList.size() > 0)
        {
            try
            {
                int count = productImgDao.batchInsertProductImg(productImgList);
                if (count <= 0)
                {
                    throw new ProductOperationException("添加商品详情图失败！");
                }
            } catch (ProductOperationException e)
            {
                throw new ProductOperationException("创建商品详情图失败：" + e.toString());
            }
        }
    }

    /**
     * 添加商品缩略图
     *
     * @param product   商品信息
     * @param thumbnail 商品缩略图
     */
    private void addThumbnail(Product product, ImageHolder thumbnail)
    {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
