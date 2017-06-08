package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import MyView.ProgressWebView;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/9.
 */

public class MyWebView extends BaseActivity {

    ProgressWebView webView;
    Context mcontext;
    TextView webview_title;
    ImageView webview_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        mcontext=this;
        findViewById();
        initWebSettings(webView);
        setUp();
    }
    /**
     * 初始化webview
     * @param webView
     */
    public void initWebSettings( WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        //属性可以让webview只显示一列，也就是自适应页面大小,不能左右滑动
        if (Build.VERSION.SDK_INT>=19){
            webView.getSettings().setLoadsImagesAutomatically(true);
        }else{
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        //设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setLoadWithOverviewMode(true);
        }
        //页面支持缩放
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            webSettings.setBuiltInZoomControls(false);
        }
        webSettings.setSupportZoom(false);
//        webSettings.setDisplayZoomControls(false);
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(false);
        //设置 缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setDomStorageEnabled(true);
        }
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }
    @Override
    public void findViewById() {
        webView=(ProgressWebView) findViewById(R.id.webView_content);
        webview_title=(TextView)findViewById(R.id.webview_title);
        webview_back=(ImageView)findViewById(R.id.webview_back);
    }

    @Override
    public void setUp() {
        webView.loadUrl("https://www.baidu.com");
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {//获取网页标题，或者跟换为自己的标题
                super.onReceivedTitle(view, title);
                Logs.Debug("title====" + title);
                webview_title.setText(title);//使用默认链接地址的标题
            }
        };
        webView.setWebChromeClient(wvcc);
        webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()){
                    webView.goBack();
                }else{
                    finish();
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            //在页面开始加载时候做一些操作，比如展示进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载处理
                try {
//                    dialog.show();
                    Logs.Debug("webviewloade=========");
                }catch (Exception e){
                    Toast.makeText(mcontext,"",Toast.LENGTH_SHORT);
                }
                super.onPageStarted(view, url, favicon);
            }
//            在页面加载完成的时候做一些操作,比如隐藏进度条
            @Override
            public void onPageFinished(WebView view, String url) {//页面加载完毕处理
                try {
//                    dialog.dismiss();
                }catch (Exception e){
                    Toast.makeText(mcontext,"",Toast.LENGTH_SHORT);
                }
                super.onPageFinished(view, url);
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
            //返回值：true 不会显示网页资源，需要等待你的处理，false 就认为系统没有做处理，会显示网页资源
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String
                    url) {
                Logs.Debug("gg=======gg=="+url);
                view.loadUrl(url);
                return true;
            }
        });
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });


    }

    @Override
    public void refreshView() {

    }
}
