package MyView.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

/**
 * Created by Administrator on 2017/4/21 0021.
 */
//设置绕着某中心点进行360度旋转渐变效果，构造方法：
    ////坐标(cx,cy)决定了中心点的位置，会绕着该中心点进行360度旋转。color0表示的是起点的颜色，color1表示的是终点的颜色
//SweepGradient(float cx, float cy, int color0, int color1)
//坐标(cx,cy)决定了中心点的位置,colors颜色数组,position取值范围为[0,1]，0和1都表示3点钟位置，0.25表示6点钟位置，0.5表示9点钟位置，0.75表示12点钟位置，诸如此类
//        SweepGradient(float cx, float cy, int[] colors, float[] positions)
public class PaintCanvasThree extends View{
    private Paint mPaint;
    private Context mContext;
    private Shader mShader;

    public PaintCanvasThree(Context context) {
        super(context);
        init();
    }

    private void init() {
//        mShader = new SweepGradient(250, 250, Color.BLUE, Color.GREEN);
        mShader = new SweepGradient(150, 155, Color.BLUE, Color.GREEN);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setShader(mShader);
        canvas.drawCircle(150, 155, 140, mPaint);
    }
}
