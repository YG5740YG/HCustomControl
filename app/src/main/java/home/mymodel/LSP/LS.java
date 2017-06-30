package home.mymodel.LSP;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import home.mymodel.R;
import me.nereo.multi_image_selector.bean.Image;
import newhome.baselibrary.ImageHandle.CompressImage.AbImageUtil;
import newhome.baselibrary.Tools.AsynImageUtil;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;

/**
 * Created by Administrator on 2017/6/27.
 */

public class LS extends Activity implements View.OnClickListener{
    LinearLayout mMainContent;
//    LinearLayout mLOne;
//    LinearLayout mLOneO;
//    LinearLayout mLTwo;
//    LinearLayout mLTwoT;
//    LinearLayout mLThree;
//    LinearLayout mLThreeT;
//    LinearLayout mLFour;
//    LinearLayout mLFourF;
//    LinearLayout mLFive;
//    LinearLayout mLFiveF;
//    LinearLayout mLSix;
//    LinearLayout mLSixS;
//    LinearLayout mLServen;
//    LinearLayout mLServenS;
//    LinearLayout mLEight;
//    LinearLayout mLEightE;
    TextView mRefreshT;
    ImageView mImageView;
    RadioButton mRadioOne;
    RadioButton mRadioTwo;
    RadioButton mRadioThree;
    List<LinearLayout>linearLayouts;
    List<LinearLayout>mLinearLayoutRows;
    List<Integer>mImages;
    List<Integer>mImageOther;
    Map<Integer,Integer>mImageMap;
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
        mMainContent=(LinearLayout)findViewById(R.id.mMainContent);
//        mLOne=(LinearLayout)findViewById(R.id.mLayoutOne);
//        mLOneO=(LinearLayout)findViewById(R.id.mLayoutOneO);
//        mLTwo=(LinearLayout)findViewById(R.id.mLayoutTwo);
//        mLTwoT=(LinearLayout)findViewById(R.id.mLayoutTwoT);
//        mLThree=(LinearLayout)findViewById(R.id.mLayoutThree);
//        mLThreeT=(LinearLayout)findViewById(R.id.mLayoutThreeT);
//        mLFour=(LinearLayout)findViewById(R.id.mLayoutFour);
//        mLFourF=(LinearLayout)findViewById(R.id.mLayoutFourF);
//        mLFive=(LinearLayout)findViewById(R.id.mLayoutFive);
//        mLFiveF=(LinearLayout)findViewById(R.id.mLayoutFiveF);
//        mLSix=(LinearLayout)findViewById(R.id.mLayoutSix);
//        mLSixS=(LinearLayout)findViewById(R.id.mLayoutSixS);
//        mLServen=(LinearLayout)findViewById(R.id.mLayoutServen);
//        mLServenS=(LinearLayout)findViewById(R.id.mLayoutServenS);
//        mLEight=(LinearLayout)findViewById(R.id.mLayoutEight);
//        mLEightE=(LinearLayout)findViewById(R.id.mLayoutEightE);
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
        mScreenWidth=Tools.getScreenWith(this);
        mScreenHeight=Tools.getScreenHeight(this);
        if(mRadioOne.isChecked()){
            mLayoutType=1;
        }else if(mRadioTwo.isChecked()){
            mLayoutType=2;
        }else if(mRadioThree.isChecked()){
            mLayoutType=3;
        }
        mEveryRowHeight=UITools.px2dip(this,TotalHeight)/mChildLayoutMap.get(mLayoutType);
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
        mImages.add(R.mipmap.e2);
        mImages.add(R.mipmap.eight);
    }

    /**
     * 填入需要显示的图片
     */
    public void getShowImages(){
        for (int i=0;i<mChildLayoutMap.get(mLayoutType)*mChildLayoutMap.get(mLayoutType);i++){
            if (mXuHaoMap.get(i) < 8) {
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i)));
            } else {
                mImageMap.put(i, mImages.get(mXuHaoMap.get(i) - 8));
            }
        }
    }

//    /**
//     * 得到展示图片的布局
//     */
//    public void getContent(){
//        linearLayouts=new ArrayList<>();
//        linearLayouts.add(mLOne);
//        linearLayouts.add(mLOneO);
//        linearLayouts.add(mLTwo);
//        linearLayouts.add(mLTwoT);
//        linearLayouts.add(mLThree);
//        linearLayouts.add(mLThreeT);
//        linearLayouts.add(mLFour);
//        linearLayouts.add(mLFourF);
//        linearLayouts.add(mLFive);
//        linearLayouts.add(mLFiveF);
//        linearLayouts.add(mLSix);
//        linearLayouts.add(mLSixS);
//        linearLayouts.add(mLServen);
//        linearLayouts.add(mLServenS);
//        linearLayouts.add(mLEight);
//        linearLayouts.add(mLEightE);
//    }

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
        mSelect=-1;
        mSelectNum=-1;
        mClears=new ArrayList<>();
        mImageMap=new ArrayMap<>();
        mImageOther=new ArrayList<>();
        mXuHaoMap=new ArrayMap<>();
        childLayoutSet();//初始化布局
        initImages();//初始化需要用到的图片
        getRandom();//为图片随机排序
        getShowImages();//得到需要展示的图片
//        getContent();//得到需要载入图片的布局
        for (int i=0;i<linearLayouts.size();i++){
            final int finalI = i;
            linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
            makeImage();//生成Image控件
            if(i<16) {
                mImageView.setImageDrawable(getResources().getDrawable(mImageMap.get(i)));
                linearLayouts.get(i).removeAllViews();
                linearLayouts.get(i).addView(mImageView);
                if (!mClears.contains(i)) {
                    linearLayouts.get(i).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Logs.Debug("gg===========" + event.getAction() + "==" + MotionEvent.ACTION_DOWN + "==" + MotionEvent.ACTION_UP);
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                if (mSelectNum == finalI) {
                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
                                } else if (mSelectNum != finalI && mSelectNum != -1) {
                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
                                    linearLayouts.get(mSelectNum).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
                                } else if (mSelectNum != -1) {
                                    linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
                                }
                                if ((mSelect + 8 == mXuHaoMap.get(finalI) || mSelect - 8 == mXuHaoMap.get(finalI)) && mSelect != -1) {
                                    linearLayouts.get(mSelectNum).removeAllViews();
                                    linearLayouts.get(finalI).removeAllViews();
                                    mClears.add(mSelectNum);
                                    mClears.add(finalI);
                                }
                                mSelect = mXuHaoMap.get(finalI);
                                mSelectNum = finalI;
                            }
                            return false;
                        }
                    });
                }
            }
        }
    }
}
