package top.ywlog.o2o.web.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.ywlog.o2o.dto.UserAccessToken;
import top.ywlog.o2o.dto.WechatAuthExecution;
import top.ywlog.o2o.dto.WechatUser;
import top.ywlog.o2o.entity.PersonInfo;
import top.ywlog.o2o.entity.WechatAuth;
import top.ywlog.o2o.enums.WechatAuthStateEnum;
import top.ywlog.o2o.service.PersonInfoService;
import top.ywlog.o2o.service.WechatAuthService;
import top.ywlog.o2o.util.WechatUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/1/4 15:21
 * Description: 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0299cd46dce460dd&redirect_uri=http://o2o.durianyang.xyz/o2o/wechatLogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 */
@Controller
@RequestMapping("/wechatLogin")
public class WechatLoginController
{
    private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);
    private static final String FRONT = "1";
    private static final String SHOP = "2";

    private final PersonInfoService personInfoService;
    private final WechatAuthService wechatAuthService;

    @Autowired
    public WechatLoginController(PersonInfoService personInfoService, WechatAuthService wechatAuthService)
    {
        this.personInfoService = personInfoService;
        this.wechatAuthService = wechatAuthService;
    }

    @RequestMapping(value = "/logincheck", method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request)
    {
        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        WechatAuth wechatAuth = null;
        if (null != code)
        {
            UserAccessToken token;
            try
            {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e)
            {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }
        if (wechatAuth == null)
        {
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            wechatAuth = new WechatAuth();
            wechatAuth.setOpenId(openId);
            if (Objects.equals(FRONT, roleType))
            {
                personInfo.setUserType(1);
            } else if (Objects.equals(SHOP, roleType))
            {
                personInfo.setUserType(2);
            }
            wechatAuth.setPersonInfo(personInfo);
            WechatAuthExecution we = wechatAuthService.register(wechatAuth);
            if (we.getState() != WechatAuthStateEnum.SUCCESS.getState())
            {
                return null;
            } else
            {
                personInfo = personInfoService.getPersonInfoById(wechatAuth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user", personInfo);
            }
        }
        // 用户点击进入的系统
        if (Objects.equals(FRONT, roleType))
        {
            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
            return "front/index";
        } else if (Objects.equals(SHOP, roleType))
        {
            return "shop/shopList";
        }
        return null;
    }
}
