package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import MyView.IPtrHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Tools;
import newhome.baseres.view.*;
import newhome.baseres.view.MyReboundScrollView;

/**
 * Created by 20160330 on 2017/2/7.
 */

public class MyPtrClassicFrameLayoutView extends BaseActivity {
    Context mcontext;
    private PtrClassicFrameLayout mPullRefreshScrollView;//下拉刷新控件，用于布局下拉刷新
    IPtrHeader header;//下拉刷新header
    public LinearLayout toolbar;
    newhome.baseres.view.MyReboundScrollView scrollView;
    int scrollview_startY=0;
    int scrollview_endY=0;
    int kk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ptrframelayout);
        findViewById();
        mcontext=this;
        findViewById();
        header=new IPtrHeader(mcontext,toolbar);
        mPullRefreshScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullRefreshScrollView.autoRefresh();
            }
        }, 50);//延迟50秒刷新
        mPullRefreshScrollView.setHeaderView(header);
        mPullRefreshScrollView.addPtrUIHandler(header);
        mPullRefreshScrollView.disableWhenHorizontalMove(true);
        mPullRefreshScrollView.setLastUpdateTimeRelateObject(this);
        mPullRefreshScrollView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
//                refreh_flage=true;
                refreshView();
            }
        });
        scrollView_interface();//滑动时改变标题头的颜色
    }
    @Override
    public void findViewById() {
        mPullRefreshScrollView=(PtrClassicFrameLayout)findViewById(R.id.pull_refresh_scrollview_home);
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        scrollView=(MyReboundScrollView) findViewById(R.id.ReboundScroll);    kk=0;
    }
    //实现重写scrollview的方法，滑动时对toolbar的背景色进行改变
    public void scrollView_interface() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                                                                        @Override
                                                                        public void onScrollChanged() {
                                                                            if (scrollView.getScrollY() >= 0 && scrollView.getScrollY() <= 255) {
                                                                                toolbar.setBackgroundColor(Color.argb(scrollView.getScrollY(), 255, 103, 0));
                                                                            }
                                                                        }
                                                                    });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (scrollView.getScrollY() >= 0 && scrollView.getScrollY() <= 255) {
                            toolbar.setBackgroundColor(Color.argb(scrollView.getScrollY(), 255, 103, 0));
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN:
                        if (scrollView.getScrollY() >= 0 && scrollView.getScrollY() <= 255) {
                            toolbar.setBackgroundColor(Color.argb(scrollView.getScrollY(), 255, 103, 0));
                        }
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {
        mPullRefreshScrollView.refreshComplete();
    }
}
