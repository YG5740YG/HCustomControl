package newhome.baselibrary.ImageHandle.Image;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;


/**
 * Created by 20160330 on 2017/4/5 0005.
 */
//图片灰色过来，布局颜色值改变，背景色改变
public class MyImage extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    ListView mlistView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myimage);
        findViewById();
        mcontext=this;
        changeImageColor(imageView);
    }

    @Override
    public void findViewById() {
        imageView=(ImageView) findViewById(R.id.myImageTest);
    }
    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
    public void changeImageColor(ImageView imageView){
        LightingColorFilter lightingColorFilter=new LightingColorFilter(Color.TRANSPARENT,getResources().getColor(R.color.es_rf));
        imageView.getBackground().setColorFilter(lightingColorFilter);//更改图片的背景色
//        LightingColorFilter lightingColorFilter1=new LightingColorFilter(Color.TRANSPARENT,getResources().getColor(R.color.es_g));
//        imageView.getDrawable().setColorFilter(lightingColorFilter1);//更改图片的颜色,整个图片都会变成色值的颜色值，如果是有轮廓的图片则使用后会显示不出轮廓

//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
//        imageView.getDrawable().setColorFilter(cf);//ColorMatrixColorFilter颜色过滤（离线用户的头像处理）


        //这个求值器用来执行计算用整形表示的颜色的差值,一般用于两个Drawable
//        PorterDuffColorFilter  用于遮罩，透明度
        ArgbEvaluator argbEvaluator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            argbEvaluator = new ArgbEvaluator();
        }
        int   startColor = ActivityCompat.getColor(MyImage.this,R.color.colorPrimary);//黄色
        int   endColor   = Color.WHITE;
        //根据fraction计算出开始和结束中间的色值
        int calcColor = 0;//第一个参数0-255，255是全不透明
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            calcColor = (int) argbEvaluator.evaluate(180, startColor, endColor);
        }
        ColorFilter colorFilter = new PorterDuffColorFilter(calcColor, PorterDuff.Mode.DARKEN);// //PorterDuffColorFilter  应用颜色过滤器
        imageView.getDrawable().setColorFilter(colorFilter);

        /*
        效果图：drawable/porterfilter
        //http://www.open-open.com/lib/view/open1488441181432.html
        在Android的PorterDuff.Mode类中列举了他们制定的规则：

android.graphics.PorterDuff.Mode.SRC:只绘制源图像

android.graphics.PorterDuff.Mode.DST:只绘制目标图像

android.graphics.PorterDuff.Mode.DST_OVER:在源图像的顶部绘制目标图像

android.graphics.PorterDuff.Mode.DST_IN:只在源图像和目标图像相交的地方绘制目标图像

android.graphics.PorterDuff.Mode.DST_OUT:只在源图像和目标图像不相交的地方绘制目标图像

android.graphics.PorterDuff.Mode.DST_ATOP:在源图像和目标图像相交的地方绘制目标图像，在不相交的地方绘制源图像

android.graphics.PorterDuff.Mode.SRC_OVER:在目标图像的顶部绘制源图像

android.graphics.PorterDuff.Mode.SRC_IN:只在源图像和目标图像相交的地方绘制源图像

android.graphics.PorterDuff.Mode.SRC_OUT:只在源图像和目标图像不相交的地方绘制源图像

android.graphics.PorterDuff.Mode.SRC_ATOP:在源图像和目标图像相交的地方绘制源图像，在不相交的地方绘制目标图像

android.graphics.PorterDuff.Mode.XOR:在源图像和目标图像重叠之外的任何地方绘制他们，而在不重叠的地方不绘制任何内容

android.graphics.PorterDuff.Mode.LIGHTEN:获得每个位置上两幅图像中最亮的像素并显示

android.graphics.PorterDuff.Mode.DARKEN:获得每个位置上两幅图像中最暗的像素并显示

android.graphics.PorterDuff.Mode.MULTIPLY:将每个位置的两个像素相乘，除以255，然后使用该值创建一个新的像素进行显示。结果颜色=顶部颜色*底部颜色/255

android.graphics.PorterDuff.Mode.SCREEN:反转每个颜色，执行相同的操作（将他们相乘并除以255），然后再次反转。结果颜色=255-(((255-顶部颜色)*(255-底部颜色))/255)
         */
    }
}

