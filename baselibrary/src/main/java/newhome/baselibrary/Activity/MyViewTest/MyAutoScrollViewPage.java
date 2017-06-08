package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import newhome.baselibrary.Adapter.ViewPageAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/6.
 */

public class MyAutoScrollViewPage extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    ViewPager autoScrollViewPager;
    CircleIndicator indicator;//滚动图片的指示器控件
    ViewPageAdapter viewPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpagecircleindicator);
        findViewById();
        fruitInfoList=new ArrayList<FruitInfoListMoel>();
        mcontext=this;
        initFruits();
        findViewById();
//        fruitListViewAdapter=new FruitListViewAdapter(mcontext,fruitInfoList);
//        autoScrollViewPager.setAdapter(fruitListViewAdapter);
    }
    @Override
    public void findViewById() {
        autoScrollViewPager=(ViewPager)findViewById(R.id.ViewFlipper1);
        indicator=(CircleIndicator)findViewById(R.id.indicator);
        //设置viewpage高宽
//        int width= mcontext.getResources().getDisplayMetrics().widthPixels;
        if(fruitInfoList!=null) {
            if (fruitInfoList.size() > 0) {
                //图片轮播控件使用
                if (fruitInfoList.size() > 0) {
                    viewPageAdapter = new ViewPageAdapter(fruitInfoList, mcontext);
                    autoScrollViewPager.setAdapter(viewPageAdapter);//适配器加载数据
                    indicator.setViewPager(autoScrollViewPager); //icon  导航栏图片轮转中，圆点指示器初始化
                }
//                autoScrollViewPager.stopAutoScroll();
//                autoScrollViewPager.setInterval(3000);//图片轮播的时间
//                autoScrollViewPager.startAutoScroll();//图片开始滚动
                autoScrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % fruitInfoList.size());
                autoScrollViewPager.setCurrentItem(0);
            }
        }
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
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}
