package newhome.baselibrary.MyHttp.OkHttp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import newhome.baselibrary.MyViewI.DataResponseT;
import newhome.baselibrary.Tools.Logs;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/27 0027.
 */
//http://blog.csdn.net/lmj623565791/article/details/47911083
public class MyHttp {
    //创建okHttpClient对象
    static OkHttpClient mOkHttpClient = new OkHttpClient();
    static Request mRequest;
    static String mPath;
    static Call mCall;
    static FormEncodingBuilder mBuilder;
    static String mResponse;
    static LinkedHashMap<String,String> mParams;
    MultipartBuilder mMultipartBuilder;
    public static final int SHOW_RESPONSE = 0;
    /**
     * 参数初始化
     * @param mPath
     * @param params
     * @return
     */
    public MyHttp myHttp(String mPath, final LinkedHashMap<String,String> params){
        MyHttp myHttp=new MyHttp();
        this.mPath=mPath;
        this.mParams=params;
        return myHttp;
    }
    /**
     * get请求
     * @param dataResponse
     */
    public void get(final DataResponseT dataResponse){
        //创建一个Request
        Logs.Debug("gg=======mPath=="+mPath);
        if(mParams.size()>0){
            mPath=mPath+"?";
            for (String key:
                 mParams.keySet()) {
                mPath=mPath+key+"="+mParams.get(key)+"&";
            }
           mPath= mPath.substring(0,mPath.length()-1);
        }
        Logs.Debug("gg=======mPath==1="+mPath);
        mRequest = new Request.Builder()
                .url(mPath)
                .build();
        mCall = mOkHttpClient.newCall(mRequest);
        //请求加入调度
        mCall.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                Logs.Debug("gg========onFaile=="+e.toString());
            }
            @Override
            public void onResponse(final Response response) throws IOException
            {
                Logs.Debug("gg========success==");
                mResponse   =response.body().string();
//                byte []bytes=response.body().bytes();
//                InputStream inputStream=response.body().byteStream();
                //String htmlStr =  response.body().string();  //支持大文件下载
                //使用handle回到主线程
//                Message message = new Message();
//                message.what = SHOW_RESPONSE;
//                message.obj = mResponse;
//                handler.sendMessage(message);

                //从其他线程返回的数据，在主线程中使用，使用观察监听
                obs(mResponse,dataResponse);
            }
        });
    }
    /**
     * Post请求
     * @param dataResponse
     */
    public void post(final DataResponseT dataResponse){
        mBuilder = new FormEncodingBuilder();
        if(mParams.size()>0) {
            for (String key:
                    mParams.keySet()) {
                mBuilder.add(key,mParams.get(key).toString());
            }
        }
        //创建一个Request
        mRequest = new Request.Builder()
                .url(mPath)
                .post(mBuilder.build())
                .build();
        mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponse=response.body().string();
                obs(mResponse,dataResponse);
            }
        });
    }
    /**
     * Post请求(input),带参数,一次性上传多个文件，可能会有错
     * @param dataResponse
     */
    public void postFile(final DataResponseT dataResponse){
        mMultipartBuilder=new MultipartBuilder().type(MultipartBuilder.FORM);
        if(mParams.size()>0) {
            for (String key:
                    mParams.keySet()) {
                if(!(mParams.get(key).endsWith(".png")||mParams.get(key).endsWith(".jpg")||mParams.get(key).endsWith(".txt"))) {
                    mMultipartBuilder
                            .addPart(Headers.of(
                                    "Content-Disposition",
                                    "form-data; name=\\"+key+"\\"),
                                    RequestBody.create(null,mParams.get(key)));
                }else{
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), new File(mParams.get(key)));
                    mMultipartBuilder
                            .addPart(Headers.of(
                                    "Content-Disposition",
                                    "form-data; name=\"mFile\";filename=\\"+key+"\\"),//此处key值为上传到服务器文件名，需要带后缀
                                    fileBody);
                }
            }
        }
        RequestBody mRequestBody = mMultipartBuilder.build();
        //创建一个Request
        mRequest = new Request.Builder()
                .url(mPath)
                .post(mRequestBody)
                .build();
        mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponse=response.body().string();
                obs(mResponse,dataResponse);
            }
        });
    }
    /**
     * Post请求(input),不带参数,且只传一个文件,mParam只有一个参数
     * @param dataResponse
     */
    public void postFileOne(final DataResponseT dataResponse){
        mMultipartBuilder=new MultipartBuilder().type(MultipartBuilder.FORM);
        if(mParams.size()>0) {
            for (String key:
                    mParams.keySet()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), new File(mParams.get(key)));
                mMultipartBuilder
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"mFile\";filename=\\"+key+"\\"),
                                fileBody);
            }
        }
        RequestBody mRequestBody = mMultipartBuilder.build();
        //创建一个Request
        mRequest = new Request.Builder()
                .url(mPath)
                .post(mRequestBody)
                .build();
        mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(Response response) throws IOException {
                mResponse=response.body().string();
                obs(mResponse,dataResponse);
            }
        });
    }
    /**
     * 数据返回，主线程使用
     * @param respose
     * @param dataResponse
     */
    public void obs(Object respose,final DataResponseT dataResponse){
        Observable observable = Observable.just(respose)
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
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
            }
        }
    };
}
