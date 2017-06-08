package newhome.baselibrary.XML;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by 20160330 on 2017/3/30 0030.
 */
/*
startElement()、characters()和 endElement()这三个方
法是有参数的，从 XML 中解析出的数据就会以参数的形式传入到这些方法中。需要注意的
是，在获取结点中的内容时，characters()方法可能会被调用多次，一些换行符也被当作内容
解析出来，我们需要针对这种情况在代码中做好控制。
新建一个 ContentHandler类继承自 DefaultHandler
 */
public class SAXHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    //startDocument()方法会在开始 XML 解析的时候调用，
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }
//    startElement()方法会在开始解析某个结点的时候调用
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
// 记录当前结点名
        nodeName = localName;
    }
//    characters()方法会在获取结点中内容的时候调用
    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
// 根据当前的结点名判断将内容添加到哪一个StringBuilder对象中
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }
//    endElement()方法会在完成解析某个结点的时候调用
    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        if ("app".equals(localName)) {
            Log.d("ContentHandler", "id is " + id.toString().trim());
            Log.d("ContentHandler", "name is " + name.toString().trim());
            Log.d("ContentHandler", "version is " + version.toString().trim());
// 最后要将StringBuilder清空掉
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }
//    endDocument()方法会在完成整个 XML 解析的时候调用。
    @Override
    public void endDocument() throws SAXException {
    }
}
