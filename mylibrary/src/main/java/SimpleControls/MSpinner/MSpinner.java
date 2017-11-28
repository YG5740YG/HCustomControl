package SimpleControls.MSpinner;

import android.annotation.SuppressLint;
import android.content.Context;
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
    BaseModel queryBasePrintSearchTypeModel;
    private List<BaseModel> mQueryPrintSearchTypeModels=new ArrayList<>();
    SpinnerAdapter mSpinnerAdapter;
    ClickItemlisten mClickItemListen;
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
    public void setAdapter(List<String> datas, int type){
        this.mDatas=datas;
        for (int i=0;i<mDatas.size();i++){
            BaseModel queryPrintSearchTypeModel=new BaseModel(i,mDatas.get(i));
            mQueryPrintSearchTypeModels.add(queryPrintSearchTypeModel);
        }
        mSpinnerAdapter=new SpinnerAdapter(mContext, R.layout.spinner_item,mQueryPrintSearchTypeModels);
        mSpinnerAdapter.setType(1);
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
    }
    public interface ClickItemlisten{
        void setClickItem(int index);
        void setNothingSelector();
    }
    public void setClickListen(ClickItemlisten clickItemListen){
        this.mClickItemListen=clickItemListen;
    }
}
