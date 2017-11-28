package SimpleControls.MSpinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntRange;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import SimpleControls.Adapter.SpinnerAdapter;
import SimpleControls.Model.BaseModel;
import yg.customcontrol.com.mylibrary.R;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

@SuppressLint("AppCompatCustomView")
public class MSpinner extends Spinner {
    private List<String> mDatas;
    private Context mContext;
    private List<BaseModel> mQueryPrintSearchTypeModels=new ArrayList<>();
    SpinnerAdapter mSpinnerAdapter;
    ClickItemlisten mClickItemListen;
    int mType=-1;
    public MSpinner(Context context) {
        super(context);
    }

    public MSpinner(Context context, int mode) {
        super(context, mode);
    }

    public MSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }
    public MSpinner setAdapter(List<String> datas,@IntRange(from = 0,to = 3) int type){
        mType=type;
        this.mDatas=datas;
        for (int i=0;i<mDatas.size();i++){
            BaseModel queryPrintSearchTypeModel=new BaseModel(i,mDatas.get(i));
            mQueryPrintSearchTypeModels.add(queryPrintSearchTypeModel);
        }
        mSpinnerAdapter=new SpinnerAdapter(mContext, R.layout.spinner_item,mQueryPrintSearchTypeModels);
        mSpinnerAdapter.setValue(type);
        if(type==3){
            this.setBackgroundResource(R.drawable.main_item_background);
        }
        this.setAdapter(mSpinnerAdapter);
        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mClickItemListen!=null) {
                    mClickItemListen.setClickItem(mQueryPrintSearchTypeModels.get(i).getIndex());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(mClickItemListen!=null) {
                    mClickItemListen.setNothingSelector();
                }
            }
        });
        return this;
    }
    public MSpinner setImageList(List<Integer>imageList){
        if(mType!=-1&&imageList.size()>0){
            mSpinnerAdapter.setImageList(imageList);
        }
        return this;
    }
    public void setTitleImage(int titleImage){
        if(mType!=-1&&titleImage>0) {
            mSpinnerAdapter.setTitleImage(titleImage);
        }
    }
    public interface ClickItemlisten{
        void setClickItem(int index);
        void setNothingSelector();
    }
    public void setClickListen(ClickItemlisten clickItemListen){
        this.mClickItemListen=clickItemListen;
    }
}
