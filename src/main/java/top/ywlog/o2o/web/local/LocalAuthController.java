package top.ywlog.o2o.web.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.dto.JsonMapResult;
import top.ywlog.o2o.dto.LocalAuthExecution;
import top.ywlog.o2o.entity.LocalAuth;
import top.ywlog.o2o.entity.PersonInfo;
import top.ywlog.o2o.enums.LocalAuthEnum;
import top.ywlog.o2o.exceptions.LocalAuthOperationException;
import top.ywlog.o2o.service.LocalAuthService;
import top.ywlog.o2o.util.CodeUtil;
import top.ywlog.o2o.util.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/1/11 17:46
 * Description: 本地账号控制器
 */
@Controller
@RequestMapping("/local")
public class LocalAuthController
{
    private static final String PATH = "localAuth/";

    private final LocalAuthService localAuthService;

    @Autowired
    public LocalAuthController(LocalAuthService localAuthService)
    {
        this.localAuthService = localAuthService;
    }

    @RequestMapping("/{pageName}")
    public String page(@PathVariable String pageName)
    {
        return PATH + pageName;
    }

    @RequestMapping(value = "/bindLocalAuth", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonMapResult<LocalAuth> bindLocalAuth(HttpServletRequest request, String username, String password)
    {
        JsonMapResult<LocalAuth> result = new JsonMapResult<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        LocalAuth authByUserName = localAuthService.getLocalAuthByUserName(username);
        if (authByUserName != null)
        {
            result.setSuccess(false);
            result.setErrMsg("用户名已存在！");
            return result;
        }
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (username != null && password != null && user.getUserId() != null)
        {
            // 创建localAuth对象
            LocalAuth localAuth = new LocalAuth();
            localAuth.setPersonInfo(user);
            localAuth.setUsername(username);
            localAuth.setPassword(password);
            // 绑定账号
            LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
            if (localAuthExecution.getState() == LocalAuthEnum.SUCCESS.getState())
            {
                result.setSuccess(true);
            } else
            {
                result.setSuccess(false);
                result.setErrMsg(localAuthExecution.getStateInfo());
            }
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("账号密码不能为空！");
        }
        return result;
    }

    @RequestMapping(value = "/updateLocalPwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<LocalAuth> updateLocalAuthPassword(HttpServletRequest request, String password, String newPassword)
    {
        JsonMapResult<LocalAuth> result = new JsonMapResult<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user.getUserId() != null && password != null && newPassword != null)
        {
            // 查看原账号，判断两次账号密码是否一致
            LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
            if (localAuth == null || !Objects.equals(localAuth.getPassword(), MD5Util.getMd5(password)))
            {
                // 不一致则返回
                result.setSuccess(false);
                result.setErrMsg("原密码错误！");
                return result;
            }
            // 修改账号密码
            try
            {
                LocalAuthExecution localAuthExecution = localAuthService.updateLocalAuthPassword(
                        user.getUserId(), password, newPassword, new Date());
                if (localAuthExecution.getState() == LocalAuthEnum.SUCCESS.getState())
                {
                    result.setSuccess(true);
                } else
                {
                    result.setSuccess(false);
                    result.setErrMsg(localAuthExecution.getStateInfo());
                }
            } catch (LocalAuthOperationException e)
            {
                result.setSuccess(false);
                result.setErrMsg("修改密码失败！");
            }
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("密码不能为空！");
        }
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonMapResult<LocalAuth> login(HttpServletRequest request, Boolean needVerify, String username, String password)
    {
        JsonMapResult<LocalAuth> result = new JsonMapResult<>();
        // 验证码校验
        if (needVerify && !CodeUtil.checkVerifyCode(request))
        {
            result.setSuccess(false);
            result.setErrMsg("验证码错误！");
            return result;
        }
        // 非空检验(工具类)
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password))
        {
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(username, password);
            if (localAuth != null)
            {
                // 账号存在
                result.setSuccess(true);
                result.setRow(localAuth);
                // 将用户信息设置进session中
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else
            {
                result.setSuccess(false);
                result.setErrMsg("账号或密码错误！");
            }
        } else
        {
            result.setSuccess(false);
            result.setErrMsg("账号和密码不能为空！");
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public JsonMapResult<Void> logout(HttpServletRequest request)
    {
        JsonMapResult<Void> result = new JsonMapResult<>();
        request.getSession().removeAttribute("user");
        result.setSuccess(true);
        return result;
    }

}
