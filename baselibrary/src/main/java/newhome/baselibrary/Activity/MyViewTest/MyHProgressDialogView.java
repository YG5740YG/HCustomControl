package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import MyView.MyHProgressBar;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/9.
 */

public class MyHProgressDialogView extends BaseActivity {
    TextView ppwindow;
    Context mcontext;
    EditText price;
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
                final MyHProgressBar myDialog=new MyHProgressBar(mcontext);
                myDialog.Show();
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
