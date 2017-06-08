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

import java.util.List;

import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/2/4.
 */

public class FruitListViewAdapter extends BaseAdapter  {

    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    public FruitListViewAdapter(Context context, List<FruitInfoListMoel> fruitInfolist){
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
        final FruitInfoListMoel fruitInfo=(FruitInfoListMoel)getItem(i);
        View viewItem;
        ViewHolder viewHolder;
        LinearLayout layout;
        switch (getItemViewType(i)) {//加载多种布局情况
            case 0:
                if(view==null) {
                    viewItem = LayoutInflater.from(mcontext).inflate(R.layout.fruitlistitem_adapter, null);
                    viewHolder=new ViewHolder();
                    viewHolder.layout=(LinearLayout)viewItem.findViewById(R.id.item_content);
                    viewHolder.fruitImage=(ImageView)viewItem.findViewById(R.id.fruitImage);
                    viewHolder.fruitName=(TextView)viewItem.findViewById(R.id.fruitName);
                    viewItem.setTag(viewHolder); //  将ViewHolder 存储在View 中
                }else{
                    viewItem=view;
                    viewHolder = (ViewHolder) view.getTag(); //  重新获取ViewHolder
                }
                viewHolder.fruitImage.setImageResource(fruitInfo.getFruitImageId());
                viewHolder.fruitName.setText(fruitInfo.getFruitImageName());
                viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Logs.Debug("gg======"+fruitInfo.getFruitImageName());
                        Toast.makeText(mcontext,fruitInfo.getFruitImageName(),Toast.LENGTH_SHORT);
                    }
                });
                break;
            case 1:
                viewItem = LayoutInflater.from(mcontext).inflate(R.layout.fruitlistitem_adapter, null);
                viewHolder=new ViewHolder();
                viewHolder.fruitImage .setImageResource(R.mipmap.chat_bq1);
                viewHolder.fruitName.setText("nihao");
                break;
            case 2:
                viewItem = LayoutInflater.from(mcontext).inflate(R.layout.fruitlistitem_adapter, null);
                viewHolder=new ViewHolder();
                viewHolder.fruitImage .setImageResource(R.mipmap.chat_bq1);
                viewHolder.fruitName.setText("nihao");
                break;
            default:
                viewItem = LayoutInflater.from(mcontext).inflate(R.layout.fruitlistitem_adapter, null);
                break;
        }
        return viewItem;
    }

    class ViewHolder {//为了提高效率
        LinearLayout layout;
        ImageView fruitImage;
        TextView fruitName;
    }
}
