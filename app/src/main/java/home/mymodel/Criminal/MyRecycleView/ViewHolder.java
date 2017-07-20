package home.mymodel.Criminal.MyRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class ViewHolder {
    private View mView;
    public ViewHolder(ViewGroup parent,int viewType, int mType, Context mContext,int mItemView){
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        switch (mType){
            case -1://CRIME_RECYCLE_VIEW_TYPT
                break;
            default:
                mView=layoutInflater.inflate(mItemView,parent,false);
        }
    }
    public ViewHolder(ViewGroup parent, int viewType, int mType, Context mContext){
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        switch (mType){
            case 0://CRIME_RECYCLE_VIEW_TYPT
                mView=layoutInflater.inflate(R.layout.simple_list_item_1,parent,false);
                break;
            case 1:mView=layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            break;
            default:
                mView=new View(mContext);
        }
    }
    public View getView(){
        return mView;
    }
}
