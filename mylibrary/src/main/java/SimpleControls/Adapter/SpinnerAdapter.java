package SimpleControls.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

import SimpleControls.Model.BaseModel;
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

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mContext=context;
        mQueryPrintSearchTypeModels=(List<BaseModel>)objects;
    }
    public void setType(int type){
        mType=type;
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
        CheckedTextView itemTextView = (CheckedTextView) convertView
                .findViewById(R.id.spinner_dropdown_item);
        if (convertView != null) {
            switch (mType) {
                case 0:
                    if (mSelectIndex != mQueryPrintSearchTypeModels.get(position).getIndex()) {
                    itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
                } else {
                    return new View(mContext);
                }
                break;
                case 1:
                    itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
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
            CheckedTextView itemTextView = (CheckedTextView) view
                    .findViewById(R.id.item_textview);
            itemTextView.setText(mQueryPrintSearchTypeModels.get(position).getType());
            mSelectIndex=mQueryPrintSearchTypeModels.get(position).getIndex();
        }
        return view;
    }
}
