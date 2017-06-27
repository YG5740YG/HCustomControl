package MyView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;


import newhome.baselibrary.R;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
//https://juejin.im/entry/582ab2dfda2f600063cc704e
public class MyViewAb extends Activity{
    // 画笔
    Paint mPaint = new Paint();
    public MyViewAb(Context context, View view){
        int mTop=view.getTop();       //获取子View左上角距父View顶部的距离
        int mLeft=view.getLeft();      //获取子View左上角距父View左侧的距离
        int mBottom=view.getBottom();    //获取子View右下角距父View顶部的距离
        int mRight=view.getRight();     //获取子View右下角距父View左侧的距离

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float mEvnetX= event.getX();       //触摸点相对于其所在组件坐标系的坐标
                float mEventY= event.getY();
                float mEventRawX=event.getRawX();    //触摸点相对于屏幕默认坐标系的坐标
                float mEventRawY=event.getRawY();
                return false;
            }
        });
        int mHeight=view.getHeight();//视图高度
        int mWidth=view.getWidth();//视图宽度
        //颜色
        int color = Color.GRAY;     //灰色
         color = Color.argb(127, 255, 0, 0);   //半透明红色
         color = 0xaaff0000;                   //带有透明度的红色
        color = getResources().getColor(R.color.es_gr2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = getColor(R.color.es_gr2);    //API 23 及以上支持该方法
        }

        //绘制
        Canvas canvas=new Canvas();
        canvas.drawColor(Color.BLUE); //绘制蓝色
        // 1.创建一个画笔
         mPaint = new Paint();
         initPaint();
        canvas.drawPoint(200, 200, mPaint);     //在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
                500,500,
                500,600,
                500,700
        },mPaint);
        canvas.drawLine(300,300,500,600,mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                100,200,200,200,
                100,300,200,300
        },mPaint);
        //矩形
        // 第一种
        canvas.drawRect(100,100,800,400,mPaint);
        // 第二种
        Rect rect = new Rect(100,100,800,400);
        canvas.drawRect(rect,mPaint);
        // 第三种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawRect(rectF,mPaint);
//        绘制圆角矩形也提供了两种重载方式
        // 第一种
        rectF = new RectF(100,100,800,400);
        canvas.drawRoundRect(rectF,30,30,mPaint);//两个参数实际上是椭圆的圆弧的两个半径
//        我们了解到原理后，就可以为所欲为了，通过计算可知我们上次绘制的矩形宽度为700，高度为300，当你让 rx大于350(宽度的一半)，
// ry大于150(高度的一半) 时奇迹就出现了， 你会发现圆角矩形变成了一个椭圆， 他们画出来是这样的 ( 为了方便确认我更改了画笔颜色，
// 同时绘制出了矩形和圆角矩形 )：
        // 矩形
        rectF = new RectF(100,100,800,400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);
        // 绘制圆角矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(rectF,700,400,mPaint);
//        绘制椭圆，绘制椭圆实际上就是绘制一个矩形的内切图形，原理如下，就不多说了：如果你传递进来的是一个长宽相等的矩形(即正方形)，那么绘制出来的实际上就是一个圆。
        rectF = new RectF(100,100,800,400);
        canvas.drawOval(rectF,mPaint);
        canvas.drawCircle(500,500,400,mPaint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
        //圆弧
        rectF = new RectF(100,100,800,400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,90,false,mPaint);//开始角度0,  扫过角度90,是否使用中心
//        用了中心点之后绘制出来类似于一个扇形，而不使用中心点则是圆弧起始点和结束点之间的连线加上圆弧围成的图形
        //使用较多的是圆形
        //Paint
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
//        STROKE                //描边        FILL                  //填充                FILL_AND_STROKE       //描边加填充
//        translate是坐标系的移动，可以为图形绘制选择一个合适的坐标系。 请注意，位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        //缩放
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect1 = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.scale(0.5f,0.5f);                // 画布缩放
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect2 = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.scale(0.5f,0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
//        当缩放比例为负数的时候会根据缩放中心轴进行翻转
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect4 = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.scale(-0.5f,-0.5f);          // 画布缩放
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect5 = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.scale(-0.5f,-0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
//        PS:和位移(translate)一样，缩放也是可以叠加的。
             canvas.scale(0.5f,0.5f);
             canvas.scale(0.5f,0.1f);
//        调用两次缩放则 x轴实际缩放为0.5x0.5=0.25 y轴实际缩放为0.5x0.1=0.05
//        利用这一特性制作一个有趣的图形。
        // 将坐标系原点移动到画布正中心,相当不错的重叠图
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect6 = new RectF(-400,-400,400,400);   // 矩形区域
        for (int i=0; i<=20; i++)
        {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rect6,mPaint);
        }
//        旋转
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        rect1 = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.rotate(180);                     // 旋转180度 <-- 默认旋转中心为原点
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
         rect1 = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.rotate(180,200,0);               // 旋转180度 <-- 旋转中心向右偏移200个单位
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
//        调用两次旋转，则实际的旋转角度为180+20=200度。
        canvas.rotate(180);
        canvas.rotate(20);
        //同心圆
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0,0,400,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,mPaint);
        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,mPaint);
            canvas.rotate(10);
        }
//        ⑷错切(skew)
//        skew这里翻译为错切，错切是特殊类型的线性变换。
//        错切只提供了一种方法：
//        public void skew (float sx, float sy)
//        参数含义：
//        float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
//        float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
//                变换后:
//        X = x + sx * y
//        Y = sy * x + y
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect7 = new RectF(0,0,200,200);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.skew(1,0);                       // 水平错切 <- 45度
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
//        错切也是可叠加的，不过请注意，调用次序不同绘制结果也会不同
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect89 = new RectF(0,0,200,200);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.skew(1,0);                       // 水平错切
        canvas.skew(0,1);                       // 垂直错切
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);
    }
    // 2.初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }
}
