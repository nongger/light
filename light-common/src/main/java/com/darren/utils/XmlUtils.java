package com.darren.utils;

import com.thoughtworks.xstream.XStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Map;


/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/6/28 13:04
 * Desc   :  *
 */
public class XmlUtils {

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }

    /**
     * Java对象转Xml字符串（序列化）
     *
     * @param object
     * @param rootName
     * @param rootType
     * @return
     */
    public static String bean2Xml(Object object, String rootName, Class<?> rootType) {
        //创建xStream对象
        XStream xStream = new XStream();
        //给指定类起别名
        xStream.alias(rootName, rootType);
        //暂时忽略掉一些新增的字段
        xStream.ignoreUnknownElements();
        //调用toXML 将对象转成字符串
        return xStream.toXML(object);
    }

    /**
     * Xml字符串转Java对象（反序列化）
     *
     * @param xml
     * @param rootName 根元素名称
     * @param rootType 根元素对应的Java类类型
     * @return
     */
    public static Object xml2Bean(String xml, String rootName, Class<?> rootType) {
        XStream xStream = new XStream();
        xStream.alias(rootName, rootType);
        //暂时忽略掉一些新增的字段
        xStream.ignoreUnknownElements();
        return xStream.fromXML(xml);
    }

}
