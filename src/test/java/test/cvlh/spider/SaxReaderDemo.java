package test.cvlh.spider;

/**
 * Created by LucasX on 2017/3/20.
 */

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxReaderDemo {
    public static void main(String[] args) {

        try {
            File inputFile = new File("E:\\Data\\news_tensite_xml.full\\news_tensite_xml.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserHandler extends DefaultHandler {

    boolean bUrl = false;
    boolean bDocno = false;
    boolean bContentTitle = false;
    boolean bContent = false;

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("url")) {
            bUrl = true;
        } else if (qName.equalsIgnoreCase("docno")) {
            bDocno = true;
        } else if (qName.equalsIgnoreCase("contenttitle")) {
            bContentTitle = true;
        } else if (qName.equalsIgnoreCase("content")) {
            bContent = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("doc")) {
            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        if (bUrl) {
            System.out.println("url: "
                    + new String(ch, start, length));
            bUrl = false;
        } else if (bDocno) {
            System.out.println("docno: "
                    + new String(ch, start, length));
            bDocno = false;
        } else if (bContentTitle) {
            System.out.println("contenttitle: "
                    + new String(ch, start, length));
            bContentTitle = false;
        } else if (bContent) {
            System.out.println("content: "
                    + new String(ch, start, length));
            bContent = false;
        }
    }
}