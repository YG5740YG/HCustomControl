package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import MyView.MDToolbar;
import MyView.Refresh.CustomRefreshFooterView;
import MyView.Refresh.CustomRefreshHeadView;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/4/13 0013.
 */
//SwipeToLoadLayout上啦加载，下拉刷新
//    http://www.jianshu.com/p/d69ae409a52c
    /*
    注意，swipetoloadlayout中布局包裹的View id是指定的，不能乱改，否则找不到<item name="swipe_target" type="id" />刷新目标
<item name="swipe_refresh_header" type="id" />刷新头部
<item name="swipe_load_more_footer" type="id" />刷新尾部
     */
public class SwipeToLoadLayoutTestView extends BaseActivity implements MDToolbar.OnMenuClickListener {
    MDToolbar mdToolbar;
    Context context;
    SwipeToLoadLayout swipeToLoadLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipetoloadlayout);
        findViewById();
        setUp();
    }
    @Override
    public void findViewById() {
        swipeToLoadLayout=(SwipeToLoadLayout)findViewById(R.id.swipeToLoad);
        mdToolbar = (MDToolbar) findViewById(R.id.toolbar);
        mdToolbar.setBackTitle(" ");
        mdToolbar.setBackTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setBackIcon(R.mipmap.ic_back_gray);
        mdToolbar.setMainTitle("抽屉，左拉效果");
        mdToolbar.setMainTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setRightTitle("");
        mdToolbar.setRightTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setOnMenuClickListener(this);
        mdToolbar.setToolbarBackgroud(getResources().getColor(R.color.es_w));
    }

    @Override
    public void setUp() {
        this.context=getApplication();
        CustomRefreshHeadView refreshHeadView = new CustomRefreshHeadView(this);
        refreshHeadView.setPadding(20,20,20,20);
        refreshHeadView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
        refreshHeadView.setLayoutParams(lp);

        CustomRefreshFooterView refreshFooterView = new CustomRefreshFooterView(this);
        refreshFooterView.setPadding(20,20,20,20);
        refreshFooterView.setGravity(Gravity.CENTER);
        refreshFooterView.setLayoutParams(lp);
        swipeToLoadLayout.setRefreshHeaderView(refreshHeadView);
        swipeToLoadLayout.setLoadMoreFooterView(refreshFooterView);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void onBackClick() {
//        finish();
        if(swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }
        if(swipeToLoadLayout.isLoadingMore()){
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onRigthClick() {

    }
}

