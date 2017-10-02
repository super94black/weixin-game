package org.cet.service.impl;

import org.cet.common.Const;
import org.cet.component.JedisClient;
import org.cet.pojo.access_token.AccessToken;
import org.cet.service.IndexService;
import org.cet.util.CurlUtil;
import org.cet.util.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2017/9/26 12:07
 * @Content
 */
@Service
public class IndexServiceImpl implements IndexService {

    public String showIndex(){
        return "index";
    }

    /**
     * 获取redis中的AccessToken
     * @return
     */
    public String getAccessToken(){
        //检查本地有没有AccessToken
        JedisClient js = new JedisClient();
        String accessToken = js.get("access_token");
        if(accessToken == null || "".equals(accessToken.trim())){
            //调用微信获取access_token接口
            AccessToken at = curlAccessToken();
            accessToken = at.getAccess_token();
            //设置到redis中
            js.set("access_token",accessToken);
            js.expire("access_token",at.getExpires_in());
        }
        return accessToken;

    }

    /**
     * 爬取微信Access_Token接口
     * @return
     */
    public AccessToken curlAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("appid",Const.AppId);
        params.put("secret",Const.AppSecret);
        String json = CurlUtil.getContent(url,params,"GET");
        AccessToken accessToken = JsonUtils.jsonToPojo(json, AccessToken.class);
        return accessToken;
    }

}
