package newhome.baselibrary.ImageHandle.MyCamear;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by 20160330 on 2017/3/3.
 */
//（1）首先我们要自己创建一个照相，必须考虑用什么控件显示照相机中的预览效果，显然android已经帮我们做好了选择，那就是
//        SurfaceView,而控制SurfaceView则需要一个surfaceHolder,他是系统提供的一个用来设置surfaceView的一个对象，
//      而它通过surfaceView.getHolder()这个方法来获得。而Camera提供一个setPreviewDisplay(SurfaceHolder)的方法来连接
//        surfaceHolder,并通过他来控制surfaceView,而我们则使用android的Camera类提供了startPreview()和stopPreview()来开启和关闭预览.
//        关系如下：
//        Camera -- -->SurfaceHolder------>SurfaceView.
//        （2）知道怎么预览，当然也要知道怎么开启相机.Camera.open()这是个静态方法，如果相机没有别人用着，则会返回一个 相机引用，如果被人用着，则会抛出异常。很奇怪的是，这个方法，不能随便放，如放在构造方法或者onCreate()方法中，都会照成没有预览效果.
//        （3）
//        SurfaceHolder.Callback,这是个holder用来显示surfaceView 数据的接口,他分别必须实现3个方法
//        surfaceCreated（）这个方法是surface 被创建后调用的
//        surfaceChanged()这个方法是当surfaceView发生改变后调用的
//        surfaceDestroyed()这个是当surfaceView销毁时调用的.
//        surfaceHolde通过addCallBack()方法将响应的接口绑定到他身上.
//        surfaceHolder还必须设定一个setType()方法，查看api的时候，发现这个方法已经过时，但是没有写，又会报错。。各种奇怪。
//        （4）
//        我用以上知识写了一个MySurfaceView类，他继承于SurfaceView,并在里面实现了照相机的预览功能.这个我觉得最简单的照相机预览代码:
//        MySurfaceView.java
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    SurfaceHolder holder;
    Camera myCamera;
    public MySurfaceView(Context context)
    {
        super(context);
        holder = getHolder();//获得surfaceHolder引用
        holder.addCallback(this);//绑定回调
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,  int height) {
        myCamera.startPreview();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if(myCamera == null)
        {
            myCamera = Camera.open();//开启相机,不能放在构造函数中，不然不会显示画面.
            try {
                myCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        myCamera.stopPreview();//停止预览
        myCamera.release();//释放相机资源
        myCamera = null;
        Log.d("ddd", "4");
        Log.d("ddd", "4");
    }
}
