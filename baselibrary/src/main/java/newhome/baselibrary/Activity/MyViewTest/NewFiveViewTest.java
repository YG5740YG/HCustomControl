package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import MyView.MDToolbar;
import newhome.baselibrary.Fragment.TableLayoutAndViewPageFragmentOther;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/4/12 0012.
 */

public class NewFiveViewTest extends BaseActivity implements MDToolbar.OnMenuClickListener {
    MDToolbar mdToolbar;
    NavigationView navigationView;
    Context context;
    DrawerLayout drawerLayout;
    FloatingActionButton f_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newfive_drawerlayout);
        findViewById();
        setUp();
    }
    @Override
    public void findViewById() {
        navigationView=(NavigationView)findViewById(R.id.navigation);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        f_button=(FloatingActionButton)findViewById(R.id.f_button);
        mdToolbar = (MDToolbar) findViewById(R.id.toolbar);
        mdToolbar.setBackTitle(" ");
        mdToolbar.setBackTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setBackIcon(R.mipmap.ic_back_gray);
        mdToolbar.setMainTitle("抽屉，左拉效果");
        mdToolbar.setMainTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setRightTitle("");
        mdToolbar.setRightTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setOnMenuClickListener(this);
        mdToolbar.setToolbarBackgroud(getResources().getColor(R.color.es_w));
    }

    @Override
    public void setUp() {
        this.context=getApplication();
        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            fragment = new TableLayoutAndViewPageFragmentOther();
        }
        fm.beginTransaction().replace(R.id.fragment_content,fragment).commit();
        navigationView.setItemIconTintList(null);//我们需要的是彩色的图标(原始颜色，原始图片），而不是统一的图标颜色
        //获得头部内控件
//        在旧版本中，假如你要获得 NavigationView 中的头像 ImageView，你可以在 activity 中直接调用 findViewById() 方法来获得
//        final ImageView headImageView=(ImageView)findViewById(R.id.custom_header_image) ;//使用会报错，4.4手机
//        在 Support Library 23.1.0 版本之后，这个不再适用，在我使用的 Support Library 23.1.1 版本中，可以直接调用 getHeaderView()方法先得到 Header,然后在通过header来获得头部内的控件。
        View headerView=navigationView.getHeaderView(0);
        final TextView headTextView=(TextView)headerView.findViewById(R.id.head_testView);
        final ImageView headImageView_test=(ImageView)headerView.findViewById(R.id.custom_header_image) ;
        //隐藏抽屉里的某个itemView
        final MenuItem menuItem=navigationView.getMenu().findItem(R.id.nav_others);
        menuItem.setVisible(false);
        f_button.setOnClickListener(new View.OnClickListener() {//点击打开抽屉界面
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        //点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.nav_camera) {
                    Toast.makeText(context, "nav_camera", Toast.LENGTH_SHORT).show();
//                        headImageView.setImageDrawable(getResources().getDrawable(R.drawable.five));
                    //使用会报错，4.4手机

                } else if (i == R.id.nav_gallery) {
                    Toast.makeText(context, "nav_gallery", Toast.LENGTH_SHORT).show();
                    headTextView.setText("nav_gallery");//改变头部文字

                } else if (i == R.id.nav_manage) {
                    Toast.makeText(context, "nav_manage", Toast.LENGTH_SHORT).show();
                    headImageView_test.setImageDrawable(getResources().getDrawable(R.drawable.five));
                    //改变头部图片

                } else if (i == R.id.nav_send) {
                    Toast.makeText(context, "nav_send", Toast.LENGTH_SHORT).show();

                } else if (i == R.id.nav_share) {
                    Toast.makeText(context, "nav_share", Toast.LENGTH_SHORT).show();

                } else if (i == R.id.nav_slideshow) {
                    Toast.makeText(context, "nav_slideshow", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "sorry", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new TableLayoutAndViewPageFragmentOther();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", item.getTitle().toString());
                    fragment.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.fragment_content, fragment).commit();
                    drawerLayout.closeDrawer(Gravity.LEFT);//替换fragment,并把抽屉关闭

                }
                return false;
            }
        });
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

