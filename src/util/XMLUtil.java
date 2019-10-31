package util;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

public class XMLUtil {
    public static Object getBean(String name){
        try{
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuild = df.newDocumentBuilder();
            Document doc;
            doc = dbuild.parse(new File("Comprehensive.xml"));

            NodeList nl = doc.getElementsByTagName(name);
            Node node = nl.item(0).getFirstChild();
            return node.getNodeValue();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
