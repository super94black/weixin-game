package org.cet.service;

import org.cet.pojo.menu.Menu;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @Author zhang
 * @Date 2017/9/26 14:15
 * @Content
 */
public interface MenuService {
    public Menu getMenu();
    public String curlMenuInterface(Menu menu,String accessToken) throws IOException;
    public String deleteMenu(String accessToken);
}
