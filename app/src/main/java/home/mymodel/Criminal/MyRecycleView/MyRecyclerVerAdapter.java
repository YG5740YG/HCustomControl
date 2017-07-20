package home.mymodel.Criminal.MyRecycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class MyRecyclerVerAdapter<T> extends RecyclerView.Adapter<MyRecyclerHolder>{
    //        private List<CrimeData> mCrimes;
    private T mDatas;
    private int mItemView;
    private Context mContext;
    private int mType;
    private boolean mHasItemView;
    /**
     * 构造函数一：参数中具有recycleView需要加载的item布局
     * @param datas  数据
     * @param itemView  需要加载的item布局
     * @param context
     * @param type  加载逻辑判断
     */
    public MyRecyclerVerAdapter(T datas, int itemView, Context context,int type){
        this.mDatas=datas;
        this.mItemView=itemView;
        this.mContext=context;
        this.mType=type;
        mHasItemView=true;
    }
    /**
     * 构造函数二：参数中具没有recycleView需要加载的item布局
     * @param datas 数据
     * @param context
     * @param type 加载逻辑判断
     */
    public MyRecyclerVerAdapter(T datas, Context context,int type){
        this.mDatas=datas;
        this.mContext=context;
        this.mType=type;
        mHasItemView=false;
    }
    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHasItemView) {
            return new MyRecyclerHolder((new ViewHolder(parent,viewType,mType,mContext,mItemView)).getView(),mType,mContext,this);
        }else{
            return new MyRecyclerHolder((new ViewHolder(parent,viewType,mType,mContext)).getView(),mType,mContext,this);
        }
    }
    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, int position) {
       holder.bindData(mDatas,position);
    }
    @Override
    public int getItemViewType(int position) {
        return (new ViewItemType(mType)).getItemType();
    }
    @Override
    public int getItemCount() {
        return (new ViewSize(mType,mDatas)).getSize();
    }
    public void refresh(T datas){
        this.mDatas=datas;
        notifyDataSetChanged();
    }
    public void itemMove(int start,int end){
        notifyItemMoved(start,end);
    }
}
