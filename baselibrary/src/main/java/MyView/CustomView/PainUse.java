package MyView.CustomView;

import android.annotation.TargetApi;
import android.graphics.BlurMaskFilter;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.os.Build;

/**
 * Created by Administrator on 2017/4/21 0021.
 */
/*
 * Paint即画笔，在绘图过程中起到了极其重要的作用，画笔主要保存了颜色，
 * 样式等绘制信息，指定了如何绘制文本和图形，画笔对象有很多设置方法，
 * 大体上可以分为两类，一类与图形绘制相关，一类与文本绘制相关。
 */
public class PainUse {
    Paint paint=new Paint();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PainUse(){
        //     **1.图形绘制**
        paint.setARGB(2,100,100,100);//    设置绘制的颜色，a代表透明度，r，g，b代表颜色值。
        paint.setAlpha(2);// * 设置绘制图形的透明度。
        paint.setColor(20);//* 设置绘制的颜色，使用颜色值来表示，该颜色值包括透明度和RGB颜色。
        paint.setAntiAlias(true);//* 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
        paint.setDither(false);//* 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setFilterBitmap(true);//* 如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示 * 速度，本设置项依赖于dither和xfermode的设置
        paint.setMaskFilter(new BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID));// * 设置MaskFilter，可以用不同的MaskFilter实现滤镜的效果，如滤化，立体等
//参数1：模糊延伸半径，必须>0； 参数2：有四种枚举 NORMAL，同时绘制图形本身内容+内阴影+外阴影,正常阴影效果 INNER，绘制图形内容本身+内阴影，不绘制外阴影 OUTER，不绘制图形内容以及内阴影，只绘制外阴影 SOLID，只绘制外阴影和图形内容本身，不绘制内阴影 BlurMaskFilter绘制的Bitmap基本完全不受影响
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0,
        });
        //第一行表示的R（红色）的向量，第二行表示的G（绿色）的向量，第三行表示的B（蓝色）的向量，最后一行表示A（透明度）的向量，这一顺序必须要正确不能混淆！这个矩阵不同的位置表示的RGBA值，其范围在0.0F至2.0F之间，1为保持原图的RGB值。每一行的第五列数字表示偏移值。
//        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// * 设置颜色过滤器，可以在绘制颜色时实现不用颜色的变换效果
//        paint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));// * 设置颜色过滤器，可以在绘制颜色时实现不用颜色的变换效果
        //也只有一个构造方法，PorterDuffColorFilter (int color, PorterDuff.Mode mode)，参数1：16进制表示的颜色值；参数2：PorterDuff内部类Mode中的一个常量值，这个值表示混合模式。
//        paint.setPathEffect();// * 设置绘制路径的效果，如点画线等
//        paint.setShader();// * 设置图像效果，使用Shader可以绘制出各种渐变效果
//        paint.setShadowLayer(10,10,10,10);// 在图形下面设置阴影层，产生阴影效果，radius为阴影的角度，dx和dy为阴影在x轴和y轴上的距离，color为阴影的颜色
//        paint.setStyle(Paint.Style style);// * 设置画笔的样式，为FILL（填充），FILL_AND_STROKE(填充且描边)，或STROKE（描边）
//        paint.setStrokeCap(Paint.Cap cap);// * 当画笔样式为STROKE或FILL_AND_STROKE时，设置笔刷的图形样式，如圆形样式 * Cap.ROUND,或方形样式Cap.SQUARE
        paint.setStrokeWidth(10);// * 当画笔样式为STROKE或FILL_AND_STROKE时，设置笔刷的粗细度
//        paint.setXfermode(Xfermode xfermode);// * 设置图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
        //设置图像混合模式，Xfermode 有个子类去实现PorterDuffXfermode
//        PorterDuffXfermode
//        构造方法PorterDuffXfermode(PorterDuff.Mode mode)，参数就是上面的提到的，图形混合
        // * **2.文本绘制 **
        paint.setFakeBoldText(false);// * 模拟实现粗体文字，设置在小字体上效果会非常差
        paint.setSubpixelText(false);// * 设置该项为true，将有助于文本在LCD屏幕上的显示效果
        paint.setTextAlign(Paint.Align.LEFT);//* 设置绘制文字的对齐方向
        paint.setTextSize(10);//设置绘制文字的字号大小
        paint.setTextScaleX(10);//设置绘制文字x轴的缩放比例，可以实现文字的拉伸的效果,1.0f为原始
        paint.setTextSkewX(10);//设置斜体文字，skewX为倾斜弧度,设置斜体文字，skewX为倾斜弧度，默认值0，大于0，向左斜，小于0，向右斜
//        paint.setTypeface();//设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
        paint.setUnderlineText(false);//设置带有下划线的文字效果
        paint.setStrikeThruText(false);//设置带有删除线的效果
        paint.setLetterSpacing(10);//Added in API level 21，设置文本字母间距，默认0，负值收紧文本
        paint.setHinting(0);//Added in API level 14，设置暗示模式，HINTING_OFF 或 HINTING_ON
//        paint.setTextAlign(Paint.Join.MITER);//当设置setStyle是Stroke或StrokeAndFill，设置绘制时各图形的结合方式，如影响矩形角的外轮廓
        paint.setStrokeMiter(0);//当设置setStyle是Stroke或StrokeAndFill，设置斜切
//        Canvas涉及到主要方法
//        类别	API	描述
//        绘制图形	drawPoint、drawPoints、drawLine、drawLines、drawRect、drawRoundRect、drawOval、drawCircle、drawArc	依次为绘制点、直线、矩形、圆角矩形、椭圆、圆、散形
//        绘制文本	drawText、drawPosText、drawTextOnPath	依次为绘制文字、指定每个字符位置绘制文字、根据路径绘制文字
//        画布变换	translate、scale、rotate、skew	依次为平移、缩放、旋转、倾斜（错切）
//        画布裁剪	clipPath、clipRect、clipRegion	依次为按路径、按矩形、按区域或对画布进行裁剪
//        1、屏幕坐标系
//        屏幕坐标系以手机屏幕到左上角为坐标原点，过原点的水平直线为X轴，向右为正方向；过原点的垂线为Y轴，向下为正方向；
//        2、View坐标系
//        View坐标系以父视图到左上角为坐标原点，过原点的水平直线为X轴，向右为正方向；过原点的垂线为Y轴，向下为正方向
//        View内部拥有四个函数，用于获取View到位置
//        getTop(); //View的顶边到其Parent View的顶边的距离，即View的顶边与View坐标系的X轴之间的距离
//        getLeft(); //View的左边到其Parent View的左边的距离，即View的左边与View坐标系的Y轴之间的距离
//        getBottom(); //View的底边到其Parent View的顶边的距离，即View的底边与View坐标系的X轴之间的距离
//        getRight(); //View的右边到其Parent View的左边的距离，即View的右边与View坐标系的Y轴之间的距离
//        4、onSizeChanged
//        如其名，在View大小改变是调用此函数，用于确定View的大小。至于View大小为什么会改变，因为View的大小不仅由本身确定，同事还受父View到影响
//        @Override
//        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//            super.onSizeChanged(w, h, oldw, oldh);
//        }
//        这里到w、h就是确定后到高宽值，如果查看View中的onLayoutChange也会看到类似的情况，拥有l, t, r, b, oldL, oldT, oldR, oldB，新旧两组参数
//        5、onDraw
//        onDraw是View的绘制部分，给了我们一张空白的画布，使用Canvas进行绘制。也是后面几篇文章所要分享的内容。
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//        }
//        6、onTouchEvent
//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//            return super.onTouchEvent(event);
//        }
//        当返回true时，说明该View消耗了触摸事件，后续到触摸事件由它来进行处理。返回false时，说明该View对触摸事件不感兴趣，事件继续传递下去。触屏事件类型被封装在MotionEvent中，MotionEvent提供了很多类型事件，主要关系以下几种类型：
//        事件类型	描述
//        ACTION_DOWN	手指按下
//        ACTION_MOVE	手指移动
//        ACTION_UP	手指抬起
//        在MotionEvent中有两组可以获得触摸位置到函数
//        event.getX(); //触摸点相对于View坐标系的X坐标
//        event.getY(); //触摸点相对于View坐标系的Y坐标
//        event.getRawX(); //触摸点相对于屏幕坐标系的X坐标
//        event.getRawY(); //触摸点相对于屏幕坐标系的Y坐标
//        onWindowFocusChanged运行于onMeasure与onLayout之后，可以获取到正确的width、height、top、left等属性值。
    }
}
