package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import MyView.ListViewDelete;
import newhome.baselibrary.Adapter.FruitListViewDeleteAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/6.
 */
//自定义滑动view:ListViewDeleteSlideView
    //自定义listview:ListViewDelete
    //数据适配器：FruitListViewDeleteAdapter
    //布局：listview_delete_item.xml（加载列表的item布局，包含内容容器和额外按钮:可增加，事件在FruitListViewDeleteAdapter写）
    //      fruitlistitem_adapter.xml(加载需要展示的item布局）
public class MyListViewDelete extends BaseActivity{
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewDeleteAdapter fruitListViewDeleteAdapter;
    ListViewDelete mlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewdelete);
        findViewById();
        fruitInfoList=new ArrayList<FruitInfoListMoel>();
        mcontext=this;
        initFruits();
        findViewById();
        fruitListViewDeleteAdapter=new FruitListViewDeleteAdapter(mcontext,fruitInfoList);
        mlistView.setAdapter(fruitListViewDeleteAdapter);
    }
    @Override
    public void findViewById() {
        mlistView=(ListViewDelete)findViewById(R.id.myListView);
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
