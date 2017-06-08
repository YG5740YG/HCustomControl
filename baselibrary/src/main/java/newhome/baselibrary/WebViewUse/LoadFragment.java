package newhome.baselibrary.WebViewUse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import newhome.baselibrary.Bus.RoutersAction;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class LoadFragment extends BaseActivity {
    FrameLayout frameLayout_content;
    Bundle bundle;
    String path;
    static Fragment fragment;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadfragment);
        findViewById();
        setUp();
    }
    @Override
    public void findViewById() {
        frameLayout_content = (FrameLayout) findViewById(R.id.fragment_content);
    }
    @Override
    public void setUp() {
        if (getIntent().hasExtra("data")) {
            bundle = getIntent().getBundleExtra("data");
            if (bundle.containsKey("pathUrl")) {
                path = bundle.getString("pathUrl");
                Logs.Debug("path==========" + path);
            }
        }
        boolean flage=false;
        if (bundle.containsKey("webview_key")) {
            if (bundle.getString("webview_key").equals(RoutersAction.WEBVIEW_CF)) {
                flage=true;
            }
        }
        if(flage==true){
            Intent newWebViewInt = new Intent(context, NewWebView.class);
            newWebViewInt.putExtra("Url",path);
            startActivity(newWebViewInt);
            finish();
        }
        else {
            if (path.contains(RoutersAction.STORE)) {//附近门店
//                fragment = new TopciNearByStoreFragment();
            } else if (path.contains(RoutersAction.HOTTOPIC)) {//稀奇集中营
//                fragment = new TopicUnusualToCampFragment();
            } else if (path.contains(RoutersAction.RECHARGE)) {//充值中心
//                fragment = new TopicRechargeFragment();
            } else if (path.contains(RoutersAction.JIUJIBUY)) {//9机buy
//                fragment = new TopicJiujiBuyFragment();
            } else if (path.contains(RoutersAction.CART)) {
//                fragment = new CartInfoFragment();
                fragment.setArguments(bundle);
            } else if (path.contains(RoutersAction.ORDEREVALUATE)) {
//                fragment = new OrderEvaluateFragment();
                bundle = getKeyValues();
                fragment.setArguments(bundle);
            }
            frameLayout_content.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
        }
    }
    public Bundle getKeyValues() {
        if (path.contains("?")) {
            String[] str = path.split("\\?");
            String item_lastThePart = str[1];
            String[] item_lastPart_item = item_lastThePart.split("&");
            if (item_lastPart_item.length > 0) {
                for (int i = 0; i < item_lastPart_item.length; i++) {
                    String[] key_value = item_lastPart_item[i].split("=");
                    String key = key_value[0];
                    String value = key_value[1];
                    bundle.putString(key, value);
                }
            }
        }
        return bundle;
    }

    @Override
    public void refreshView() {

    }
}

