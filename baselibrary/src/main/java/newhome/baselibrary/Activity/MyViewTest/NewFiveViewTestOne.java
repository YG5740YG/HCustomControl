package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import MyView.MDToolbar;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Tools;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/4/13 0013.
 */

public class NewFiveViewTestOne extends BaseActivity implements MDToolbar.OnMenuClickListener {
    Context context;
    List<String> mData;
    Snackbar snackbar;//新特性，和Toast类似，弹出一个提示框，不需要再布局文件中使用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newfive_coordinatorlayout);
        findViewById();
        setUp();
    }
    private void initData(int pager) {
        mData = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            mData.add("pager" + pager + " 第" + i + "个item");
        }
    }
    private void initView() {
        //设置ToolBar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("");
//        toolbar.setNavigationIcon(R.mipmap.gray_right_arrow);
//        setSupportActionBar(toolbar);//替换系统的actionBar
        //设置TabLayout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        for (int i = 1; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
        }
        //TabLayout的切换监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initData(tab.getPosition() + 1);
                setScrollViewContent();
//                Snackbar.make(tabLayout,"您好，您选择了"+tab.getPosition()+1,Snackbar.LENGTH_SHORT).show();//使用一
                final Snackbar snackbar = Snackbar.make(tabLayout, "Snack Bar", Snackbar.LENGTH_SHORT);//使用二
//                final Snackbar snackbar = Snackbar.make(tabLayout, ceshi, Snackbar.LENGTH_INDEFINITE);//使用二， Snackbar.LENGTH_INDEFINITE固定再界面中
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
//                        Toast.makeText(context,"wohenhao",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                snackbar.show();
                /*
                make()方法的第一个参数是一个view.要想支持Swipe手势的话,这个view需要是一个CoordinatorLayout(也是在Android Support Library里面的控件)
setAction,用法也是一目了然.设置下显示内容string,设置一个OnClickListener.
                 */
//                1. make()方法的第一个参数的view,不能是有一个ScrollView.
//                因为SnackBar的实现逻辑是往这个View去addView.而ScrollView我们知道,是只能有一个Child的.否则会Exception.
//                2. 如果大家在想把Toast替换成SnackBar.需要注意的是,Toast和SnackBar的区别是,前者是悬浮在所有布局之上的包括键盘,而SnackBar是在View上直接addView的.
//                所以SnackBar.show()的时候,要注意先把Keyboard.hide()了.不然,键盘就会遮住SnackBar.
//                3. 在Android2.3,SnackBar即使用了CoordinatorLayout也是不支持Swipe功能.
//                个人觉得,这一点点小瑕疵就可以忽略啦哈哈.
//                4. 在Android2.3,SnackBar的深色背景颜色和字体颜色相近.可以用SpannableString换一下String的颜色在传给make()方法.
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setScrollViewContent();
    }
    /**
     * 刷新ScrollView的内容
     */
    private void setScrollViewContent() {
        //NestedScrollView下的LinearLayout
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_sc_content);
        layout.removeAllViews();
        for (int i = 0; i < mData.size(); i++) {
            Tools tools=new Tools();
            SpannableString ceshi=tools.SpannableStringUse("测试",context);
            View view = View.inflate(NewFiveViewTestOne.this, R.layout.fruitlistitem_adapter, null);
            ((ImageView)view.findViewById(R.id.fruitImage)).setImageResource(R.mipmap.e8);
            ((TextView) view.findViewById(R.id.fruitName)).setText(mData.get(i)+"  "+ceshi);
            //触发事件的话，要加上下面这句话
            //tvTest.setMovementMethod(LinkMovementMethod.getInstance());
            ((TextView) view.findViewById(R.id.fruitName)).setMovementMethod(LinkMovementMethod.getInstance());
            //动态添加 子View
            layout.addView(view, i);
        }
    }

    @Override
    public void findViewById() {
    }

    @Override
    public void setUp() {
        initData(1);
        initView();
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRigthClick() {

    }
}

