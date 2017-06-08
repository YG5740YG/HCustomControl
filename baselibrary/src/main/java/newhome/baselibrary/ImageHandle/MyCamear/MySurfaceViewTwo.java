package newhome.baselibrary.ImageHandle.MyCamear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 20160330 on 2017/3/3.
 */
//　这样就是实现了拍照的功能，那么怎样要图片保存呢？那么这是就需要在那个参数中的jpeg的
//        方法里面进行处理了，那个方法的data参数，就是相片的数据。
//        我们通过BitmapFactory.decodeByteArray(data, 0, data.length)来获得图片并通过io处理，将图片保存到想要保存的位置
//        下面这段代码，是将照片保存到/sdcard/wjh.jpg;并把一些没有用到的代码全部删掉，剩下一些必须的代码
    /**
     * Created by 20160330 on 2017/3/3.
     */
    public class MySurfaceViewTwo extends SurfaceView implements SurfaceHolder.Callback{
        SurfaceHolder holder;
        Camera myCamera;
        private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                // TODO Auto-generated method stub
                Log.d("ddd", "shutter");
            }
        };
        private Camera.PictureCallback raw = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // TODO Auto-generated method stub
                Log.d("ddd", "raw");
            }
        };
        private Camera.PictureCallback jpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // TODO Auto-generated method stub
                try
                {
                    Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);//把二进制图片转换成jpg格式文件
                    File file = new File("/sdcard/wjh.jpg");
                    BufferedOutputStream bos  = new BufferedOutputStream(new FileOutputStream(file));
                    bm.compress(Bitmap.CompressFormat.JPEG,100,bos);//将图片压缩到流中
                    bos.flush();
                    bos.close();
                }catch(Exception e)

                {

                    e.printStackTrace();
                }
                Log.d("ddd","jpeg");
            }
        };
        public MySurfaceViewTwo(Context context)
        {
            super(context);
            holder = getHolder();//获得surfaceHolder引用
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型
        }
        public void tackPicture()
        {
            myCamera.takePicture(null,null,null);
        }
        public void voerTack()
        {
            myCamera.startPreview();
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            myCamera.startPreview();
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            if(myCamera == null)
            {
                myCamera = Camera.open();//开启相机,不能放在构造函数中，不然不会显示画面.
                myCamera.setDisplayOrientation(90);//需要把相机旋转90度才能正常显示
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

        }

    }

