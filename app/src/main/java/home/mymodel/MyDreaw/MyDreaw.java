package home.mymodel.MyDreaw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import newhome.baselibrary.Tools.Tools;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class MyDreaw extends View {
    Context mContext;
    int mHeight=0;
    int mWith=0;
    RectF rectF;
    Paint paint;
    public MyDreaw(Context context,Activity activity){
        super(context);
        this.mContext=context;
        mHeight=Tools.getScreenHeight(activity);
        mWith= Tools.getScreenWith(activity);


        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10);

        rectF=new RectF();
        rectF.set(10,10,10,500);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rectF,paint);
    }
}
