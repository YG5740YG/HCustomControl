package newhome.baselibrary.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/2/6.
 */

public class ViewPageAdapter extends PagerAdapter {
    List<FruitInfoListMoel> fruitInfoList;
    Context mcontext;
    public ViewPageAdapter(List<FruitInfoListMoel> fruitInfoList, Context context) {
        this.fruitInfoList = fruitInfoList;
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        if(fruitInfoList!=null&&fruitInfoList.size()>0){
            return fruitInfoList.size();
        }
        return 0;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
//        return false;
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView((View) object);
    }
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
            ImageView iv = new ImageView(mcontext);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片为拉伸填充
            iv.setImageResource(fruitInfoList.get(position).getFruitImageId());
            container.addView(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logs.Debug("gg======clicke=="+fruitInfoList.get(position).getFruitImageName());
                }
            });
            return iv;
    }

}
