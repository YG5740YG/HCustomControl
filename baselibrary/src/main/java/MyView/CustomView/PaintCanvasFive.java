package MyView.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

/**
 * Created by Administrator on 2017/4/21 0021.
 */
//Android在绘图时会先检查该画笔Paint对象有没有设置Xfermode，如果没有设置Xfermode，那么直接将绘制的图形覆盖Canvas对应位置原有的像素；如果设置了Xfermode，那么会按照Xfermode具体的规则来更新Canvas中对应位置的像素颜色。
//    画笔Paint已经设置Xfermode的值为PorterDuff.Mode.CLEAR，此时Android首先是在内存中绘制了这么一
// 个矩形，所绘制的图形中的像素称作源像素（source，简称src），所绘制的矩形在Canvas中对应位置的矩形
// 内的像素称作目标像素（destination，简称dst）。源像素的ARGB四个分量会和Canvas上同一位置处的目标像
// 素的ARGB四个分量按照Xfermode定义的规则进行计算，形成最终的ARGB值，然后用该最终的ARGB值更新目标像
// 素的ARGB值。本例中的Xfermode是PorterDuff.Mode.CLEAR，该规则比较简单粗暴，直接要求目标像素的ARGB\
// 四个分量全置为0，即(0，0，0，0)，即透明色，所以我们通过canvas.drawRect()在Canvas上绘制了一个透明
// 的矩形，由于Activity本身屏幕的背景时白色的，所以此处就显示了一个白色的矩形。
//两个布局重合
public class PaintCanvasFive extends View{
    private Paint paint;
    public PaintCanvasFive(Context context) {
        super(context);
        paint=new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);

        int canvasWidth = canvas.getWidth();
        int r = canvasWidth / 3;
        //正常绘制黄色的圆形
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(r, r, r, paint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//将画笔的PorterDuff模式设置为CLEAR。
//        paint.setColor(0xFF66AAFF);
        paint.setColor(Color.RED);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
        //最后将画笔去除Xfermode
        paint.setXfermode(null);
    }
}
/*
1.PorterDuff.Mode.CLEAR
  所绘制不会提交到画布上。
2.PorterDuff.Mode.SRC
   显示上层绘制图片
3.PorterDuff.Mode.DST
  显示下层绘制图片
4.PorterDuff.Mode.SRC_OVER
  正常绘制显示，上下层绘制叠盖。
5.PorterDuff.Mode.DST_OVER
  上下层都显示。下层居上显示。
6.PorterDuff.Mode.SRC_IN
   取两层绘制交集。显示上层。
7.PorterDuff.Mode.DST_IN
  取两层绘制交集。显示下层。
8.PorterDuff.Mode.SRC_OUT
 取上层绘制非交集部分。
9.PorterDuff.Mode.DST_OUT
 取下层绘制非交集部分。
10.PorterDuff.Mode.SRC_ATOP
 取下层非交集部分与上层交集部分
11.PorterDuff.Mode.DST_ATOP
 取上层非交集部分与下层交集部分
12.PorterDuff.Mode.XOR
  异或：去除两图层交集部分
13.PorterDuff.Mode.DARKEN
  取两图层全部区域，交集部分颜色加深
14.PorterDuff.Mode.LIGHTEN
  取两图层全部，点亮交集部分颜色
15.PorterDuff.Mode.MULTIPLY
  取两图层交集部分叠加后颜色
16.PorterDuff.Mode.SCREEN
  取两图层全部区域，交集部分变为透明色
 */
/*
Xfermode有三个子类 :
AvoidXfermode  指定了一个颜色和容差，强制Paint避免在它上面绘图(或者只在它上面绘图)。
PixelXorXfermode  当覆盖已有的颜色时，应用一个简单的像素异或操作。
PorterDuffXfermode  这是一个非常强大的转换模式，使用它，可以使用图像合成的16条Porter-Duff规则的任意一条来控制Paint如何与已有的Canvas
 */