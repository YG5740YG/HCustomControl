package MyView.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by Administrator on 2017/4/21 0021.
 */

///https://juejin.im/entry/57b65a565bbb50005b66e26e
//渐变效果左上角到右下角
public class PaintCanvas extends View{
    private Paint mPaint;
    private Context mContext;
    private Bitmap mBitmap;
    private Shader mShader;

    public PaintCanvas(Context context) {
        super(context);
        init();
    }

    private void init() {
//        mShader = new LinearGradient(0, 0, 50, 50, Color.BLUE, Color.GREEN,Shader.TileMode.CLAMP);
        mShader = new LinearGradient(0, 0, 50, 50, Color.BLUE, Color.GREEN,Shader.TileMode.MIRROR);
//        mShader = new LinearGradient(0, 0, 50, 50, Color.BLUE, Color.GREEN,Shader.TileMode.REPEAT);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
                mPaint.setShader(mShader);
                canvas.drawCircle(150, 155, 140, mPaint);
    }
}
