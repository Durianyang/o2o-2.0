package top.ywlog.o2o.util;

import java.io.File;

/**
 * Author: Durian
 * Date: 2019/12/25 22:00
 * Description: 本地图片路径工具类
 */
public class PathUtil
{
    public static String getImgBasePath()
    {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win"))
        {
            basePath = "D:/o2o/images";
        } else
        {
            basePath = "/home/o2o/images";
        }
        basePath = basePath.replace("/", File.separator);
        return basePath;
    }

    public static String getShopImagePath(Long shopId)
    {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", File.separator);
    }
}
