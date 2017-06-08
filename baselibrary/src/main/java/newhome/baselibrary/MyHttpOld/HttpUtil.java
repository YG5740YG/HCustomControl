package newhome.baselibrary.MyHttpOld;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

import newhome.baselibrary.MyHttpOld.JSON.Interface.InterfaceHttpCallbackListener;


/**
 * Created by 20160330 on 2017/3/30 0030.
 */

public class HttpUtil {
        public static void sendHttpRequest(final String address, final LinkedHashMap<String,String> params, final InterfaceHttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
//                NewVersion=1&act=GetHome&cityid=530102&plat=1&uid=
                try {
                   String addressUrl=address;
                    if(!params.isEmpty()){
                        addressUrl=addressUrl+"?";
                    }
                    for (String key:params.keySet()){
                        addressUrl=addressUrl+key+"="+params.get(key)+"&";
                    }
                    URL url = new URL(addressUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    /***************************************/
//                    提交数据给服务器
//                    只需要将 HTTP 请求的方法改成 POST，并在获取输入流之前把要提交的数据写出即可
//每条数据都要以键值对的形式存在，数据与数据之间用&符号隔开，比如说我们想要向服务器提交用户名和密码
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("?NewVersion=1&act=GetHome&cityid=530102&plat=1&uid=");
                    /***************************************/

                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //  回调onFinish() 方法
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {//  回调onError() 方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
