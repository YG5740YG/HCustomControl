package newhome.baselibrary.WebViewUse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import MyView.MDToolbar;
import newhome.baselibrary.Bus.BusAction;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.BusProvider;
import newhome.baselibrary.Bus.RoutersAction;
import newhome.baselibrary.ImageHandle.ImageUse.ImageData;
import newhome.baselibrary.ImageHandle.ImageUse.PickImageUtil;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.R;
import newhome.baselibrary.Routers.Routers;
import newhome.baselibrary.Save.PreferencesProcess;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;
import newhome.baseres.view.BaseActivity;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class ExtrasActivity extends BaseActivity implements MDToolbar.OnMenuClickListener {
    String mStrBridgeJs = "window.jiujiJsBridge={nativeCallback:{},objectToParamString:function(obj){var param='';for(var i in obj){param+=(i+'='+obj[i]+'&');}param=param.substring(0,param.length-1);return param;},pay:function(data,callback){this.nativeCallback.pay=callback;var param=this.objectToParamString(data);location.href='jiuji://jsBridge:0/pay?'+param;}};if(window.onJiujiJsBridgeReady&&typeof(window.onJiujiJsBridgeReady)==='function'){setTimeout(window.onJiujiJsBridgeReady(),100);};";
    String path = "";
    WebView webView_content;
    static Fragment fragment;
    RelativeLayout top_RLayout;
    LinearLayout webview_layout;
    int url_number = 0;
    ProgressBar progressBar;
    List<String> url_list = new ArrayList<String>();
    List<String> loadContentFlage = new ArrayList<String>();
    List<Bundle> url_list_bundle = new ArrayList<Bundle>();
    private ValueCallback<Uri> mUploadMessage;//回调图片选择，4.4以下
    private ValueCallback<Uri[]> mFilePathCallback; //图片回调选择，5.0以上
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    private String mCameraPhotoPath;
    Bitmap bmp = null;
    File file = null;
    String file_path = FileUtil.appSavePath();
    Uri[] dataUris = null;
    Bitmap bitmap;
    int dataUris_num = 0;
    FileOutputStream fOut = null;
    boolean back = false;
    boolean webview_flage = false;
    String newString;
    ImageView iv_menu;
    int p_flage_num = 0;
    MDToolbar mdToolbar;
    boolean webView_flage1 = false;
    String WebViewTopTitle;
    boolean right_icon = false;
    JSONObject share_json;
//    MyShareActivity myShareActivity;
    float mDensity = 0;
    ShareHandler shareHandler = new ShareHandler();
    int flage_num = 0;
    int flage_num_ = 0;
    BusEvent event;
    String share_string = "";
    Bundle bundle = new Bundle();
    private static Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    ImageView pictest;
    public static final  int CROP_PHOTO=2;

    boolean selectImageFlage=false;
    //webVeiw打开系统文件选择器
    /**
     * Android 5.0以下版本的文件选择回调
     */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * Android 5.0及以上版本的文件选择回调
     */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected static final int FILECHOOSER_RESULTCODE = 2;
    protected String mUploadableFileTypes = "image/*";

    String scheme = "";
    File outputImage;
    int payBackFlage=0;
    String filecompresspath="";
    protected Context context;

    /** 视频全屏参数 */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 处理webview 键盘挡住输入框
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_extras);
        if(isNotStartScheme()){
            finish();
        }else{
            findViewById();
            setUp();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        flage_num = 0;
        payBackFlage = PreferencesProcess.getVersionFlage(context, "paybackflage", 0);
        if(payBackFlage!=0||path.contains("appRefresh=1")) {
            PreferencesProcess.putPreferencesInt(this, "paybackflage", 0);
            webView_content.reload();
        }
        isNotNeetReLoadWebview();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView_content != null) {
            webView_content.loadUrl("");
        }
    }
    @Override
    public void findViewById() {
        webView_content = (WebView) findViewById(R.id.webView_content);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webview_layout = (LinearLayout) findViewById(R.id.webview_layout);
        pictest = (ImageView) findViewById(R.id.pictest);
        mdToolbar = (MDToolbar) findViewById(R.id.toolbar);
        titleSZ();
        busEventUse();
    }
    @Override
    public void setUp() {
        fragment = null;
//        myShareActivity = new MyShareActivity();
        if (getIntent().hasExtra("data")) {
            bundle = getIntent().getBundleExtra("data");
            if (bundle.containsKey("pathUrl")) {
                path = bundle.getString("pathUrl");
                Logs.Debug("path==========" + path);
            }
        }
//        path="http://9ji.cn/ic60 ";
        Logs.Debug("gg=========url=="+path);
        if(!(path.contains("youku://")||path.contains("https://9ji.cn/")||path.contains("http://9ji.cn/")||path.contains("www.9ji.com")||path.contains("m.9ji.com")||path.contains("jishi")||path.contains("huishou")||Routers.judge(path, RoutersAction.FRAGMENT)))
        {
            Intent newWebViewInt = new Intent(context, NewWebView.class);
            newWebViewInt.putExtra("Url",path);
            startActivity(newWebViewInt);
            finish();
        }else {
            if(path.contains("https://m.9ji.com/news/")&&path.contains(".html?from=app_push")){
//                MsgToNewsFragment msgToNewsFragment=new MsgToNewsFragment(Integer.parseInt(path.split("news/")[1].replace(".html?from=app_push","")),context);
                finish();
            }else{
                useWebViewLoad();
            }
        }
    }
    //是否为第三方启动app，及传递数据接受
    public boolean isNotStartScheme(){
        if (getIntent().hasExtra("data")) {
            bundle = getIntent().getBundleExtra("data");
            if (bundle.containsKey("pathUrl")) {
                path = bundle.getString("pathUrl");
                Logs.Debug("path==========" + path);
            }
        }
        scheme = this.getIntent().getDataString();
        HashMap<String, String> hashMap = new HashMap<>();
//        String local_id = BaseInfo.getInstance(context).getInfo().getUserId();
        String local_id = "";
        Logs.Debug("local_id===" + local_id + hashMap.toString());
        if (!Tools.isEmpty(scheme)) {
            path = scheme.replace("jiuji", "https");
        }
        if (scheme.startsWith("jiuji://")) {
            if (path.equals("https://m.9ji.com")||path.equals("https://m.9ji.com/")) {
                Routers.open(context, RoutersAction.MAIN);
//                finish();
            } else if (scheme.contains("jiuji://chat.9ji.com")||scheme.contains("jiuji://chat.9ji.com/")) {
                if (scheme.contains("?")) {

                    Uri parse = Uri.parse(scheme);
                    String userID = parse.getQueryParameter("userID");

                    if (Tools.isEmpty(userID)) {

                        Routers.open(context, RoutersAction.ACOUNT_LOGIN);

                    } else {
                        int chatType = Integer.parseInt(parse.getQueryParameter("chatType"));

                        Bundle bundle = new Bundle();
                        bundle.putInt("action", BusAction.CUSTOMER_CHAT);

                        switch (chatType) {
                            case 1:
                                bundle.putInt("staff_type", 0);
                                break;
                            case 2:
                                String staffID = parse.getQueryParameter("staffID");
                                String titleName = parse.getQueryParameter("titleName");
                                String staffType = parse.getQueryParameter("staffType");
                                // TODO: 2017/4/11  下一版本iOS发布就需要移除
                                if (!TextUtils.isEmpty(titleName)){
                                    String[] split = titleName.split("\\-");
                                    String url = split[split.length - 1];
                                    url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                                    try {
                                        String staffName = URLDecoder.decode(url, "UTF-8");
                                        bundle.putString("staffname", staffName);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                                bundle.putInt("staff_type", 2);
                                bundle.putString("staffid", staffID);

                                if (!TextUtils.isEmpty(staffType)) {
                                    bundle.putInt("stafftype", Integer.parseInt(staffType));
                                }

                                break;
                        }

                        //判断是否登录
                        if (userID.equals(local_id)) {
                            bundle.putBoolean("chat",true);
                            Routers.open(context, RoutersAction.CHAT, bundle);
//                            finish();
                        } else {
                            // intent login
                            Logs.Debug("chat 12");
                            bundle.putString("key", "MAIN");
//                            BaseInfo.getInstance(context).clearUserInfo();
                            Routers.open(context, RoutersAction.ACOUNT_LOGIN, bundle);
//                            finish();
                        }
                    }
                }
            } else {
                if (Tools.isEmpty(local_id)) {
                    //
                    Routers.open(context, RoutersAction.ACOUNT_LOGIN);
//                        finish();
                } else {
                    Routers.open(context, path);
                }
//                finish();
            }
            return true;
        } else {
            return false;
        }
    }
    //判断重新打开时，是否重新加载webview
    public void isNotNeetReLoadWebview(){
        if (path.contains("https://m.9ji.com/user/myinfo.aspx")||path.contains("https://huishou.9ji.com/OrderDes/MOrderDes")) {
            webView_content.reload();
        }
    }
    //标题设置
    public void titleSZ(){
        mdToolbar.setBackTitle(" ");
        mdToolbar.setBackTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setBackIcon(R.mipmap.ic_back_gray);
        mdToolbar.setMainTitle("九机网 ");
        mdToolbar.setMainTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setRightTitle("");
        mdToolbar.setRightIcon(R.drawable.kong_icon);
//        mdToolbar.setRightIcon(R.mipmap.share);
//        mdToolbar.setRightTitleColor(getResources().getColor(R.color.font_dark));
        mdToolbar.setOnMenuClickListener(this);
        mdToolbar.setToolbarBackgroud(getResources().getColor(R.color.es_w));
        mdToolbar.setBackIcon(R.mipmap.cancel31, UITools.dip2px(context,15), UITools.dip2px(context,15));
    }
    //广播设置
    public void busEventUse(){
        event = new BusEvent();
        event.setAction(BusAction.OrderBack);
        BusProvider.getInstance().register(this);
    }
    //回调js
    private class JsInterface {
        private Context context;
        JsInterface(Context c) {
            context = c;
        }
        @JavascriptInterface
        public void send(String msg) {
            Logs.Debug("send====1" + msg);
//            share_json = JSON.parseObject(msg);
//            share_json = new JSONObject();
//            if (share_json.containsKey("pageTitle")) {
//                mdToolbar.setMainTitle(share_json.getString("pageTitle"));
//            }

        }

        @JavascriptInterface
        public void processHTML(String html) {
            // process the html as needed by the app
        }
    }
    //加载webView默认显示标题，特殊情况不显示标题
    public void noShowTitle(){
        if (path.equals("https://m.9ji.com/help/12484.html")) {
            mdToolbar.setVisibility(View.GONE);
        }
    }
    //在打开的webView中点击，需要通过上个页面传递参数进行判断，参数设置
    public void paramSZ(){
        if(path.contains("https://m.9ji.com/pastingappoint.aspx?from=tb9")){
            bundle.putString("pastingappoint","");
        }
    }
    //是否显示分享
    public void showShare(String url){
        if (bundle.containsKey("isShare")) {
            mdToolbar.setRightIcon(R.drawable.kong_icon);
        }else{
//            mdToolbar.setRightIcon(R.mipmap.share_gray);
        }
        if (url.contains("https://m.9ji.com/groupbuy/buyorder.aspx?groupid=") || (url.contains("https://www.9ji.com/news") && url.contains(".html"))
                || url.contains("https://huishou.9ji.com/Mlpordercinfig/orderSubResult?subid") || url.contains("https://huishou.9ji.com/Mlpordercinfig/orderDes?subid=")
                || (url.contains("https://m.9ji.com/addrList.aspx?id=") && url.contains("&groupid=")) ||
                (url.contains("https://m.9ji.com/payList.aspx?cityid=") && url.contains("&groupid=")) ||
                (url.contains("https://m.9ji.com/user/myaddress.aspx?addId=") && url.contains("&id=")) ||
                (url.contains("https://m.9ji.com/user/myaddress.aspx?act=") && url.contains("&id=")) ||
                (url.contains("https://huishou.9ji.com/mlpordercinfig/index?pid=")) ||
                (url.contains("https://huishou.9ji.com/Mlpordercinfig/SelectShouhuoren?pid=") && url.contains("&deliveryName=")) ||
                (url.contains("https://huishou.9ji.com/WapAlipay/Index?sub_id=") && url.contains("&payType=")) ||
                (url.contains("https://huishou.9ji.com/Mlpordercinfig/index?shid=") && url.contains("&pid="))
                ||(url.contains("/user/"))//个人中心有关的都不需要分享按钮
                ) {
            mdToolbar.setRightTitle("");
            mdToolbar.setRightIcon(R.drawable.kong_icon);
        }
        if (bundle.containsKey(RoutersAction.WEBVIEWTITLE)) {
            if (bundle.getString(RoutersAction.WEBVIEWTITLE) != null) {
                if (bundle.getString(RoutersAction.WEBVIEWTITLE).equals("我的订单")) {
                    mdToolbar.setRightTitle("");
                    mdToolbar.setRightIcon(R.mipmap.ic_chat);
                    right_icon = true;
                }
            }
        }
    }
    /**
     * 用webview加载网页
     */
    @SuppressLint("JavascriptInterface")
    public void useWebViewLoad() {
        Logs.Debug("gg=============useWebViewLoadUrl==" + scheme);
//        if(path.contains("https://m.9ji.com/event/")) {
//        path="https://m.9ji.com/zt/test.html";//jsbradge测试
//        }
        noShowTitle();
        paramSZ();
        initWebSettings(webView_content);
        webViewClick();
        webViewChrome();
        webViewLongClick();
        // app link
        if (!scheme.startsWith("jiuji://")) {
            webView_content.loadUrl(path);
        }
    }
    //重写标题内容
    public void viewTitleReWrite(String title,int flage){
        Logs.Debug("title======1="+title);
        if (!Tools.isEmpty(title)) {
            title.replace(" ", "");
        }
        if (title.equals("个人信息-九机网触屏版")) {
            flage_num = 0;
            bundle.remove(RoutersAction.WEBVIEWTITLE);
        }
        newString=title;
        if (flage_num == 0) {//当用另一个activity打开webview时会重复执行，用于限制不重复执行
            if (bundle.containsKey(RoutersAction.WEBVIEWTITLE)) {
                if (Tools.isEmpty(bundle.getString(RoutersAction.WEBVIEWTITLE))) {
                    if (title.contains("-九机触屏版")) {
                        newString = title.replace("-九机触屏版", "");
                    } else if (title.contains("-九机网触屏版")) {
                        newString = title.replace("-九机网触屏版", "");
                    } else if (title.contains("-九机网")) {
                        newString = title.replace("-九机网", "");
                    } else if (title.contains("九机网-")) {
                        newString = title.replace("九机网-", "");
                    } else {
                        newString = title;
                    }
                } else {
                    newString = bundle.getString(RoutersAction.WEBVIEWTITLE);
                }
            } else {
                if (title.contains("-九机触屏版")) {
                    newString = title.replace("-九机触屏版", "");
                } else if (title.contains("-九机网触屏版")) {
                    newString = title.replace("-九机网触屏版", "");
                } else if (title.contains("-九机网")) {
                    newString = title.replace("-九机网", "");
                } else if (title.contains("九机网-")) {
                    newString = title.replace("九机网-", "");
                } else {
                    newString = title;
                }
            }
            mdToolbar.setMainTitle(newString);
        }
        if(flage==1) {
            bundle.remove(RoutersAction.WEBVIEWTITLE);
        }
        flage_num = 1;
    }
    public void webViewChrome() {
        webView_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (p_flage_num == 0) {
                    progressBar.setProgress(newProgress);
                }
            }
            @Override
            public void onReceivedTitle(WebView view, final String title) {
                super.onReceivedTitle(view, title);
                WebViewTopTitle = title;
                viewTitleReWrite(title,0);
            }
            // 对话框
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 构建一个Builder来显示网页中的alert对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }
            // 带按钮的对话框
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("带选择的对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        result.confirm();
                    }
                });
                builder.setNeutralButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        result.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }
            //webView打开系统文件夹
            //  Android 2.2 (API level 8)到Android 2.3 (API level 10)版本选择文件时会触发该隐藏方法
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, null);
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);
            }
            // Android 3.0 (API level 11)到 Android 4.0 (API level 15))版本选择文件时会触发，该方法为隐藏方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg, acceptType, null);
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(
//                        Intent.createChooser(i, "Image Chooser"),
//                        FILECHOOSER_RESULTCODE);
            }
            // Android 4.1 (API level 16) -- Android 4.3 (API level 18)版本选择文件时会触发，该方法为隐藏方法
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileInput(uploadMsg, null, false);
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "Image Chooser"),FILECHOOSER_RESULTCODE);
            }
            // Android 5.0 (API level 21)以上版本会触发该方法，该方法为公开方法
            @SuppressWarnings("all")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                if (Build.VERSION.SDK_INT >= 21) {
                    final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;
                    //是否支持多选
                    openFileInput(null, filePathCallback, allowMultiple);
                    return true;
                } else {
                    return true;
                }
            }
            @SuppressLint("NewApi")
            protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]>
                    fileUploadCallbackSecond, final boolean allowMultiple) {
//                Android 5.0以下版本
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                }
                mFileUploadCallbackFirst = fileUploadCallbackFirst;
//                //Android 5.0及以上版本
                if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                }
                mFileUploadCallbackSecond = fileUploadCallbackSecond;
                final String[] permition=new String[]{Manifest.permission.CAMERA};
                BottomSheet builder= new BottomSheet.Builder(ExtrasActivity.this).title("选择图片").sheet(R.menu.pick_img).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == R.id.camera) {
                            // 创建File对象，用于存储拍照后的图片
                            selectImageFlage=true;
                            if (!Tools.checkoutPermissions(context,permition)){
//                                RxPermissions.getInstance(context).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
//                                    @Override
//                                    public void call(Boolean aBoolean) {
//                                        if (aBoolean) {
//                                        String tem_image_path = "file://" + FileUtil.appSavePathFile((new Date()).getTime() + "_tem_pick_image.jpg");
//                                        imageUri = Uri.parse(tem_image_path);
//                                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//打开相机
//                                        int currentapiVersion = Build.VERSION.SDK_INT;
//                                        if (currentapiVersion < 24) {
//                                            intent.putExtra("output", imageUri);
//                                            startActivityForResult(intent, TAKE_PHOTO);
//                                        } else {
//                                            ContentValues contentValues = new ContentValues(1);
//                                            contentValues.put("_data", imageUri.getPath());
//                                            Uri uri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);
//                                            intent.putExtra("output", uri);
//                                            startActivityForResult(intent, TAKE_PHOTO);
//                                        }
//                                            PickImageUtil.startCamera(ExtrasActivity.this);
//                                        }
//                                    }
//                                });
                            }else {
//                                PickImageUtil.startCamera(ExtrasActivity.this);
                            }
                        } else if (which == R.id.photo) {
                            selectImageFlage=true;
//                            Intent selectIntent = new Intent(context, ScanImageActivity.class);
//                            selectIntent.putExtra("count", 1);
//                            MyAdapter.setSelectedImage(new ArrayList<String>());
//                            startActivityForResult(selectIntent, REQUEST_CODE_FILE_PICKER);




//                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                                File photoFile = null;
//                                try {
//                                    photoFile = createImageFile();
//                                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
//                                } catch (IOException ex) {
//                                }
//                                if (photoFile != null) {
//                                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
//                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                            Uri.fromFile(photoFile));
//                                } else {
//                                    takePictureIntent = null;
//                                }
//                                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//                                contentSelectionIntent.setType("image/*");
//                                Intent[] intentArray;
//                                if (takePictureIntent != null) {
//                                    intentArray = new Intent[]{takePictureIntent};
//                                } else {
//                                    intentArray = new Intent[0];
//                                }
//                                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//                                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//                                chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");
//                    //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
//                                startActivityForResult(chooserIntent, REQUEST_CODE_FILE_PICKER);
//                            }
                        }
                    }
                }).show();
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (mFileUploadCallbackFirst != null &&!selectImageFlage) {
                            mFileUploadCallbackFirst.onReceiveValue(null);
                            mFileUploadCallbackFirst = null;
                        } else if (mFileUploadCallbackSecond != null &&!selectImageFlage) {
                            mFileUploadCallbackSecond.onReceiveValue(null);
                            mFileUploadCallbackSecond = null;
                        }
                    }
                });
            }

            //webView打开系统文件夹
            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否允许小九获取您的位置信息");
                builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.invoke(origin, true, true);
                    }
                });

                builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.invoke(origin, false, false);
                    }
                });
                builder.show();
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(ExtrasActivity.this);
                frameLayout.setLayoutParams(new SwipeToLoadLayout.LayoutParams(SwipeToLoadLayout.LayoutParams.MATCH_PARENT, SwipeToLoadLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }

        });
    }
    public void webViewClick() {
        //js回调4.4以后
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView_content.evaluateJavascript("javascript:", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String arg0) {
//
//                }
//            });
//        }else{  //4.4以前
//            String call = "javascript:";
//            webView_content.loadUrl(call);
//        }
        webView_content.setWebViewClient(new WebViewClient() {
            //返回值：true 不会显示网页资源，需要等待你的处理，false 就认为系统没有做处理，会显示网页资源
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {     // TODO Auto-generated method stub
                boolean returnFlage = true;
                RoutersWebViewClick wc = new RoutersWebViewClick();
                bundle.remove("RoutersAction.WEBVIEWTITLE");
                Logs.Debug("gg===========url==" + url);
                share_string = url;
                if (bundle.containsKey("webview_key")) {
                    bundle.remove("webview_key");
                }
                if(url.contains("https://api.map.baidu.com/direction?")){
                    Intent newWebViewInt = new Intent(context, NewWebView.class);
                    newWebViewInt.putExtra("Url",path);
                    startActivity(newWebViewInt);
                    return true;
                }
                if (url.contains("jiuji://jsBridge:0/pay?")) {
                    String type = Uri.parse(url).getQueryParameter("type");
                    String orderNo = Uri.parse(url).getQueryParameter("orderId");
                    Bundle bund = new Bundle();
                    bund.putInt("type", Integer.valueOf(type));
                    bund.putString("orderNo", orderNo);
                    Logs.Debug("gg=========type=="+type+"==orderId=="+orderNo);
                    Routers.open(context, RoutersAction.PAYMENT, bund);
                    return true;
                }else {
                    returnFlage = wc.startUrl(ExtrasActivity.this, context, webView_content, url, bundle);
                    if (!returnFlage) {
                        if(url.contains("https://huishou.9ji.com/MHsOrderList/Index")){
                            webView_content.goBack();
                        }else{
                            progressBar.setVisibility(View.VISIBLE);
                            view.loadUrl(url);
                        }
                    }
                }
                return returnFlage;
            }
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showShare(url);
            }
            public void onPageFinished(WebView view, String url) {
                Logs.Debug("title======"+view.getTitle());
                super.onPageFinished(view, url);
                if (webView_content != null) {
                    view.loadUrl("javascript:" + mStrBridgeJs);
                }
                progressBar.setVisibility(View.GONE);
                String title = String.valueOf(view.getTitle());
//                viewTitleReWrite(title,1);
                showShare(url);
                webView_content.loadUrl("javascript:APP.shareInfoAndroid();void(0)");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Logs.Debug("gg==========1==onReceivedError"+error.getErrorCode());
                }
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Logs.Debug("gg==========1==onReceivedHttpError");
                super.onReceivedHttpError(view, request, errorResponse);
            }

            // 加载错误时要做的工作
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Logs.Debug("error=" + description);
                Toast.makeText(context, errorCode + "/" + description, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });
    }
    //监听wenView长安
    public void webViewLongClick() {
        webView_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = ((WebView) v).getHitTestResult();
                if (hitTestResult.getType() == 5) {
                    showSaveDialog(hitTestResult.getExtra());
                    return true;
                }
                return false;
            }
        });
    }
    //打开文件后做的返回操作
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //Android 5.0以下版本
//               dataUris =  new Uri[]{imageUri};
                selectImageFlage=false;
//                if(imageUri != null){
//                ImageData imageData = new ImageData(imageUri);
                imageUri = PickImageUtil.handleResult(requestCode,resultCode,intent);
                if (imageUri != null){
                    String path = imageUri.getPath();
                    ImageData imageData = new ImageData(readPictureDegree(path,imageUri));
                    //压缩图片
                    compressImage_two(context,imageData,new DataResponse(){
                        @Override
                        public void onSucc(Object response) {
                            imageUri = Uri.parse("file://" + response.toString().split(",")[0]);
                            dataUris =  new Uri[]{imageUri};
                            if(mFileUploadCallbackFirst!=null){
                                mFileUploadCallbackFirst.onReceiveValue(imageUri);
                            }
                            else if (mFileUploadCallbackSecond != null) {//Android 5.0及以上版本
                                Logs.Debug("gg===============30=="+dataUris[0].getPath());
                                mFileUploadCallbackSecond.onReceiveValue(dataUris);
                            }
                        }

                        @Override
                        public void onFail(String error) {
                            UITools.toastShowLong(context,error);
                        }
                    });
                }
            }else{
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                }
            }
        }else if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                Logs.Debug("gg===============12");
                selectImageFlage=false;
                Logs.Debug("gg===============13");
                File f;
                Logs.Debug("gg===============14");
                if (intent != null) {
                    Logs.Debug("gg===============15");
                    dataUris = null;
                    final List<String> imalist = (List<String>) intent.getSerializableExtra("filepaths");
                    Logs.Debug("gg===========imageString=="+imalist.get(0));///storage/emulated/0/9ji/1492070962710_tem_pick_image.jpg
//                            dataUris = new Uri[]{Uri.parse(imalist.get(0))};
                    f = new File(imalist.get(0));
                    Uri uri = Uri.fromFile(f);
                    ImageData imagedata = new ImageData(uri);
                    compressImage_two(context, imagedata, new DataResponse() {
                        @Override
                        public void onSucc(Object response) {
                            Logs.Debug("gg===============16=="+response.toString());
                            Logs.Debug("gg===========imageString==1="+response.toString().split(",")[0]);
                            dataUris = new Uri[]{Uri.parse("file://" +response.toString().split(",")[0])};
                        }
                        @Override
                        public void onFail(String error) {
                            Logs.Debug("gg===============17");
                            UITools.toastShowLong(context, error);
                        }
                    });
                }
                else {
                    if (mCameraPhotoPath != null&&!Tools.isEmpty(mCameraPhotoPath)) {
                        dataUris = new Uri[]{Uri.parse(mCameraPhotoPath)};
                        ImageData imageData = new ImageData(Uri.parse(mCameraPhotoPath));
                        compressImage_two(context, imageData, new DataResponse() {
                            @Override
                            public void onSucc(Object response) {
                                dataUris = new Uri[]{Uri.parse("file://" + response.toString().split(",")[0])};
                            }

                            @Override
                            public void onFail(String error) {
                                UITools.toastShowLong(context, error);
                            }
                        });
                    }
                }
                //Android 5.0以下版本
                if (mFileUploadCallbackFirst != null && intent != null) {
                    mFileUploadCallbackFirst.onReceiveValue(dataUris[0]);
                } else if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(dataUris[0]);
                } else if (mFileUploadCallbackSecond != null) {//Android 5.0及以上版本
                    Logs.Debug("gg===============22"+dataUris[0].getPath());
                    mFileUploadCallbackSecond.onReceiveValue(dataUris);///storage/emulated/0/9ji/1494325919110.jpg
                }
            }else{
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                } else if (mFileUploadCallbackSecond != null) {
                    Logs.Debug("gg===============25");
                    mFileUploadCallbackSecond.onReceiveValue(null);
                }
            }
        }
        mFileUploadCallbackFirst = null;
        mFileUploadCallbackSecond = null;
        return;
    }
    //压缩图片
    public void compressImage_two(final Context context, final ImageData imagedata, final DataResponse dr) {
        if (imagedata != null) {
            String time1 = new Date().getTime() + ".jpg";
            final String btm = imagedata.compress(context, 480, 800, time1);
            Logs.Debug("gg==========btm=====" + btm);
            filecompresspath = FileUtil.appSavePathFile(time1);
            dr.onSucc(filecompresspath + "," + btm);
        }
    }
    //判断图片是否被旋转，并把图片旋转回去
    public Uri readPictureDegree(String path,Uri mUri) {
        int degree  = 0;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Uri imgUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resizedBitmap, null,null));
        return imgUri;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logs.Debug(path);
    }
    //压缩图片
//    public void compressImage_two(final Context context, final ImageData imagedata, final DataResponse dr) {
//        if (imagedata != null) {
//            String time1 = new Date().getTime() + ".jpg";
//            final String btm = imagedata.compress(context, 480, 800, time1);
//            Logs.Debug("gg==========btm=====" + btm);
//            filecompresspath = FileUtil.appSavePathFile(time1);
//            dr.onSucc(filecompresspath + "," + btm);
//        }
//    }
    //监听分享的回调
    @Subscribe
    public void onPostEvent(BusEvent event) {
        int action = event.getAction();
        switch (action) {
            case BusAction.SUCCESS:
                try {
                    webView_content.loadUrl("javascript:APP.shareSuccess();");
                } catch (Exception e) {

                }
                break;
            case BusAction.FAILE:
                try {
                    webView_content.loadUrl("javascript:APP.shareFail();");
                } catch (Exception e) {
                    Routers.open(context, path);
                }
                break;
            case BusAction.OrderBack:
                if (event.getObject() != null && !Tools.isEmpty(event.getObject().toString())) {
                    String orderId = event.getObject().toString();
                    webView_content.reload();
                }
                break;
        }
    }
    //监听返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webView_content.canGoBack()) {
                Logs.Debug("gg===============apay=="+path);
                if (path.contains("https://huishou.9ji.com/MHsOrderList/Index")||path.contains("https://huishou.9ji.com/m/?from=tb5")) {
                    webView_content.goBack();
                    webView_content.goBack();
                } else {
                    if (!path.contains("https://www.9ji.com/event")) {
                        webView_content.goBack();
                    } else {
                        finish();
                    }
                }
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //MDToolBar控件返回键监听
    @Override
    public void onBackClick() {
        if (share_json != null) {
            share_json = null;
        }
        finish();
    }
    //MDToolBar控件分享键监听
    @Override
    public void onRigthClick() {
        String intenTitle = WebViewTopTitle;
        String url = path;
        String img = "";
        String content = intenTitle;
        if (share_json != null) {
            try {
                intenTitle = share_json.getString("title");
            url = share_json.getString("link");
            img = share_json.getString("imgUrl");
            content = share_json.getString("desc");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (!Tools.isEmpty(share_string)) {
            url = share_string;
            Logs.Debug("gg===========sh0are_json=2=" + share_string);
        } else {
            url = path;
            Logs.Debug("gg===========share_json=3=" + url);
        }

        if (right_icon) {
            Routers.open(context, RoutersAction.CHAT);
        } else {
            Logs.Debug("img==="+ img);
//            Intent intent = new Intent(context, MyShareActivity.class);
//            Bundle bundle = new Bundle();
//            ShareData shareData = new ShareData(intenTitle, 3);
//            shareData.setTitle(intenTitle);
//            shareData.setUrl(url);
//            shareData.setImagerUrl(img);
//            shareData.setDescription(content);
//            bundle.putSerializable("data", shareData);
//            intent.putExtras(bundle);
//            startActivity(intent);
        }
    }
    //分享成功后，回调js方法
    private class ShareHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 成功
                    webView_content.loadUrl("javascript:APP.shareSuccess();");
                    break;
                case 1:
                    // 失败
                    webView_content.loadUrl("javascript:APP.shareFail();");
                    break;
            }
        }
    }
    /**
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //prefix,suffix,directory
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        return imageFile;
    }

    /**
     * 保存图片对话框
     *
     * @param url
     */
    private void showSaveDialog(final String url) {
        new BottomSheet.Builder(this).sheet(R.menu.save_img).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == R.id.ic_save) {
                    saveImg(url);

                } else if (which == R.id.ic_cancel) {
                    dialog.dismiss();

                }
            }
        }).show();
    }

    private void saveImg(final String url) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    bmp = Glide.with(context).load(url).asBitmap().into(150, 150).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                }
                File dir = new File(file_path);
                if (!dir.exists())
                    dir.mkdirs();
                file = new File(dir, new Date().getTime() + ".png");
                try {
                    fOut = new FileOutputStream(file);
                    if (!bmp.isRecycled()) {
                        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    }
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                saveImageToGallery(file,context, bmp);
            }
        }.execute();
    }

    private static void saveImageToGallery(File file, Context context, Bitmap bmp) {
        //把文件加入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
            UITools.toastShowShort(context, "图片保存至" + file.getAbsolutePath() + "/" + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /** 视频播放全屏 **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        ExtrasActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(ExtrasActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView_content.setVisibility(View.VISIBLE);
    }

    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * 初始化webview   基本不会变
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
        webView.getSettings().setBlockNetworkLoads(false);
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
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        //调用页面中java的方法
        JsInterface jsInterface = new JsInterface(context);
        webView.addJavascriptInterface(jsInterface, "shareAndroid");

        // 可以让不同的density的情况下，可以让页面进行适配//设置默认缩放方式尺寸是far
        mDensity = context.getResources().getDisplayMetrics().density;
        //WebView加上这个设置后,WebView里的字体就不会随系统字体大小设置发生变化了.
        webSettings.setTextSize( WebSettings.TextSize.SMALLEST );
        if (mDensity >= 240) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                webSettings.setTextZoom(100);
            }
            webView_content.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity >= 160&&mDensity<240) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                webSettings.setTextZoom(100);
            }
            webView_content.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity<160){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                webSettings.setTextZoom(100);
            }
            webView_content.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }
    }
}
