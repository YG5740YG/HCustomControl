package newhome.baselibrary.Activity.MyViewPagerTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Adapter.TableViewPageAdapter;
import newhome.baselibrary.Fragment.TableLayoutAndViewPageFragment;
import newhome.baselibrary.Fragment.TableLayoutAndViewPageFragmentOther;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;

/**
 * Created by 20160330 on 2017/2/7.
 */
//viewpage加载两个fragment
    //设计好需要加载的fragment
public class MyViewPagerAndTableLayout extends Fragment {

    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    ViewPager viewPager;
    TabLayout tableLayout;
    List<String> titleList;//给tableLayout 增加选择内容，也就是viewpage标题
    View mFragmentView;//加载的fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.tablelayoutviewpager, null);
        findView();
        initViews();
        mcontext=getActivity();
        return mFragmentView;
    }
    public void findView() {
        viewPager=(ViewPager)mFragmentView.findViewById(R.id.vp);
        tableLayout=(TabLayout) mFragmentView.findViewById(R.id.tl);
    }
    //初始化tablelayout和写入需要加载的fragment
    private void initViews() {
        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        titleList = new ArrayList<>();
        titleList.add("我喜欢的");
        titleList.add("你喜欢的");
//        titleList.add("-更多供您考虑的商品-");
        //设置TabLayout的模式
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.font_dark));
        //为TabLayout添加tab名称
        tableLayout.addTab(tableLayout.newTab().setText(titleList.get(0)));
        tableLayout.addTab(tableLayout.newTab().setText(titleList.get(1)));
        final List<Fragment> fragmentList = getFragments();
        FragmentManager fragmentManager=getFragmentManager();
        TableViewPageAdapter myVPAdapter = new TableViewPageAdapter(getFragmentManager(), getResources(), fragmentList, titleList);
        //headerViewPager加载adapter
        viewPager.setAdapter(myVPAdapter);
        viewPager.setCurrentItem(0);
        //TabLayout加载viewpager
        tableLayout.setupWithViewPager(viewPager);//把viewpage与tablelayout关联
    }
    //设置需要加载的fragment
    TableLayoutAndViewPageFragment fragment1;
    TableLayoutAndViewPageFragmentOther fragment2;
    private List<Fragment> getFragments() {
        final FragmentManager manager = getFragmentManager();
        final List<Fragment> list = new ArrayList<>();
        if (fragment1 == null) {
            fragment1 =new TableLayoutAndViewPageFragment();
        }
        if (fragment2 == null) {
            fragment2 =new TableLayoutAndViewPageFragmentOther();
        }
        Collections.addAll(list, fragment1, fragment2);
        return list;
    }
}
