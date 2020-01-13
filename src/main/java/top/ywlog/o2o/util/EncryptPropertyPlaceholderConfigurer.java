package top.ywlog.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/1/6 19:45
 * Description: 应用启动时运行，对加密过的properties字段进行解密
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    /**
     * 需要加密的字段数组
     */
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password", "redis.password"};

    /**
     * 对关键属性进行转换
     *
     * @param propertyName  属性名称
     * @param propertyValue 属性值
     * @return 转换后的值
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue)
    {
        if (isEncryptProp(propertyName))
        {
            // 对加密的字段调用DESUtils的解密方法解密
            return DESUtil.getDecryptString(propertyValue);
        } else
        {
            return propertyValue;
        }
    }

    /**
     * 判断属性是否已经加密
     *
     * @param propertyName 属性名称
     * @return boolean
     */
    private boolean isEncryptProp(String propertyName)
    {
        for (String encryptPropName : encryptPropNames)
        {
            if (Objects.equals(encryptPropName, propertyName))
            {
                return true;
            }
        }
        return false;
    }
}
