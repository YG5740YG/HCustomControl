package newhome.baselibrary.MyHttp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.UUID;

import newhome.baselibrary.CommonAction.CommonUseAction;
import newhome.baselibrary.CommonAction.HttpAction;
import newhome.baselibrary.GenericParadigm.ClassInfo;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.MyViewI.DataResponseT;
import newhome.baselibrary.MyViewI.HttpCallbackListener;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.MyTools;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static newhome.baselibrary.CommonAction.CommonUseAction.POST;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class UseHttp {
    String flage;
    String url;
    LinkedHashMap<String, String>params;
    String  imagePath;
    public UseHttp(String flage, String url, LinkedHashMap<String, String>params, final DataResponseT dataResponse){
        this.flage=flage;
        this.url=url;
        this.params=params;
        switch (flage){
            case "POST":
               Post(dataResponse);
            break;
            case "GET":
                Get(dataResponse);
                break;
            case "LOADFILE":
                GetNetPic(dataResponse);
                break;
        }
    }
    public UseHttp(String flage, String url, LinkedHashMap<String, String>params,String imagePath, final DataResponseT dataResponse){
        this.flage=flage;
        this.url=url;
        this.params=params;
        this.imagePath=imagePath;
        UpLoadeFile(dataResponse);
    }
    //post请求
    private void Post(final DataResponseT dataResponse){
        HttpUtil.sendHttpRequestPost(url, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final Object response) {
                //从其他线程返回的数据，在主线程中使用，使用观察监听
                Observable observable = Observable.just(response.toString())
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
                Subscriber<String> mySubscriber1 = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
//                            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                        dataResponse.onFail(e.toString());
                    }
                    @Override
                    public void onNext(String o) {
//                            parseJSONWithGSON(o.toString());
                        dataResponse.onSucc(o);
                    }
                };
                observable.subscribe(mySubscriber1);
            }
            @Override
            public void onError(Object e) {

            }
        });
    }
    //get请求
    private void Get(final DataResponseT dataResponse){
        HttpUtil.sendHttpRequestGet(url, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final Object response) {
                //从其他线程返回的数据，在主线程中使用，使用观察监听
                Observable observable = Observable.just(response.toString())
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
                Subscriber<String> mySubscriber1 = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
//                            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                        dataResponse.onFail(e.toString());
                    }
                    @Override
                    public void onNext(String o) {
//                            parseJSONWithGSON(o.toString());
                        dataResponse.onSucc(o);
                    }
                };
                observable.subscribe(mySubscriber1);
            }
            @Override
            public void onError(Object e) {

            }
        });
    }
    //获取网络图片
    private void GetNetPic(final DataResponseT dataResponse) {
        HttpUtil.LoadAndSaveImage(url, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final Object response) {
                //从其他线程返回的数据，在主线程中使用，使用观察监听
                Observable observable = Observable.just(response)
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
                Subscriber<Object> mySubscriber1 = new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dataResponse.onFail(e.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        dataResponse.onSucc(o);
                    }
                };
                observable.subscribe(mySubscriber1);
            }

            @Override
            public void onError(Object e) {

            }
        });
    }
        //上传文件
    private void UpLoadeFile(final DataResponseT dataResponse){
        HttpUtil.UpLoadeFile(url, imagePath, params,new HttpCallbackListener() {
            @Override
            public void onFinish(final Object response) {
                //从其他线程返回的数据，在主线程中使用，使用观察监听
                Observable observable = Observable.just(response)
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
                Subscriber<Object> mySubscriber1 = new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        dataResponse.onFail(e.toString());
                    }
                    @Override
                    public void onNext(Object o) {
                        dataResponse.onSucc(o);
                    }
                };
                observable.subscribe(mySubscriber1);
            }
            @Override
            public void onError(Object e) {

            }
        });
    }
    //上传文件1
}
