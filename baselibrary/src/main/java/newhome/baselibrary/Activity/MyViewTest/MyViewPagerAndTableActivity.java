package newhome.baselibrary.Activity.MyViewTest;

import android.os.Bundle;

import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/7.
 */
//activity加载fragment步骤：
//1、该activity中先加载一个布局，该布局中有个Fragment控件，用于加载fragment
//2、把需要加载的fragment设计完成
//3、相关文件：MyViewPagerAndTableLayout,TableLayoutAndViewPageFragment,TableLayoutAndViewPageFragmentOther
//4、tablelayout_viewpage_content.xml、tablelayoutviewpager.xml

public class MyViewPagerAndTableActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablelayout_viewpage_content);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,new MyViewPagerAndTableLayout()).commit();//把fragment载入
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
