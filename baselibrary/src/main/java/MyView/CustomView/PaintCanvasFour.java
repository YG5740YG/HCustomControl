package MyView.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.view.View;

import newhome.baselibrary.R;


/**
 * Created by Administrator on 2017/4/21 0021.
 */
/*
ComposeShader
混合，有两个构造函数
ComposeShader(Shader shaderA, Shader shaderB, Xfermode mode)
ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)
 */
//把图片填充到布局里面，把布局铺满
public class PaintCanvasFour extends View {
    private Paint mPaint;
    private Context mContext;
    private Bitmap mBitmap;
    private Shader bitmapShader, linearGradient, composeShader;
    public PaintCanvasFour(Context context) {
        super(context);
        this.mContext=context;
        init();
    }
    private void init() {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo);
        bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        linearGradient = new LinearGradient(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);
        composeShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setShader(composeShader);
        canvas.drawCircle(150, 155, 140, mPaint);
    }
}
