package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import MyView.KeybordPPWindow;
import MyView.MDProgressDialog;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/9.
 */

public class MyMDProgressDialog extends BaseActivity {
    TextView ppwindow;
    KeybordPPWindow keybordPPWindow;
    Context mcontext;
    EditText price;
    int itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        findViewById();
        mcontext=this;
        ppwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //自定义布局dailog
//                final MyProgressBar myDialog=new MyProgressBar(mcontext,"加载中。。。");
                final MDProgressDialog myDialog=new MDProgressDialog(mcontext);
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MyToast myToast = new MyToast(MyTextView.this, "nihao", 1000, Gravity.LEFT);
//                    }
                myDialog.show();

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

