package newhome.baselibrary.Activity.MyScrollLayout.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;

import newhome.baselibrary.Activity.MyScrollLayout.fragment.base.ScrollAbleFragment;
import newhome.baselibrary.R;


public class WebViewFragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer{

    private WebView webView;
    private EditText mAddressEdt;

    public static WebViewFragment newInstance() {
        WebViewFragment scrollViewFragment = new WebViewFragment();
        return scrollViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        mAddressEdt = (EditText) view.findViewById(R.id.addressEdt);
        view.findViewById(R.id.goBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(mAddressEdt.getText().toString());
            }
        });
        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.getSettings().setAllowContentAccess(true);
        }
        webView.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.getSettings().setDisplayZoomControls(true);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("https://github.com/cpoopc");
        return view;
    }

    @Override
    public View getScrollableView() {
        return webView;
    }
}
