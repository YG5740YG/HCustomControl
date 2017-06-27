package newhome.baselibrary.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import newhome.baselibrary.R;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class AnimationAnimUse extends Activity {

    Button scaleBtn ;
    Animation scaleAnimation;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_animuse);
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_show);
        scaleBtn = (Button)findViewById(R.id.btn_animation);
        tv =(TextView)findViewById(R.id.tv);
        scaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tv.startAnimation(scaleAnimation);
            }
        });

    }

}
