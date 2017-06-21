package newhome.baselibrary.Activity.MyViewTest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import MyView.KeybordPPWindow;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Tools;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/4.
 */

public class MyTextView extends BaseActivity {

    TextView ppwindow;
    KeybordPPWindow keybordPPWindow;
    Context mcontext;
    EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.textview);
        findViewById();
        mcontext=this;
        ppwindow.setTextColor(Color.parseColor("#ffffff"));
        ppwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Routers.open(mcontext,"https://www.baidu.com");
//                MyToast myToast=new MyToast(MyTextView.this,"nihao",1000, Gravity.LEFT);
//                Neutra
                Tools.setWindowhalph(MyTextView.this,0.6f);
                keybordPPWindow = new KeybordPPWindow(mcontext, ppwindow, MyTextView.this, price);
            }
        });
    }
    @Override
    public void findViewById() {
        ppwindow=(TextView)findViewById(R.id.ppwindow);
        price=(EditText)findViewById(R.id.price);
    }
    @Override
    public void setUp() {

    }
    @Override
    public void refreshView() {

    }
}
