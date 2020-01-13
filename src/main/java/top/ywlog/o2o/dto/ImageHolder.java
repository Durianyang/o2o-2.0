package top.ywlog.o2o.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * Author: Durian
 * Date: 2019/12/30 16:18
 * Description: 图片封装
 */
@Setter
@Getter
public class ImageHolder
{
    private InputStream image;
    private String imageName;

    public ImageHolder(){}

    public ImageHolder(InputStream image, String imageName)
    {
        this.image = image;
        this.imageName = imageName;
    }
}
