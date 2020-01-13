package top.ywlog.o2o.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Author: Durian
 * Date: 2020/1/6 13:49
 * D escription: des是一种对称加密算法，加密和解密使用相同的密钥
 */
public class DESUtil
{
    private static Key key;
    /** 设置密钥*/
    private static final String KEY_STR = "mykey";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String ALGORITHM = "DES";

    static
    {
        try
        {
            // 生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // 运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 设置密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            // 生成密钥对象
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e)
        {
           throw new RuntimeException(e);
        }
    }

    /**
     *获取加密后的信息
     * @param str 待加密信息
     */
    public static String getEncryptString(String str)
    {
        // 基于BASE64编码，接收byte[]转换为String
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try
        {
            // 按UTF-8编码
            byte[] bytes = str.getBytes(CHARSET_NAME);
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] doFinal = cipher.doFinal(bytes);
            // byte[] to encoder好的String并返回
            return base64Encoder.encode(doFinal);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取解密后的信息
     * @param str 待解密的信息
     * @return 解密后的信息
     */
    public static String getDecryptString(String str)
    {
        // 基于base64编码
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try
        {
            // 将字符串decode为byte
            byte[] bytes = base64Decoder.decodeBuffer(str);
            // 获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] doFinal = cipher.doFinal(bytes);
            // 返回解密后的信息
            return new String(doFinal, CHARSET_NAME);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("Yw19981016"));

        System.out.println(getDecryptString("lM2+kVMwvQqMi6wmpH5mVw=="));
    }

}
