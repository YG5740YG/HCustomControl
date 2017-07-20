package home.mymodel.Criminal.MyRecycleView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import home.mymodel.Criminal.CrimeActivity;
import home.mymodel.Criminal.CrimeData;
import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class MyRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private int mType;
    CrimeData mCrimeData;
    Context mContext;
    MyRecyclerVerAdapter mMyRecyclerVerAdapter;
    int mPosition;
    //mType==0
    public TextView mTitleTextView;
    //mType==1;
    public TextView mDateTextView;
    public CheckBox mSolvedCheckBox;
    public MyRecyclerHolder(View view, int mType, Context context,MyRecyclerVerAdapter myRecyclerVerAdapter) {
        super(view);
        view.setOnClickListener(this);
        this.mContext=context;
        this.mType=mType;
        mMyRecyclerVerAdapter=myRecyclerVerAdapter;
        switch (mType) {
            case 0:
                mTitleTextView = (TextView) view;
                break;
            case 1:
                mTitleTextView=(TextView)view.findViewById(R.id.list_item_crime_title_text_view);
                mDateTextView=(TextView)view.findViewById(R.id.list_item_crime_data_text_view);
                mSolvedCheckBox=(CheckBox) view.findViewById(R.id.list_item_crime_check_box);
                break;
        }
    }
    /**
     * 数据绑定
     * @param mData
     * @param position
     */
    public void bindData(Object mData,int position){
        mPosition=position;
        switch (mType) {
            case 0:
                mCrimeData=((List<CrimeData>)mData).get(position);
                mTitleTextView.setText(mCrimeData.getTitle());
                break;
            case 1:
                mCrimeData=((List<CrimeData>)mData).get(position);
                mTitleTextView.setText(mCrimeData.getTitle());
                mDateTextView.setText(mCrimeData.getDate().toString());
                mSolvedCheckBox.setChecked(mCrimeData.isSolved());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (mType) {
            case 0:
                Toast.makeText(mContext,
                        mCrimeData.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                        .show();
                mMyRecyclerVerAdapter.itemMove(mPosition,0);
                break;
            case 1:
                Toast.makeText(mContext,
                        mCrimeData.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(mContext, CrimeActivity.class);
                intent.putExtra("TAG","home.mymodel.Criminal.CrimeFragment");
                intent.putExtra("crimeUUID",mCrimeData.getId());
                mContext.startActivity(intent);
                break;
        }
    }
}
