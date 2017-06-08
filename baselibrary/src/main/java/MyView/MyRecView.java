package MyView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.UITools;

/**
 * Created by Administrator on 2017/5/18 0018.
 */
//使用方法（具有三个构造方法）：int currentNum=0; MyRecView myRecView;  myRecView=new MyRecView(context,5,Gravity.CENTER,LinearLayout.HORIZONTAL);
//        myRecView=new MyRecView(context,11,Gravity.CENTER,LinearLayout.HORIZONTAL,R.color.es_bl,R.drawable.ratingbar_background_white
//                ,R.color.es_gr,R.drawable.ratingbar_background_red);
//    LinearLayout recRecommendationLinear;            recRecommendationLinear.addView(myRecView.getLinearLayout());
    // currentNum=myRecView.getCurrentNum();
public class MyRecView {
    Context context;
    LinearLayout linearLayout;
    int textViewsNum;
    List<TextView>listTextViews;
    int currentNum;
    int gravity;//布局位置，左中右设置
    int orientation;//布局横竖向设置
    int lLeft;//子布局左外边距
    int lRight;
    int lTop;
    int lBottom;
    int textStartColor;
    int textEndColor;
    int textBackgroundStartColor;
    int textBackgroundEndColor;
    public MyRecView(Context context, int textViewsNum){
        this.context=context;
        this.textViewsNum=textViewsNum;
        this.gravity=Gravity.CENTER;
        this.orientation=LinearLayout.HORIZONTAL;
        lRight= UITools.dip2px(context,4);
        lBottom=0;
        initData();
    }
    public MyRecView(Context context, int textViewsNum, int gravity, int orientation){
        this.context=context;
        this.textViewsNum=textViewsNum;
        this.gravity=gravity;
        this.orientation=orientation;
        if(orientation==LinearLayout.VERTICAL){
            lRight=0;
            lBottom=UITools.dip2px(context,4);
        }else if(orientation==LinearLayout.HORIZONTAL){
            lRight=UITools.dip2px(context,4);
            lBottom=0;
        }
        initData();
    }
    public MyRecView(Context context, int textViewsNum, int gravity, int orientation, int textStartColor, int textBackgroundStartColor, int textEndColor, int textBackgroundEndColor){
        this.context=context;
        this.textViewsNum=textViewsNum;
        this.listTextViews=new ArrayList<>();
        this.currentNum=0;
        this.gravity=gravity;
        this.orientation=orientation;
        if(orientation==LinearLayout.VERTICAL){
            lRight=0;
            lBottom=UITools.dip2px(context,4);
            lTop=0;
            lLeft=0;
        }else if(orientation==LinearLayout.HORIZONTAL){
            lRight=UITools.dip2px(context,4);
            lBottom=0;
            lTop=0;
            lLeft=0;
        }
        this.textStartColor=textStartColor;
        this.textBackgroundStartColor=textBackgroundStartColor;
        this.textEndColor=textEndColor;
        this.textBackgroundEndColor=textBackgroundEndColor;
        initViews();
    }
    private void initData(){
        this.listTextViews=new ArrayList<>();
        this.currentNum=0;
        lTop=0;
        lLeft=0;
        textStartColor= R.color.es_b;
        textEndColor=R.color.es_w;
        textBackgroundStartColor=R.drawable.ratingbar_background_white;
        textBackgroundEndColor=R.drawable.ratingbar_background_red;
        initViews();
    }
    private void initViews(){
        linearLayout=new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(lLeft,lTop,lRight,lBottom);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setVerticalGravity(Gravity.CENTER);
            linearLayout.setOrientation(orientation);
            linearLayout.setHorizontalGravity(gravity);
            linearLayout.setGravity(gravity);
        for (int i=0;i<textViewsNum;i++){
            final TextView tab = new TextView(context);
            tab.setTag(i);
            tab.setGravity(Gravity.CENTER);
            tab.setText(i+"");
            tab.setTextSize(12);
            LinearLayout.LayoutParams textLayoutParams=new LinearLayout.LayoutParams(UITools.dip2px(context,28),UITools.dip2px(context,28));
            textLayoutParams.setMargins(0,0,UITools.dip2px(context,2),0);
            tab.setLayoutParams(textLayoutParams);
            tab.setBackgroundResource(textBackgroundStartColor);
            tab.setTextColor(context.getResources().getColor(textStartColor));
            listTextViews.add(tab);
            linearLayout.addView(tab);
        }
        textClick(listTextViews);
    }
    private void textClick(final List<TextView>listViews){
        for (int i=0;i<listViews.size();i++){
            final int finalI = i;
            listViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentNum(finalI+1);
                    textViewsColorWhite(listViews,currentNum);
                }
            });
        }
    }
    private void textViewsColorWhite(List<TextView>listViews,int currentSelectNum){
        for (int i=currentSelectNum;i<listViews.size();i++){
            listViews.get(i).setBackgroundResource(textBackgroundStartColor);
            listViews.get(i).setTextColor(context.getResources().getColor(textStartColor));
        }
        textViewsColorRed(listViews,currentSelectNum);
    }
    private void textViewsColorRed(List<TextView>listViews,int currentSelectNum){
        for (int i=0;i<currentSelectNum;i++){
            listViews.get(i).setBackgroundResource(textBackgroundEndColor);
            listViews.get(i).setTextColor(context.getResources().getColor(textEndColor));
        }
    }
    private void setCurrentNum(int num){
        currentNum=num;
    }
    public int getCurrentNum(){
        return currentNum;
    }
    public LinearLayout getLinearLayout(){
        return linearLayout;
    }
}
