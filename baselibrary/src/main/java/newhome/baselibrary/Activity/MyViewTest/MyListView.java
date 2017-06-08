package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/4.
 */

public class MyListView extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    ListView mlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        findViewById();
        fruitInfoList=new ArrayList<FruitInfoListMoel>();
        mcontext=this;
        initFruits();
        fruitListViewAdapter=new FruitListViewAdapter(mcontext,fruitInfoList);
        mlistView.setAdapter(fruitListViewAdapter);
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
