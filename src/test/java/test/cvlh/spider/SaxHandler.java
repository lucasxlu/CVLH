package test.cvlh.spider;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;


public class SaxHandler extends DefaultHandler {

    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        String content = new String(arg0, arg1, arg2);
        System.out.println(content);
        super.characters(arg0, arg1, arg2);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        super.endElement(arg0, arg1, arg2);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String arg0, String arg1, String arg2,
                             Attributes arg3) throws SAXException {
        System.out.print(arg3.getQName(2) + " : " + arg3.getValue(2));
        super.startElement(arg0, arg1, arg2, arg3);
    }

    public static void main(String[] args) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            File f = new File("E:\\Data\\news_tensite_xml.full\\news_tensite_xml.xml");
            SaxHandler dh = new SaxHandler();
            parser.parse(f, dh);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
