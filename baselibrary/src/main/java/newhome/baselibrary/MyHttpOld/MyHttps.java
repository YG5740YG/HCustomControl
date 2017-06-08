package newhome.baselibrary.MyHttpOld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.UUID;

import newhome.baselibrary.MyHttpOld.JSON.Interface.InterfaceHttpCallbackListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class MyHttps {
    private static int readTimeOut = 10 * 1000;
    private static int connectTimeout = 10 * 1000;
    private static final String CONTENT_TYPE = "multipart/form-data";
    private static String newName ="image.jpg";
    private static String uploadFile ="/sdcard/image.JPG";//上传的图片路径
    private String actionUrl ="http://192.168.0.71:8086/HelloWord/myForm";//服务器地址
    static String end ="\r\n";
    static String twoHyphens ="--";
    static String boundary = UUID.randomUUID().toString(); // 边界标识 随机生成;
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    public static final String SUCCESS="1";
    public static final String FAILURE="0";
    public static String stats="";
    public static String data="";

    //donwLoad
    private static ImageView iv;
    private static String imageurl = "http://img06.tooopen.com/images/20161106/tooopen_sl_185050524199.jpg";
    private static Bitmap bitmap;
    static Context context;

    //调用 parseJSONWithGSON()方法来解析数据
    public static void Get(String netUrl,LinkedHashMap<String,Object>params, final InterfaceHttpCallbackListener listener) {
        sendHttpRequestGet(netUrl,params, new InterfaceHttpCallbackListener() {
            @Override
            public void onFinish(Object response) {
                //从其他线程返回的数据，在主线程中使用，使用观察监听
                final Observable observable= Observable.just(response.toString())
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
                Subscriber<String> mySubscriber1 = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
//                            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                        if(listener!=null) {
                            listener.onError(e.toString());
                        }
                    }
                    @Override
                    public void onNext(String respson) {
//                            parseJSONWithGSON(o.toString());
                        if(listener!=null) {
                            listener.onFinish(respson);
                        }
                    }
                };
                observable.subscribe(mySubscriber1);
            }
            @Override
            public void onError(Object e) {
                if(listener!=null) {
                    listener.onError(e.toString());
                }
            }
        });
    }
    //Get
    private static void sendHttpRequestGet(final String address, final LinkedHashMap<String,Object> params, final InterfaceHttpCallbackListener listener) {
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
                        try {
                                JSONObject jsonObject =new JSONObject(response.toString());
                                stats = jsonObject.getString("stats");
                                data = jsonObject.getString("data");
                                Log.d("gg=========MainActivity", "id is ==" + stats);
                                Log.d("gg=========MainActivity", "name is ==" + data);
                            }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
//                        String data=JsonTo(response.toString());
                        if(stats.equals("1")) {
                            listener.onFinish(data);
                        }else {
                            listener.onError(data);
                        }
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
//Post带参数请求
    public static void sendHttpRequestPost(final String address, final LinkedHashMap<String,Object> params, final InterfaceHttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    String addressUrl=address;
                    String param="";
                    if(!params.isEmpty()){
                        param=param+"?";
                    }
                    for (String key:params.keySet()){
                        param=param+key+"="+params.get(key)+"&";
                    }
                    URL url = new URL(addressUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(param);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
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
    //Post上传图片
    public static void PostUpLoad(final String address, final LinkedHashMap<String,Object> params, final InterfaceHttpCallbackListener listener) {

    }
    //json解析
    public static String JsonTo(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            //            循环遍历这个 JSONArray，从中取出的每一个元素都是一个 JSONObject 对象
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                每个 JSONObject 对象中又会包含 id、name 和version 这些数据。接下来只需要调用 getString()方法将这些数据取出
                String stats = jsonObject.getString("stats");
                String data = jsonObject.getString("data");
                Log.d("MainActivity", "id is " + stats);
                Log.d("MainActivity", "name is " + data);
                return data;
            }
        }catch (Exception extraTypes){

        }
        return null;
    }

    //Post下载
    public static void PostDonwLoad(final String address, final LinkedHashMap<String,Object> params, final InterfaceHttpCallbackListener listener) {
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
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    byte[] bytesInputStream = getBytesInputStream(in);
                    bitmap = BitmapFactory.decodeByteArray(bytesInputStream,0,bytesInputStream.length);
//                        String data=JsonTo(response.toString());
                            listener.onFinish(bitmap);
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
    public static byte[] getBytesInputStream( InputStream is) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[512];
        int len;
        while ((len = is.read(buff))!= -1){
            arrayOutputStream.write(buff,0,len);
        }
        is.close();
        arrayOutputStream.close();
        return arrayOutputStream.toByteArray();
    }
}
