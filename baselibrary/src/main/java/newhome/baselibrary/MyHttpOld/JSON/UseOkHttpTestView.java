package newhome.baselibrary.MyHttpOld.JSON;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import newhome.baselibrary.MyHttp.MyHttp;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.MyHttpOld.JSON.Data.HomeData;

/**
 * Created by 20160330 on 2017/3/30 0030.
 */

public class UseOkHttpTestView extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private TextView responseText;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    parseJSONWithGSON(response);
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
//            MyHttp myHttp=new MyHttp("https://m.9ji.com/app/2_0/ProductSearch.aspx?act=GetLimitBuy&cityid=530102&t=1490865077224", new DataResponse() {
            MyHttp myHttp=new MyHttp("https://m.9ji.com/app/2_0/ProductSearch.aspx?NewVersion=1&act=GetHome&cityid=530102&plat=1&uid=", new DataResponse() {
                @Override
                public void onSucc(Object response) {
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
//                    parseJSONWithGSON(response.toString());//直接调用无法改变UI
                }
                @Override
                public void onFail(String error) {

                }
            });
        }
    }
    //调用 parseJSONWithGSON()方法来解析数据
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
//        AreaInfoTest homeData = gson.fromJson(jsonData, AreaInfoTest.class);
//        Logs.Debug("gg=======Json=="+jsonData);
//        Logs.Debug("gg=======jsondata=="+homeData.getCityname()+"=="+homeData.getPname());

        HomeData homeData = gson.fromJson(jsonData, HomeData.class);
        Logs.Debug("gg=======Json=="+jsonData);
        Logs.Debug("gg=======Jsondata=="+homeData.getStats());
        responseText.setText(jsonData);

//        LimitBuy homeData = gson.fromJson(jsonData, LimitBuy.class);
//        Logs.Debug("gg=======Json=="+jsonData);
//        Logs.Debug("gg=======jsondata=="+homeData.getStats()+"=="+homeData.getData().getGoods().get(homeData.getData().getGoods().size()-1).getName());
//        responseText.setText(homeData.getData().getBegintime()+"     "+homeData.getData().getGoods().get(homeData.getData().getGoods().size()-1).getName());
    }
}