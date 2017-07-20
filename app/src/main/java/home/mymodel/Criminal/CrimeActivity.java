package home.mymodel.Criminal;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import Base.SingleFragmentActivity;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class CrimeActivity extends SingleFragmentActivity {
    private static String TAG_CrimeListFragment="home.mymodel.Criminal.CrimeListFragment";
    private static String TAG_CrimeFragment="home.mymodel.Criminal.CrimeFragment";
    @Override
    protected Fragment createFragment() {
//        return new CrimeFragment();
        if(getIntent().hasExtra("TAG")) {
            if (getIntent().getStringExtra("TAG").equals(TAG_CrimeListFragment)) {
                return new CrimeListFragment();
            } else if (getIntent().getStringExtra("TAG").equals(TAG_CrimeFragment)) {
                Bundle args=new Bundle();
                args.putSerializable("crimeUUID",getIntent().getSerializableExtra("crimeUUID"));
                CrimeFragment crimeFragment=new CrimeFragment();
                crimeFragment.setArguments(args);
                return crimeFragment;
            } else {
                return new CrimeListFragment();
            }
        }else{
            return new CrimeListFragment();
        }
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void loadeData() {

    }
}
