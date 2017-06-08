package newhome.baselibrary.ImageHandle;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * Created by 20160330 on 2017/2/10.
 */
//主要是调用ZXing库里面MultiFormatWriter().encode的方法对我们传进去的URL进行编码，
//一维码生成
public class CreateQRImageOne {
    String url;
    ImageView imageView;
    int height;
    int width;
    private static final String CODE = "utf-8";
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;
    public CreateQRImageOne(String url, ImageView imageView){
        this.url=url;
        this.imageView=imageView;
        this.height=100;
        this.width=200;
    }
    public void create(){
        try {
            // 文字编码
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, CODE);
            //一维码生成.  //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix=new MultiFormatWriter().encode(url, BarcodeFormat.CODE_128,width,height,hints);
            int []pixels=new int[width*height];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    if(bitMatrix.get(x,y)){
                        pixels[y*width+x]=0xff000000;
                    }else{
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap= Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels,0,width,0,0,width,height);
            //显示到一个ImageView上面
            imageView.setImageBitmap(bitmap);

        }catch (Exception e){

        }
    }
}
