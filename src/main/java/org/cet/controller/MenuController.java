package org.cet.controller;

import javafx.scene.chart.ValueAxis;
import org.cet.pojo.menu.Menu;
import org.cet.service.IndexService;
import org.cet.service.MenuService;
import org.cet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhang
 * @Date 2017/9/26 14:19
 * @Content 菜单控制器
 */
@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private IndexService indexService;

    /**
     * 创建菜单
     * @return
     */
    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    @ResponseBody
    public Result createMeunTest(){
        Result result = new Result();
        try {
            Menu menu = menuService.getMenu();
            String accessToken = indexService.getAccessToken();
            String json = menuService.curlMenuInterface(menu,accessToken);
            return Result.ok(json);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(400);
            result.setMsg("创建菜单失败");
            return result;
        }
    }

    @RequestMapping(value = "/menu/delete",method = RequestMethod.GET)
    @ResponseBody
    public Result deleteMenu(){
        Result result = new Result();
        try {
            String accessToken = indexService.getAccessToken();
            String json = menuService.deleteMenu(accessToken);
            return Result.ok(json);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(400);
            result.setMsg("删除菜单失败");
            return result;
        }
    }
}
