package home.mymodel.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Tools.AppKey;
import newhome.baselibrary.Tools.Logs;


/**
 * Created by xiaolu on 2016/12/22.
 */
//http://www.cnblogs.com/lenkevin/p/5485701.html
//微信绑定回调
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    BusEvent event;

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p/>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p/>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义
     * 信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p/>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.Debug("onCreate ===WXEntryActivity");
        //   LoginPresenter.wxApi.handleIntent(getIntent(),this);
        api = WXAPIFactory.createWXAPI(this, AppKey.WX);
        api.handleIntent(getIntent(), this);
//        if(weixinActivity.wxApi!=null){
//            weixinActivity.wxApi.handleIntent(getIntent(), this);
//        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Logs.Debug("onReq==" + baseReq.openId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        finish();
    }
    @Override
    public void onResp(BaseResp baseResp) {
        Logs.Debug("baseResp == " + baseResp.errStr);
        Logs.Debug("baseResp == " + baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
//                if (weixinActivity.isWXLogin) {
//                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
//                    weixinActivity.WX_CODE = sendResp.code;
//                } else if (LoginPresenter.isWXLogin) {
//                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
//                    LoginPresenter.WX_CODE = sendResp.code;
//                    Logs.Debug("code ====================== " + sendResp.code);
//                }else if (UserUpImgActivity.isWXLogin){
//                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
//                    UserUpImgActivity.WX_CODE = sendResp.code;
//                }else if(HongBaoActivity.isWXLogin){
//                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
//                    HongBaoActivity.WX_CODE = sendResp.code;
//                }
//                finish();
//                event = new BusEvent();
//                event.setAction(BusAction.SUCCESS);
//                event.setObject("0");
//                BusProvider.getInstance().post(event);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                UITools.toastShowShort(this, "取消");
//                LoginPresenter.isWXLogin = false;
//                UserUpImgActivity.isWXLogin = false;
//              finish();
//                event = new BusEvent();
//                event.setAction(BusAction.SUCCESS);
//                event.setObject("0");
//                BusProvider.getInstance().post(event);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                UITools.toastShowShort(this, "拒绝");
//                LoginPresenter.isWXLogin = false;
//                UserUpImgActivity.isWXLogin = false;
//                finish();
                break;
            case BaseResp.ErrCode.ERR_COMM:
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                break;
            default:
//                UITools.toastShowShort(this, "失败");
//                LoginPresenter.isWXLogin = false;
//                UserUpImgActivity.isWXLogin = false;
//                event = new BusEvent();
//                event.setAction(BusAction.FAILE);
//                event.setObject("1");
//                BusProvider.getInstance().post(event);
//                finish();
                break;
        }
    }
}
