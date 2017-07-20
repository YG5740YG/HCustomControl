package home.mymodel.LSP;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class HomeActivity extends Activity{
    Context mContext;
    private static final String TAG="HomeActivity";
    private static final String KEY_INDEX="index";
    private int mCurrentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext=getApplicationContext();
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment=new HomeFragment();
        transaction.add(R.id.content,homeFragment,null);
        transaction.commit();
        //取出savedInstanceState中保存的值
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
    }

    //存储数据，在onCreate中取出
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX,1);
    }
}
