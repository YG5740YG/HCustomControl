package newhome.baselibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import MyView.ListViewDeleteSlideView;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/2/6.
 */
//向左滑动Item，出现删除按钮，点击删除按钮，执行删除事件
public class FruitListViewDeleteAdapter extends BaseAdapter implements ListViewDeleteSlideView.OnSlideListener{
    private static final String TAG="FruitListViewDeleteAdapter";
    private Context mcontext;
    private LayoutInflater mInflater;
    private List<FruitInfoListMoel> mFruitInfoList=new ArrayList<FruitInfoListMoel>();
    // 上次显示了删除按钮的itemView；
    private ListViewDeleteSlideView listViewDeleteSlideView;
   public FruitListViewDeleteAdapter(Context context,List<FruitInfoListMoel> fruitInfoListMoels) {
        mcontext = context;
        mFruitInfoList=fruitInfoListMoels;
        mInflater = LayoutInflater.from(mcontext);
    }
    @Override
    public int getCount() {
        if(mFruitInfoList==null){
            mFruitInfoList=new ArrayList<FruitInfoListMoel>();
        }
        return mFruitInfoList.size();
//        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(mFruitInfoList!=null){
            return mFruitInfoList.get(i);
        }else {
        return null;
        }
    }

    @Override
    public long getItemId(int i) {
        if(mFruitInfoList!=null){
            return i;
        }else {
            return 0;
        }
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        //自定义itemView
        ListViewDeleteSlideView slideView=(ListViewDeleteSlideView)view;
        if(slideView==null){
            View itemView=mInflater.inflate(R.layout.fruitlistitem_adapter,null);
            slideView=new ListViewDeleteSlideView(mcontext);
            slideView.setContentView(itemView);
            slideView.setButtonText("删除");
            holder=new ViewHolder(slideView);
            slideView.setOnSlideListener(this);
            slideView.setTag(holder);
        }else{
            holder=(ViewHolder)slideView.getTag();
        }
        FruitInfoListMoel item=mFruitInfoList.get(i);
        item.listViewDeleteSlideView=slideView;
        item.listViewDeleteSlideView.shrink();

        holder.fruitImage.setImageResource(item.getFruitImageId());
        holder.fruitName.setText(item.getFruitImageName());
        holder.deleteHolder.setOnClickListener(new View.OnClickListener() {//实现删除事件
            @Override
            public void onClick(View view) {
                mFruitInfoList.remove(i);
                notifyDataSetChanged();
            }
        });
        return slideView;
//        return null;
    }
    private static class ViewHolder {//为了提高效率
        public  ImageView fruitImage;
        public  TextView fruitName;
        public  ViewGroup deleteHolder;
//        public  ViewGroup deleteHolder1;
        ViewHolder(View view) {
            fruitImage = (ImageView) view.findViewById(R.id.fruitImage);
            fruitName = (TextView) view.findViewById(R.id.fruitName);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        }
    }
    /**
     * 自定义view接口回调， 更新上一次正在滑动的View
     */
    @Override
    public void onSlide(View view, int status) {
        if(listViewDeleteSlideView!=null && listViewDeleteSlideView!=view){
            listViewDeleteSlideView.shrink();
        }
        if(status==SLIDE_STATUS_ON){
            listViewDeleteSlideView=(ListViewDeleteSlideView)view;
        }
    }
}
