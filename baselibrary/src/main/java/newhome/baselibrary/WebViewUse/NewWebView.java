package newhome.baselibrary.WebViewUse;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import MyView.MDToolbar;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baseres.view.BaseActivity;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class NewWebView extends BaseActivity implements MDToolbar.OnMenuClickListener{
    MDToolbar mdToolbar;
    FrameLayout frameLayout_content;
    LinearLayout webview_layout;
    WebView webView_content;
    ProgressBar progressBar;
    String url;
    boolean flage=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        findViewById();
        setUp();
    }
    @Override
    public void findViewById() {
        webview_layout = (LinearLayout) findViewById(R.id.webview_layout);
        webView_content = (WebView) findViewById(R.id.webView_content);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mdToolbar = (MDToolbar) findViewById(R.id.toolbar);
        mdToolbar.setBackTitle(" ");
        mdToolbar.setBackTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setBackIcon(R.mipmap.ic_back_gray);
        mdToolbar.setMainTitle("九机网 ");
        mdToolbar.setMainTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setRightTitle("");
        mdToolbar.setRightTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setOnMenuClickListener(this);
        mdToolbar.setToolbarBackgroud(getResources().getColor(R.color.es_w));
    }

    @Override
    public void setUp() {
        webview_layout.setVisibility(View.VISIBLE);
        if(getIntent().hasExtra("Url")){
            url=getIntent().getStringExtra("Url");
        }
        if (url.startsWith("https://")&&url.contains("cn.mikecrm.com")) {//https:开头的都跟换为http://
            url = "http://" + url.split("https://")[1];
        }
        Logs.Debug("gg=============url=="+url);
        mdToolbar.setBackIcon(R.mipmap.cancel31,45,45);
        initWebSettings(webView_content);
        webViewClick();
        webViewChrome();
        if(url.contains("https://huishou.9ji.com/MhsOrderConfig/Index?pid")){
            flage=true;
        }
        webView_content.loadUrl(url);
    }
    public void webViewClick() {
        webView_content.setWebViewClient(new WebViewClient() {
            //返回值：true 不会显示网页资源，需要等待你的处理，false 就认为系统没有做处理，会显示网页资源
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(view.getUrl().contains("baidumap://map/direction?mode=driving&origin=name")){
                    Logs.Debug("gg===========url==1234==" + view.getUrl());
                    return false;
                }
                else {
                    Logs.Debug("gg===========url==123==" + view.getUrl());
                    progressBar.setVisibility(View.VISIBLE);
                    view.loadUrl(view.getUrl());
//                return super.shouldOverrideUrlLoading(view, request);
                    return true;
                }
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void webViewChrome() {
        webView_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, final String title) {
                super.onReceivedTitle(view, title);
                mdToolbar.setMainTitle(title);
            }
            //webView打开系统文件夹
            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, true);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Logs.Debug("gg===============apay==123==" + url);
            if (url.contains("https://huishou.9ji.com/MHsOrderList/Index")) {
                webView_content.goBack();
                webView_content.goBack();
                return true;
            } else {
                if (flage) {
                    finish();
                } else {
                    if (webView_content.canGoBack()) {
                        webView_content.goBack();
                        return true;
                    }
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 初始化webview
     *
     * @param webView
     */
    public void initWebSettings(WebView webView) {
        String ua = webView.getSettings().getUserAgentString();
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        //属性可以让webview只显示一列，也就是自适应页面大小,不能左右滑动
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        // 如果访问的页面中有Javascript，则webview必须设置支持Javascript。
        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        }else{
            webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        }
        //触摸焦点起作用
        webView.requestFocus();
        //取消滚动条
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        // 设置允许访问文件数据
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setUseWideViewPort(false);//设置此属性，可任意比例缩放。大视图模式
        webSettings.setLoadWithOverviewMode(false);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setSupportZoom(false);//是否可以缩放，默认true
        //设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false);
        }
        // 设置是否保存密码
        webSettings.setSavePassword(false);
        // 设置支持各种不同的设备//设置用户代理，一般不用
        webView.getSettings().setUserAgentString(ua + "9ji/" + Tools.currentVersion(context, "") + "/" + Build.MODEL);
        // 开启 DOM storage API 功能
        webSettings.setAppCacheEnabled(true); //启用应用缓存
        webSettings.setDomStorageEnabled(true); //启用或禁用DOM缓存。
        webSettings.setDatabaseEnabled(true); //启用或禁用DOM缓存。
        if (Tools.isNetworkAvailable(context)) { //判断是否联网
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //默认的缓存使用模式
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY); //不从网络加载数据，只从缓存加载数据。
        }

        webView.setBackgroundColor(0);//先设置背景色为transparent
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //设置滚动条样式
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false

        //运行webview通过URI获取安卓文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(context.getFilesDir().getPath());
        // 可以让不同的density的情况下，可以让页面进行适配//设置默认缩放方式尺寸是far
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            webSettings.setTextZoom(100);
        }
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
