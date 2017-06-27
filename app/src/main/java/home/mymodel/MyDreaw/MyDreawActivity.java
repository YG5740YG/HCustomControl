package home.mymodel.MyDreaw;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import home.mymodel.R;


/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class MyDreawActivity extends Activity{
    Context context;
    LinearLayout mLinearLayout;
    //    LayoutInflater factory = LayoutInflater.from(switchbutton.this);
//    View myView = factory.inflate(R.layout.layout_switchbutton, null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(newhome.baselibrary.R.layout.layout_mydreaw);
        context=getApplicationContext();
        findView();
    }
    public void findView() {
        mLinearLayout=(LinearLayout)findViewById(R.id.mydreaw);
        MyDreaw myDreaw=new MyDreaw(context,this);
        mLinearLayout.addView(myDreaw);
    }
}
