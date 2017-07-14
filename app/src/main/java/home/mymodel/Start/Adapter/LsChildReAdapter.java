package home.mymodel.Start.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import home.mymodel.R;
import newhome.baselibrary.Model.LsWeiBoData;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class LsChildReAdapter extends RecyclerView.Adapter{
    Context mcontext;
    List<LsWeiBoData.FruitInfo> fruitInfos;
    LsWeiBoData.FruitInfo fruitInfo;
    public LsChildReAdapter(Context context, List<LsWeiBoData.FruitInfo> fruitInfos){
        this.mcontext=context;
        this.fruitInfos=fruitInfos;
        fruitInfo=new LsWeiBoData.FruitInfo("banana", newhome.baselibrary.R.mipmap.e11);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =new View(mcontext);
        view = LayoutInflater.from(parent.getContext()).inflate(newhome.baselibrary.R.layout.fruitlistitem_adapter, null);
        return new myViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myViewHolder holder1 = (myViewHolder) holder;
        Logs.Debug("gg========="+fruitInfos.get(position).getFruitImageName()+"=="+fruitInfos.get(position).getFruitImageId());
        holder1.fruitName.setText(fruitInfos.get(position).getFruitImageName());
        holder1.fruitImage.setImageResource(fruitInfos.get(position).getFruitImageId());
//            AsynImageUtil.display(R.mipmap.e1, holder1.fruitImage);
        holder1.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logs.Debug("gg========"+fruitInfos.get(position).getFruitImageName());
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
        if(fruitInfos!=null&&fruitInfos.size()>0){
            return fruitInfos.size();
        }
        return 0;
    }
    public void addData( int position) {//增加条目并刷新列表
        fruitInfos.add(position, fruitInfo);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, fruitInfos.size());
    }
    public void removeData( int position) {//删除条目并刷新列表
        fruitInfos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fruitInfos.size());
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
