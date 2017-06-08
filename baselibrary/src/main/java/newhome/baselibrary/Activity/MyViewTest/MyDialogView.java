package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import MyView.KeybordPPWindow;
import MyView.MyDialog;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/9.
 */

public class MyDialogView extends BaseActivity {
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
//                MyToast myToast=new MyToast(MyTextView.this,"nihao",1000, Gravity.LEFT);
//                Neutra
                //自定义dialog测试
//                MyDialog myDialog=new MyDialog(mcontext, "nihao", "我很好", "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MyToast myToast = new MyToast(MyTextView.this, "nihao", 1000, Gravity.LEFT);
//                    }
//                }, "取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }, "一般", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MyToast myToast=new MyToast(mcontext,"wohao",1000);
//                    }
//                });

//                final EditText editText=new EditText(mcontext);
//                String []items={"item1","item2"};
//                boolean [] checkItems={true,false};
//                final MyDialog myDialog=new MyDialog(mcontext, "nihao", "",editText, "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        Logs.Debug("gg========="+editText.getText());
//                    }
//                }, R.drawable.icon_dot_green);

////多选框
//                String []items={"item1","item2"};
//                boolean [] checkItems={false,false};
//                final MyDialog myDialog=new MyDialog(mcontext, "nihao", "", "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }, R.drawable.icon_dot_green, items,checkItems, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        Logs.Debug("gg==============i=="+i+"==b=="+b);
//                        if(b){
//                            Logs.Debug("gg==============nihao++"+i);
//                        }
//                    }
//                });

                //单选框
//                final String []items={"item1","item2"};
//                boolean [] checkItems={false,false};
//                final MyDialog myDialog=new MyDialog(mcontext, "nihao", "", "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Logs.Debug("gg========item=="+items[itemId]);
//                        dialogInterface.dismiss();
//                    }
//                }, R.drawable.icon_dot_green, items, 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        itemId =i;
//                        Logs.Debug("gg===========i=="+i);
//                    }
//                });
//                //普通列表item框
//                final String []items={"item1","item2"};
//                boolean [] checkItems={false,false};
//                final MyDialog myDialog=new MyDialog(mcontext, "nihao", "", "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Logs.Debug("gg========item=="+items[itemId]);
//                        dialogInterface.dismiss();
//                    }
//                }, R.drawable.icon_dot_green, items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        itemId =i;
//                        Logs.Debug("gg===========i=="+i);
//                    }
//                });
                //自定义布局dailog
                final MyDialog myDialog=new MyDialog(mcontext,R.mipmap.icon_search,R.layout.editview);

//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        MyToast myToast = new MyToast(MyTextView.this, "nihao", 1000, Gravity.LEFT);
//                    }
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
