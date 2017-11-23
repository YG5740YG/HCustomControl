package yg.customcontrol.com.SimpleControls.MScrollControl;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import yg.customcontrol.com.MyTools.CommonTools;


/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */
public class DragLinearlayout extends LinearLayout {
    private MoveListen moveListen;
    private int lastX,lastY,screenWidth,screenHeight,totalMoveHeight=0,starY;
    private int mNowMoveHeight=0,mLastMoveHeight=0;
    private long startTime=0,endTIme=0;
    boolean mHasUpListen=false;
    int mTopValue=200;
    public DragLinearlayout(Context context) {
        this(context, null);
    }

    public DragLinearlayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = ((dm.heightPixels)- CommonTools.dpToPx(mTopValue,getResources()))/8;
        screenHeight=screenHeight*6+CommonTools.dpToPx(530,getResources());
    }

    public void setTopValue(int topValue){
        mTopValue=topValue;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = ((dm.heightPixels)- CommonTools.dpToPx(mTopValue,getResources()))/8;
        screenHeight=screenHeight*6+CommonTools.dpToPx(530,getResources());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startTime= System.currentTimeMillis();
                lastX = (int) ev.getRawX();
                lastY = (int) ev.getRawY();
                starY=(int)ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) ev.getRawX() - lastX;
                int dy = (int) ev.getRawY() - lastY;
                int left = this.getLeft();
                int top = this.getTop() + dy;
                int right = this.getRight();
                int bottom = this.getBottom() + dy;
                if (top <= 0) {
                    top = 0;
                    bottom =this.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - this.getHeight();
                }
                if(top>0&&bottom<=screenHeight) {
                    totalMoveHeight = totalMoveHeight + dy;
                }
                lastX = (int) ev.getRawX();
                lastY = (int) ev.getRawY();
                endTIme= System.currentTimeMillis();
                if(mLastMoveHeight!=mNowMoveHeight){
                    mLastMoveHeight=mNowMoveHeight;
                }
                mNowMoveHeight=totalMoveHeight;
                if (endTIme-startTime>80 && mNowMoveHeight!=mLastMoveHeight) {
                    this.layout(left, top, right, bottom);
                    if (moveListen != null) {
                        moveListen.motionMoveListen(mNowMoveHeight,mLastMoveHeight);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                endTIme= System.currentTimeMillis();
                if (endTIme-startTime>80) {
                    if (moveListen != null) {
                        mHasUpListen=true;
                        moveListen.motionUpListen();
                    }
                }
                totalMoveHeight=0;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface MoveListen{
        void motionMoveListen(int nowMoveHeight, int lastMoveHeight);
        void motionUpListen();
    }

    public void setMoveListen(MoveListen moveListen){
        this.moveListen=moveListen;
    }
}

