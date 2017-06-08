package newhome.baselibrary.XML;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/3/30 0030.
 */
//XML数据格式
/**
<apps>
<app>
<id>1</id>
<name>Google Maps</name>
<version>1.0</version>
</app>
<app>
<id>2</id>
<name>Chrome</name>
<version>2.1</version>
</app>
<app>
<id>3</id>
<name>Google Play</name>
<version>2.3</version>
</app>
</apps>
 */
public class PullTestView extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private TextView responseText;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
// 在这里进行UI操作，将结果显示到界面上
//                    responseText.setText(response);
                    parseXMLWithPull(response);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_httpconnection);
        sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response);
        sendRequest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithHttpURLConnection();
        }
    }
    /*
    开启了一个子线程，然后在子线程里使用HttpURLConnection
发出一条 HTTP 请求，请求的目标地址就是百度的首页。接着利用 BufferedReader 对服务器
返回的流进行读取，并将结果存放到了一个 Message 对象中。这里为什么要使用 Message 对
象呢？当然是因为子线程中无法对 UI 进行操作了。我们希望可以将服务器返回的内容显示
到界面上，所以就创建了一个 Message 对象，并使用 Handler 将它发送出去。之后又在 Handler
的 handleMessage()方法中对这条 Message 进行处理，最终取出结果并设置到 TextView 上。
     */
    private void sendRequestWithHttpURLConnection() {
// 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    //一般只需 new 出一个 URL 对象，并传入 目标的网络地址，然后调用一下 openConnection()方法即可
                    URL url = new URL("https://10.0.2.2/get_data.xml");
                    connection = (HttpURLConnection) url.openConnection();
                    //得到了 HttpURLConnection 的实例之后，我们可以设置一下 HTTP 请求所使用的方法。常用的方法主要有两个，GET 和 POST
                    connection.setRequestMethod("GET");

                    /***************************************/
//                    提交数据给服务器
//                    只需要将 HTTP 请求的方法改成 POST，并在获取输入流之前把要提交的数据写出即可
//每条数据都要以键值对的形式存在，数据与数据之间用&符号隔开，比如说我们想要向服务器提交用户名和密码
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("username=admin&password=123456");
                    /***************************************/

                    //进行一些自由的定制了，比如设置连接超时、读取超时的毫秒数，以及服务器希望得到的一些消息头等。这部分内容根据自己的实际情况进行编写
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //再调用 getInputStream()方法就可以获取到服务器返回的输入流了
                    InputStream in = connection.getInputStream();
// 下面对获取到的输入流进行读取,输入流进行读取
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //调用 disconnect()方法将这个 HTTP 连接关闭掉
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //调用了 parseXMLWithPull()方法来解析服务器返回的数据
    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//获取到一个XmlPullParserFactory 的 实 例
            XmlPullParser xmlPullParser = factory.newPullParser();// 得 到 XmlPullParser 对 象
            xmlPullParser.setInput(new StringReader(xmlData));// 调 用XmlPullParser 的 setInput()方法将服务器返回的 XML 数据设置进去就可以开始解析了
            int eventType = xmlPullParser.getEventType();//通过 getEventType()可以得到当前的解析事件
            String id = "";
            String name = "";
            String version = "";
            //通过 getName()方法得到当前结点的名字，如果发现结点名等于
//            id、name 或 version，就调用 nextText()方法来获取结点内具体的内容，每当解析完一个 app
//            结点后就将获取到的内容打印出来
            while (eventType != XmlPullParser.END_DOCUMENT) {//在一个 while 循环中不断地进行解析，如果当前的解析事件不等于 XmlPullParser.END_DOCUMENT，说明解
//                析工作还没完成，调用 next()方法后可以获取下一个解析事件
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
//                开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
//  完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            Logs.Debug("MainActivity id is " + id);
                            Logs.Debug("MainActivity name is " + name);
                            Logs.Debug("MainActivity version is " + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
