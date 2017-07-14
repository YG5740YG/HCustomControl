package home.mymodel.LSP.Drage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import home.mymodel.LSP.LS;
import home.mymodel.R;
import newhome.baselibrary.ImageHandle.CompressImage.BitmapTo;
import newhome.baselibrary.Model.SplitImageData;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class MakeImage extends Activity implements View.OnClickListener{
    LinearLayout mMainContent;
    TextView mRefreshT;
    ImageView mImageView;
    RadioButton mRadioOne;
    RadioButton mRadioTwo;
    RadioButton mRadioThree;
    TextView mLineText;
    List<LinearLayout> linearLayouts;
    List<LinearLayout>mLinearLayoutRows;
    List<Integer>mImages;
    List<Integer>mImageOther;
    /**
     * 存数固定图片索引和图片id
     */
    Map<Integer,Integer> mImageMap;
    /**
     * 存储图片索引和路径
     */
    Map<Integer,String>mFlowImageMap;
    Map<Integer,Integer>mXuHaoMap;
    /**
     * 存储被选中图片key
     */
    int mSelect;
    /**
     * 存储被选中图片序号
     */
    int mSelectNum;
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
    List<Integer>mCanClickItems;
    ImageView mShowImageView;
    /**
     * 需要拼接的图片
     */
    Bitmap mUseBitmap;
    List<SplitImageData> splitImageDatas;
    /**
     * 图片手机路径
     */
    List<String>mImagePath;
    /**
     * bug模式
     */
    boolean mBugModel;
    int mBugNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ls_layout);
        findView();
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBugModel=false;
        mBugNum=0;
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
        mShowImageView=(ImageView)findViewById(R.id.mShowImage);
        mLineText=(TextView)findViewById(R.id.mLineText);
        mRadioOne.setSelected(true);
        typeCell();
        clickEvent();
        mContext=getApplicationContext();
    }

    /**
     * 获取需要拼接的图片
     */
    public Bitmap getBitmap(){
        mUseBitmap=BitmapTo.getDrawableToBitmap(getResources().getDrawable(R.mipmap.big_hzw_image));
        return mUseBitmap;
    }

    /**
     * 获取拆分后的图片列表路径
     * @return
     */
    public List<SplitImageData>getSplitImages(){
        getBitmap();//获取拼图所需要的图片
        File file = new File("/storage/emulated/0/SplitImage");
        Logs.Debug("gg============="+file.getName());
        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
        }
        splitImageDatas=BitmapTo.ImageSplitter(mContext,mUseBitmap,mChildLayoutMap.get(mLayoutType),mChildLayoutMap.get(mLayoutType),"MyFirst");
        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                mImagePath.add(item.getAbsolutePath());
            }
        }
        return splitImageDatas;
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
     * 图片初始化,固定的图片，用于测试
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
     * 填入需要显示的图片,对于动态的图片，存储的手机路径,用于拼图
     */
    public void getShowFlowImages(){
        for (int i=0;i<mChildLayoutMap.get(mLayoutType)*mChildLayoutMap.get(mLayoutType);i++){
//            if (mXuHaoMap.get(i) < mNumber) {
                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i)));
//            } else if(mXuHaoMap.get(i) < mNumber*2){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber));
//            } else if(mXuHaoMap.get(i) < mNumber*3){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber*2));
//            }else if(mXuHaoMap.get(i) < mNumber*4){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber*3));
//            }else if(mXuHaoMap.get(i) < mNumber*5){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber*4));
//            }else if(mXuHaoMap.get(i) < mNumber*6){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber*5));
//            }else if(mXuHaoMap.get(i) < mNumber*7){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber*6));
//            }else if(mXuHaoMap.get(i) < mNumber*8){
//                mFlowImageMap.put(i, mImagePath.get(mXuHaoMap.get(i) - mNumber*7));
//            }
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

    /**
     * 获取可以点击的模块   1、四个角
     * fillItem 需要填充的模块
     */
    public List<Integer> getCanClickItems(int fillItem){
        mCanClickItems=new ArrayList<>();
        int rows=mChildLayoutMap.get(mLayoutType);
        mCanClickItems.add(fillItem-1);
        mCanClickItems.add(fillItem+1);
        mCanClickItems.add(fillItem-rows);
        mCanClickItems.add(fillItem+rows);
        if(fillItem%rows==0){
            mCanClickItems.remove(0);
            if(fillItem==rows*(rows-1)){
               mCanClickItems.remove(2);
            }else if(fillItem==0){
                mCanClickItems.remove(1);
            }
        }else if (fillItem%rows==rows-1){
            mCanClickItems.remove(1);
            if(fillItem==rows-1){
                mCanClickItems.remove(1);
            }else if(fillItem==rows*rows-1){
                mCanClickItems.remove(2);
            }
        }else{
            if(fillItem<rows){
                mCanClickItems.remove(2);
            }else if(fillItem>=(rows-1)*rows){
                mCanClickItems.remove(3);
            }
        }
        return mCanClickItems;
    }
    public void setup(){
        mSelectBool=true;
        mSelect=-1;
        mSelectNum=-1;
        mImageMap=new ArrayMap<>();
        mFlowImageMap=new ArrayMap<>();
        mImageOther=new ArrayList<>();
        mXuHaoMap=new ArrayMap<>();
        mImagePath=new ArrayList<>();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        childLayoutSet();//初始化布局
        initImages();//初始化需要用到的图片，固定
        getRandom();//为图片随机排序
//        getShowImages();//得到需要展示的图片，固定
        mContainer=linearLayouts.get(0);
        getCanClickItems(0);//获取可以点击的items
        getSplitImages();//获取拆分后的图片
        getShowFlowImages();//得到需要展示的图片，动态
        mShowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBugNum<2) {
                    mShowImageView.setBackground(getResources().getDrawable(R.color.transparent));
                    mBugModel=false;
                    mBugNum++;
                }else{
                    mShowImageView.setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
                    mBugModel=true;
                    mBugNum=0;
                }
            }
        });
        mLineText.setText("连——连");
        mLineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LS.class);
                startActivity(intent);
                finish();
            }
        });

        for (int i=0;i<linearLayouts.size();i++) {
            final int finalI = i;
            linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
            if(i!=0) {
                makeImage();//生成Image控件
//                mImageView.setImageDrawable(getResources().getDrawable(mImageMap.get(i)));//获取手机上写固定的图片
                if(mBugModel) {
                mImageView.setImageURI(Uri.parse(mImagePath.get(finalI)));//动态图片拆分...,一次性排好顺序
                }else {
                    mImageView.setImageURI(Uri.parse(mFlowImageMap.get(finalI)));//动态图片拆分...正常排序
                }

                linearLayouts.get(i).removeAllViews();
                linearLayouts.get(i).addView(mImageView);
            }
            if(mSelectNum!=finalI) {
                linearLayouts.get(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Logs.Debug("gg===========" + event.getAction() + "==" + MotionEvent.ACTION_DOWN + "==" + MotionEvent.ACTION_UP);
                        for (int k=0;k<mCanClickItems.size();k++){
                            Logs.Debug("gg===========gg=="+finalI+"==="+mCanClickItems.get(k));
                        }
                        if (mCanClickItems.contains(finalI)) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
                                if (mSelectNum != -1) {
                                    linearLayouts.get(mSelectNum).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
                                }
                                mSelectNum = finalI;
                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                ImageView mImageView1 = new ImageView(mContext);
                                mImageView1 = (ImageView) linearLayouts.get(finalI).getChildAt(0);
                                linearLayouts.get(finalI).removeAllViews();
                                mContainer.removeAllViews();
                                mContainer.addView(mImageView1);
                                mContainer = linearLayouts.get(finalI);
                                getCanClickItems(finalI);
                            }
                        }
                        return true;
                    }
                });
            }
        }
    }
}

