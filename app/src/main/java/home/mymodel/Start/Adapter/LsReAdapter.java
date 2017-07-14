package home.mymodel.Start.Adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import MyView.DividerGridItemDecoration;
import home.mymodel.R;
import newhome.baselibrary.Adapter.FruitRecyclerViewAdapter;
import newhome.baselibrary.Model.LsWeiBoData;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
//http://blog.csdn.net/tiankong1206/article/details/47088995  recycleView瀑布流
public class LsReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //模式4
    private static final int TYPT_TOP=4;
    //模式1
    private static final int TYPE_SINGLE = 0;
    //模式2
    private static final int TYPE_MULTI = 1;
    //模式3
    private static final int TYPE_HEADER = 2;
    private List<LsWeiBoData> weiBoDatas;
    private Context mContext;
    public LsReAdapter(List<LsWeiBoData> list,Context context) {
        weiBoDatas=list;
        this.mContext=context;
    }
    //    根据viewType值，实例化不同类型的ViewHolder对象。
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER:return new MasonryView(LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_recycleview_item, parent, false));
            case TYPE_SINGLE:return new MasonryViewOne(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false));
            case TYPE_MULTI:return new MasonryViewTwo(LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_recycleview_item, parent, false));
            case TYPT_TOP:return new MasonryViewTop(LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_layout_top,parent,false));
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        if (holder instanceof MasonryView) {
            setMasonryView((MasonryView)holder,position);
        }else if(holder instanceof MasonryViewOne){
            setMasonryViewOne((MasonryViewOne)holder,position);
        }else if(holder instanceof MasonryViewTwo){
            setMasonryViewTwo((MasonryViewTwo)holder,position);
        }
    }
    @Override
    public int getItemCount() {
        return weiBoDatas.size();
    }
    //返回不同的模式
    @Override
    public int getItemViewType(int position) {
        //所有的新闻详情数据，在第一个位置自己手动加入了一个假的新闻数据，把它的title设置为“头布局”
        //识别出“头布局”，则是第一个数据，把它归于头布局类型
        if(weiBoDatas.get(position).getmType()==1){
            return TYPE_HEADER;
        }
        //有新闻具体内容，是普通新闻
        if (weiBoDatas.get(position).getmType()==2)
        {
            return TYPE_SINGLE;
        }
        if(weiBoDatas.get(position).getmType()==4){
            return TYPT_TOP;
        }
        //其他情况，是图集新闻
        return TYPE_MULTI;
    }
//创建不同的ViewHolder类，针对不同的布局类型，进行对应的布局内控件的初始化
    public static class MasonryView extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
            textView= (TextView) itemView.findViewById(R.id.masonry_item_title);
        }
    }
    //嵌入recycleView
    public static class MasonryViewOne extends  RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public MasonryViewOne(View itemView){
            super(itemView);
            recyclerView= (RecyclerView) itemView.findViewById(R.id.rv );
        }
    }
    public static class MasonryViewTwo extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MasonryViewTwo(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
            textView= (TextView) itemView.findViewById(R.id.masonry_item_title);
        }
    }
    //模式4  头部模式
    public static class MasonryViewTop extends  RecyclerView.ViewHolder{
        ImageView imageView;
        public MasonryViewTop(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.mLsModeFourImage);
        }
    }
//根据不同的模式和布局设置不同的值
    public void setMasonryView(MasonryView holder,int position){
        holder.imageView.setImageResource(weiBoDatas.get(position).getImg());
        holder.textView.setText(weiBoDatas.get(position).getTitle());
    }
    //嵌套recycleView
    private void setMasonryViewOne(MasonryViewOne holder, int position) {
//        LinearLayoutManager layoutManager=new GridLayoutManager();
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);//设置为垂直布局，默认为垂直布局
//        holder.recyclerView.setHasFixedSize(true);//保持固定大小，该信息被用于自身的优化
        //设置增加或删除条目的动画
        holder.recyclerView.setItemAnimator( new DefaultItemAnimator());
        //加载数据
        for(int i=0;i<weiBoDatas.get(position).getFruitInfos().size();i++) {
            Logs.Debug("gg==============getFruitInfos==" +weiBoDatas.get(position).getFruitInfos().get(i).getFruitImageName());
        }
       LsChildReAdapter lsChildReAdapter=new LsChildReAdapter(mContext,weiBoDatas.get(position).getFruitInfos());
        holder.recyclerView.setAdapter(lsChildReAdapter);
        //增加分割线，模仿gridview
        holder.recyclerView .addItemDecoration(new DividerGridItemDecoration(mContext));//上下左右都有分割线
    }
    private void setMasonryViewTwo(MasonryViewTwo holder, int position) {
    }
    public void isRefresh(List<LsWeiBoData> weiBoDatas) {
        this.weiBoDatas=weiBoDatas;
        notifyDataSetChanged();
    }
}
