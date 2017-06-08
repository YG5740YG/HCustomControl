package MyView.CustomView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class PaintCanvasEight extends BaseActivity {
    EditText editText;
    EditText product_describle;
    TextView txt_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editview);
        findViewById();
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
