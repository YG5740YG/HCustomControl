package newhome.baselibrary.Activity.MyViewTest;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/4.
 */

public class MyEditView extends BaseActivity {
    EditText editText;
    EditText product_describle;
    TextView txt_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editview);
        findViewById();
        //监听回车键
        editViewBackListen(editText);
        //监听编辑框内容字数
        myEditViewListen(product_describle);
        //获取EditText文本  
        Button getValue=(Button)findViewById(R.id.btn_get_value);
        getValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyEditView.this,editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        Button all=(Button)findViewById(R.id.btn_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 editText.selectAll();
            }
        });
        //从第2个字符开始选择EditText文本  
        Button select=(Button)findViewById(R.id.btn_select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable editable=editText.getText();
                Selection.setSelection(editable,1,editable.length());
            }
        });
        //获取选中的文本  
        Button getSelect=(Button)findViewById(R.id.btn_get_select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start=editText.getSelectionStart();
                int end=editText.getSelectionEnd();
                CharSequence selectText=editText.getText().subSequence(start,end);
                Toast.makeText(MyEditView.this,selectText,Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void switchIndex(int start,int end){
        int temp=start;
        start=end;
        end=temp;
    }
    protected void editViewBackListen(final EditText editText){
        //监听回车键
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Logs.Debug("gg====huiche jianting==="+String.valueOf(i));
                return false;
            }
        });
        //监听按下放开
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        break;
                }
                return false;
            }
        });
        //监听点击
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        //监听输入
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 20) {
                    editText.setText(charSequence.subSequence(0, charSequence.length() - 1));
                    editText.setSelection(charSequence.length() - 1);
                    editText.setError("最多可输入20个字符");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    //自定义EditView:输入内容，监听内容字数
    protected void myEditViewListen(EditText editText){
        editText.setVerticalScrollBarEnabled(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txt_number.setText("您还可以输入"+(200-charSequence.length())+"个字符");
                if (charSequence.length() > 200) {
                    product_describle.setText(charSequence.subSequence(0, charSequence.length() - 1));
                    product_describle.setMovementMethod(ScrollingMovementMethod.getInstance());
//                    product_describle.setSelection(charSequence.length() - 1);
                    product_describle.setError("您好，您最多可输入200个字符。");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    @Override
    public void findViewById() {
        editText=(EditText)findViewById(R.id.edit_text);
        product_describle=(EditText)findViewById(R.id.product_describle);
        txt_number=(TextView)findViewById(R.id.txt_number);
    }
    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}

/**
 *1、editview onclick事件，当第一次触摸时不执行这个事件（原因是该事件执行有个要求，必须焦点要在该控件上面），
 * 但是执行setOnTouchListener和setOnFocusChangeListener，第二次点击时，三个事件都执行，顺序为setOnFocusChangeListener第一，
 * setOnTouchListener第二，onclick第三。如果需要第一次点击就执行onclick事件，就需要在setOnTouchListener事件里面把焦点设置上去，
 * 加入焦点的方法{
 editview .setFocusable(true);
 editview .setFocusableInTouchMode(true);
 editview .requestFocus();
 editview .requestFocusFromTouch();
 }这样设置以后就可以让第一次点击也执行onclick事件
 */
