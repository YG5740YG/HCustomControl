package newhome.baselibrary.MyViewI;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

//网络请求，开线程回调，网络请求通常都是属于耗时操作，调用时会使得主线程被阻塞住，所以需要使用开启线程，并数据回调
public interface HttpCallbackListener {
    void onFinish(Object response);
    void onError(Object e);
}
