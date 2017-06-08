package MyView.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class PaintCanvaSix extends View {
    private Paint paint;
    public PaintCanvaSix(Context context) {
        super(context);
        paint=new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);
//我们重写了View的onDraw方法，首先将View的背景色设置为绿色，然后绘制了一个黄色的圆形，然后再绘制一个蓝色的矩形
        int canvasWidth = canvas.getWidth();
        int r = canvasWidth / 3;
        //绘制黄色的圆形
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(r, r, r, paint);
        //绘制蓝色的矩形
        paint.setColor(Color.RED);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
    }
}
