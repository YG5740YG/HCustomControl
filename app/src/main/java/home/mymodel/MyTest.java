package home.mymodel;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import newhome.baselibrary.CommonAction.CommonUseAction;
import newhome.baselibrary.CommonAction.HttpAction;
import newhome.baselibrary.Model.MyData;
import newhome.baselibrary.MyHttp.UseHttp;
import newhome.baselibrary.MyViewI.DataResponseT;
import newhome.baselibrary.ImageHandle.CompressImage.BitmapTo;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.MyTools;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MyTest extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private TextView responseText;
    Context context;
    public String reserveData;
    private ImageView iv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_httpconnection);
        sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response);
        context = this;
        sendRequest.setOnClickListener(this);
        iv_show=(ImageView)findViewById(R.id.iv_show);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("userName", "zgh");
            params.put("passWord", "123456");
            UseHttp useHttp = new UseHttp(CommonUseAction.POST, HttpAction.MYPHP, params, new DataResponseT() {
                @Override
                public void onSucc(Object claxx) {
                    String jsonData = "{\"FirstName\":\"Peter\",\"LastName\":\"Griffin\",\"Age\":\"35\"}";
                    MyData myData = MyTools.jsonToModel(jsonData, MyData.class);
                    responseText.setText("post"+myData.getAge()+"=="+myData.getLastName());
                }
                @Override
                public void onFail(String error) {

                }
            });
            UseHttp useHttp1=new UseHttp(CommonUseAction.GET, HttpAction.MYPHP, params, new DataResponseT() {
                @Override
                public void onSucc(Object claxx) {
                    String jsonData = "{\"FirstName\":\"Peter\",\"LastName\":\"Griffin\",\"Age\":\"35\"}";
                    MyData myData = MyTools.jsonToModel(jsonData, MyData.class);
                    responseText.setText(responseText.getText()+"++get++"+myData.getAge()+"=="+myData.getLastName());
                }
                @Override
                public void onFail(String error) {

                }
            });
            UseHttp useHttp2=new UseHttp(CommonUseAction.LOADFILE, HttpAction.IMAGEURL, params, new DataResponseT() {
                @Override
                public void onSucc(Object response) {
                    Bitmap bitmap=(Bitmap)response;
                    BitmapTo bitmapTo=new BitmapTo(context);
                    bitmapTo.saveBitmap(bitmap);
                    iv_show.setVisibility(View.VISIBLE);
                    iv_show.setImageBitmap(bitmap);
                }
                @Override
                public void onFail(String error) {

                }
            });
//            String imageUrl="/storage/emulated/0/Tencent/QQ_Images/-67881c25ac90f684.jpg";
//            String imageUrl="/storage/emulated/0/9ji/1487223027268.jpg";
            String imageUrl="/storage/emulated/0/Download/123.jpg";
            File file=new File("");
            Uri uri;
            if (imageUrl.startsWith("/")) {
                file = new File(imageUrl);
                uri = Uri.fromFile(file);
                try {
                    FileInputStream is = new FileInputStream(file);
                }catch (IOException e){
Logs.Debug("gg=========错误");
                }
            } else if (imageUrl.startsWith("file://") || imageUrl.startsWith("content://")) {
                uri = Uri.parse(imageUrl);
            } else {
                uri = Uri.parse("");
            }
            UseHttp useHttp3=new UseHttp(CommonUseAction.UPLOADE, HttpAction.UPLOADE, params, imageUrl, new DataResponseT() {
                @Override
                public void onSucc(Object claxx) {
                    String jsonData = "{\"FirstName\":\"Peter\",\"LastName\":\"Griffin\",\"Age\":\"35\"}";
                    MyData myData = MyTools.jsonToModel(jsonData, MyData.class);
                    responseText.setText(responseText.getText()+"++updata++"+myData.getAge()+"=="+myData.getLastName()+"===="+claxx.toString());
                }
                @Override
                public void onFail(String error) {
                    responseText.setText(responseText.getText()+"==gg==");
                }
            });
        }
    }
}