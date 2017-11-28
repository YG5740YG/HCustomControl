package SimpleControls.Adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import SimpleControls.Model.BaseModel;
import me.grantland.widget.AutofitTextView;
import yg.customcontrol.com.mylibrary.R;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class SpinnerAdapter extends ArrayAdapter {
    private Context mContext;
    private List<BaseModel> mQueryPrintSearchTypeModels;
    int mSelectIndex =0;
    int mType=0;
    private List<Integer>mImageList=new ArrayList<>();
    private int mTitleImage=R.mipmap.ic_launcher;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mContext=context;
        mQueryPrintSearchTypeModels=(List<BaseModel>)objects;
    }
    public void setValue(@IntRange(from = 0,to = 3)int type){
        mType=type;
    }
    public void setImageList(List<Integer>imageList){
        mImageList=imageList;
    }
    public void setTitleImage(int titleImage){
        mTitleImage=titleImage;
    }
    @Override
    public int getCount() {
        return mQueryPrintSearchTypeModels.size();
    }

    @Override
    public Object getItem(int i) {
        return  mQueryPrintSearchTypeModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(mContext);
        convertView = layoutInflater1.inflate(R.layout.spinner_dropdown_item, null);
        AutofitTextView itemTextView = (AutofitTextView) convertView
                .findViewById(R.id.spinner_dropdown_item);
        ImageView itemImageView=(ImageView)convertView.findViewById(R.id.item_image);
        if (convertView != null) {
            switch (mType) {
                case 0:
                    if (mSelectIndex != mQueryPrintSearchTypeModels.get(position).getIndex()) {
                        itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
                    } else {
                        return new View(mContext);
                    }
                    itemImageView.setVisibility(View.GONE);
                    break;
                case 1:
                    itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
                    itemImageView.setVisibility(View.GONE);
                    break;
                case 2:
                case 3:
                    itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
                    if(mImageList.size()>0){
                        itemImageView.setImageResource(mImageList.get(position));
                    }
                    break;
            }
        }
        return convertView;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.spinner_item, null);
        if (view != null) {
                AutofitTextView itemTextView = (AutofitTextView) view.findViewById(R.id.item_textview);
                ImageView itemImageView=(ImageView)view.findViewById(R.id.item_image);
                itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
                mSelectIndex = mQueryPrintSearchTypeModels.get(position).getIndex();
                switch (mType){
                    case 0:
                    case 1:
                    case 2:
                        itemImageView.setVisibility(View.GONE);
                        break;
                    case 3:
                            itemImageView.setImageResource(mTitleImage);
                        break;
                }
        }
        return view;
    }
}
