package org.cet.controller.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author zhang
 * @Date 2017/9/30 13:18
 * @Content
 */
@Controller
public class WebSocketController {

    @RequestMapping(value = "websocket",method = RequestMethod.GET)
    public String getConnection(){
        return "index";
    }
}
