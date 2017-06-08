package newhome.baselibrary.MyHttp;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MyhttpConnection {
    public static final int SHOW_RESPONSE = 0;
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
//                    URL url = new URL("https://m.9ji.com/app/2_0/ProductSearch.aspx?NewVersion=1&act=GetHome&cityid=530102&plat=1&uid=");
//                    URL url = new URL("https://m.9ji.com/app/2_0/ProductSearch.aspx?act=GetAreaId&lat=25.06698032313706&lng=102.72644498342382");
                    URL url = new URL("https://m.9ji.com/app/2_0/ProductSearch.aspx?act=GetLimitBuy&cityid=530102&t=1490865077224");
                    connection = (HttpURLConnection) url.openConnection();
                    //得到了 HttpURLConnection 的实例之后，我们可以设置一下 HTTP 请求所使用的方法。常用的方法主要有两个，GET 和 POST
                    connection.setRequestMethod("GET");
//                    connection.setRequestMethod("POST");
                    // 设置允许输出
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    // 设置不用缓存
//                    connection.setUseCaches(false);
                    // 设置维持长连接
//                    connection.setRequestProperty("Connection", "Keep-Alive");
                    // 设置文件字符集:
//                    connection.setRequestProperty("Charset", "UTF-8");
                    //转换为字节数组
//                    byte[] data = (obj.toString()).getBytes();
                    // 设置文件长度
//                    connection.setRequestProperty("Content-Length", String.valueOf(3000));
                    // 设置文件类型:
//                    connection.setRequestProperty("contentType", "application/json");

                    /***************************************/
//                    提交数据给服务器
//                    只需要将 HTTP 请求的方法改成 POST，并在获取输入流之前把要提交的数据写出即可
//每条数据都要以键值对的形式存在，数据与数据之间用&符号隔开，比如说我们想要向服务器提交用户名和密码
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("username=admin&password=123456");
                    /***************************************/

                    //进行一些自由的定制了，比如设置连接超时、读取超时的毫秒数，以及服务器希望得到的一些消息头等。这部分内容根据自己的实际情况进行编写
//                    connection.setConnectTimeout(10000);
//                    connection.setReadTimeout(10000);
                    //再调用 getInputStream()方法就可以获取到服务器返回的输入流了
                    if(connection.getResponseCode()==200) {
                        InputStream in = connection.getInputStream();
// 下面对获取到的输入流进行读取,输入流进行读取
                        BufferedReader reader = new BufferedReader(new
                                InputStreamReader(in, "UTF-8"));
                        StringBuilder response = new StringBuilder();
//                        InputStreamReader isr = new InputStreamReader(in);
//                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
//                        response.append("\r\n");
                        }
                        reader.close();
                        //方法二，重新处理数据//将输入流转换成字符串
//                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                        byte [] buffer=new byte[1024];
//                        int len=0;
//                        while((len=in.read(buffer))!=-1){
//                            baos.write(buffer, 0, len);
//                        }
//                        String response=baos.toString();
//                        baos.close();
                        in.close();
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
                        message.obj = response.toString();
                        handler.sendMessage(message);
                    }
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
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
// 在这里进行UI操作，将结果显示到界面上
//                    responseText.setText(response);
//                    parseJSONWithGSON(response);
            }
        }
    };
}
