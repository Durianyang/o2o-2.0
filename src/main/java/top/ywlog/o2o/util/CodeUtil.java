package top.ywlog.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Durian
 * Date: 2019/12/26 20:21
 * Description: 验证码工具类
 */
public class CodeUtil
{
    public static boolean checkVerifyCode(HttpServletRequest request)
    {
        String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeInput = HttpServletRequestUtil.getString(request, "verifyCodeInput");
        return verifyCodeInput != null && verifyCodeInput.equalsIgnoreCase(verifyCodeExpected);
    }
}
