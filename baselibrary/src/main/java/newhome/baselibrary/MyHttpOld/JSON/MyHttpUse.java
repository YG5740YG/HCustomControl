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
import newhome.baselibrary.MyHttpOld.MyHttps;
import newhome.baselibrary.PathAction;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.MyHttpOld.JSON.Data.DataType;
import newhome.baselibrary.MyHttpOld.JSON.Data.HomeData;
import newhome.baselibrary.MyHttpOld.JSON.Data.HomeDataNew;
import newhome.baselibrary.MyHttpOld.JSON.Interface.InterfaceHttpCallbackListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 20160330 on 2017/3/31 0031.
 */

public class MyHttpUse  extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private TextView responseText;
    Context context;
    public String reserveData;
    HomeData homeData1;
    LinkedHashMap<String,Object> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_httpconnection);
        sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response);
        params=new LinkedHashMap<>();
        context=this;
        sendRequest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            params.put("NewVersion",1+"");
            params.put("act","GetHome");
            params.put("cityid",530102+"");
            params.put("plat",1+"");
            params.put("uid","");
          MyHttps.Get(PathAction.TESTINTERNETURL,params, new InterfaceHttpCallbackListener() {
// NewVersion=1&act=GetHome&cityid=530102&plat=1&uid=
                @Override
                public void onFinish(Object response) {
                    Gson gson = new Gson();
//                    HomeData homeData=gson.fromJson(response.toString(),HomeData.class);
                    HomeDataNew homeData=gson.fromJson(response.toString(),HomeDataNew.class);
                    Logs.Debug("gg=======Json=="+response.toString());
//                    Logs.Debug("gg=======Jsondata=="+homeData.getStats());
                    Logs.Debug("gg=======Jsondata=="+homeData.getFreephone());
//                    responseText.setText(homeData.getStats()+"");
                    responseText.setText(homeData.getFreephone()+"");
                }
                @Override
                public void onError(Object e) {

                }
            });
        }
    }
    //调用 parseJSONWithGSON()方法来解析数据
    private void parseJSONWithGSON(String netUrl,LinkedHashMap<String,String>params, final InterfaceHttpCallbackListener listener) {
        HttpUtil.sendHttpRequest(netUrl,params, new InterfaceHttpCallbackListener() {
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
    public Object JsonToObject(String reserveData,DataType dataType){
        Gson gson= new Gson();
//        HomeData homeData=gson.fromJson(reserveData.toString(),dataType.getData().);
        return dataType.getData();
    }
    public void SetReserveData(String response){
        this.reserveData=response;
    }
    public String getReserveData(){
        return this.reserveData;
    }
}