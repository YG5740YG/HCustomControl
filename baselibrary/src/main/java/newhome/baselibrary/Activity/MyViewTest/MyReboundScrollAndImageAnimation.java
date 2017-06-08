package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baseres.view.*;

/**
 * Created by 20160330 on 2017/2/7.
 */

public class MyReboundScrollAndImageAnimation extends BaseActivity {

    Context mcontext;
    ImageView Float_image;//浮动图片控件
    ScrollView scrollView;
    int scrollview_startY=0;
    int scrollview_endY=0;
    int kk=0;
    boolean scrollview_flage=true;
    ViewGroup.MarginLayoutParams margin1;
    RelativeLayout.LayoutParams r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reboundscrollview);
        findViewById();
        mcontext=this;
        findViewById();
        setUp();
    }
    @Override
    public void findViewById() {
        Float_image=(ImageView)findViewById(R.id.Float_image);
        scrollView=(ScrollView)findViewById(R.id.ReboundScroll);
        Float_image.setImageResource(R.mipmap.e16);
        margin1 = new ViewGroup.MarginLayoutParams(Float_image.getLayoutParams());
        margin1.setMargins(0,0,0,0);
        r2=new RelativeLayout.LayoutParams(margin1);
        kk=0;
    }

    @Override
    public void setUp() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            Logs.Debug("gg============1"+scrollView.getScrollY());
            Logs.Debug("gg============21=="+scrollview_startY+"=="+scrollview_endY);
            if(scrollview_startY==scrollview_endY+1||scrollview_startY==scrollview_endY){
//                    Float_image.setAlpha(255);
                Logs.Debug("gg============2");
                if(kk!=0) {
                    Logs.Debug("gg============3");
                    scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Logs.Debug("gg============5");
                            Logs.Debug("gg============5=="+scrollview_startY+"=="+scrollview_endY);
                            if(scrollview_startY==scrollview_endY||scrollview_startY==scrollview_endY-1||scrollview_startY==scrollview_endY+1) {
                                Logs.Debug("gg============4");
                                kk = 0;
                                Tools.animationUser(Float_image, false, 500, 0.5f, 1f, 1f, 0.5f, 0f, false, 0, 360);
                            }
                        }
                    },500);
                }
            }
            else{
                Logs.Debug("gg============6");
                if (kk == 0) {
                    Logs.Debug("gg============7");
                    scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Logs.Debug("gg============8");
                            if(scrollview_startY!=scrollview_endY) {
                                Logs.Debug("gg============9");
                                Tools.animationUser(Float_image, true, 500, 0.5f, 1f, 1f, 0.5f, 0f, false, 0, -360);
                            }
                        }
                    },100);
                }
                kk = 1;
            }
            if(scrollview_flage){
                Logs.Debug("gg============10");
                scrollview_flage=false;
                scrollview_startY=scrollView.getScrollY();
            }else{
                Logs.Debug("gg============11");
                scrollview_flage=true;
                scrollview_endY=scrollView.getScrollY();
            }
        }
    });
}

    @Override
    public void refreshView() {

    }
}
