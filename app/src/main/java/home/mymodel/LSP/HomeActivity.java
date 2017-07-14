package home.mymodel.LSP;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class HomeActivity extends Activity{
    Context mContext;
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
    }
}
