package top.ywlog.o2o.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ywlog.o2o.dto.UserAccessToken;
import top.ywlog.o2o.dto.WechatUser;
import top.ywlog.o2o.entity.PersonInfo;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Author: Durian
 * Date: 2020/1/4 15:31
 * Description: 微信工具类
 */
public class WechatUtil
{
    private static Logger log = LoggerFactory.getLogger(WechatUtil.class);

    /** 测试号信息里的appId */
    private static final String APP_ID = "wx0299cd46dce460dd";
    /** 测试号信息里的appsecret */
    private static final String APPSECRET = "a70998f5f948ebc07d0d1e9b93eeb7e4";

    /**
     * 将WechatUser里的信息转换成personInfo的信息
     * @param user WeChatUser
     * @return PersonInfo
     */
    public static PersonInfo getPersonInfoFromRequest(WechatUser user)
    {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(user.getNickName());
        personInfo.setGender(user.getSex() + "");
        personInfo.setProfileImg(user.getHeadimgurl());
        personInfo.setEnableStatus(1);
        return personInfo;
    }


    /**
     * 获取UserAccessToken实体类
     *
     * @param code
     * @return
     * @throws IOException
     */
    public static UserAccessToken getUserAccessToken(String code) throws IOException
    {
        log.debug("appId:" + APP_ID);
        log.debug("secret:" + APPSECRET);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret=" + APPSECRET
                + "&code=" + code + "&grant_type=authorization_code";
        // 向相应URL发送请求获取token json字符串
        String tokenStr = httpsRequest(url, "GET", null);
        log.debug("userAccessToken:" + tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            // 将json字符串转换成相应对象
            token = objectMapper.readValue(tokenStr, UserAccessToken.class);
        } catch (JsonParseException e)
        {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e)
        {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        }
        if (token == null)
        {
            log.error("获取用户accessToken失败。");
            return null;
        }
        return token;
    }

    /**
     * 获取WechatUser实体类
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static WechatUser getUserInfo(String accessToken, String openId)
    {
        // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        // 访问该URL获取用户信息json 字符串
        String userStr = httpsRequest(url, "GET", null);
        log.debug("user info :" + userStr);
        WechatUser user = new WechatUser();
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            // 将json字符串转换成相应对象
            user = objectMapper.readValue(userStr, WechatUser.class);
        } catch (JsonParseException e)
        {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e)
        {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        }
        if (user == null)
        {
            log.error("获取用户信息失败。");
            return null;
        }
        return user;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return json字符串
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr)
    {
        StringBuilder buffer = new StringBuilder();
        try
        {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr)
            {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            log.debug("https buffer:" + buffer.toString());
        } catch (ConnectException ce)
        {
            log.error("Weixin server connection timed out.");
        } catch (Exception e)
        {
            log.error("https request error:{}", e);
        }
        return buffer.toString();
    }
}

