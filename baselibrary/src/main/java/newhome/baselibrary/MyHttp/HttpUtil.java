package newhome.baselibrary.MyHttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.UUID;

import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.MyViewI.HttpCallbackListener;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
//网络请求
public class HttpUtil {
    InputStream is;
    //Get网络请求
    public static void sendHttpRequestGet(final String address, final LinkedHashMap<String,String> params, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    String addressUrl = address;
                    if (!params.isEmpty()) {
                        addressUrl = addressUrl + "?";
                    }
                    for (String key : params.keySet()) {
                        addressUrl = addressUrl + key + "=" + params.get(key) + "&";
                    }
                    Logs.Debug("httpUrl==========get=="+addressUrl);
                    URL url = new URL(addressUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new
                                InputStreamReader(in, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        //  回调onFinish() 方法
                        if (listener != null) {
                            listener.onFinish(response.toString());
                        }
                    }else{
                        listener.onError("网络数据获取错误！");
                    }
                    } catch(Exception e){
                        e.printStackTrace();
                        if (listener != null) {//  回调onError() 方法
                            listener.onError(e);
                        }
                    } finally{
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
            }
        }).start();
    }
    //Post网络请求
    public static void sendHttpRequestPost(final String address, final LinkedHashMap<String,String> params, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                String parasValue="";
                try {
                    String addressUrl = address;
                    for (String key : params.keySet()) {
                        parasValue = parasValue + key + "=" + params.get(key) + "&";
                    }
                    Logs.Debug("httpUrl==========post=="+addressUrl);
                    byte[] data = parasValue.getBytes();
                    URL url = new URL(addressUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST"); //这是请求方式为POST
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
                    //设置post请求必要的请求头
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 请求头, 必须设置
                    connection.setRequestProperty("Content-Length", data.length + ""); // 注意是字节长度, 不是字符长度
                    connection.setDoInput(true);
                    connection.setDoOutput(true);// 准备写出
                    connection.getOutputStream().write(data); // 写出数据
                    int code = connection.getResponseCode();
                    if (code == 200) {
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
                    }else{
                        listener.onError("网络数据获取错误！");
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    if (listener != null) {//  回调onError() 方法
                        listener.onError(e);
                    }
                } finally{
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //获取服务器的图片并保存到本地
    public static void LoadAndSaveImage(final String address, final LinkedHashMap<String, String> params, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            private InputStream is;
            @Override
            public void run() {
                try {
                    URL imageurl = new URL(address);
                    // 使用HttpURLConnection打开连接
                    HttpURLConnection urlConn = (HttpURLConnection) imageurl
                            .openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(false);
                    urlConn.setRequestMethod("GET");
                    urlConn.setConnectTimeout(3000);
//                不要设置setDoOutput(true)，post请求上传参数得设置为true;
//                它默认为false:  urlConn.setDoOutput(false);
                    urlConn.setUseCaches(true);
                    urlConn.connect();
                    int code = urlConn.getResponseCode();
                    Log.e("tag", "run: "+code );
                    // 将得到的数据转化成InputStream
                    InputStream is = urlConn.getInputStream();
                    // 将InputStream转换成Bitmap
//            bitmap = getBitmapInputStream(is);
                    //数据流写入
                    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                    byte[] buff = new byte[512];
                    int len=0;
                    while ((len = is.read(buff))!= -1){
                        arrayOutputStream.write(buff,0,len);
                    }
//                    String response=arrayOutputStream.toString();//如果为json
                    is.close();
                    arrayOutputStream.close();
                    byte[] bytesInputStream =  arrayOutputStream.toByteArray();
                    if(listener!=null){
                        Bitmap bitmap=BitmapFactory.decodeByteArray(bytesInputStream,0,bytesInputStream.length);
                        listener.onFinish(bitmap);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if (null != is){
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
    //上传文件
    public static void UpLoadeFile(final String http_url, final String filepath, LinkedHashMap<String, String>params, final HttpCallbackListener listener){
        final String encode="UTF-8";
        final String result = null;
        final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        final String PREFIX = "--", LINE_END = "\r\n";
        final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(filepath);
                    if (!file.exists()) {
                        Logs.Debug("gg============错误文件不存在");
                    }
                    URL url = new URL(http_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(3000);
                    conn.setDoInput(true); // 允许输入流
                    conn.setDoOutput(true); // 允许输出流
                    conn.setUseCaches(false); // 不允许使用缓存
                    conn.setRequestMethod("POST"); // 请求方式
                    conn.setRequestProperty("Charset", encode); // 设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                    DataOutputStream dos = new DataOutputStream(
                            conn.getOutputStream());
                    if (file != null) {
                        /**
                         * 当文件不为空，把文件包装并且上传
                         */
                        StringBuffer sb = new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        /**
                         * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件(与服务器端约定好的标识）
                         * filename是文件的名字，包含后缀名的 比如:abc.png
                         */
                        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                + file.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset="
                                + encode + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());
                        FileInputStream is=new FileInputStream(file);
//                        InputStream is = new FileInputStream(file);
//                        InputStream strem = context.getResources().openRawResource(R.drawable.bd_bord_gray_ss);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        is.close();
                        dos.write(LINE_END.getBytes());
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                                .getBytes();

                        dos.write(end_data);
                        dos.flush();
                        /**
                         * 获取响应码 200=成功 当响应成功，获取响应的流
                         */
                        int res = conn.getResponseCode();
                        Log.e("Upload", "response code:" + res);
                         if(res==200) {
                             Log.e("Upload", "request success");
                             InputStream input = conn.getInputStream();
                             StringBuffer sb1 = new StringBuffer();
                             int ss;
                             while ((ss = input.read()) != -1) {
                                 sb1.append((char) ss);
                             }
                             if(listener!=null){
                                 listener.onFinish(sb1);
                             }else{
                                 listener.onError("图片上传失败！");
                             }
                             Logs.Debug("gg===========shang ");
                             Log.e("Upload", "result : " + result);
                         }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Logs.Debug("gg===========失败");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public Bitmap getBitmapInputStream(InputStream is){
        Bitmap bp;
        bp = BitmapFactory.decodeStream(is);
        return bp;
    }
    public byte[] getBytesInputStream( InputStream is) throws IOException {
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

