package org.cet.util;


import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;


/**
 * xml中区分 Node Element Attribute三者区别，注意Node的种类
 */
public class XMLUtil {

    /**
     * XML转化成Map
     * @param xml
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Map<String,String> getXMLToMap(String xml) throws ParserConfigurationException, IOException, SAXException {
        //通过输入流获取Document对象

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        StringReader reader = new StringReader(xml);
        InputSource in = new InputSource(reader);
        Document document = builder.parse(in);
        //获取根节点
        Element rootNode = document.getDocumentElement();
        //根节点名
        String name = rootNode.getNodeName();
        //获取子节点数组
        NodeList items = rootNode.getChildNodes();
        Map<String, String> result = new HashMap<>();
        XMLUtil.readXMLToMap(items,result);
        return result;
    }

    /**
     * 递归解析XML
     * @param items
     * @param map
     * @return
     */
    public static Map<String,String> readXMLToMap(NodeList items,Map<String,String> map){
        for (int i = 0; i < items.getLength(); i++) {
            //判断是不是还有子节点
            Node item = items.item(i);
            if(item.getChildNodes().getLength() != 0){
                NodeList childenList = item.getChildNodes();
                readXMLToMap(childenList,map);
            }
            //否则的话放入map
            String itemName = item.getNodeName();
            if(itemName.equals("#text")){
                continue;
            }
            String itemValue = item.getTextContent();
            map.put(itemName,itemValue);
        }
        return map;
    }

}