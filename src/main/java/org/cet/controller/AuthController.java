package org.cet.controller;

import org.cet.common.Const;
import org.cet.component.JedisClient;
import org.cet.pojo.openid.SnsapiBase;
import org.cet.util.HttpClientUtil;
import org.cet.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhang
 * @Date 2017/9/29 20:20
 * @Content
 */
@Controller
public class AuthController {

    public static String code = null;
    public static final String APPID = Const.AppId;
    public static final String APPSercet = Const.AppSecret;


    @RequestMapping(value = "/auth.html",method = RequestMethod.GET)
    public String getView(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String accessTokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID +
                "&secret=" + APPSercet + "&code="+ code
                + "&grant_type=authorization_code";
        String json = HttpClientUtil.doGet(accessTokenURL);
        SnsapiBase snsapiBase = JsonUtils.jsonToPojo(json,SnsapiBase.class);
        JedisClient js = new JedisClient();
        js.set(snsapiBase.getOpenid(),snsapiBase.getOpenid());
//        js.set("access_token",snsapiBase.getAccess_token());
//        js.expire("access_token", Integer.parseInt(snsapiBase.getExpires_in()));
        model.addAttribute("openId",snsapiBase.getOpenid());
        return "auth";
    }
}
