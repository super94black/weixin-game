package org.cet.service.impl;

import org.cet.common.Const;
import org.cet.component.JedisClient;
import org.cet.pojo.menu.*;
import org.cet.service.MenuService;
import org.cet.util.CurlUtil;
import org.cet.util.HttpClientUtil;
import org.cet.util.JsonUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2017/9/26 13:00
 * @Content 菜单实现类
 */
@Service
public class MenuServiceImpl implements MenuService{

    /**
     * 封装菜单
     * @return
     */
    public Menu getMenu(){
        //设置第一个菜单下边的二级菜单
        CommonButton btn1_1 = new CommonButton();
        btn1_1.setName("课表查询");
        btn1_1.setType("click");
        btn1_1.setKey(Const.BUTTON_ONE_ONE);

        CommonButtonFix btn1_2 = new CommonButtonFix();
        btn1_2.setName("四/六级查询");
        btn1_2.setType("view");
        btn1_2.setUrl("http://www.baidu.com");

        CommonButton btn1_3 = new CommonButton();
        btn1_3.setName("考试查询");
        btn1_3.setType("click");
        btn1_3.setKey(Const.BUTTON_ONE_THREE);

        //封装第一个带有二级菜单的一级菜单
        ComplexButton btn1 = new ComplexButton();
        btn1.setName("信息查询");
        btn1.setSub_button(new Button[]{btn1_1,btn1_2,btn1_3});

        //设置第二个菜单
        CommonButtonFix btn2_1 = new CommonButtonFix();
        btn2_1.setName("openId");
        btn2_1.setType("view");
        btn2_1.setUrl("http://zhlwx.mynatapp.cc/auth.html");

        CommonButton btn2_2 = new CommonButton();
        btn2_2.setName("@微社区");
        btn2_2.setType("click");
        btn2_2.setKey(Const.BUTTON_TWO_TWO);

        //封装第二个菜单
        ComplexButton btn2 = new ComplexButton();
        btn2.setName("校园生活");
        btn2.setSub_button(new Button[]{btn2_1,btn2_2});

        //设置第三个菜单
        CommonButton btn3 = new CommonButton();
        btn3.setName("地理位置");
        btn3.setType("location_select");
        btn3.setKey("3");

        //封装整个菜单
        Menu menu = new Menu();
        menu.setButton(new Button[]{btn1,btn2,btn3});
        return menu;
    }

    public String curlMenuInterface(Menu menu,String accessToken) throws IOException {
        String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        String menuJson = JsonUtils.objectToJson(menu);
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        conn.setDoOutput(true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
        writer.write(menuJson);
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();
        writer.close();

        return sb.toString();
    }

    public String deleteMenu(String accessToken){
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken;
        String string = HttpClientUtil.doGet(url);
        return string;
    }
}
