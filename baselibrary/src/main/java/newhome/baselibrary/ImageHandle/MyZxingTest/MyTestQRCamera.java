package newhome.baselibrary.ImageHandle.MyZxingTest;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Timer;
import java.util.TimerTask;

import newhome.baselibrary.ImageHandle.NewMyZxing.camear.PlanarYUVLuminanceSource;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/15.
 */
//zxing 解码
public class MyTestQRCamera extends BaseActivity {
    /** Called when the activity is first created. */
    private SurfaceView sfvCamera;
    private SFHCamera sfhCamera;
    private ImageView imgView;
    private View centerView;
    private TextView txtScanResult;
    private Timer mTimer;
    private MyTimerTask mTimerTask;
    // 按照标准HVGA
    final static int width = 480;
//    final static int width = 300;
//    final static int height = 180;
    final static int height = 320;
    int dstLeft=0, dstTop, dstWidth, dstHeight;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_mytestcamera);
        this.setTitle("Android条码/二维码识别Demo-----hellogv");
//        CameraManager.init(getApplication());
        imgView = (ImageView) this.findViewById(R.id.ImageView01);
        centerView = (View) this.findViewById(R.id.centerView);
        sfvCamera = (SurfaceView) this.findViewById(R.id.sfvCamera);
        sfhCamera = new SFHCamera(sfvCamera.getHolder(), width, height,
                previewCallback);
        txtScanResult=(TextView)this.findViewById(R.id.txtScanResult);
        // 初始化定时器
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        mTimer.schedule(mTimerTask, 0, 80);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (dstLeft == 0) {//只赋值一次
                dstLeft = centerView.getLeft() * width
                        / getWindowManager().getDefaultDisplay().getWidth();
                dstTop = centerView.getTop() * height
                        / getWindowManager().getDefaultDisplay().getHeight();
                dstWidth = (centerView.getRight() - centerView.getLeft())* width
                        / getWindowManager().getDefaultDisplay().getWidth();
                dstHeight = (centerView.getBottom() - centerView.getTop())* height
                        / getWindowManager().getDefaultDisplay().getHeight();
            }
            sfhCamera.AutoFocusAndPreviewCallback();
        }
    }
    /**
     *  自动对焦后输出图片
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera arg1) {
            //取得指定范围的帧的数据
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    data, width, height, dstLeft, dstTop, dstWidth, dstHeight);
            //取得灰度图
            Bitmap mBitmap = source.renderCroppedGreyscaleBitmap();
            //显示灰度图
            imgView.setImageBitmap(mBitmap);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader = new MultiFormatReader();
            try {
                Result result = reader.decode(bitmap);
                String strResult = "BarcodeFormat:"
                        + result.getBarcodeFormat().toString() + "  text:"
                        + result.getText();
                txtScanResult.setText(strResult);
            } catch (Exception e) {
                txtScanResult.setText("Scanning");
            }
        }
    };
    @Override
    public void findViewById() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}
