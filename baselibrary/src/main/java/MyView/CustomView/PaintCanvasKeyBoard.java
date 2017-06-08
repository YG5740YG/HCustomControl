package MyView.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import newhome.baselibrary.R;


/**
 * Created by Administrator on 2017/4/21 0021.
 */
//自定义数字键盘，半成品，暂时用不了
public class PaintCanvasKeyBoard extends View {
    private boolean isInit=false;
    Paint mPaint;
    int mHeight=100;
    int mRectWidth=100;
    int mRectHeight=50;
    int []xs={100,200,300};
    int []ys={100,200,300};
    Context context;
    Bitmap mBpDelete;
    int mWidthOfBp=50;
    int mHeightOfBp=60;

    public PaintCanvasKeyBoard(Context context) {
        super(context);
        this .context=context;
        mPaint=new Paint();
        mBpDelete=BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initData();
        }
        mPaint.setColor(Color.WHITE);
        //画宫格
        //第一排
        canvas.drawRoundRect(10, mHeight / 2 + 10, 10 + mRectWidth, mHeight / 2 + 10 + mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 10, 20 + 2 * mRectWidth, mHeight / 2 + 10 + mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 10, 30 + 3 * mRectWidth, mHeight / 2 + 10 + mRectHeight, 10, 10, mPaint);
        //第二排
        canvas.drawRoundRect(10, mHeight / 2 + 20 + mRectHeight, 10 + mRectWidth, mHeight / 2 + 20 + 2 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 20 + mRectHeight, 20 + 2 * mRectWidth, mHeight / 2 + 20 + 2 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 20 + mRectHeight, 30 + 3 * mRectWidth, mHeight / 2 + 20 + 2 * mRectHeight, 10, 10, mPaint);
        //第三排
        canvas.drawRoundRect(10, mHeight / 2 + 30 + 2 * mRectHeight, 10 + mRectWidth, mHeight / 2 + 30 + 3 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 30 + 2 * mRectHeight, 20 + 2 * mRectWidth, mHeight / 2 + 30 + 3 * mRectHeight, 10, 10, mPaint);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 30 + 2 * mRectHeight, 30 + 3 * mRectWidth, mHeight / 2 + 30 + 3 * mRectHeight, 10, 10, mPaint);
        //第四排
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(10, mHeight / 2 + 40 + 3 * mRectHeight, 10 + mRectWidth, mHeight / 2 + 40 + 4 * mRectHeight, 10, 10, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(20 + mRectWidth, mHeight / 2 + 40 + 3 * mRectHeight, 20 + 2 * mRectWidth, mHeight / 2 + 40 + 4 * mRectHeight, 10, 10, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(30 + 2 * mRectWidth, mHeight / 2 + 40 + 3 * mRectHeight, 30 + 3 * mRectWidth, mHeight / 2 + 40 + 4 * mRectHeight, 10, 10, mPaint);


        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);// 设置字体大小
        mPaint.setStrokeWidth(2);
        //画数字
        //第一排
        canvas.drawText("1", xs[0], ys[0], mPaint);
        canvas.drawText("2", xs[1], ys[0], mPaint);
        canvas.drawText("3", xs[2], ys[0], mPaint);
        //第二排
        canvas.drawText("4", xs[0], ys[1], mPaint);
        canvas.drawText("5", xs[1], ys[1], mPaint);
        canvas.drawText("6", xs[2], ys[1], mPaint);
        //第三排
        canvas.drawText("7", xs[0], ys[2], mPaint);
        canvas.drawText("8", xs[1], ys[2], mPaint);
        canvas.drawText("9", xs[2], ys[2], mPaint);
        //第四排
        canvas.drawText(".", xs[0], ys[3], mPaint);
        canvas.drawText("0", xs[1], ys[3], mPaint);
        canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
    }
    public void initData(){

    }
}
