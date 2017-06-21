package newhome.baselibrary.Activity.Swutchbutton;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class switchbutton extends Activity{
    SwitchButton switchButton1;
    Context context;
//    LayoutInflater factory = LayoutInflater.from(switchbutton.this);
//    View myView = factory.inflate(R.layout.layout_switchbutton, null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_switchbutton);
        context=getApplicationContext();
        findView();
    }
    public void findView() {
        switchButton1 = (SwitchButton)findViewById(R.id.switchButton);
        switchButton1.isChecked();
        switchButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Tools.getVersionInfo(context);
            }
        });
    }
}
