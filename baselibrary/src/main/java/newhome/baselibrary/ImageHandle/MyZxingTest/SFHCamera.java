package newhome.baselibrary.ImageHandle.MyZxingTest;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/2/15.
 */
//SFHCamera.java是Camera控制类
public class SFHCamera implements SurfaceHolder.Callback {
    private SurfaceHolder holder = null;
    private Camera mCamera;
    private int width,height;
    private Camera.PreviewCallback previewCallback;

    public SFHCamera(SurfaceHolder holder,int w,int h,Camera.PreviewCallback previewCallback) {
        this.holder = holder;
        this.holder.addCallback(this);
        this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        width=w;
        height=h;
        this.previewCallback=previewCallback;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mCamera = Camera.open();//启动服务
        try {
            // 旋转90°,使相机换面正确选中
             mCamera.setDisplayOrientation(90);
//
//            if (Integer.parseInt(Build.VERSION.SDK) >= 8)
//            {// 判断系统版本是否大于等于2.2
//                 setDisplayOrientation(mCamera, 90);// 旋转90°，前提是当前页portrait，纵向}
// else {// 系统版本在2.2以下的采用下面的方式旋转if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
// { params.set("orientation", "portrait");params.set("rotation", 90); }
// if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
// { params.set("orientation", "landscape"); params.set("rotation", 90); } }
            mCamera.setPreviewDisplay(holder);//设置预览
            Log.e("Camera","surfaceCreated");
        } catch (IOException e) {
            mCamera.release();//释放
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(width, height);//设置尺寸
        parameters.setPictureFormat(PixelFormat.JPEG);//设置图片的文件格式
        mCamera.setParameters(parameters);
        mCamera.startPreview();//开始预览
        Log.e("Camera","surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();//停止预览
        mCamera = null;
        Log.e("Camera","surfaceDestroyed");
    }
    /**
     * 自动对焦并回调Camera.PreviewCallback
     */
    public void AutoFocusAndPreviewCallback()
    {
        if(mCamera!=null)
            mCamera.autoFocus(mAutoFocusCallBack);
    }
    /**
     * 自动对焦
     */
    private Camera.AutoFocusCallback mAutoFocusCallBack = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {  //对焦成功，回调Camera.PreviewCallback
                Logs.Debug("gg==========12");
                mCamera.setOneShotPreviewCallback(previewCallback);
            }
        }
    };
}
