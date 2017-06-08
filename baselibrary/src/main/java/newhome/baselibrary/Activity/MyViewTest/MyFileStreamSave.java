package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/3/21.
 */

public class MyFileStreamSave  extends BaseActivity {
    EditText edit;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_filestream);
        edit = (EditText) findViewById(R.id.edit);
        context = getApplicationContext();
        UITools.save("new DataTest",context);//经过测试保存对了
        edit.setText(UITools.load(context));//获取到了
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
