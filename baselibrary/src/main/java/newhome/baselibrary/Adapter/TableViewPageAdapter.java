package newhome.baselibrary.Adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 20160330 on 2017/2/7.
 */

public class TableViewPageAdapter extends FragmentPagerAdapter {
    private Resources mResources;
    private List<Fragment> mFragments;
    private List<String> titleList;
    private int mChildCount = 0;
    public TableViewPageAdapter(FragmentManager fm , Resources r, List<Fragment> fragments, List<String> titleList) {
        super(fm);
        this.mResources = r;
        this.mFragments = fragments;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments!=null&&mFragments.size()>0){
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(mFragments!=null&&mFragments.size()>0){
            return mFragments.size();
        }
        return 0;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(titleList!=null&&titleList.size()>0){
            return titleList.get(position);
        }
       return null;
    }
    public void refresh(){
        notifyDataSetChanged();
    }
}
