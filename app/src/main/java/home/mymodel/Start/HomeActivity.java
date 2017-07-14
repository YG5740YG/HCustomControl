package home.mymodel.Start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class HomeActivity extends Activity {
    Context mContext;
    TextView mLsTextButton;
    Animation mScaleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ls_activity_home);
        mContext=getApplicationContext();
        mScaleAnimation = AnimationUtils.loadAnimation(this, newhome.baselibrary.R.anim.scale_show);
        findView();
        setUp();
    }
    public void findView(){
        mLsTextButton=(TextView)findViewById(R.id.mLsTextButton);
    }
    public void setUp(){
        mLsTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentHomeFragment=new Intent(mContext, home.mymodel.LSP.HomeActivity.class);
                startActivity(mIntentHomeFragment);
            }
        });
    }
}
