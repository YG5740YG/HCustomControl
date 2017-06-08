package newhome.baselibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class ListAdapter extends BaseAdapter  {
    Context mcontext;
    List<String> fruitInfoList;
    public ListAdapter(Context context, List<String> fruitInfolist){
        this.mcontext=context;
        this.fruitInfoList=fruitInfolist;
    }

    @Override
    public int getItemViewType(int position) {//列表需要加载多种类型的时候使用，此列只有一个布局所以返回0
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 3;
    }//列表需要加载多种类型的时候使用，此时最多可以加载三种布局

    @Override
    public int getCount() {
        if(fruitInfoList!=null&&fruitInfoList.size()>0){
            return fruitInfoList.size();
        }
        return 0;
    }
    @Override
    public Object getItem(int i) {
        if(fruitInfoList!=null&&fruitInfoList.size()>0){
            return fruitInfoList.get(i);
        }
        return null;
    }
    @Override
    public long getItemId(int i) {
        if(fruitInfoList!=null&&fruitInfoList.size()>0) {
            return i;
        }
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        final FruitInfoListMoel fruitInfo=(FruitInfoListMoel)getItem(i);
        View viewItem;
        ViewHolder viewHolder;
        LinearLayout layout;
        switch (getItemViewType(i)) {//加载多种布局情况
            case 0:
                if(view==null) {
                    viewItem = LayoutInflater.from(mcontext).inflate(R.layout.listview_item_style_for_map, null);
                    viewHolder=new ViewHolder();
                    viewHolder.title=(TextView) viewItem.findViewById(R.id.title);
                    viewItem.setTag(viewHolder); //  将ViewHolder 存储在View 中
                }else{
                    viewItem=view;
                    viewHolder = (ViewHolder) view.getTag(); //  重新获取ViewHolder
                }
                viewHolder.title.setText(fruitInfoList.get(i));
                break;
            case 1:
                viewItem=null;
                break;
            case 2:
                viewItem=null;
                break;
            default:
                viewItem = LayoutInflater.from(mcontext).inflate(R.layout.fruitlistitem_adapter, null);
                break;
        }
        return viewItem;
    }

    class ViewHolder {//为了提高效率
        TextView title;
    }
}
