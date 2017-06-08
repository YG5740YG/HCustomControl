package newhome.baselibrary.MyHttpOld.JSON;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.LinkedHashMap;

import newhome.baselibrary.MyHttpOld.HttpUtil;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.MyHttpOld.JSON.Data.LimitBuy;
import newhome.baselibrary.MyHttpOld.JSON.Interface.InterfaceHttpCallbackListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 20160330 on 2017/3/30 0030.
 */

public class JsonCallBackTestView extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private TextView responseText;
    Context context;
    public String reserveData;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
// 在这里进行UI操作，将结果显示到界面上
//                    responseText.setText(response);
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
        context=this;
        sendRequest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            LinkedHashMap<String,String>params=new LinkedHashMap<>();
            HttpUtil.sendHttpRequest("https://m.9ji.com/app/2_0/ProductSearch.aspx?act=GetLimitBuy&cityid=530102&t=1490865077224", params,new InterfaceHttpCallbackListener() {
                @Override
                public void onFinish(Object response) {
                    //从其他线程返回的数据，在主线程中使用，使用观察监听
                    Observable observable= Observable.just(response.toString())
                            .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                            .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
                    Subscriber<String> mySubscriber1 = new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {
//                            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                            result(e.toString(),false);
                        }
                        @Override
                        public void onNext(String o) {
//                            parseJSONWithGSON(o.toString());
                            result(o,true);

                        }
                    };
                    observable.subscribe(mySubscriber1);
//                    Message message = new Message();
//                    message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
//                    message.obj = response.toString();
//                    handler.sendMessage(message);
//                    parseJSONWithGSON(response);//直接调用无法改变UI
                }
                @Override
                public void onError(Object e) {

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

//        HomeData homeData = gson.fromJson(jsonData, HomeData.class);
//        Logs.Debug("gg=======Json=="+jsonData);
//        Logs.Debug("gg=======jsondata=="+homeData.getSearchKey()+"=="+homeData.getIcon());
        LimitBuy homeData = gson.fromJson(jsonData, LimitBuy.class);
        Logs.Debug("gg=======Json=="+jsonData);
        Logs.Debug("gg=======jsondata=="+homeData.getData().getBegintime()+"=="+homeData.getData().getGoods().get(homeData.getData().getGoods().size()-1).getName());
        responseText.setText(homeData.getData().getBegintime()+"     "+homeData.getData().getGoods().get(homeData.getData().getGoods().size()-1).getName());
//        List<App> appList = gson.fromJson(jsonData, new
//                TypeToken<List<App>>() {}.getType());
//        for (App app : appList) {
//            Log.d("MainActivity", "id is " + app.getId());
//            Log.d("MainActivity", "name is " + app.getName());
//            Log.d("MainActivity", "version is " + app.getVersion());
//        }
    }
    public boolean result(String respose,boolean zhuagntai){
        if(zhuagntai){
            parseJSONWithGSON(respose);
        }
        return zhuagntai;
    }
    public void SetReserveData(String response){
       this.reserveData=response;
    }
    public String getReserveData(){
        return this.reserveData;
    }
    //如果json简单，只需要调用
    //{"name":"Tom","age":20}
//Gson gson = new Gson();
//    Person person = gson.fromJson(jsonData, Person.class);
//    如果需要解析的是一段 JSON 数组会稍微麻烦一点，我们需要借助 TypeToken 将期望解析成的数据类型传入到 fromJson()方法中
//    List<Person> people = gson.fromJson(jsonData, new TypeToken<List<Person>>(){}.getType());
}



