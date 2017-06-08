package newhome.baselibrary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/2/5.
 */

public class FruitRecyclerViewAdapter extends RecyclerView.Adapter{
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitInfoListMoel fruitInfoListMoel;
    public FruitRecyclerViewAdapter(Context context, List<FruitInfoListMoel> fruitInfolist){
        this.mcontext=context;
        this.fruitInfoList=fruitInfolist;
        fruitInfoListMoel=new FruitInfoListMoel("banana", R.mipmap.e11);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =new View(mcontext);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruitlistitem_adapter, null);
        return new myViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myViewHolder holder1 = (myViewHolder) holder;
        Logs.Debug("gg========="+fruitInfoList.get(position).getFruitImageName()+"=="+fruitInfoList.get(position).getFruitImageId());
            holder1.fruitName.setText(fruitInfoList.get(position).getFruitImageName());
            holder1.fruitImage.setImageResource(fruitInfoList.get(position).getFruitImageId());
//            AsynImageUtil.display(R.mipmap.e1, holder1.fruitImage);
            holder1.fruitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logs.Debug("gg========"+fruitInfoList.get(position).getFruitImageName());
                }
            });
        holder1.layout.setOnLongClickListener(new View.OnLongClickListener() {//长按删除并更新列表
            @Override
            public boolean onLongClick(View view) {
                Logs.Debug("gg==========51");
                removeData(position);
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        if(fruitInfoList!=null&&fruitInfoList.size()>0){
            return fruitInfoList.size();
        }
        return 0;
    }
    public void addData( int position) {//增加条目并刷新列表
        fruitInfoList.add(position, fruitInfoListMoel);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, fruitInfoList.size());
    }
    public void removeData( int position) {//删除条目并刷新列表
        fruitInfoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fruitInfoList.size());
    }
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        LinearLayout layout;
        ImageView fruitImage;
        TextView fruitName;
        public myViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_content);
            fruitImage = (ImageView) itemView.findViewById(R.id.fruitImage);
            fruitName =(TextView) itemView.findViewById(R.id.fruitName);
            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Logs.Debug("gg========11");
        }

        @Override
        public boolean onLongClick(View v) {
            Logs.Debug("gg========12");
//            removeData(v.getId());
            return false;
        }
    }
}
/**
 * public final void notifyDataSetChanged()
 public final void notifyItemChanged(int position)
 public final void notifyItemRangeChanged(int positionStart, int itemCount)
 public final void notifyItemInserted(int position)
 public final void notifyItemMoved(int fromPosition, int toPosition)
 public final void notifyItemRangeInserted(int positionStart, int itemCount)
 public final void notifyItemRemoved(int position)
 public final void notifyItemRangeRemoved(int positionStart, int itemCount)

 notifyDataSetChanged()这个方法跟我们平时用到的ListView的Adapter的方法一样，这里就不多做描述了。
 notifyItemChanged(int position)，当position位置的数据发生了改变时就会调用这个方法，就会回调对应position的onBindViewHolder()方法了，当然，因为ViewHolder是复用的，所以如果position在当前屏幕以外，也就不会回调了，因为没有意义，下次position滚动会当前屏幕以内的时候同样会调用onBindViewHolder()方法刷新数据了。其他的方法也是同样的道理。public final void notifyItemRangeChanged(int positionStart, int itemCount)，顾名思义，可以刷新从positionStart开始itemCount数量的item了（这里的刷新指回调onBindViewHolder()方法）。
 public final void notifyItemInserted(int position)，这个方法是在第position位置被插入了一条数据的时候可以使用这个方法刷新，注意这个方法调用后会有插入的动画，这个动画可以使用默认的，也可以自己定义。
 public final void notifyItemMoved(int fromPosition, int toPosition)，这个方法是从fromPosition移动到toPosition为止的时候可以使用这个方法刷新
 public final void notifyItemRangeInserted(int positionStart, int itemCount)，显然是批量添加。
 public final void notifyItemRemoved(int position)，第position个被删除的时候刷新，同样会有动画。
 将上述更改运行，点击添加和删除按钮效果图如下：
 public final void notifyItemRangeRemoved(int positionStart, int itemCount)，批量删除。
 */
