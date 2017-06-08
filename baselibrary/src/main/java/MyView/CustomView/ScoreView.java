package MyView.CustomView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import newhome.baselibrary.R;


/**
 * Created by Administrator on 2017/4/21 0021.
 */
//自定义圆圈控件
public class ScoreView extends View{
    //实现构造方法时传入要显示的数字
    int score;
    int unitage;
    Paint paint;
    Paint redPaint;
    RectF rectF;
    int score_text=0;
    int arc_y=0;
    public ScoreView(Context context,int score) {
        super(context);
        init(score);
    }
    //初始化所有的备用条件
    private void init(int score){
        this.score=score;
        Resources res=getResources();
        //以10dp作为单位量
        unitage=res.getDimensionPixelOffset(R.dimen.base_padding);
        //初始化蓝色笔
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(unitage*0.2f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(unitage*5);
        //初始化红色笔
        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(unitage*0.2f);
        redPaint.setDither(true);
        redPaint.setColor(Color.RED);
        redPaint.setTextSize(unitage*6); //设置文本的字号大小
        redPaint.setTextAlign(Paint.Align.CENTER);//设置文本的对其方式为水平居中
        //初始化圆弧所需条件（及设置圆弧的外接矩形的四边）
        rectF=new RectF();
        rectF.set(unitage*0.5f,unitage*0.5f,unitage*18.5f,unitage*18.5f);
        //设置整个控件的宽高配置参数
        setLayoutParams(new ViewGroup.LayoutParams((int)(unitage*19.5f),(int)(unitage*19.5f)));
        //获取该view的视图树观察者并添加绘制变化监听者
        //实现有绘制变化时的回调方法
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //2.开启子线程对绘制用到的数据进行修改
                new DrawThread();//
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        PainUse painUse=new PainUse();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
//        canvas.drawBitmap(bitmap, 0, 0, painUse);
        //绘制弧形
        //黑笔画的是一个整圆所以从哪里开始都一样
        canvas.drawArc(rectF,0,360,false,paint);
        //白笔之所以从-90度开始，是因为0度其实使我们的3点钟的位置，所以-90才是我们的0点的位置
        canvas.drawArc(rectF,-90,arc_y,false,redPaint);
        //绘制文本
        canvas.drawText(score_text+"",unitage*9.7f,unitage*11.0f,paint);
        //到此整个自定义View就已经写完了
    }
    //开启子线程并在绘制监听的回调方法中实现实时更新绘制数据
    public class DrawThread implements Runnable{
        private final Thread mDrawThread;
        private int statek=0;
        int count=0;
        public DrawThread() {
            mDrawThread = new Thread(this);
            mDrawThread.start();
        }
        @Override
        public void run() {
            while (true) {
                switch (statek) {
                    case 0://给一点点缓冲的时间
                        try {
                            Thread.sleep(200);
                            statek = 1;
                        } catch (InterruptedException e) {

                        }
                        break;
                    case 1:
                        try {//更新显示的数据
                            Thread.sleep(20);
                            arc_y += 3.6f;
                            score_text++;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= score)//满足该条件就结束循环
                    break;
            }
        }
    }
}
