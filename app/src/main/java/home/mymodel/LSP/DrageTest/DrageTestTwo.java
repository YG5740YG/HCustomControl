package home.mymodel.LSP.DrageTest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import home.mymodel.R;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class DrageTestTwo extends Activity{
    public static final String  Tag = "MainActivity";

    private TextView creatorBtn;
    private CreatView mCreatView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drage_one);
        mCreatView = CreatView.getCreatView(this);

        creatorBtn = (TextView)this.findViewById(R.id.button1);
        creatorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(Tag, "creatorBtn  click");
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                Log.d(Tag, "x = "+location[0]+",y = "+location[1]);
                mCreatView.setLocation(location);
                mCreatView.addViewToScreen();
            }
        });
    }
}
