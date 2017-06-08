package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/8.
 */
//上拉记载更多
public class MyLoadMoreListView extends BaseActivity {

   private PtrFrameLayout ptrFrameLayout;
    private LoadMoreListViewContainer moreListViewContainer;
////    private ListView listView;
    private List<String> mockStrList = new ArrayList<>();
    private int start=0;
    private int count=15;//每次展示15个item

    Context mcontext;
    List<FruitInfoListMoel>fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmorelist);
        findViewById();
        fruitInfoList=new ArrayList<FruitInfoListMoel>();
        mcontext=this;
        initFruits();
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.loadmorelist);
//        fruitInfoList=new ArrayList<FruitInfoListMoel>();
//        findViewById();
//        initFruits();
//        1.find the listview
//        mlistView=(ListView)findViewById(R.id.load_more_listview);
        // 为listview的创建一个headerview,注意，如果不加会影响到加载的footview的显示！
        View headerMarginView = new View(this);
        headerMarginView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LocalDisplay.dp2px(20)));
        //自定义一个头部
        mlistView.addHeaderView(headerMarginView);//为listview增加一个头部
        mlistView.addFooterView(headerMarginView);
        //2.绑定模拟的数据
        mockStrList.addAll(getMockData(0,15));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mockStrList);
//        fruitListViewAdapter=new FruitListViewAdapter(mcontext,fruitInfoList);
//        mlistView.setAdapter(fruitListViewAdapter);
        mlistView.setAdapter(adapter);
//        listView.setAdapter(adapter);
//        fruitListViewAdapter=new FruitListViewAdapter(mcontext,fruitInfoList);
//        mlistView.setAdapter(fruitListViewAdapter);
//        3.设置下拉刷新组件和事件监听
        ptrFrameLayout=(PtrFrameLayout)findViewById(R.id.load_more_list_view_ptr_frame);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mlistView, header);
//                return false;
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                 //实现下拉刷新的功能
                Log.i("test", "-----onRefreshBegin-----");
                //延迟500毫秒刷新
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mockStrList.clear();
                        start=0;
                        mockStrList.addAll(getMockData(start,count));
                        ptrFrameLayout.refreshComplete();
                        //第一个参数是：数据是否为空；第二个参数是：是否还有更多数据
                        moreListViewContainer.loadMoreFinish(false,true);//下拉加载控件设置
                        adapter.notifyDataSetChanged();//listView刷新
                    }
                },500);
                //设置延迟200秒刷新数据
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       ptrFrameLayout.autoRefresh();
                    }
                },200);
                initListener();
            }
        });
        //4.加载更多的组件
        moreListViewContainer=(LoadMoreListViewContainer)findViewById(R.id.load_more_list_view_container);
        moreListViewContainer.setAutoLoadMore(true);//设置是否自动加载更多
//        moreListViewContainer.useDefaultHeader();//下拉加载时显示底部，footer
        //5.添加加载更多的事件监听
        moreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                //模拟加载更多的业务处理
                moreListViewContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start++;
                        mockStrList.addAll(getMockData(start*10,count));
                        Logs.Debug("gg======"+mockStrList.size());
                        if(start*10>30){
                            moreListViewContainer.loadMoreFinish(true,false);
                            //以下是加载失败的情节
//                                    int errorCode=0;
//                                    String errormessage="加载失败，点击加载更多";
//                                    moreListViewContainer.loadMoreError(errorCode, errormessage);
                        }else{
                            moreListViewContainer.loadMoreFinish(false,true);
//                                    adapter.notifyDataSetChanged();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },100);
            }
        });
    }
    private void initListener() {
//        listView.setOnItemClickListener(this);//listView OnItemClick事件
    }
    /**
     * 做一个简单的内容数据
     * @param start 开始位置
     * @param count 每次拉取的数量
     * @return
     */
    private List<String> getMockData(int start, int count) {
        List<String> slist = new ArrayList<String>();
        for (int i = start; i < start + count; i++) {
            if(i<30) {
                slist.add("内容编号：" + i);
            }
        }
        return slist;
    }
    private void initFruits() {
        FruitInfoListMoel apple = new FruitInfoListMoel("Apple", R.mipmap.e1);
        fruitInfoList.add(apple);
        FruitInfoListMoel banana = new FruitInfoListMoel("Banana", R.mipmap.e2);
        fruitInfoList.add(banana);
        FruitInfoListMoel orange = new FruitInfoListMoel("Orange", R.mipmap.e3);
        fruitInfoList.add(orange);
        FruitInfoListMoel watermelon = new FruitInfoListMoel("Watermelon", R.mipmap.e4);
        fruitInfoList.add(watermelon);
        FruitInfoListMoel pear = new FruitInfoListMoel("Pear", R.mipmap.e5);
        fruitInfoList.add(pear);
        FruitInfoListMoel grape = new FruitInfoListMoel("Grape", R.mipmap.e6);
        fruitInfoList.add(grape);
        FruitInfoListMoel pineapple = new FruitInfoListMoel("Pineapple", R.mipmap.e7);
        fruitInfoList.add(pineapple);
        FruitInfoListMoel strawberry = new FruitInfoListMoel("Strawberry", R.mipmap.e8);
        fruitInfoList.add(strawberry);
        FruitInfoListMoel cherry = new FruitInfoListMoel("Cherry", R.mipmap.e9);
        fruitInfoList.add(cherry);
        FruitInfoListMoel mango = new FruitInfoListMoel("Mango", R.mipmap.e10);
        fruitInfoList.add(mango);
    }
    @Override
    public void findViewById() {
        mlistView=(ListView)findViewById(R.id.myListView);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}
