package home.mymodel.LSP;

import android.app.Activity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import home.mymodel.R;
import newhome.baselibrary.ImageHandle.CompressImage.AbImageUtil;
import newhome.baselibrary.Tools.AsynImageUtil;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/6/27.
 */

public class LS extends Activity{
    LinearLayout mLOne;
    LinearLayout mLOneO;
    LinearLayout mLTwo;
    LinearLayout mLTwoT;
    LinearLayout mLThree;
    LinearLayout mLThreeT;
    LinearLayout mLFour;
    LinearLayout mLFourF;
    LinearLayout mLFive;
    LinearLayout mLFiveF;
    LinearLayout mLSix;
    LinearLayout mLSixS;
    LinearLayout mLServen;
    LinearLayout mLServenS;
    LinearLayout mLEight;
    LinearLayout mLEightE;
    List<LinearLayout>linearLayouts;
    List<Integer>mImages;
    List<Integer>mImageOther;
    Map<Integer,Integer>mImageMap;
    Map<Integer,Integer>mXuHaoMap;
    int mSelect;
    List<Integer>mClears;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ls_layout);
        findView();
        setup();
    }
    public void findView(){
        mLOne=(LinearLayout)findViewById(R.id.mLayoutOne);
        mLOneO=(LinearLayout)findViewById(R.id.mLayoutOneO);
        mLTwo=(LinearLayout)findViewById(R.id.mLayoutTwo);
        mLTwoT=(LinearLayout)findViewById(R.id.mLayoutTwoT);
        mLThree=(LinearLayout)findViewById(R.id.mLayoutThree);
        mLThreeT=(LinearLayout)findViewById(R.id.mLayoutThreeT);
        mLFour=(LinearLayout)findViewById(R.id.mLayoutFour);
        mLFourF=(LinearLayout)findViewById(R.id.mLayoutFourF);
        mLFive=(LinearLayout)findViewById(R.id.mLayoutFive);
        mLFiveF=(LinearLayout)findViewById(R.id.mLayoutFiveF);
        mLSix=(LinearLayout)findViewById(R.id.mLayoutSix);
        mLSixS=(LinearLayout)findViewById(R.id.mLayoutSixS);
        mLServen=(LinearLayout)findViewById(R.id.mLayoutServen);
        mLServenS=(LinearLayout)findViewById(R.id.mLayoutServenS);
        mLEight=(LinearLayout)findViewById(R.id.mLayoutEight);
        mLEightE=(LinearLayout)findViewById(R.id.mLayoutEightE);
    }
    public void setup(){
        random=new Random();
        mSelect=-1;
        mClears=new ArrayList<>();
        mImageMap=new ArrayMap<>();
        mImageOther=new ArrayList<>();
        mXuHaoMap=new ArrayMap<>();
        mImages=new ArrayList<>();
        mImages.add(R.mipmap.one);
        mImages.add(R.mipmap.two);
        mImages.add(R.mipmap.three);
        mImages.add(R.mipmap.four);
        mImages.add(R.mipmap.five);
        mImages.add(R.mipmap.six);
        mImages.add(R.mipmap.e2);
        mImages.add(R.mipmap.eight);

        boolean flage=true;
        for (int i=0;i<mImages.size()*2;i++){
            int randomInt=random.nextInt(8);
            for (int key:mXuHaoMap.keySet()){
                if(key==randomInt){
                    i--;
                    flage=false;
                    break;
                }
            }
            if(!flage){
                flage=true;
            }else {
                mXuHaoMap.put(i, randomInt);
                Logs.Debug("gg=========i==" + i + "-----------" + randomInt);
                if (i < 8) {
                    mImageMap.put(i, mImages.get(randomInt));
                } else {
                    mImageMap.put(i, mImages.get(randomInt - 8));
                }
            }
        }
        linearLayouts=new ArrayList<>();
        linearLayouts.add(mLOne);
        linearLayouts.add(mLOneO);
        linearLayouts.add(mLTwo);
        linearLayouts.add(mLTwoT);
        linearLayouts.add(mLThree);
        linearLayouts.add(mLThreeT);
        linearLayouts.add(mLFour);
        linearLayouts.add(mLFourF);
        linearLayouts.add(mLFive);
        linearLayouts.add(mLFiveF);
        linearLayouts.add(mLSix);
        linearLayouts.add(mLSixS);
        linearLayouts.add(mLServen);
        linearLayouts.add(mLServenS);
        linearLayouts.add(mLEight);
        linearLayouts.add(mLEightE);
        for (int i=0;i<linearLayouts.size();i++){
            final int finalI = i;
            ImageView imageView=new ImageView(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(2,2,2,2);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageDrawable(getResources().getDrawable(mImageMap.get(mXuHaoMap.get(i))));
            linearLayouts.get(i).removeAllViews();
            linearLayouts.get(i).addView(imageView);
            if(!mClears.contains(i)) {
                linearLayouts.get(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Logs.Debug("gg===========" + event.getAction() + "==" + MotionEvent.ACTION_DOWN + "==" + MotionEvent.ACTION_UP);
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (mSelect == finalI) {
                                linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
                            } else if (mSelect != finalI && mSelect != -1) {
                                linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
                                linearLayouts.get(mSelect).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_r));
                            } else {
                                linearLayouts.get(finalI).setBackground(getResources().getDrawable(R.drawable.bd_bord_gray_g));
                            }
                            if (mSelect + 8 == finalI||mSelect-8==finalI) {
                                linearLayouts.get(mSelect).removeAllViews();
                                linearLayouts.get(finalI).removeAllViews();
                                mClears.add(mSelect);
                                mClears.add(finalI);
                            }
                            mSelect = finalI;
                        }
                        return false;
                    }
                });
            }
        }
    }
}
