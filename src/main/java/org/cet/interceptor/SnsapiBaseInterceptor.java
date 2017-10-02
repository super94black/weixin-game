package org.cet.interceptor;

import org.cet.common.Const;
import org.cet.component.JedisClient;
import org.cet.pojo.openid.SnsapiBase;
import org.cet.util.HttpClientUtil;
import org.cet.util.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @Author zhang
 * @Date 2017/9/29 19:35
 * @Content
 */
public class SnsapiBaseInterceptor implements HandlerInterceptor {


    public static final String APPID = Const.AppId;

    public static final String APPSercet = Const.AppSecret;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //检查有没有code
        httpServletRequest.setCharacterEncoding("utf-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        String code = httpServletRequest.getParameter("code");
        String state = httpServletRequest.getParameter("state");
        String redirectURI = "https://zhlwx.mynatapp.cc/auth.html";
        String urlEncoder = URLEncoder.encode(redirectURI,"UTF-8");
        if(null == code || "".equals(code.trim())){
            //没有code就去微信获取
            String authURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID +
                    "&redirect_uri=" + urlEncoder + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
            httpServletResponse.sendRedirect(authURL);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
