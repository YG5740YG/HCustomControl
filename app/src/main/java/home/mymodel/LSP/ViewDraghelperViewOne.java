package home.mymodel.LSP;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
//http://blog.csdn.net/wangrain1/article/details/73934012
public class ViewDraghelperViewOne extends LinearLayout{
    ViewDragHelper dragHelper ;
    int mImageWidth;
    int mImageHeight;
    int mLayoutWidth;
    int mLayoutHeight;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    int mPaddingBottom;

    ImageView mDrageImageOne;
    ImageView mDrageImageTwo;
    ImageView mDrageImageThree;
    ImageView mDrageImageFour;
    ImageView mDrageImageFive;
    ImageView mDrageImageSix;
    ImageView mDrageImageServen;
    ImageView mDrageImageEight;
    ImageView mDrageImageNine;
    ImageView mFillId;

    public ViewDraghelperViewOne(@NonNull Context context) {
        this(context,null);
    }

    public ViewDraghelperViewOne(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ViewDraghelperViewOne(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutHeight=getHeight();
        mLayoutWidth=getWidth();
        mPaddingLeft=getPaddingLeft();
        mPaddingRight=getPaddingRight();
        mPaddingTop=getPaddingTop();
        mPaddingBottom=getPaddingBottom();
        mImageWidth=mLayoutWidth-mPaddingLeft-mPaddingRight;
        mImageHeight=mLayoutHeight-mPaddingTop-mPaddingBottom;
        mFillId=mDrageImageOne;

//        创建ViewDragHelper对象,第一个参数就是当前的ViewGroup对象，第二个参数是手势拖拽的敏感系数，
// 系数越大越敏感，第三个参数也是最重要的 接口了。
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
//            判断当前哪一个 view 可以拖动，false 是所有都不支持拖动，为true的时候是支持拖动，
// 这里一般我们根据 child 判断当前是哪一个view 是否支持 拖动，支持就返回 true，否则就返回false。
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                    if(child==mDrageImageTwo||child==mDrageImageFive){\
                        return true;
//                }
//                return false;
            }
//child 是拖拽的对象，left是 view 距离左边的距离，这里如果 return 返回的是 left 那么view的位置左边距就是left
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //取得左边界的坐标
                final int leftBound = getPaddingLeft();
                //取得右边界的坐标
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
                //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
                //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
                return Math.min(Math.max(left, leftBound), rightBound);
            }
//            child 是拖拽的对象，top是 view 距离顶部的距离，这里如果 return 返回的是 top那么view的位置顶边距就是top
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
                return Math.min(Math.max(top, topBound), bottomBound);
            }
//水平拖拽的范围，所以这里如果return 返回参数那就要返回当前view水平水平拖拽在屏幕中的范围。
            @Override
            public int getViewHorizontalDragRange(View child) {
                //计算水平拖拽范围，让view 在屏幕内 拖拽
                if (child==mDrageImageOne){
                    return getWidth()-getPaddingLeft()-child.getWidth();
                }
                return super.getViewHorizontalDragRange(child);
            }
//如果我们设置了水平方向的拖拽范围，同理也要设置垂直方向上的拖拽范围，不然不起作用。如果你想 禁止 水平或者 垂直方向的拖动，那可一直接返回 0 。
            @Override
            public int getViewVerticalDragRange(View child) {
                //计算垂直拖拽范围，让view 在屏幕内 拖拽
                if (child == mDrageImageOne){
                    return (getHeight() - getTop() - child.getHeight())*3;
                }
                return super.getViewVerticalDragRange(child);
            }
        });
    }
    /**
     * 布局加载完成后，运行这个方法，可以在这个方法中初始化一些 对象view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDrageImageOne = (ImageView) this.findViewById(R.id.mDrageImageOne);
        mDrageImageTwo=(ImageView)this.findViewById(R.id.mDrageImageTwo);
        mDrageImageThree=(ImageView)this.findViewById(R.id.mDrageImageThree);
        mDrageImageFour=(ImageView)this.findViewById(R.id.mDrageImageFour);
        mDrageImageFive=(ImageView)this.findViewById(R.id.mDrageImageFive);
        mDrageImageSix=(ImageView)this.findViewById(R.id.mDrageImageSix);
        mDrageImageServen=(ImageView)this.findViewById(R.id.mDrageImageServen);
        mDrageImageEight=(ImageView)this.findViewById(R.id.mDrageImageEight);
        mDrageImageNine=(ImageView)this.findViewById(R.id.mDrageImageNine);
    }
    /**
     * 下面这两个方法必须要增加，接管手势的
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }
}
