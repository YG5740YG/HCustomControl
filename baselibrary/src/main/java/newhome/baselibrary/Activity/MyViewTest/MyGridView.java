package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/5.
 */

public class MyGridView extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    GridView mgridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        findViewById();
        fruitInfoList=new ArrayList<FruitInfoListMoel>();
        mcontext=this;
        initFruits();
        findViewById();
        setGridView_limit(mgridView);
        fruitListViewAdapter=new FruitListViewAdapter(mcontext,fruitInfoList);
        mgridView.setAdapter(fruitListViewAdapter);
    }
    //动态设置gridview的属性
    private void setGridView_limit(GridView gv) {
        int size = fruitInfoList.size();
        int row=fruitInfoList.size()/3;//把列表中的数据分为三行显示
        int width=mcontext.getResources().getDisplayMetrics().widthPixels;//屏幕的宽度
        int callwidth=width/3;//设置每列的宽度
        int rowwidth=  (fruitInfoList.size()/2) * (width/3);//设置每行的长度，屏幕宽度的三分之一乘以一半的列表数据量
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (fruitInfoList.size()/2) * (width/3), LinearLayout.LayoutParams.MATCH_PARENT);
        gv.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gv.setColumnWidth(callwidth); // 设置列表项宽
        gv.setNumColumns(row); // 设置列数量,总数量分为三列
//        gv.setNumColumns(size); // 设置列数量=列表集合数
    }
    @Override
    public void findViewById() {
        mgridView =(GridView)findViewById(R.id.gv);
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
