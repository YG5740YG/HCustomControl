package home.mymodel.LSP.Drage;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import home.mymodel.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class DrageView extends Activity implements View.OnClickListener{
    LinearLayout mMainContent;
    TextView mRefreshT;
    ImageView mImageView;
    RadioButton mRadioOne;
    RadioButton mRadioTwo;
    RadioButton mRadioThree;
    List<LinearLayout> linearLayouts;
    List<LinearLayout>mLinearLayoutRows;
    List<Integer>mImages;
    List<Integer>mImageOther;
    Map<Integer,Integer> mImageMap;
    Map<Integer,Integer>mXuHaoMap;
    /**
     * 存储被选中图片key
     */
    int mSelect;
    /**
     * 存储被选中图片序号
     */
    int mSelectNum;
    List<Integer>mClears;
    Random random;
    int mScreenWidth;
    int mScreenHeight;
    Context mContext;
    /**
     * 每个子模块布局加载
     */
    View mImageLayoutView;
    /**
     * 布局形式
     */
    int mLayoutType=0;
    /**
     * 不同的布局具有不同列,存入不同的type和对应列数
     */
    Map<Integer,Integer>mChildLayoutMap;
    /**
     * 每行的高度
     */
    int mEveryRowHeight=0;
    /**
     * 每行的宽度
     */
    int mEveryRowWidth=0;
    int TotalHeight=3200;
    int mNumber;
    boolean mSelectBool;
    LinearLayout mContainer;
    LinearLayout mLayoutOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ls_layout);
        findView();
        setup();
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.mRefresh){
            setup();
        }
    }
    public void findView(){
        mLayoutOne=(LinearLayout) findViewById(R.id.mLayoutOne);
        mMainContent=(LinearLayout)findViewById(R.id.mMainContent);
        mRefreshT=(TextView)findViewById(R.id.mRefresh);
        mRadioOne=(RadioButton)findViewById(R.id.mRedioOne);
        mRadioTwo=(RadioButton)findViewById(R.id.mRedioTwo);
        mRadioThree=(RadioButton)findViewById(R.id.mRedioThree);
        mRadioOne.setSelected(true);
        typeCell();
        clickEvent();
        mContext=getApplicationContext();
    }

    /**
     *不同的类型对应不同的列数初始化
     */
    public void typeCell(){
        mChildLayoutMap=new ArrayMap<>();
        mChildLayoutMap.put(1,4);
        mChildLayoutMap.put(2,6);
        mChildLayoutMap.put(3,8);
    }
    /**
     * 自布局设置,不同选择具有不同的布局
     */
    public void childLayoutSet(){
        mScreenWidth= Tools.getScreenWith(this);
        mScreenHeight=Tools.getScreenHeight(this);
        if(mRadioOne.isChecked()){
            mLayoutType=1;
            mNumber=8;
        }else if(mRadioTwo.isChecked()){
            mLayoutType=2;
            mNumber=6;
        }else if(mRadioThree.isChecked()){
            mLayoutType=3;
            mNumber=8;
        }
        mEveryRowHeight= UITools.px2dip(this,TotalHeight)/mChildLayoutMap.get(mLayoutType);
        mEveryRowWidth=((mScreenWidth-150)/mChildLayoutMap.get(mLayoutType));
        Logs.Debug("gg======Height=="+mEveryRowWidth+"==="+mEveryRowHeight);
        mainLayoutSet();
    }
    /**
     * 容器设置-外部容器设置
     */
    public void mainLayoutSet(){
        mMainContent.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UITools.px2dip(this,TotalHeight));
        mMainContent.setLayoutParams(layoutParams);
        layoutInnerRow();
    }
    /**
     * 为每行加入layout
     */
    public void layoutInnerRow(){
        mLinearLayoutRows=new ArrayList<>();
        for (int i=0;i<mChildLayoutMap.get(mLayoutType);i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mEveryRowHeight);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayoutRows.add(linearLayout);
        }
        imageLayoutInnerRow();
    }
    /**
     * 为每行加入布局
     */
    public void imageLayoutInnerRow(){
        linearLayouts=new ArrayList<>();
        for (int i=0;i<mLinearLayoutRows.size();i++) {
            for (int j=0;j<mChildLayoutMap.get(mLayoutType);j++) {
                mImageLayoutView = LayoutInflater.from(mContext).inflate(R.layout.ls_layout_item, null);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(mEveryRowWidth,mEveryRowHeight);
                mImageLayoutView.setLayoutParams(layoutParams);
                LinearLayout mLinearLayout = (LinearLayout) mImageLayoutView.findViewById(R.id.mLayoutOne);
                linearLayouts.add(mLinearLayout);
                mLinearLayoutRows.get(i).addView(mImageLayoutView);
            }
            mMainContent.addView(mLinearLayoutRows.get(i));
        }
    }
    /**
     * 点击事件
     */
    public void clickEvent(){
        mRefreshT.setOnClickListener(this);
    }

    /**
     * 对图片进行随机排序,获取随后后的key_value
     */
    public void getRandom() {
        random=new Random();
        List<Integer>checkValues=new ArrayList<>();
        for (int i = 0; i < mChildLayoutMap.get(mLayoutType)*mChildLayoutMap.get(mLayoutType); i++) {
            int randomInt = random.nextInt(mChildLayoutMap.get(mLayoutType)*mChildLayoutMap.get(mLayoutType));
            if (checkValues.contains(randomInt)) {
                i--;
            } else {
                checkValues.add(randomInt);
                mXuHaoMap.put(i, randomInt);
                Logs.Debug("gg===========randomInt=="+randomInt);
            }
        }
    }

    /**
     * 图片初始化
     */
    public void initImages(){
        mImages=new ArrayList<>();
        mImages.add(R.mipmap.one);
        mImages.add(R.mipmap.two);
        mImages.add(R.mipmap.three);
        mImages.add(R.mipmap.four);
        mImages.add(R.mipmap.five);
        mImages.add(R.mipmap.six);
        if(mNumber!=6) {
            mImages.add(R.mipmap.e2);
            mImages.add(R.mipmap.eight);
        }
    }

    /**
     * 填入需要显示的图片
     */
    public void getShowImages(){
        for (int i=0;i<mChildLayoutMap.get(mLayoutType)*mChildLayoutMap.get(mLayoutType);i++){
            if (mXuHaoMap.get(i) < mNumber) {
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i)));
            } else if(mXuHaoMap.get(i) < mNumber*2){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber));
            } else if(mXuHaoMap.get(i) < mNumber*3){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber*2));
            }else if(mXuHaoMap.get(i) < mNumber*4){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber*3));
            }else if(mXuHaoMap.get(i) < mNumber*5){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber*4));
            }else if(mXuHaoMap.get(i) < mNumber*6){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber*5));
            }else if(mXuHaoMap.get(i) < mNumber*7){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber*6));
            }else if(mXuHaoMap.get(i) < mNumber*8){
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - mNumber*7));
            }
        }
    }
    /**
     * 生成图片控件
     */
    public void makeImage(){
        mImageView=new ImageView(this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(2,2,2,2);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    public void setup(){
        mSelectBool=true;
        mSelect=-1;
        mSelectNum=-1;
        mClears=new ArrayList<>();
        mImageMap=new ArrayMap<>();
        mImageOther=new ArrayList<>();
        mXuHaoMap=new ArrayMap<>();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        childLayoutSet();//初始化布局
        initImages();//初始化需要用到的图片
        getRandom();//为图片随机排序
        getShowImages();//得到需要展示的图片
        mContainer=linearLayouts.get(0);
        for (int i=1;i<linearLayouts.size();i++) {
            final int finalI = i;
            linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
            makeImage();//生成Image控件
                mImageView.setImageDrawable(getResources().getDrawable(mImageMap.get(i)));
                linearLayouts.get(i).removeAllViews();
                linearLayouts.get(i).addView(mImageView);
            ((ImageView)linearLayouts.get(i).getChildAt(0)).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    Logs.Debug("gg===========gg==" + event.getAction() + "==" + MotionEvent.ACTION_DOWN + "==" + MotionEvent.ACTION_MOVE
                            + "==" + MotionEvent.ACTION_UP);
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        view.startDrag(data, shadowBuilder, view, 0);
                        view.setVisibility(View.INVISIBLE);
//                        mContainer=(LinearLayout)view.getParent();
                        return true;
                    }else {
                        return false;
                    }
                }
            });
            final Drawable enterShape = getResources().getDrawable(R.drawable.bd_bord_gray_ssb);
            final Drawable normalShape = getResources().getDrawable(R.drawable.bd_bord_gray_ssg);
            ((ImageView)linearLayouts.get(1).getChildAt(0)).setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent event) {
                    int action = event.getAction();
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            Logs.Debug("gg==========gg=="+1);
                            // Do nothing
                            break;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            Logs.Debug("gg==========gg=="+2);
                            view.setBackgroundDrawable(enterShape);
                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
                            Logs.Debug("gg==========gg=="+3);
                            view.setBackgroundDrawable(normalShape);
                            break;
                        case DragEvent.ACTION_DROP:
                            Logs.Debug("gg==========gg=="+4);
                            // Dropped, reassign View to ViewGroup
                            break;
                        case DragEvent.ACTION_DRAG_ENDED:
                            Logs.Debug("gg==========gg=="+5);
                            linearLayouts.get(finalI).removeAllViews();
                            mContainer.removeAllViewsInLayout();
                            mContainer.addView(view);
                            view.setVisibility(View.VISIBLE);
                        default:
                            break;
                    }
                    return true;
                }
            });
//                linearLayouts.get(i).setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        Logs.Debug("gg===========" + event.getAction() + "==" + MotionEvent.ACTION_DOWN + "==" + MotionEvent.ACTION_MOVE);
//                        if (!mClears.contains(finalI)) {
//                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                                if (mSelectNum == finalI && mSelectBool) {
//                                    mSelectBool=false;
//                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
//                                } else if (mSelectNum != finalI && mSelectNum != -1) {
//                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
//                                    linearLayouts.get(mSelectNum).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
//                                } else {
//                                    mSelectBool=true;
//                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
//                                }
//                                if ((mSelect % mNumber == mXuHaoMap.get(finalI) % mNumber) && mSelect != mXuHaoMap.get(finalI) && mSelect != -1) {
//                                    linearLayouts.get(mSelectNum).removeAllViews();
//                                    linearLayouts.get(finalI).removeAllViews();
//                                    mClears.add(mSelectNum);
//                                    mClears.add(finalI);
//                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
//                                    mSelect = -1;
//                                    mSelectNum = -1;
//                                } else {
//                                    mSelect = mXuHaoMap.get(finalI);
//                                    mSelectNum = finalI;
//                                }
//                            }
//                        }
//                        return false;
//                    }
//                });
            }
    }
}

