package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import newhome.baselibrary.R;
import newhome.baselibrary.Save.SaveFile.MySQLite;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/3/21.
 */
//查看第一行代码
public class MySQLiteTest extends BaseActivity {
    EditText edit;
    Context context;
    private MySQLite mySQLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_filestream);
        edit = (EditText) findViewById(R.id.edit);
        context = getApplicationContext();
        mySQLite=new MySQLite(context,"BookStore.db",null,2);//初始化数据库，不能低于之前设置的最低版本号，高于之前版本号时，数据库升级执行onUpgrade中的代码
        //如果更新数据库只需要在mySQLite类中的onUpgrade中增加操作语句，初始化数据库时版本增加就行
        mySQLite.CreatSQLite(mySQLite);  //创建数据库，并创建数据表，若改数据表存在，则不进行创建，不存在改表，则进行创建
        mySQLite.insertData(mySQLite);//给book表中装入数据，方法里写了两行数据
        /**
         * 检测
         到当前程序中并没有 BookStore.db这个数据库，于是会创建该数据库并调用 MyDatabaseHelper
         中的 onCreate()方法，这样 Book 表也就得到了创建，然后会弹出一个 Toast 提示创建成功。
         再次点击 Create database按钮时，会发现此时已经存在 BookStore.db 数据库了，因此不会再
         创建一次。
         */
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                edit.setText(NewTools.PreferenceGetData(context));
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
