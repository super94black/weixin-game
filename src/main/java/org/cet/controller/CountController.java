package org.cet.controller;

import org.cet.component.JedisClient;
import org.cet.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang
 * @Date 2017/9/29 22:11
 * @Content
 */
@Controller
public class CountController {

    /**
     * 用户点击记录openid和点击次数
     * @param openId
     * @return
     */
    @RequestMapping(value = "/click",method = RequestMethod.POST)
    @ResponseBody
    public Result caculateClickCount(@RequestParam String openId){
        try {
            JedisClient js = new JedisClient();
            if(js.get("click") == null || "".equals(js.get("click").trim())){
                js.set("click","1");
            }else{
                js.incr("click");
            }
            String redisOpenId = js.get(openId);
            if(null == redisOpenId || "".equals(redisOpenId.trim())){
                js.set(openId,"1");
                return Result.ok(js.get(openId));
            }
            js.incr(openId);
            js.expire("click",1800);
            js.expire(openId,1800);
            return Result.ok(js.get(openId));
        }catch (Exception e){
            Result result = new Result();
            result.setStatus(400);
            result.setMsg("添加失败");
            return result;
        }

    }

    /**
     * 得到点击总数
     * @return
     */
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public String getTotalCount(){
        JedisClient js = new JedisClient();
        String click = js.get("click");
        if(null == click || "".equals(click.trim())){
            click = "0";
            return click;
        }
        return click;
    }
}
