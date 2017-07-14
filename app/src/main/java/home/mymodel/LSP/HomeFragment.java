package home.mymodel.LSP;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import home.mymodel.R;
import home.mymodel.Start.Adapter.LsReAdapter;
import home.mymodel.Start.Adapter.SpacesItemDecoration;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.Model.LsWeiBoData;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class HomeFragment extends Fragment {
    View mFragmentView;
    RefreshLayout refreshLayout;
    Context mContext;
    RecyclerView mRecyclerView;
    List<LsWeiBoData>mWeiBoList;
    LsWeiBoData mWeiBoData;
    String mContent;
    LsReAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_home, null);
        refreshLayout = (RefreshLayout)mFragmentView.findViewById(R.id.refreshLayout);
        //recycleView
        mRecyclerView=(RecyclerView)mFragmentView.findViewById(R.id.recyclerview);
        //设置layoutManager
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mContext=getContext();
        }
        mContent="one";
        //设置adapter
        initData();
        mAdapter=new LsReAdapter(mWeiBoList,mContext);
        mRecyclerView.setAdapter(mAdapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(40);
        mRecyclerView.addItemDecoration(decoration);
        setUp();
        return mFragmentView;
    }
    public void initData(){
        mWeiBoList=new ArrayList<>();
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
        mWeiBoData=new LsWeiBoData(1,R.mipmap.one,mContent);
        mWeiBoList.add(mWeiBoData);
    }
    public void initDataOne(){
        mWeiBoData=new LsWeiBoData(2,R.mipmap.two,mContent);
        List<LsWeiBoData.FruitInfo>fruitInfoList=new ArrayList<>();
        for (int i=0;i<8;i++) {
            LsWeiBoData.FruitInfo fruitInfo = new LsWeiBoData.FruitInfo("Apple", R.mipmap.two);
            fruitInfoList.add(fruitInfo);
        }
        mWeiBoData.setFruitInfos(fruitInfoList);
        mWeiBoList.add(mWeiBoData);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void findView(){

    }
    public void setUp(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mContent="two";
                initData();
                initDataOne();//第二种布局数据初始化
                mAdapter.isRefresh(mWeiBoList);
                refreshlayout.finishRefresh(2000);
            }
        });//下拉刷新
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mContent="three";
                initData();
                mAdapter.isRefresh(mWeiBoList);
                refreshlayout.finishLoadmore(2000);
            }
        });//下拉加载
//        Java代码设置优先级最高(刷新头脚)
//设置 Header 为 Material风格
//        refreshLayout.setRefreshHeader(new MaterialHeader(mContext).setShowBezierWave(true));
//设置 Footer 为 球脉冲
//        refreshLayout.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));

//       此方法 设置的Header和Footer的优先级是最低的
//设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
//            }
//        });
    }
}
