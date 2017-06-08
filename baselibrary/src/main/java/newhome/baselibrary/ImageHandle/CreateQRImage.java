package newhome.baselibrary.ImageHandle;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

import newhome.baselibrary.Tools.Tools;

/**
 * Created by 20160330 on 2017/2/10.
 */
//要转换的地址或字符串,可以是中文
    //主要是调用ZXing库里面QRCodeWriter().encode的方法对我们传进去的URL进行编码，
    //二维码生成
public class CreateQRImage {
    String url;
    ImageView imageView;
    private static final String CODE = "utf-8";
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;
    public CreateQRImage(String url, ImageView imageView){
        this.url=url;
        this.imageView=imageView;
    }
    public void create(){
        try {
            //判断URL合法性
            if(url==null|| Tools.isEmpty(url)){
                return;
            }
            Hashtable<EncodeHintType,String>hints=new Hashtable<EncodeHintType,String>();
            hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix=new QRCodeWriter().encode(url,BarcodeFormat.QR_CODE, 100, 100, hints);
            int []pixels=new int[100*100];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y=0;y<100;y++){
                for(int x=0;x<100;x++){
                    if(bitMatrix.get(x,y)){
                        pixels[y*100+x]=0xff000000;
                    }else{
                        pixels[y * 100 + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap= Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels,0,100,0,0,100,100);
            //显示到一个ImageView上面
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){

        }
    }
}
