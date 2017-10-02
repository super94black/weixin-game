package org.cet.test;

import org.cet.util.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2017/10/1 16:03
 * @Content
 */
public class ClickThread extends Thread {

    @Override
    public void run() {
        String url = "http://localhost:8080/click";
        Map<String,String> map = new HashMap<>();
        for (int i = 0; i < 1000; i++){
            map.put("openId","id:"+ i);
            HttpClientUtil.doPost(url,map);
        }
    }
    public static void main(String[] args) {
        ClickThread thread = new ClickThread();
        thread.run();
    }
}
