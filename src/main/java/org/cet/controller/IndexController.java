package org.cet.controller;

import org.cet.common.Const;
import org.cet.pojo.message.TextMessage;
import org.cet.service.IndexService;
import org.cet.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2017/8/19 1:06
 * @Content
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    public static String receviceMsg = null;
	@RequestMapping(value="/",method=RequestMethod.GET)
    public void showIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = Const.Token;

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //判断是否四个参数有空
        if (!StringUtil.hasBlank(signature, timestamp, nonce, echostr)) {
            String[] list = {token, timestamp, nonce};
            //按照字典排序
            Arrays.sort(list);

            StringBuilder builder = new StringBuilder();
            //将拼接成一个字符串
            for (String str : list) {
                builder.append(str);
            }
            //进行sha1加密
            String hashcode = EncryptUtil.sha1(builder.toString());
            //和signature比对
            if (hashcode.equalsIgnoreCase(signature)) {

                response.getWriter().println(echostr);

            }
        }
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    public void messageEnter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //回复的消息XML
        String respXml = null;
        //回复的content
        String content = null;
        try{
            //判断是明文模式还是密文模式
            //判断是否加密 如果加密得到的是解密后的明文 如果没有加密得到的就是明文
            String res = AESUtil.decryptMsg(request);

            //将XML转化为Map
            Map<String,String> map = XMLUtil.getXMLToMap(res);
            String toUserName = map.get("FromUserName");
            String FromUserName = map.get("ToUserName");
            //获得用户的消息类型 是消息类型
            String msgType = map.get("MsgType");

            //将回复文本信息封装到实体类
            TextMessage text = new TextMessage();
            text.setToUserName(toUserName);
            text.setFromUserName(FromUserName);
            text.setCreateTime(map.get("CreateTime"));
            text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            //对微信服务器发来的请求做判断 继而进行不同的业务逻辑
            //先对消息类型做判断
            //文本消息
            if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
                //调用回复消息内容函数 获取回复Content
                content = map.get("Content");
            }
            //图片消息
            else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
                content ="tupian";
            }
            //视频消息 图片消息。。。。。

            //事件推送

            else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
                //先判断是哪种事件类型
                String eventType = map.get("Event");
                //如果是自定义菜单点击事件
                if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
                   content = "点击事件啊";
                }else if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
                    content = "欢迎关注";
                }else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION_SELECT)){
                    content = "地理位置";
                }

            }
            //设置回复内容
            text.setContent(content);
            //将信息添加到xml中去
            respXml = AESUtil.formatXml(text);
            //判断是否加密
            if(AESUtil.isEncryptMsg()){
                //加密的话就进行解密
                respXml =  AESUtil.EncryptMsg(respXml);
            }
            System.out.println(respXml);

            //回复微信服务器
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(respXml);
            response.getWriter().flush();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/access_token",method = RequestMethod.GET)
    @ResponseBody
    public Result getAccessToken(){
	    Result result = new Result();
        try {
            String accessToken = indexService.getAccessToken();
            return Result.ok(accessToken);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(400);
            result.setMsg("获取access_token失败");
            return result;
        }
    }
}
