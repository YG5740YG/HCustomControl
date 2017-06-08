package newhome.baselibrary.MyHttp;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.observer.ConcreteWatched;
import newhome.baselibrary.observer.ConcreteWatcher;
import newhome.baselibrary.observer.MyContentData;

/**
 * Created by 20160330 on 2017/1/23.
 */

public class MyHttp {
    private OkHttpClient client;
    private Response response;
    private String responseOk;
    private Request request;
    //实例一个被观察者
    ConcreteWatched concreteWatched;
    ConcreteWatcher concreteWatcher;
    MyContentData myContentData;
    Call call;
    public MyHttp(String Neturl,DataResponse dr){//url = "http://www.weather.com.cn/adat/cityinfo/101190404.html";//请求设置
        client = new OkHttpClient();
        request = new Request.Builder().url(Neturl).build();
        concreteWatched=new ConcreteWatched();
        concreteWatcher=new ConcreteWatcher();
        concreteWatched.add(concreteWatcher);
        myContentData=new MyContentData();
        newThreadRun(dr);
    }
    private void newThreadRun(final DataResponse dr) {
        new Thread(new Runnable() {//因为网络请求是耗时操作，所以要开启一个子线程，放在子线程中请求。
            @Override
            public void run() {
                try {
                    //方法一
                    response = client.newCall(request).execute();
//                    if (response.isSuccessful()) {
//                        // 返回数据
//                        responseOk = response.body().string();
//                        Logs.Debug(getClass()+"网络返回数据：：：：：：：：：：："+responseOk);
//                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                    call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {//网络请求失败
//                            myContentData.setData(e.toString());
//                            myContentData.setflage(false);
                            dr.onFail(e.toString());
                        }
                        @Override
                        public void onResponse(Response response) throws IOException {//网络请求成功
                            if (response.isSuccessful()) {
                                // 返回数据
                                Logs.Debug("scucce========");
                                responseOk = response.body().string();
                                Logs.Debug(getClass() + "网络返回数据：：：：：：：：：：：" + responseOk);
//                                myContentData.setData(responseOk);
//                                myContentData.setflage(true);
                                dr.onSucc(responseOk);
                            }else {
                                Logs.Debug("defalut========");
//                                myContentData.setData("数据请求失败！");
//                                myContentData.setflage(false);
                                dr.onFail("数据请求失败！");
                            }
                        }
                    });

            }
        }).start();
    }
}
