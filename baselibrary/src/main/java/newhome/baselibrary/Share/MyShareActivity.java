package newhome.baselibrary.Share;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import newhome.baselibrary.Bus.BusAction;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.BusProvider;
import newhome.baselibrary.ImageHandle.CompressImage.BitmapTo;
import newhome.baselibrary.ImageHandle.CompressImage.Compress;
import newhome.baselibrary.ImageHandle.CompressImage.ImageData;
import newhome.baselibrary.Model.ShareData;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.AppKey;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;
import newhome.baselibrary.Tools.UITools;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class MyShareActivity extends Activity implements  View.OnClickListener,IWeiboHandler.Response, DataResponse {
    /*
        仿照dialog,从下到上
      */
    int intScreenBrightness;
    LinearLayout item_share_wx;
    LinearLayout item_share_wxquan;
    LinearLayout item_share_qzone;
    LinearLayout item_share_wxfav;
    LinearLayout item_share_copy;
    LinearLayout item_share_qq;
    LinearLayout item_share_xinlang;
    LinearLayout item_share_msg;
    LinearLayout ll_share_otherdismiss;
    TextView item_share_cancel;
    ShareData mData ;
    public static Tencent mTencent;////QQ分享,需要open_sdk包
    public static final int MSGRESULT =1002;
    Activity mContext;
    private IWeiboShareAPI mWeiboShareAPI;//微博分享，需要weiboSDKCore包
    IWXAPI mWeixinApi;//微信分享，需要libammsdk包
    int mSort;
    Bitmap shareBitmap;
    BusEvent event;
    ImageData imagedata;
    String time1="";
    static double mid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //底部显示菜单
        Window w = this.getWindow();
        WindowManager.LayoutParams wl = w.getAttributes();
        wl.x = 0;
        wl.y = 500;
        w.setAttributes(wl);
//        screenBrightnessCheck();
//        if (intScreenBrightness!=255) {
//            setBrightness(this,intScreenBrightness-35);
//        }
        setTheme(R.style.TransparentActivty);
        setContentView(R.layout.activity_my_share);
        mContext =this;
        BusProvider.getInstance().register(this);
        findViewById();
        setUp(savedInstanceState);
    }
    public void findViewById() {
        item_share_wx = (LinearLayout) findViewById(R.id.item_share_wx);
        item_share_wxquan = (LinearLayout) findViewById(R.id.item_share_wxquan);
        item_share_qq = (LinearLayout) findViewById(R.id.item_share_qq);
        item_share_xinlang = (LinearLayout) findViewById(R.id.item_share_xinlang);
        item_share_cancel = (TextView) findViewById(R.id.item_share_cancel);
        item_share_msg = (LinearLayout) findViewById(R.id.item_share_msg);
        item_share_qzone = (LinearLayout) findViewById(R.id.item_share_qzone);
        item_share_wxfav = (LinearLayout) findViewById(R.id.item_share_wxfav);
        item_share_copy = (LinearLayout) findViewById(R.id.item_share_copy);
        ll_share_otherdismiss = (LinearLayout) findViewById(R.id.ll_share_otherdismiss);
        item_share_wx.setOnClickListener(this);
        item_share_wxquan.setOnClickListener(this);
        item_share_qq.setOnClickListener(this);
        item_share_xinlang.setOnClickListener(this);
        item_share_cancel.setOnClickListener(this);
        item_share_msg.setOnClickListener(this);
        item_share_qzone.setOnClickListener(this);
        item_share_wxfav.setOnClickListener(this);
        item_share_copy.setOnClickListener(this);
        ll_share_otherdismiss.setOnClickListener(this);

    }
    private void setUp(Bundle savedInstanceState) {
        event = new BusEvent();
//        WXPayEntryActivity.mshare =this;
        if (mTencent == null) {
            //QQ分享
//            mTencent = Tencent.createInstance(AppKey.QQ, this);
        }
        //获得数据
        mData = (ShareData) getIntent().getSerializableExtra("data");
        mSort=mData.getSort();
        // 提前获取 分享的  bitmap
        if (!Tools.isEmpty(mData.getImagerUrl())) {
            // 网络图片
            new Thread(new GetBitmapTask(mData.getImagerUrl(), new DataResponse() {
                @Override
                public void onSucc(Object o) {
                    shareBitmap = (Bitmap) o;
                }

                @Override
                public void onFail(String s) {
                    shareBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
                }
            })).start();
        }else{
            shareBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
        }
        // 创建微博分享接口实例
//        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, AppKey.XL_APP_KEY);
//        mWeiboShareAPI.registerApp();

        //微信api注册
//        mWeixinApi = WXAPIFactory.createWXAPI(mContext, null);
//        mWeixinApi.registerApp(AppKey.WX);

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.item_share_wx) {
            shareWX(0);
        } else if (id == R.id.item_share_wxquan) {
            shareWX(1);
        } else if (id == R.id.item_share_qq) {
            shareOnQQ(false);
        } else if (id == R.id.item_share_qzone) {
            shareOnQQ(true);
        } else if (id == R.id.item_share_msg) {
            shareSMS();
        } else if (id == R.id.item_share_xinlang) {
            if (mWeiboShareAPI.isWeiboAppInstalled()) {
                shareWeibo();
            }else {
                UITools.toastShowShort(mContext,"您的手机没有安装微博");
            }
        } else if (id == R.id.item_share_wxfav) {
            shareWX(2);
        } else if (id == R.id.item_share_copy) {
            shareCopy();
        } else { // 取消，点击外部
            finish();
        }
        finish();
    }
    /**
     * 微信分享
     * 0 好友 1 朋友圈 2 收藏
     */
    private void shareWX(int type) {
        UITools.toastShowShort(mContext,"开始分享");
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mData.getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = mData.getTitle();
        msg.description = mData.getDescription();
        if (shareBitmap != null) {
            Logs.Debug("gg===========mid=="+mid);
            msg.thumbData = BitmapTo.bmpToByteArray(shareBitmap, true);//微信分享图片为byte格式,理论上大于32K的图片无法进行微信分享
            if(mid>32) {
                msg.thumbData=BitmapTo.bmpToByteArray(BitmapFactory.decodeResource(getResources(),R.mipmap.logo),true);
            }
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (type == 0) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 1) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else if (type == 2) {
            req.scene = SendMessageToWX.Req.WXSceneFavorite;
        }
        mWeixinApi.sendReq(req);
    }
    public static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 分享到QQ 、qq 空间
     */
    public void shareOnQQ(boolean qzone) {
        //本地地址一定要传sdcard路径，不要什么getCacheDir()或getFilesDir()
        mTencent = Tencent.createInstance(AppKey.QQ, mContext);
        if (mTencent == null) {
            return;
        }
        if (!mTencent.isSessionValid()) {
            qqShareListener = new IUiListener() {
                @Override
                public void onCancel() {

                }
                @Override
                public void onComplete(Object response) {
                    // TODO Auto-generated method stub
                    //  Util.toastMessage(QQActivity.this, "onComplete: " + response.toString());
                    UITools.toastShowShort(mContext, "QQ分享成功");
                    event.setAction(BusAction.SUCCESS);
                    BusProvider.getInstance().post(event);
                    finish();
                }
                @Override
                public void onError(UiError e) {
                    // TODO Auto-generated method stub
                    event.setAction(BusAction.FAILE);
                    BusProvider.getInstance().post(event);
                    UITools.toastShowShort(mContext, "分享失败：: " + e.toString());
                    finish();
                }
            };
            Bundle params = new Bundle();
            if (!Tools.isEmpty(mData.getTitle())) {
                params.putString(QQShare.SHARE_TO_QQ_TITLE, mData.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mData.getDescription());
            }else{
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "九机网");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "九机网");
            }
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mData.getUrl());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mData.getImagerUrl());
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "九机网");
            if (qzone){
                ArrayList<String> strs = new ArrayList<String>();
                strs.add(mData.getUrl());
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, strs);
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                mTencent.shareToQzone(MyShareActivity.this,params,qqShareListener);
            }else{
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                mTencent.shareToQQ(MyShareActivity.this, params, qqShareListener);
            }
        }
    }
    /**
     * 短信分享
     */
    private void shareSMS() {
        item_share_msg.post(new Runnable() {
            @Override
            public void run() {
                RxPermissions.getInstance(mContext).request(Manifest.permission.SEND_SMS).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            String smsBody = mData.getDescription();
                            Uri smsToUri = Uri.parse( "smsto:" );
                            Intent sendIntent =  new  Intent(Intent.ACTION_SENDTO, smsToUri);
                            //短信内容
                            sendIntent.putExtra( "sms_body", smsBody);
                            mContext.startActivityForResult(sendIntent, MSGRESULT );
                        }
                    }
                });
            }
        });
    }
    /**
     * 微博分享
     */
    private void shareWeibo() {
        WeiboMessage weiboMessage = new WeiboMessage();
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(shareBitmap);
        weiboMessage.mediaObject = imageObject;
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        mWeiboShareAPI.sendRequest(MyShareActivity.this, request);
    }
    /**
     * 复制并分享
     */
    private void shareCopy() {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            clipboard.setText(mData.getDescription() + mData.getUrl());
        }
        UITools.toastShowShort(mContext,"复制成功");
    }
    @Override
    protected void onStart() {
        super.onStart();
//        Statistics.getInstance().onStart(this);//统计
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Statistics.getInstance().onStop(this);//统计
    }
    /**
     * 获取分享的 bitmap
     */
    class GetBitmapTask implements Runnable {

        private String uri;
        private DataResponse dr;

        public GetBitmapTask(String url, DataResponse dr) {
            this.uri = url;
            this.dr = dr;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                final byte[] b = output.toByteArray();
                mid = b.length/1024;
                Logs.Debug("gg=======file==123456mid=="+mid);
                File file=bitmapToFile(bitmap);
                Uri uri = Uri.fromFile(file);
                compressImage_two(getApplicationContext(), uri, new DataResponse() {
                    @Override
                    public void onSucc(Object o) {
                        Logs.Debug("gg=======file=="+o.toString());
                        Bitmap bitmap1=getSDCardImg(o.toString());
                        dr.onSucc(bitmap1);
                    }
                    @Override
                    public void onFail(String s) {
                        Logs.Debug("gg=======file==12=="+s);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                dr.onFail(e.toString());
            }
        }
    }
    IUiListener qqShareListener ;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }
    //微博分享回调
    @Override
    public void onResponse(BaseResponse baseResponse) {
        if (baseResponse != null) {
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    event.setAction(BusAction.SUCCESS);
                    BusProvider.getInstance().post(event);
                    UITools.toastShowShort(mContext,"新浪微博分享成功");
                    finish();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, "你取消了分享", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    event.setAction(BusAction.FAILE);
                    BusProvider.getInstance().post(event);
                    UITools.toastShowShort(mContext,"新浪微博分享失败"+baseResponse.errMsg);
                    break;
            }
        }
    }

    @Override
    public void onSucc(Object response) {
        UITools.toastShowShort(mContext,response.toString());
    }

    @Override
    public void onFail(String error) {
        UITools.toastShowShort(mContext,error);
    }
    /** * 设置亮度 */
    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha=1f;
        activity.getWindow().setAttributes(lp);
    }
    //压缩图片
    public void compressImage_two(final Context context, final Uri imageUri, final DataResponse dr) {
            String filecompresspath = FileUtil.appSavePathFile(time1);
            final String btm = Compress.Compress(context, 480, 800, filecompresspath,imageUri);
            Logs.Debug("gg==========btm=====" + btm);
            Logs.Debug("gg==========btm=====" + filecompresspath);
            dr.onSucc(filecompresspath);
    }
    //bitmap保存为file
    public File bitmapToFile(Bitmap bm){
        Logs.Debug("保存图片");
        time1 = new Date().getTime() + ".jpg";
        String filecompresspath = FileUtil.appSavePathFile(time1);
        File file=new File(filecompresspath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Logs.Debug("已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }
    public static Bitmap getSDCardImg(String imagePath)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }
}
