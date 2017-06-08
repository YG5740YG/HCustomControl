package MyView;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/2/6.
 */

public class ListViewDeleteSlideView extends LinearLayout {
    private static final String TAG="ListViewDeleteSlideView";
    private Context mcontext;
    private LinearLayout mViewContent;
    private RelativeLayout mHolder;
    private Scroller mScroller;
    private OnSlideListener mOnSlideListener;
    //删除按钮宽度
    private int mHolderWidth=200;
    private int mLastX = 0;
    private int mLastY = 0;
    private static final int TAN = 2;
    public interface OnSlideListener {
        public static final int SLIDE_STATUS_OFF = 0;
        public static final int SLIDE_STATUS_START_SCROLL = 1;
        public static final int SLIDE_STATUS_ON = 2;
        /**
         * @param view
         *            current SlideView
         * @param status
         *            SLIDE_STATUS_ON or SLIDE_STATUS_OFF
         */
        public void onSlide(View view, int status);
    }
    public ListViewDeleteSlideView(Context context) {
        super(context);
        initView();
    }
    public ListViewDeleteSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    private void initView(){
        mcontext=getContext();
        mScroller=new Scroller(mcontext);
        // 设置linearlayout的orientation为横向
        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(mcontext, R.layout.listview_delete_item,this);//把布局文件放入改activity
        mViewContent=(LinearLayout)findViewById(R.id.view_content);
        mHolderWidth=Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mHolderWidth,getResources().getDisplayMetrics()));
        Logs.Debug("gg=======mHolderWidth=="+mHolderWidth);
    }
    public void setButtonText(CharSequence text){
        ((TextView)findViewById(R.id.delete)).setText(text);
    }

    /**
     * 设置左边内容View
     *
     * @param view
     */
    public void setContentView(View view){
        mViewContent.addView(view);
    }

    public void setOnSlideListener(OnSlideListener onSlideListener){
        mOnSlideListener=onSlideListener;
    }

    public void onRequireTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y =(int)event.getY();
        // 得到的相对于View初始xy轴位置的距离
        int scrollX=getScrollX();//获取view滑动到的位置
        Log.d(TAG, "x=" + x + "  y=" + y + "  scrollX=" + scrollX);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                if (!mScroller.isFinished()) {//如果滚动产生滚动动画
                    mScroller.abortAnimation();
                }
                if (mOnSlideListener != null) {//如果监听事件存在
                    mOnSlideListener.onSlide(this, OnSlideListener.SLIDE_STATUS_START_SCROLL);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX=x-mLastX;//得到的相对于View初始x轴位置的距离
                int deltaY=y-mLastY;//得到的相对于View初始y轴位置的距离
                if(Math.abs(deltaX)<Math .abs(deltaY)*TAN){
                    break;
                }
                int newScrollX=scrollX-deltaX;//得到相对于父view  x方向移动的距离
                if(deltaX!=0){
                    if(newScrollX<0){//向左移动newScrollX为负值
                        newScrollX=0;
                    }else if(newScrollX>mHolderWidth){//如果移动的距离大于按钮的宽度
                        newScrollX=mHolderWidth;//移动的距离等于按钮宽度
                    }
                    this.scrollTo(newScrollX,0);//view移动newScrollX距离
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                int newScrollX=0;
                if(scrollX-mHolderWidth*0.75>0){//滑动的距离大于按钮宽度的四分之三，则滑动距离为按钮宽度
                    newScrollX=mHolderWidth;
                }else{
                    newScrollX=0;
                }
//                this.smoothScrollTo(newScrollX, 0);//view缓慢滑动过去
                this.scrollTo(newScrollX,0);//view移动newScrollX距离
                if(mOnSlideListener!=null){//滑动监听事件存在
                    mOnSlideListener.onSlide(this,
                            newScrollX==0?OnSlideListener.SLIDE_STATUS_OFF:OnSlideListener.SLIDE_STATUS_ON);
                    //如果需要滑动距离为0，返回0，否则返回2
                }
                break;
            }
            default:
                break;
        }
        mLastX=x;//初始view距离父view的x值
        mLastY=y;//初始view距离父view的y值
    }
    public void shrink(){//获取一个View的X（横向）缩放比例，如果没有调用过setScaleX，那么返回1。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(getScaleX()!=0){
                this.smoothScrollTo(0, 0);
            }
        }
    }

    private void smoothScrollTo(int destX, int destY) {
        // 缓慢滚动到指定位置
        int scrollX = getScrollX();//获取view当前滑动距离
        int delta = destX - scrollX;//得到当前距离与需要滑动到距离的差值
//        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
//        public void startScroll (int startX, int startY, int dx, int dy)
        mScroller.startScroll(scrollX, 0, delta, 0);
        /**
         * 　　以提供的起始点和将要滑动的距离开始滚动。滚动会使用缺省值250ms作为持续时间。
         　　参数
         　　startX 水平方向滚动的偏移值，以像素为单位。正值表明滚动将向左滚动
         　　startY 垂直方向滚动的偏移值，以像素为单位。正值表明滚动将向上滚动
         　　dx 水平方向滑动的距离，正值会使滚动向左滚动
         　　dy 垂直方向滑动的距离，正值会使滚动向上滚动
         */
        //参数：当前位置（目前以滑动的距离）,0，当前距离与需要滑动到距离的差值，0，
        invalidate();
    }
    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll");
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrX());
            postInvalidate();
        }
    }
}
/**
 * SlideView继承LinearLayout，它里面有两个子View，SlideView把orientation设为HORIZONTAL，两个子View就横向排列了。 因为左边mViewContent宽占满屏幕，所有右边子View默认就超出屏幕。
 onRequireTouchEvent(MotionEvent event)处理传过来的触摸事件。
 当MotionEvent.ACTION_DOWN时，停止当前滚动，并且回调mOnSlideListener.onSlide()方法，接口的实现在PrivateListingAdapter里面。接口回调处理的是:判断当前Touch的ItemView是否和上一个Touch的View是否是同一个，如果不是，把上一个Touch的View
 mLastSlideViewWithStatusOn.shrink()(即向右滑动至影藏删除按钮)。
 当MotionEvent.ACTION_MOVE时，计算手指滑动的横向距离，然后this.scrollTo(newScrollX, 0)（即自己横向滚动相应的距离），主意当手指只要一直触摸着屏幕，onRequireTouchEvent方法会一直被调用，然后事件类型一直为ACTION_MOVE，所以每次move的距离不会很长。通过log可以看到每次的距离为1，2，或3等。如果滑动的越快，距离会相应的变大。
 当MotionEvent.ACTION_UP时，计算当前滚动的距离是否大于mHolderWidth * 0.75，是的话就向左滚动至mHolderWidth ，否则向右滚动0（即隐藏删除按钮）；最后在mOnSlideListener.onSlide()下，更新mLastSlideViewWithStatusOn指向当前Touch的View；
 SlideView中的privatelisting_delete_merge.xml如下：
 */