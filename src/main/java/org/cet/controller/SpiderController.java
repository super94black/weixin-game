package org.cet.controller;

import org.cet.service.SpiderService;
import org.cet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhang
 * @Date 2017/9/27 20:09
 * @Content
 */
@Controller
public class SpiderController {

    @Autowired
    private SpiderService spiderService;

    @RequestMapping(value = "/spider/{stuNum}",method = RequestMethod.GET)
    @ResponseBody
    public Result getSpiderFromJwzx(@PathVariable String stuNum){
        Result result = new Result();
        try {
            if (stuNum == null || "".equals(stuNum.trim())){
                result.setStatus(400);
                result.setMsg("学好不能为空");
                return result;
            }
           result =  spiderService.getClassTableFromJwzx(stuNum);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(400);
            result.setMsg("爬取失败");
        }finally {
            return result;
        }
    }
}
