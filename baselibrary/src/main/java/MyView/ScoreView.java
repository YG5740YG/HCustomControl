package MyView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.UITools;

/**
 * Created by Administrator on 2017/5/18 0018.
 */
//使用方法ScoreView scoreView2; scoreView2  =new ScoreView(context,staffData.getAllSort(),Float.valueOf(staffData.getAllSortPrecent()),3);
    //LinearLayout dashboardView2;dashboardView2.addView(scoreView2);
//自定义圆圈控件
public class ScoreView extends View {
    private int score;
    //实现构造方法时传入要显示的数字
    int unitage;
    Paint paint,paint1;
    Paint redPaint,tianPaint,tianPain1,tianPain2;
    RectF rectF,rectBigF,rectTianF,rectFxiexian,rectFxiexian1;
    int score_text=0;
    float arc_y=0;
    Context context;
    int baiFenBi=0;
    float baiFenBiValue=0;
    float huduValue=0;
    int times=10;//循环次数
    double everyHuDu=0;//每次走的弧度
    int everyValue=1;
    int flage=0;
    //单列
    /*private volatile static ScoreView scoreView;
    public static ScoreView getInstance(Context context,int score){
        //双重校验，解决性能问题
        if(scoreView==null){
//            synchronized 具有同步的作用
            synchronized (ScoreView.class){
                if(null==scoreView){
                    scoreView=new ScoreView(context,score);
                }
            }
        }
        return scoreView;
    }*/
    public ScoreView(Context context,int score,float baiFenBiValue,int flage) {
        super(context);
        this.context=context;
        init(score,baiFenBiValue,flage);
    }
    //初始化所有的备用条件
    private void init(int score,float baiFenBiValue,int flage){
        this.score=score;
        this.baiFenBiValue=baiFenBiValue;
        huduValue=360*baiFenBiValue;// 最终需要走动的弧度
        this .flage=flage;
        everyHuDu=huduValue/times;

//        times=score/everyValue;//需要走的次数，每次走10
//        everyHuDu=huduValue/times;
//        everyHuDu=Float.valueOf((huduValue/times+"")+((huduValue%times)+""));//每次需要走的弧度
        Logs.Debug("gg==========everyHuDu=="+everyHuDu);
        Resources res=getResources();
        //以10dp作为单位量
        unitage=res.getDimensionPixelOffset(R.dimen.base_padding);
        //初始化红色笔
        paint1=new Paint();
        paint1.setAntiAlias(true);
        paint1.setDither(true);
        paint1.setColor(Color.LTGRAY);
        paint1.setStyle(Paint.Style.STROKE);
//        paint.setStyle(Paint.Style.FILL);
        paint1.setStrokeWidth(unitage*0.2f);
        paint1.setTextAlign(Paint.Align.CENTER);
        paint1.setTextSize(unitage*3);
        //初始化红色笔
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.LTGRAY);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(unitage*0.1f);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(unitage*3);
        //初始化灰色笔
        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(unitage*0.4f);
        redPaint.setDither(true);
        redPaint.setColor(getResources().getColor(R.color.es_gr2));
        redPaint.setTextSize(unitage*3); //设置文本的字号大小
        redPaint.setTextAlign(Paint.Align.CENTER);//设置文本的对其方式为水平居中
        //初始化灰色笔，加粗
        tianPaint = new Paint();
        tianPaint.setAntiAlias(true);
        tianPaint.setStyle(Paint.Style.STROKE);
        tianPaint.setStrokeWidth(unitage*0.4f);
        tianPaint.setDither(true);
        tianPaint.setColor(getResources().getColor(R.color.es_bl));
        tianPaint.setTextSize(unitage*3); //设置文本的字号大小
        tianPaint.setTextAlign(Paint.Align.CENTER);//设置文本的对其方式为水平居中
        //初始化灰色笔，加粗
        tianPain1 = new Paint();
        tianPain1.setAntiAlias(true);
        tianPain1.setStyle(Paint.Style.STROKE);
        tianPain1.setStrokeWidth(unitage*0.3f);
        tianPain1.setDither(true);
        tianPain1.setColor(getResources().getColor(R.color.es_gr2));
        tianPain1.setTextSize(unitage*3); //设置文本的字号大小
        tianPain1.setTextAlign(Paint.Align.CENTER);//设置文本的对其方式为水平居中
        //初始化灰色笔，加粗
        tianPain2 = new Paint();
        tianPain2.setAntiAlias(true);
        tianPain2.setStyle(Paint.Style.STROKE);
        tianPain2.setStrokeWidth(unitage*0.3f);
        tianPain2.setDither(true);
        tianPain2.setColor(getResources().getColor(R.color.es_r));
        tianPain2.setTextSize(unitage*3); //设置文本的字号大小
        tianPain2.setTextAlign(Paint.Align.CENTER);//设置文本的对其方式为水平居中
        //初始化圆弧所需条件（及设置圆弧的外接矩形的四边）
        rectF=new RectF();
        rectF.set(unitage*0.5f,unitage*0.5f,unitage*7.5f,unitage*7.5f);
        rectBigF=new RectF();
        rectBigF.set(unitage*(0.25f),unitage*(0.25f),unitage*7.75f,unitage*7.75f);
        rectTianF=new RectF();
        rectTianF.set(unitage,unitage,unitage*8.25f,unitage*8.25f);
        rectFxiexian=new RectF();
        rectF.set(unitage*0.5f,unitage*0.5f,unitage*7.5f,unitage*7.5f);
        rectFxiexian1=new RectF();
        rectF.set(unitage*0.5f,unitage*0.5f,unitage*7.5f,unitage*7.5f);
        //设置整个控件的宽高配置参数
        setLayoutParams(new ViewGroup.LayoutParams((int)(unitage*9f),(int)(unitage*9f)));
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                new DrawThread();//
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF,0,360,false,redPaint);
//        canvas.drawArc(rectBigF,0,360,false,redPaint);
//        canvas.drawArc(rectF,-90,arc_y,false,tianPaint);
        if(flage==1) {
            canvas.drawArc(rectF, -90, arc_y, false, tianPaint);
//            canvas.drawLine(160, 270, 210, 240, paint1);
//            canvas.drawLine(110, 240, 160, 270, paint1);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.smallarror);
            canvas.drawBitmap(bitmap, getWidth()/2-bitmap.getWidth(),getHeight()-UITools.dip2px(context,30),paint);
        }else if(flage==2){
            canvas.drawArc(rectF, -90, arc_y, false, tianPain1);
//            canvas.drawLine(160, 270, 210, 240, paint1);
//            canvas.drawLine(110, 240, 160, 270, paint1);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.smallarror);
            canvas.drawBitmap(bitmap, getWidth()/2-bitmap.getWidth(),getHeight()-UITools.dip2px(context,30),paint);
            canvas.drawBitmap(bitmap, getWidth()/2-bitmap.getWidth(),getHeight()-UITools.dip2px(context,30),paint);
        }else if(flage==3){
            canvas.drawArc(rectF, -90, arc_y, false, tianPain2);
//            canvas.drawLine(155, 270, 210, 240, paint1);
//            canvas.drawLine(115, 240, 155, 270, paint1);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.smallarror);
            canvas.drawBitmap(bitmap, getWidth()/2-bitmap.getWidth(),getHeight()-UITools.dip2px(context,30),paint);
        }
        canvas.drawText(score+"",getWidth()/2- UITools.dip2px(context,4),getHeight()/2+ UITools.dip2px(context,5),paint);
    }
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
//                            arc_y+=3.6;
//                            Logs.Debug("gg==========ev="+everyHuDu);
                            if(everyHuDu==0){
                                arc_y = huduValue;
                            }else {
                                if (count == times - 1) {
                                    arc_y = huduValue;
                                } else {
                                    arc_y += everyHuDu;
                                }
                            }
//                            score_text=score_text+everyValue;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= times)//满足该条件就结束循环
                    break;
            }
        }
    }
}


