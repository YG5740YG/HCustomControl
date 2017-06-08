package newhome.baselibrary.Activity.MyScrollLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/3/6.
 */

public class QiuGouActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiugou_main);
//        getSupportFragmentManager().beginTransaction().replace(R.id.content,new MyTestFragment()).commit();
    }
}
