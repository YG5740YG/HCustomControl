package SimpleControls.MScrollControl;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import MyTools.CommonTools;
import MyTools.ImageLoader;
import yg.customcontrol.com.mylibrary.R;


/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class SlideView implements DragLinearlayout.MoveListen {
    private View mContentView;
    private Context mContext;
    private  LinearLayout mTopView;
    private LinearLayout mMiddleView;
    private LinearLayout mBottomView;
    private DragLinearlayout mBottomDragLayout;
    private LinearLayout mArrowContent;
    private ImageView arrowImg;
    private LinearLayout mSlideContent;
    private int screenHeight=0;
    private boolean isFold=true;
    private int mMiddleHeight=0;
    private int mBottomLayoutTop=0;
    private int mNowMoveHeight=1,mLastMoveHeight=1;
    private int mMoveHeight=0;
    private MoveListen moveListen;
    private int mTopValue=200;
    public interface MoveListen{
        void motionMoveListen(int nowMoveHeight);
        void motionUpListen();
    }
    public void setMoveListen(MoveListen moveListen){
        this.moveListen=moveListen;
    }
    public SlideView(Context context,int topValue){
        mContext=context;
        mTopValue=topValue;
        mContentView= LayoutInflater.from(mContext).inflate(R.layout.layout_slide,null);
        findView();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        screenHeight = ((dm.heightPixels)- CommonTools.dpToPx(mTopValue,mContext.getResources()))/8;
        mBottomDragLayout.setMoveListen(this);
        mBottomDragLayout.setTopValue(mTopValue);
        mMiddleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMiddleHeight=mMiddleView.getHeight();
            }
        },500);
        initBottomLayoutPosition();
    }
    public void setTipImage(int image){
        arrowImg.setImageResource(image);
    }
    private void findView() {
        mTopView=(LinearLayout)mContentView.findViewById(R.id.top_View);
        mMiddleView=(LinearLayout)mContentView.findViewById(R.id.middle_view);
        mBottomView=(LinearLayout)mContentView.findViewById(R.id.bottom_view_content);
        mBottomDragLayout=(DragLinearlayout)mContentView.findViewById(R.id.layout_bottom_content);
        mArrowContent=(LinearLayout)mContentView.findViewById(R.id.arrow_content);
        arrowImg=(ImageView)mContentView.findViewById(R.id.arrow_img);
        mSlideContent=(LinearLayout) mContentView.findViewById(R.id.slide_content);
        mArrowContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFold) {
                    handler.postDelayed(runnable, 0);
                } else {
                    handler.postDelayed(runnable, 0);
                }
            }
        });

    }
    public LinearLayout getMiddleVIew(){
        return mMiddleView;
    }
    public LinearLayout getBottomView(){
        return mBottomView;
    }
    public View getSlideContent(){return mSlideContent;}
    public LinearLayout getTopView(){
        return mTopView;
    }
    /**
     * 设置控件的新位置
     */
    public void initBottomLayoutPosition(){
        mBottomLayoutTop=screenHeight*6+CommonTools.dpToPx(30,mContext.getResources());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,CommonTools.dpToPx(500,
                mContext.getResources()));
        params.setMargins(0,mBottomLayoutTop,0,0);
        mBottomDragLayout.setLayoutParams(params);
    }
    /**
     * 旋转箭头角标方向
     * @param isFold
     */
    private void rotateArrow(boolean isFold){
        ObjectAnimator rotateAnimator;
        if (isFold){
            rotateAnimator = ObjectAnimator.ofFloat(arrowImg, "rotation", 0, 180);
        }else {
            rotateAnimator = ObjectAnimator.ofFloat(arrowImg, "rotation", 180, 0);
        }
        rotateAnimator.start();
    }

    public int getRecycleViewOpacity(int moveHeight){
        int alpha=((moveHeight*2)/8)>255?255:(moveHeight*2)/8;
        if(alpha<20){
            alpha=0;
        }else if(alpha>230){
            alpha=250;
        }
        if (alpha < 130) {
            return 255-alpha;
        } else {
            return alpha;
        }
    }
    public void setOpacity (int moveHeight){
        float value=mBottomLayoutTop/100;
        float opacityValue= Float.valueOf(((moveHeight/value)*0.01)>0.85?1+"":((moveHeight/value)*0.01)<0?0+"":(
                (moveHeight/value)*0.01+""));
        if(isFold) {
            mMiddleView.setAlpha(opacityValue);
        }else{
            mMiddleView.setAlpha(opacityValue);
        }
    }
    public void onDestroy(){
        handler.postDelayed(runnable, 10);
    }
    public void setMoveHeight(int moveHeight){
        this.mMoveHeight=moveHeight;
    }
    public int getMoveHeight(){
        return mMoveHeight;
    }
    Handler handler=new Handler();
    Runnable runnable=new Runnable(){
        @Override
        public void run() {
            int moveUp = mBottomLayoutTop - Math.abs(mNowMoveHeight);
            int moveDown = Math.abs(mNowMoveHeight);
            if (isFold) {
                if (moveUp > 0) {
                    if (moveListen != null) {
                        moveListen.motionMoveListen(getRecycleViewOpacity(moveUp));
                    }
                    if (Math.abs(mNowMoveHeight) > 0) {
                        mNowMoveHeight = Math.abs(mNowMoveHeight) + CommonTools.dpToPx(8, mContext.getResources());
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonTools.dpToPx(500,
                            mContext.getResources()));
                    params.setMargins(0, moveUp, 0, 0);
                    mBottomDragLayout.setLayoutParams(params);
                    setOpacity(moveUp);
                    setMoveHeight(moveUp);
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, 10);
                } else {
                    rotateArrow(isFold);
                    isFold=false;
                    mNowMoveHeight=1;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonTools.dpToPx(500,
                            mContext.getResources()));
                    params.setMargins(0, 0, 0, 0);
                    mBottomDragLayout.setLayoutParams(params);
                    setOpacity(0);
                    if (moveListen != null) {
                        moveListen.motionMoveListen(getRecycleViewOpacity(0));
                    }
                    setMoveHeight(0);
                    handler.removeCallbacks(this);
                }
            } else {
                if (moveDown < mBottomLayoutTop) {
                    if (moveListen != null) {
                        moveListen.motionMoveListen(getRecycleViewOpacity(moveDown));
                    }
                    if (Math.abs(mNowMoveHeight) > 0) {
                        mNowMoveHeight = Math.abs(mNowMoveHeight) + CommonTools.dpToPx(8,  mContext.getResources());
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonTools.dpToPx(500,
                            mContext. getResources()));
                    params.setMargins(0, moveDown, 0, 0);
                    mBottomDragLayout.setLayoutParams(params);
                    setOpacity(moveDown);
                    setMoveHeight(moveDown);
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, 10);
                } else {
                    rotateArrow(isFold);
                    isFold=true;
                    mNowMoveHeight=1;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonTools.dpToPx(500,
                            mContext.getResources()));
                    params.setMargins(0, mBottomLayoutTop, 0, 0);
                    mBottomDragLayout.setLayoutParams(params);
                    if (moveListen != null) {
                        moveListen.motionMoveListen(getRecycleViewOpacity(mBottomLayoutTop));
                    }
                    setOpacity(mBottomLayoutTop);
                    setMoveHeight(mBottomLayoutTop);
                    handler.removeCallbacks(this);
                }
            }
        }
    };
    @Override
    public void motionMoveListen(int nowMoveHeight, int lastMoveHeight) {
        mLastMoveHeight=lastMoveHeight;
        mNowMoveHeight = nowMoveHeight;
        int moveUp = mBottomLayoutTop - Math.abs(mNowMoveHeight);
        int moveDown = Math.abs(mNowMoveHeight);
        if (mNowMoveHeight < mLastMoveHeight) {
            setOpacity(moveUp);
        } else {
            setOpacity(moveDown);
        }
    }

    @Override
    public void motionUpListen() {
        if (moveListen != null) {
            moveListen.motionUpListen();
        }
        if (mNowMoveHeight <= mLastMoveHeight && isFold) {
            mNowMoveHeight=mNowMoveHeight- CommonTools.dpToPx(35,mContext.getResources());
            handler.postDelayed(runnable, 0);
        } else {
            handler.postDelayed(runnable, 0);
        }
    }
}
