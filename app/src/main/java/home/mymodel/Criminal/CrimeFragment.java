package home.mymodel.Criminal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.UUID;

import Base.SingleBaseFragment;
import Base.SingleFragmentActivity;
import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class CrimeFragment extends SingleBaseFragment {
    CrimeData mCrime;
    UUID crimeId;
    EditText mTitleFiled;
    Button mDateButton;
    CheckBox mSolvedCheckBox;
    @Override
    protected int createFragmentView() {
        return R.layout.fragment_crime;
    }
    @Override
    protected void findView() {
        mTitleFiled=(EditText)mFragmentView.findViewById(R.id.crime_title);
        mDateButton=(Button)mFragmentView.findViewById(R.id.crime_date);
        mSolvedCheckBox=(CheckBox)mFragmentView.findViewById(R.id.crime_solved);
    }
    @Override
    protected void loadeData() {
        //获取参数方法一
//        crimeId = (UUID) getActivity().getIntent().getSerializableExtra("crimeUUID");
        crimeId=(UUID)getArguments().getSerializable("crimeUUID");
        mCrime = CrimeLabData.get(getActivity()).getCrimeLabData(crimeId);
    }
    @Override
    protected void setUp() {
        mTitleFiled.setText(mCrime.getTitle());
        mDateButton.setText(mCrime.getDate().toString());
        mSolvedCheckBox.setChecked(mCrime.isSolved());
    }
}
