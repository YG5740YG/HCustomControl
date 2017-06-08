package newhome.baselibrary.MyHttpOld.JSON;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import newhome.baselibrary.ImageHandle.CompressImage.BitmapTo;
import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/4/1 0001.
 */
//网络请求，下载网络图片,并显示在控件中
public class HttpConnectionDownLoadeImage extends AppCompatActivity {

    private ImageView iv;

    private String imageurl = "http://img06.tooopen.com/images/20161106/tooopen_sl_185050524199.jpg";
    private Bitmap bitmap;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_httpconnection);
        iv = (ImageView) findViewById(R.id.iv_show);
        context=this;
        findViewById(R.id.send_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(loadrunable).start();
            }
        });
    }
    private Runnable loadrunable = new Runnable() {
        private InputStream is;
        @Override
        public void run() {
            try {
                URL imgUrl = new URL(imageurl);
                // 使用HttpURLConnection打开连接
                HttpURLConnection urlConn = (HttpURLConnection) imgUrl
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
                byte[] bytesInputStream = getBytesInputStream(is);
                bitmap = BitmapFactory.decodeByteArray(bytesInputStream,0,bytesInputStream.length);
                Message msgone = new Message();
                msgone.what = 1;
                handler.sendMessage(msgone);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            Log.e("tag", "handleMessage: "+msg.what );
            if (null != bitmap && null != iv){
                BitmapTo bitmapTo=new BitmapTo(context);
                bitmapTo.saveBitmap(bitmap);
                iv.setImageBitmap(bitmap);
            }
        }
    };
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
