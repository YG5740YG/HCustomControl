package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.UITools;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/3/21.
 */

public class MySharedPreference  extends BaseActivity {
    EditText edit;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_filestream);
        edit = (EditText) findViewById(R.id.edit);
        context = getApplicationContext();
         UITools.PreferenceSaveData(context);//把数据进行存储
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText(UITools.PreferenceGetData(context));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        String inputText = edit.getText().toString();
    }
    @Override
    public void findViewById() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}