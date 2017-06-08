package home.mymodel.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.BusProvider;
import newhome.baselibrary.Pay.PayMentActivity;
import newhome.baselibrary.Save.PreferencesProcess;
import newhome.baselibrary.Tools.AppKey;
import newhome.baselibrary.Tools.Logs;


/**
 * Created by Administrator on 2017/3/23 0023.
 */
//微信支付回调
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);


        Logs.Debug("onCreate ===WXPayEntryActivity");
        //   LoginPresenter.wxApi.handleIntent(getIntent(),this);
        api = WXAPIFactory.createWXAPI(this, AppKey.WX);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
       Logs.Debug("onReq===>>>" + req.getType());
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == 5) {
            Logs.Debug("onPayFinish.errCode===>>>" + resp.errCode);
            BusEvent postEvent = new BusEvent();
            if (resp.errCode == 0) {
                postEvent.setAction(PayMentActivity.BUSACTION_WXPAY_SUCCESS);
                PreferencesProcess.putPreferencesInt(this,"paybackflage",1);
            } else if (resp.errCode == -1) {
                postEvent.setAction(PayMentActivity.BUSACTION_WXPAY_FAIL);
                PreferencesProcess.putPreferencesInt(this,"paybackflage",2);
            } else {
                postEvent.setAction(PayMentActivity.BUSACTION_WXPAY_CANCEL);
                PreferencesProcess.putPreferencesInt(this,"paybackflage",3);
            }
            BusProvider.getInstance().post(postEvent);
            finish();
        }
    }
}
