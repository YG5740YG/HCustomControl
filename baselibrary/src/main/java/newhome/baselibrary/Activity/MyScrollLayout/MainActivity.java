package newhome.baselibrary.Activity.MyScrollLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.otto.Subscribe;

import newhome.baselibrary.Activity.MyScrollLayout.fragment.PagerHeaderFragment;
import newhome.baselibrary.Activity.MyScrollLayout.fragment.ParallaxImageHeaderFragment;
import newhome.baselibrary.Bus.BusAction;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.BusProvider;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;


public class MainActivity extends AppCompatActivity {

    private View scrollView;
    BusEvent event_cityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_activity_main);
        scrollView = findViewById(R.id.scrollView);
        BusProvider.getInstance().register(this);
    }

    public void showParallaxFragment(View view) {
        showFragment(ParallaxImageHeaderFragment.class);
    }

    public void showPagerHeaderFragment(View view) {
        showFragment(PagerHeaderFragment.class);
    }

    public void showSimpleDemoFragment(View view) {
        event_cityId=new BusEvent();
        event_cityId.setAction(BusAction.TEST);
        event_cityId.setObject("发送的信息");
        BusProvider.getInstance().post(event_cityId);
        Logs.Debug("gg=============post==");
//        showFragment(SimpleDemoFragment.class);
    }
    //广播接收测试
    @Subscribe
    public void onPostEvent(BusEvent event){
        int action = event.getAction();
        switch (action) {
            case BusAction.TEST:
                event.getObject();
                Logs.Debug("gg=============receview=="+event.getObject());
                break;
        }
    }
    public <T extends Fragment> void showFragment(Class<T> clzz) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        try {
            if (fragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, clzz.newInstance(), clzz.getSimpleName()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, clzz.newInstance(), clzz.getSimpleName()).commit();
            }
            getSupportActionBar().setTitle(clzz.getSimpleName());
            scrollView.setVisibility(View.GONE);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!removeFragment()) {
            super.onBackPressed();
        } else {
            scrollView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    public boolean removeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            return true;
        } else {
            return false;
        }
    }
}
