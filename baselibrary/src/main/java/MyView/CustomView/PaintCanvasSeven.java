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
//http://blog.csdn.net/iispring/article/details/50472485
public class PaintCanvasSeven extends View {
    private Paint paint;
    public PaintCanvasSeven(Context context) {
        super(context);
        paint=new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //主要的代码都放到了canvas.saveLayer()以及canvas.restoreToCount()之间，并且我将代码缩进了一下。当我们把代码写在canvas.saveXXX()与canvas.restoreXXX()之间时，建议把里面的代码缩进，这样的写法便于代码可读，当然代码缩进与否不是强制性的，也不会影响运行效果。
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
          int r = canvasWidth / 3;
        //正常绘制黄色的圆形
          paint.setColor(0xFFFFCC44);
          canvas.drawCircle(r, r, r, paint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
          paint.setColor(0xFF66AAFF);
          canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
        //最后将画笔去除Xfermode
          paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
/*
关于canvas绘图中的layer有以下几点需要说明：

canvas是支持图层layer渲染这种技术的，canvas默认就有一个layer，当我们平时调用canvas的各种drawXXX()方法时，其实是把所有的东西都绘制到canvas这个默认的layer上面。

我们还可以通过canvas.saveLayer()新建一个layer，新建的layer放置在canvas默认layer的上部，当我们执行了canvas.saveLayer()之后，我们所有的绘制操作都绘制到了我们新建的layer上，而不是canvas默认的layer。

用canvas.saveLayer()方法产生的layer所有像素的ARGB值都是(0，0，0，0)，即canvas.saveLayer()方法产生的layer初始时时完全透明的。

canvas.saveLayer()方法会返回一个int值，用于表示layer的ID，在我们对这个新layer绘制完成后可以通过调用canvas.restoreToCount(layer)或者canvas.restore()把这个layer绘制到canvas默认的layer上去，这样就完成了一个layer的绘制工作。

那你可能感觉到很奇怪，我们只是将绘制圆形与矩形的代码放到了canvas.saveLayer()和canvas.restoreToCount()之间，为什么不再像示例二那样显示白色的矩形了？

我们在分析示例二代码时知道了最终矩形区域的目标颜色都被重置为透明色(0,0,0,0)了，最后只是由于Activity背景色为白色，所以才最终显示成白色矩形。在本例中，我们在新建的layer上面绘制完成后，其实矩形区域的目标颜色也还是被重置为透明色(0,0,0,0)了，这样整个新建layer只有圆的3/4不是透明的，其余像素全是透明的，然后我们调用canvas.restoreToCount()将该layer又绘制到了Canvas上面去了。在将一个新建的layer绘制到Canvas上去时，Android会用整个layer上面的像素颜色去更新Canvas对应位置上像素的颜色，并不是简单的替换，而是Canvas和新layer进行Alpha混合，可参见此处链接。由于我们的layer中只有两种像素：完全透明的和完全不透明的，不存在部分透明的像素，并且完全透明的像素的颜色值的四个分量都为0，所以本例就将Canvas和新layer进行Alpha混合的规则简化了，具体来说：

如果新建layer上面某个像素的Alpha分量为255，即该像素完全不透明，那么Android会直接用该像素的ARGB值作为Canvas对应位置上像素的颜色值。
如果新建layer上面某个像素的Alpha分量为0，即该像素完全透明，在本例中Alpha分量为0的像素，其RGB分量也都为0，那么Android会保留Canvas对应位置上像素的颜色值。
这样当将新layer绘制到Canvas上时，完全不透明的3/4黄色圆中的像素会完全覆盖Canvas对应位置的像素，而由于在新layer上面绘制的矩形区域的像素ARGB都为(0,0,0,0)，所以最终Canvas上对应矩形区域还是保持之前的背景色，这样就不会出现白色的矩形了。

大部分情况下，我们想要本例中实现的效果，而不是想要示例二中形成的白色矩形，所以大部分情况下在使用PorterDuffXfermode时都是结合canvas.saveLayer()、canvas.restoreToCount()的，将关键代码写在这两个方法之间。
 */
/*
网络上的图片显示效果中，黄色圆圈
// create a bitmap with a circle, used for the "dst" image
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w*3/4, h*3/4), p);
        return bm;
    }
//    蓝色圆圈
   static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
        return bm;
    }
 */