package org.cet.util;

import org.cet.aes.AesException;
import org.cet.aes.WXBizMsgCrypt;
import org.cet.common.Const;
import org.cet.pojo.message.TextMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * 消息业务处理实现类
 */
public class AESUtil {
    public static String token = Const.Token;
    public static String encodingAesKey = Const.EncodingAESKey;
    public static String appId = Const.AppId;
    public static final String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
    public static String encrypt = null;
    public static String timeStamp = null;
    public static String nonce = null;

    /**
     * 解密收到的信息
     * @param request
     * @return
     * @throws IOException
     * @throws AesException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static String decryptMsg(HttpServletRequest request) throws IOException, AesException, SAXException, ParserConfigurationException {
        if(!isEncryptMsg(request)){
            request.setCharacterEncoding("UTF-8");
            InputStream inputStream = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            inputStream.close();
            return sb.toString();
        }
        //如果加密直接返回

        //否则进行解密
        //读取密文签名
        String msgSignature = request.getParameter("msg_signature");
        //读取时间戳
        timeStamp = request.getParameter("timestamp");
        //读取随机数
        nonce = request.getParameter("nonce");
        //进行解密
        InputStream inputStream = request.getInputStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(inputStream);

        Element root = document.getDocumentElement();
        NodeList nodeList2 = root.getElementsByTagName("ToUserName");
        String toUserName = nodeList2.item(0).getTextContent();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        String encrypt = nodelist1.item(0).getTextContent();

        String format = "<xml><ToUserName><![CDATA[%s]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
        String postData = String.format(format,toUserName, encrypt);
        //调用解密函数
        WXBizMsgCrypt wx = new WXBizMsgCrypt(token,encodingAesKey,appId);
        String res = wx.decryptMsg(msgSignature,timeStamp,nonce,postData);
        inputStream.close();
        return res;
    }


    /**
     * 加密回复消息
     * @param repMsg
     * @return fromXml
     * @throws AesException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String EncryptMsg(String repMsg) throws AesException, ParserConfigurationException, IOException, SAXException {
        String timestamp = System.currentTimeMillis() + "";
        String nonce = "zxcvbnm";
        WXBizMsgCrypt wx = new WXBizMsgCrypt(token,encodingAesKey,appId);
        String res = wx.encryptMsg(repMsg, timestamp, nonce);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(res);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
        String encrypt = nodelist1.item(0).getTextContent();

        String fromXML = String.format(format, encrypt);
        return fromXML;
    }

    /**将回复信息转化成XML格式
     * 明文格式
     * @param text
     * @return
     */
    public static String formatXml(TextMessage text){
        String xml =
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[%s]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>";
        String respXml = String.format(xml,text.getToUserName(),text.getFromUserName(),text.getCreateTime(),
                text.getMsgType(),text.getContent());
        return respXml;
    }

    /**
     * 判断是否加密
     * @param request
     * @return
     */
    public static boolean isEncryptMsg(HttpServletRequest request){
        encrypt = request.getParameter("encrypt_type");
        System.out.println(encrypt);
        if("aes".equals(encrypt)){
            return true;
        }
        return false;
    }

    public static boolean isEncryptMsg(){
        if(encrypt == null || encrypt.equals("raw")){
            return false;
        }
        return true;
    }


}

