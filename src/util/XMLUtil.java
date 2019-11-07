package util;

import entity.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XMLUtil {

    private static Document doc;

    private static Document getDoc(String path){
        try {
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = df.newDocumentBuilder();
            Document tdoc = dbuild.parse(new File(path));
            return tdoc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getBean(String name) {
        doc = getDoc("src/xml/config_server.xml");
        try {
            NodeList nl = doc.getElementsByTagName(name);
            Node node = nl.item(0).getFirstChild();
            return node.getNodeValue();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static MysqlConfig getSqlCFG() {
        doc = getDoc("src/xml/config_mysql.xml");
        NodeList list = doc.getElementsByTagName("mysql_config");
        MysqlConfig mc = new MysqlConfig();
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);

            mc.setU(element.getAttribute("username"));
            mc.setP(element.getAttribute("password"));
            mc.setURL(element.getElementsByTagName("URL").item(0).getTextContent());
            mc.setDataBase(element.getElementsByTagName("database").item(0).getTextContent());
            mc.setDriver(element.getElementsByTagName("driver").item(0).getTextContent());
        }
        return mc;
    }
}
