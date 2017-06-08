package newhome.baselibrary.Pay;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.util.concurrent.TimeUnit;
import newhome.baselibrary.App;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.UITools;
import newhome.baseres.view.BaseActivity;
import rx.functions.Action1;
/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class PayMentActivity extends BaseActivity {

    CheckBox cb_zhb;
    CheckBox cb_wx;
    CheckBox cb_wxd;
    Button btn_pay;
    int index;
    PayPresenter payPresenter;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    public final static int BUSACTION_WXPAY_SUCCESS = 0;
    public final static int BUSACTION_WXPAY_FAIL = -1;
    public final static int BUSACTION_WXPAY_CANCEL = -2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);
        final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp("wx3afcc3e3066fa611");
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById();
        RxView.clicks(btn_pay)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        switch (index) {
                            case 0:
                                UITools.toastShowShort(context, "请选择支付方式");
                                break;
                            case 1:
                                Intent intent=new Intent();
                                        payPresenter.aliPay("", 12.00, "", 0, 0, intent, new DataResponse() {
                                            @Override
                                            public void onSucc(Object response) {

                                            }

                                            @Override
                                            public void onFail(String error) {

                                            }
                                        });
                                break;
                            case 2:
                                        payPresenter.weixinPay("", 12.00, 0, 0, new
                                                DataResponse() {
                                                    @Override
                                                    public void onSucc(Object response) {

                                                    }
                                                    @Override
                                                    public void onFail(String error) {

                                                    }
                                                });
                                break;
                            case 3:
//                                Intent intent = new Intent(context, WXPayOtherActivity.class);
//                                intent.putExtra("price", String.valueOf(Tools.valueFormat(price_pr)));
//                                intent.putExtra("subid", sub_id + "");
//                                intent.putExtra("payType", type);
//                                intent.putExtra("weixinContent", "帮帮您朋友的小忙呗。。。");
//                                startActivity(intent);
                                break;
                        }
                    }
                });
        setCBChangerListener();
    }

    public void setCBChangerListener() {
//        index = 1;
        cb_zhb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_zhb.setChecked(b);
                    cb_wx.setChecked(false);
                    cb_wxd.setChecked(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        cb_zhb.setBackground(getResources().getDrawable(R.mipmap.newselect));
                        cb_wx.setBackground(getResources().getDrawable(R.mipmap.newnoselecet));
                        cb_wxd.setBackground(getResources().getDrawable(R.mipmap.newnoselecet));
                    }
                    index = 1;
                }
            }
        });
        cb_wx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_zhb.setChecked(false);
                    cb_wx.setChecked(b);
                    cb_wxd.setChecked(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        cb_zhb.setBackground(getResources().getDrawable(R.mipmap.newnoselecet));
                        cb_wx.setBackground(getResources().getDrawable(R.mipmap.newselect));
                        cb_wxd.setBackground(getResources().getDrawable(R.mipmap.newnoselecet));
                    }
                    index = 2;
                }
            }
        });
        cb_wxd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_zhb.setChecked(false);
                    cb_wx.setChecked(false);
                    cb_wxd.setChecked(b);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        cb_zhb.setBackground(getResources().getDrawable(R.mipmap.newnoselecet));
                        cb_wx.setBackground(getResources().getDrawable(R.mipmap.newnoselecet));
                        cb_wxd.setBackground(getResources().getDrawable(R.mipmap.newselect));
                    }
                    index = 3;
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.rl_zhihubao) {
            cb_zhb.setChecked(true);
            cb_wx.setChecked(false);
            cb_wxd.setChecked(false);
            index = 1;
        } else if (i == R.id.rl_weixin) {
            cb_zhb.setChecked(false);
            cb_wx.setChecked(true);
            cb_wxd.setChecked(false);
            index = 2;
        } else if (i == R.id.rl_weixindaifu) {
            cb_zhb.setChecked(false);
            cb_wx.setChecked(false);
            cb_wxd.setChecked(true);
            index = 3;
        }
    }

    @Override
    public void findViewById() {
        cb_zhb = (CheckBox) findViewById(R.id.cb_zhb);
        cb_wx = (CheckBox) findViewById(R.id.cb_wx);
        cb_wxd = (CheckBox) findViewById(R.id.cb_wxd);
        btn_pay=(Button)findViewById(R.id.btn_pay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
    @Subscribe
    public void onPostEvent(BusEvent postEvent) {
        switch (postEvent.getAction()) {
            case BUSACTION_WXPAY_SUCCESS:
                //微信支付成功
                App.toast("支付成功");
                break;
            case BUSACTION_WXPAY_FAIL:
                //微信支付失败
                App.toast("支付失败");
                break;
            case BUSACTION_WXPAY_CANCEL:
                //微信支付取消
                App.toast("支付取消");
                break;

        }
    }
}
