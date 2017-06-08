package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import MyView.DividerGridItemDecoration;
import newhome.baselibrary.Adapter.FruitRecyclerViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/5.
 */
//基本上使用RecycleView进行布局，具有高度的耦合性，性能较好，省去创建ViewHolder的时间
public class MyRecyclerView extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitRecyclerViewAdapter fruitRecyclerViewAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        findViewById();
        fruitInfoList=new ArrayList<FruitInfoListMoel>();
        mcontext=this;
        initFruits();
        findViewById();
        //适配器，加载数据
        fruitRecyclerViewAdapter=new FruitRecyclerViewAdapter(mcontext,fruitInfoList);
        rv.setAdapter(fruitRecyclerViewAdapter);
        //增加分自定义割线
        //增加分割线，模仿listview
//        rv.addItemDecoration(new newhome.myviwe.View.DividerItemDecoration(mcontext,LinearLayoutManager.HORIZONTAL));
        //增加分割线，模仿gridview
        rv .addItemDecoration(new DividerGridItemDecoration(mcontext));//上下左右都有分割线
    }

    @Override
    public void findViewById() {
        rv=(RecyclerView) findViewById(R.id.rv);
        //自定义行与列
        //瀑布流式布局
//        rv.setHasFixedSize(true);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.offsetChildrenHorizontal(100);
//        rv.setLayoutManager(layoutManager);
        //默认为列
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        rv.setLayoutManager(layoutManager);
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);//设置为垂直布局，默认为垂直布局
        //网格布局
//        LinearLayoutManager layoutManager=new GridLayoutManager();
        //默认为行
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);//设置为垂直布局，默认为垂直布局
        rv.setHasFixedSize(true);//保持固定大小，该信息被用于自身的优化
        //GridView的效果
//        rv.setLayoutManager(new GridLayoutManager(mcontext,4));

        //设置增加或删除条目的动画
        rv.setItemAnimator( new DefaultItemAnimator());
    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

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
}
