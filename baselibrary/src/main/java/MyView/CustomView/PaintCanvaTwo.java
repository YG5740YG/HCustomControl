package MyView.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;

/**
 * Created by Administrator on 2017/4/21 0021.
 */
//https://juejin.im/entry/57b65a565bbb50005b66e26e
//中间向四周发散
public class PaintCanvaTwo extends View{
    private Paint mPaint;
    private Context mContext;
    private Shader mShader;

    public PaintCanvaTwo(Context context) {
        super(context);
        init();
    }

    private void init() {
        mShader = new RadialGradient(150, 150, 140, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setShader(mShader);
        mPaint.setStyle(Paint.Style.FILL);
        /*
        drawCircle(float cx,float cy,float radius,Paint paint)：
cx：圆心点x轴坐标。
cy：圆心点y轴坐标。
radius：圆的半径。
paint：绘制圆形所用画笔。
         */
//        canvas.drawCircle(150, 150, 140, mPaint);//绘制圆形
//        mPaint.setTextSize(30);
//        canvas.drawText("Airsaid", 150, 150, mPaint);//绘制文字
        //drawLine(float startX, float startY,float stopX,float stopY,Paint paint)：
        //startX：开始x坐标。 startY：开始Y坐标。 stopX：结束x坐标。 stopY：结束Y坐标。 paint：绘制直线所用画笔。
//        canvas.drawLine(0,0,200,0,mPaint);//绘制线条
//        drawLines(float[] pts, Paint paint)：一般绘制多条直线。
//        pts：坐标点数据的集合，每４个为一组绘制一条直线。
//        paint：绘制直线所用画笔。
//        mPaint.setStrokeWidth(10);
//        float pts[] = {0, 10, getWidth(), 10, 0, 50, getWidth(), 50};
//        canvas.drawLines(pts, mPaint);//绘制多条线条
//        drawLines(float[] pts, int offset, int count, Paint paint)：有选择的绘制多条直线。
//        pts：坐标点数据的集合，每４个为一组绘制一条直线。offset：跳过的数据个数，跳过的数据将不参与绘制过程。conunt：实际参与绘制的数据个数。paint：绘制直线所用画笔。
//        mPaint.setStrokeWidth(10);
//        float pts[] = {0, 10, getWidth(), 10, 0, 50, getWidth(), 50};
//        canvas.drawLines(pts, 4, 4, mPaint);//跳过前四个参数
//        drawPoint(float x, float y,Paint paint)：绘制一个点。
//        x：点的x坐标。  y：点的y坐标。  paint：绘制点所用画笔。
//        mPaint.setColor(Color.RED);
//        mPaint.setStrokeWidth(20);
//        canvas.drawPoint(100, 100, mPaint);
//        drawPoints(float[] pts, Paint paint)：绘制多个点。
//        pts：坐标点的数据集合，每两个为一组绘制一个点。
//        float pts[] = {100, 100, 200, 100, 300, 100};
//        mPaint.setStrokeWidth(20);
//        canvas.drawPoints(pts, mPaint);
//        drawPoints(float[] pts, int offset, int count, Paint paint)： 有选择的绘制多个点。
//        pts：参数同上，其他参数同绘制多条线一样。
//        float pts[] = {100, 100, 200, 100, 300, 100};
//        mPaint.setStrokeWidth(20);
//        canvas.drawPoints(pts,2,4, mPaint);
        //drawRect(Rect r, Paint paint)：根据传入的Rect绘制矩形。
//        drawRect(RectF rect, Paint paint)：根据传入的RectF绘制矩形。
//        drawRect(float left, float top, float rigth, float bottom, Paint paint)：直接传入矩形的四个点来绘制矩形。
//        Rect rect = new Rect(100, 30, 200, 100);
//        canvas.drawRect(rect, mPaint);
//        RectF rectF = new RectF(300, 30, 400, 100);
//        canvas.drawRect(rectF, mPaint);
//        canvas.drawRect(500, 30, 600, 100, mPaint);//绘制矩形
/*
drawRoundRect(RectF rect, float rx, float ry, Paint paint)： 根据传入的RectF绘制圆角矩形。
rect：要绘制的矩形。
rx：x轴圆角椭圆半径。
ry：y轴圆角椭圆半径。
drawRoundRect(float left, float top, float rigth, float bottom, float rx, float ry, Paint paint)：直接传入矩形的四个点来绘制圆角矩形，从API21开始提供。
参数同上。
 */
//        RectF rectF = new RectF(0, 0, 300, 100);
//        canvas.drawRoundRect(rectF, 30f, 30f, mPaint);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawRoundRect(400f, 0f, 700f, 100f, 30f, 30f, mPaint);
//        }//绘制圆角矩形
        /*
        drawOval(RectF oval, Paint paint)：根据矩形对象绘制椭圆。
oval：矩形对象。椭圆根据该矩形对象生成，以矩形的长作为椭圆的x轴，宽为y轴。
drawOval(float left, float top, float rigth, float bottom, Paint paint)：直接传入矩形的四个点来绘制椭圆，从API21开始提供。
         */
//        RectF rectF = new RectF(50, 0, 200, 100);
//        canvas.drawOval(rectF, mPaint);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(300, 0, 450, 100, mPaint);
//        }
        /*
        drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)：
oval：矩形对象。
startAngle：弧形开始的角度。
sweepAngle：弧形持续的角度。
useCenter：是否有弧形的两边。
paint：绘制弧形的画笔。
         */
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(50, 0, 200, 100);
        canvas.drawArc(rectF, 0f, 180f, false, mPaint);
        RectF rectF2 = new RectF(250, 0, 400, 100);
        canvas.drawArc(rectF2, 0f, 180f, true, mPaint);
        RectF rectF3 = new RectF(450, 0, 600, 100);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rectF3, 0f, 180f, true, mPaint);

//        setStyle()函数可以设置如下参数：
//* Paint.Style.STROKE：描边。
//* Paint.Style.FILL：填充内部。
//* Paint.Style.FILL_AND_STROKE：填充内部和描边。
    }
}
