package test.cvlh.spider;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by LucasX on 2017/3/20.
 */
public class BigXmlParser {

    public void parseXml(String xmlPath) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(xmlPath));
        while (dataInputStream.available() > 0) {
            int i = dataInputStream.read();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            new BigXmlParser().parseXml("E:\\Data\\news_tensite_xml.full\\news_tensite_xml.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
