package newhome.baselibrary.WebViewUse;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baseres.view.BaseActivity;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

public class NewWebViewJS extends BaseActivity {
    Context mcontext;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwebview);
        findViewById();
        mcontext = this;
        final String url = "https://www.baidu.com";
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(url);
                Toast.makeText(getApplicationContext(), "系好安全带!", Toast.LENGTH_SHORT).show();
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            //没有这个代码，webView不加载网页，而是用浏览器打开,，WebView如果要加载一个url会向ActivityManager寻求一个适合的处理者来加载该url（比如系统自带的浏览器），这通常是我们不想看到的。于是我们需要给WebView提供一个WebViewClient，并重写该方法返回true来告知WebView url的加载就在app中进行。这时便可以实现在app内访问网页。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }
            //当WebView得页面Scale值发生改变时回调。
            public void onScaleChanged(WebView view, float oldScale, float newScale){

            }
            //默认值为false，重写此方法并return true可以让我们在WebView内处理按键事件。
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event){
                return false;
            }
            //该方法在加载页面资源时会回调，每一个资源（比如图片）的加载都会调用一次。
            public void onLoadResource(WebView view, String url){

            }
            //该方法在WebView开始加载页面且仅在Main frame loading（即整页加载）时回调，一次Main frame的加载只会回调该方法一次。我们可以在这个方法里设定开启一个加载的动画，告诉用户程序在等待网络的响应。
            public void onPageStarted(WebView view, String url, Bitmap favicon){

            }
            //该方法只在WebView完成一个页面加载时调用一次（同样也只在Main frame loading时调用），我们可以可以在此时关闭加载动画，进行其他操作。
            public void onPageFinished(WebView view, String url){

            }
            //该方法在web页面加载错误时回调，这些错误通常都是由无法与服务器正常连接引起的，最常见的就是网络问题。 这个方法有两个地方需要注意： 1.这个方法只在与服务器无法正常连接时调用，类似于服务器返回错误码的那种错误（即HTTP ERROR），该方法是不会回调的，因为你已经和服务器正常连接上了（全怪官方文档(︶^︶)）；2.这个方法是新版本的onReceivedError()方法，从API23开始引进，与旧方法onReceivedError(WebView view,int errorCode,String description,String failingUrl)不同的是，新方法在页面局部加载发生错误时也会被调用（比如页面里两个子Tab或者一张图片）。这就意味着该方法的调用频率可能会更加频繁，所以我们应该在该方法里执行尽量少的操作。
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){

            }
            //onReceivedError并不会在服务器返回错误码时被回调，那么当我们需要捕捉HTTP ERROR并进行相应操作时应该怎么办呢？API23便引入了该方法。当服务器返回一个HTTP ERROR并且它的status code>=400时，该方法便会回调。这个方法的作用域并不局限于Main Frame，任何资源的加载引发HTTP ERROR都会引起该方法的回调，所以我们也应该在该方法里执行尽量少的操作，只进行非常必要的错误处理等。
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse){

            }
//当WebView加载某个资源引发SSL错误时会回调该方法，这时WebView要么执行handler.cancel()取消加载，要么执行handler.proceed()方法继续加载（默认为cancel）。需要注意的是，这个决定可能会被保留并在将来再次遇到SSL错误时执行同样的操作
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){

            }

//当WebView需要请求某个数据时，这个方法可以拦截该请求来告知app并且允许app本身返回一个数据来替代我们原本要加载的数据。 比如你对web的某个js做了本地缓存，希望在加载该js时不再去请求服务器而是可以直接读取本地缓存的js，这个方法就可以帮助你完成这个需求。你可以写一些逻辑检测这个request，并返回相应的数据，你返回的数据就会被WebView使用，如果你返回null，WebView会继续向服务器请求。
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
//
//            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            //当页面加载的进度发生改变时回调，用来告知主程序当前页面的加载进度。
            public void onProgressChanged(WebView view, int newProgress){
            }
            //用来接收web页面的icon，我们可以在这里将该页面的icon设置到Toolbar。
            public void onReceivedIcon(WebView view, Bitmap icon){
            }
            //用来接收web页面的title，我们可以在这里将页面的title设置到Toolbar。 以下两个方法是为了支持web页面进入全屏模式而存在的（比如播放视频），如果不实现这两个方法，该web上的内容便不能进入全屏模式。
            public void onReceivedTitle(WebView view, String title){
            }
            //该方法在当前页面进入全屏模式时回调，主程序必须提供一个包含当前web内容（视频 or Something）的自定义的View。
            public void onShowCustomView(View view, CustomViewCallback callback){
            }
            //该方法在当前页面退出全屏模式时回调，主程序应在这时隐藏之前show出来的View
            public void onHideCustomView(){
            }
            //当我们的Web页面包含视频时，我们可以在HTML里为它设置一个预览图，WebView会在绘制页面时根据它的宽高为它布局。而当我们处于弱网状态下时，我们没有比较快的获取该图片，那WebView绘制页面时的gitWidth()方法就会报出空指针异常~ 于是app就crash了。。这时我们就需要重写该方法，在我们尚未获取web页面上的video预览图时，给予它一个本地的图片，避免空指针的发生
            public Bitmap getDefaultVideoPoster(){
                Resources res = getApplication().getResources();
                Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.icon_message);
                return bmp;
            }
            //重写该方法可以在视频loading时给予一个自定义的View，可以是加载圆环 or something。
            public View getVideoLoadingProgressView(){
                return webView;
            }
            //处理Javascript中的Alert对话框。
            public boolean onJsAlert(WebView view, String url, String message, JsResult result){
                return false;
            }
            //处理Javascript中的Prompt对话框。
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result){
            return false;
            }
            //处理Javascript中的Confirm对话框
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result){
                return false;
            }
            //该方法在用户进行了web上某个需要上传文件的操作时回调。我们应该在这里打开一个文件选择器，如果要取消这个请求我们可以调用filePathCallback.onReceiveValue(null)并返回true。
            public boolean onShowFileChooser(WebView webView, ValueCallback filePathCallback, FileChooserParams fileChooserParams){
                return false;
            }
            //该方法在web页面请求某个尚未被允许或拒绝的权限时回调，主程序在此时调用grant(String [])或deny()方法。如果该方法没有被重写，则默认拒绝web页面请求的权限。
            public void onPermissionRequest(PermissionRequest request){

            }
            //该方法在web权限申请权限被取消时回调，这时应该隐藏任何与之相关的UI界面。
            public void onPermissionRequestCanceled(PermissionRequest request){

            }

        });
    }
    @Override
    public void findViewById() {
        webView=(WebView)findViewById(R.id.web_view);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            webSettings.setAllowFileAccess(true);
        }
        // 设置支持缩放
        webSettings.setUseWideViewPort(false);//设置此属性，可任意比例缩放。大视图模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setLoadWithOverviewMode(false);//和setUseWideViewPort(true)一起解决网页自适应问题
        }
        webSettings.setSupportZoom(false);//是否可以缩放，默认true
        //设置出现缩放工具
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            webSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false);
        }
        // 设置是否保存密码
        webSettings.setSavePassword(false);
        // 设置支持各种不同的设备//设置用户代理，一般不用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            webView.getSettings().setUserAgentString(ua + "9ji/" + Tools.currentVersion(mcontext, "") + "/" + Build.MODEL);
        }
        // 开启 DOM storage API 功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            webSettings.setAppCacheEnabled(true); //启用应用缓存
        }
        webSettings.setDomStorageEnabled(true); //启用或禁用DOM缓存。
        webSettings.setDatabaseEnabled(true); //启用或禁用DOM缓存。
        if (Tools.isNetworkAvailable(mcontext)) { //判断是否联网
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //默认的缓存使用模式
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY); //不从网络加载数据，只从缓存加载数据。
        }
        webView.setBackgroundColor(0);//先设置背景色为transparent
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        //调用页面中java的方法
        JsInterface jsInterface = new JsInterface(mcontext);
//        @JavascriptInterface
        webView.addJavascriptInterface(jsInterface, "shareAndroid");
        //运行webview通过URI获取安卓文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }
        // 可以让不同的density的情况下，可以让页面进行适配//设置默认缩放方式尺寸是far
        double  mDensity = mcontext.getResources().getDisplayMetrics().density;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            if (mDensity >= 240) {
                webSettings.setTextZoom(100);
                webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity >= 160&&mDensity<240) {
                webSettings.setTextZoom(100);
                webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if(mDensity<160){
                webSettings.setTextZoom(100);
                webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            }
        }
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(mcontext.getFilesDir().getPath());
        //WebView加上这个设置后,WebView里的字体就不会随系统字体大小设置发生变化了.
        webSettings.setTextSize( WebSettings.TextSize.SMALLEST );
        webSettings.setStandardFontFamily("sans-serif");//设置标准的字体族
        webSettings.setCursiveFontFamily("cursive");//设置草书字体族
        webSettings.setFantasyFontFamily("cursive");//设置CursiveFont字体族
        webSettings.setFixedFontFamily("monospace");//设置混合字体族
        webSettings.setSansSerifFontFamily("sans-serif");//设置梵文字体族
        webSettings.setSerifFontFamily("sans-serif");//设置衬线字体族
        webSettings.setDefaultFixedFontSize(18);//设置默认填充字体大小，默认16，取值区间为[1-72]，超过范围，使用其上限值。
        webSettings.setDefaultFontSize(18);//设置默认字体大小，默认16，取值区间[1-72]，超过范围，使用其上限值。
        webSettings.setMinimumFontSize(8);//设置最小字体，默认8. 取值区间[1-72]，超过范围，使用其上限值。
        webSettings.setMinimumLogicalFontSize(8);//设置最小逻辑字体，默认8. 取值区间[1-72]，超过范围，使用其上限值。 以上就是一些WebSettings的常用方法，具体的使用以及一些缓存的问题会在接下来的代码以及文章中有更加直观的说明

    }
    //回调js
    private class JsInterface {
        private Context context;
        JsInterface(Context c) {
            context = c;
        }
        @JavascriptInterface
        public void send(String msg) {
            Logs.Debug("send====" + msg);
//            share_json = JSON.parseObject(msg);
//            if (share_json.containsKey("pageTitle")) {
//                mdToolbar.setMainTitle(share_json.getString("pageTitle"));
//            }
        }

        @JavascriptInterface
        public void processHTML(String html) {
            // process the html as needed by the app
        }
    }

}
//利用WebView调用网页上的JavaScript代码
//在WebView中调用Js的基本格式为webView.loadUrl("javascript:methodName(parameterValues)");
//js文件：
/*
<script type="text/javascript">
function readyToGo(){
alert("Hellow");
}
function alertMessage(message){
alert(message);
}
function getYourCar(){
return "Car";
}
</script>
 */
// WebView调用JavaScript无参无返回值函数
//String call="javascript:readyToGo()";
//webView.loadUrl(call);
//WebView调用JavScript有参无返回值函数
//String call="javascript:alertMessage(\""+"content"+"\")";
//webView.loadUrl(call);
//WebView调用JavaScript有参数有返回值的函数
/*
@TargetApi(Build.VERSION_CODES.KITKAI)
private void evaluateJavaScript(WebView webView){
webView.evaluateJavascript("getYourCar()",new ValueCallback<String>(){
@Override
public void onReceiveValue(String s){
Log.d("findcar",s);
}
};
}
 */
//JavaScript通过WebView调用Java代码
//从API19开始，Android提供了@JavascriptInterface对象注解的方式来建立起Javascript对象和Android原生对象的绑定，提供给JavScript调用的函数必须带有@JavascriptInterface。
//演示一 JavaScript调用Android Toast方法
//1. 编写Java原生方法并用使用@JavascriptInterface注解
//@JavascriptInterface
//public void show(String s){
//    Toast.makeText(getApplication(),s,Toast.LENGTH_SHORT).show();
//}
//2.注册JavaScriptInterface,addJavascriptInterface的作用是把this所代表的类映射为JavaScript中的android对象。
//webView.addJavascriptInterface(this,"android");
//3.编写JavaScript代码
//function toastClilck(){
//    window.android.show("JavaScript called~!"):
//}
// JavaScript调用有返回值的Java方法
//1.定义一个带返回值的Java方法，并使用@JavaInterface：
//@JavascriptInterface
//public void getMessage(){
//    return "hellow boy";
//}
//2.添加JavaScript的映射
//webView.addJavascriptInterface(this,"android");
//3.通过JavaScript调用Java方法
//function showHellow(){
//   var str=window.Android.getMessage();
//console.log(str);
//}
// 调用js中的函数：jsFun(msg)
//        webView.loadUrl("javascript:jsFun('" + msg + "')");
//在java中调用此方法
//function jsFun(msg){
//    alert("我是js方法，被java调用，传递过来的参数是："+msg);
//}
//
//优化
//1、将所有的资源文件都存在Android的asset目录下
//在WebView中，onPageFinished()的回调意味着页面加载的完成。
// 但该方法会在JavScript脚本执行完成后才会触发，倘若我们要加载的页面使用了JQuery，会在处理完DOM对象，
// 执行完$(document).ready(function() {})后才会渲染并显示页面。
// 这是不可接受的，所以我们需要对Js进行延迟加载，当然这部分是Web前端的工作。
//那就是JsBridge一律不得滥用，这个对页面加载的完成速度是有很大影响的，倘若一个页面很多操作都通过JSbridge来控制，再怎么优化也无济于事（因为毕竟有那么多操作要实际执行）。同时要注意的是，不管你是否对资源进行缓存，都请将资源在服务器端进行压缩。因为无论是资源的获取和更新，都是要从服务器获取的，所以对于资源文件的压缩其实是最直接也最应该做的事情之一
//请在对应的 html 页面中引入  <script src="js-bridge.js"></script>


/*
接下来再介绍一些WebView的常用方法，具体演示会在后面章节的代码里统一展示。

String getUrl()：获取当前页面的URL。

reload()：重新reload当前的URL，即刷新。

boolean canGoBack()：用来确认WebView里是否还有可回退的历史记录。通常我们会在WebView里重写返回键的点击事件，通过该方法判断WebView里是否还有历史记录，若有则返回上一页。

boolean canGoForward()：用来确认WebView是否还有可向前的历史记录。

boolean canGoBackOrForward(int steps)：以当前的页面为起始点，用来确认WebView的历史记录是否足以后退或前进给定的步数，正数为前进，负数为后退。

goBack()：在WebView历史记录后退到上一项。

goForward()：在WebView历史记录里前进到下一项。

goBackOrForward(int steps)：以当前页面为起始点，前进或后退历史记录中指定的步数，正数为前进，负数为后退。

clearCache(boolean includeDiskFiles)：清空网页访问留下的缓存数据。需要注意的时，由于缓存是全局的，所以只要是WebView用到的缓存都会被清空，即便其他地方也会使用到。该方法接受一个参数，从命名即可看出作用。若设为false，则只清空内存里的资源缓存，而不清空磁盘里的。

clearHistory()：清除当前webview访问的历史记录。

clearFormData()：清除自动完成填充的表单数据。需要注意的是，该方法仅仅清除当前表单域自动完成填充的表单数据，并不会清除WebView存储到本地的数据。

onPause()：当页面被失去焦点被切换到后台不可见状态，需要执行onPause操作，该操作会通知内核安全地暂停所有动作，比如动画的执行或定位的获取等。需要注意的是该方法并不会暂停JavaScript的执行，若要暂停JavaScript的执行请使用接下来的这个方法。

onResume()：在先前调用onPause()后，我们可以调用该方法来恢复WebView的运行。

pauseTimers()：该方法面向全局整个应用程序的webview，它会暂停所有webview的layout，parsing，JavaScript Timer。当程序进入后台时，该方法的调用可以降低CPU功耗。

resumeTimers()：恢复pauseTimers时的所有操作。

destroy()：销毁WebView。需要注意的是：这个方法的调用应在WebView从父容器中被remove掉之后。我们可以手动地调用

rootLayout.removeView(webView);
webView.destroy();
getScrollY()：该方法返回的当前可见区域的顶端距整个页面顶端的距离，也就是当前内容滚动的距离。

getHeight()：方法都返回当前WebView这个容器的高度。其实以上两个方法都属于View。

getContentHeight()：该方法返回整个HTML页面的高度，但该高度值并不等同于当前整个页面的高度，因为WebView有缩放功能， 所以当前整个页面的高度实际上应该是原始HTML的高度再乘上缩放比例。因此，准确的判断方法应该是

if (webView.getContentHeight() * webView.getScale() == (webView.getHeight() + webView.getScrollY())) {
    //已经处于底端
}

if(webView.getScrollY() == 0){
    //处于顶端
}
pageUp(boolean top)：将WebView展示的页面滑动至顶部。

pageDown(boolean bottom)：将WebView展示的页面滑动至底部。
 */
