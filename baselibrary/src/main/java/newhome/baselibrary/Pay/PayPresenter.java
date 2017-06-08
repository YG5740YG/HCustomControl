package newhome.baselibrary.Pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;

import io.github.mayubao.pay_library.AliPayAPI;
import io.github.mayubao.pay_library.AliPayReq;
import io.github.mayubao.pay_library.PayAPI;
import io.github.mayubao.pay_library.WechatPayReq;
import newhome.baselibrary.App;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.RoutersAction;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.Routers.Routers;
import newhome.baselibrary.Save.PreferencesProcess;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class PayPresenter {
    PayControl mcontrol;
    Context mContext;
    Activity activity;
    BusEvent event_ALIPay;

    public PayPresenter(Activity activity, Context context) {
        this.mContext = context;
        //  mModel= new payMentPresenter(context);
        mcontrol = new PayControl();
        this.activity = activity;
    }
    /**
     * 支付宝支付
     *
     * @param orderId
     * @param price
     */
    public void aliPay(final String orderId, Double price, String otherDsc, int type, int crowducid, final Intent intent, final DataResponse dr) {
        if (Tools.isEmpty(orderId) || price == 0.0) {
            return;
        }
        mcontrol.aliPay(mContext, orderId, price, type, otherDsc, crowducid, new DataResponse() {
            @Override
            public void onSucc(Object response) {
                startAlipayClient(response,orderId,intent); //订单确定成功，顶用支付宝
            }

            @Override
            public void onFail(String error) {
//                mView.dialogDismiss();
                dr .onFail(error);
            }
        });
    }

    /**
     * 支付宝支付操作
     */
    private void startAlipayClient(Object response, final String orderId, final Intent intent) {
        final HashMap<String, String> r = (HashMap<String, String>) response;

//        /1.创建支付宝支付配置
        AliPayAPI.Config config = new AliPayAPI.Config.Builder()
//                .setRsaPrivate(rsa_private) //设置私钥 (商户私钥，pkcs8格式)
//                .setRsaPublic(rsa_public)//设置公钥(// 支付宝公钥)
//                .setPartner(partner) //设置商户
//                .setSeller(seller) //设置商户收款账号
//                .create();
                .setRsaPrivate("") //设置私钥 (商户私钥，pkcs8格式)
                .setRsaPublic("")//设置公钥(// 支付宝公钥)
                .setPartner("") //设置商户
                .setSeller("") //设置商户收款账号
                .create();
//        /2.创建支付宝支付请求
        AliPayReq aliPayReq = new AliPayReq.Builder()
                .with(activity)//Activity实例
                .apply(config)//支付宝支付通用配置
//                .setOutTradeNo(outTradeNo)//设置唯一订单号
//                .setPrice(price)//设置订单价格
//                .setSubject(orderSubject)//设置订单标题
//                .setBody(orderBody)//设置订单内容 订单详情
//                .setCallbackUrl(callbackUrl)//   .setOutTradeNo(outTradeNo)//设置唯一订单号
                .setPrice("")//设置订单价格
                .setSubject("")//设置订单标题
                .setBody("")//设置订单内容 订单详情
                .setCallbackUrl("")//设置回调地址
                .create()//
                .setOnAliPayListener(new AliPayReq.OnAliPayListener() {
                    @Override
                    public void onPaySuccess(String resultInfo) {
                        App.toast("支付成功");
                        PreferencesProcess.putPreferencesInt(mContext,"paybackflage",1);
                        if(!(!Tools.isEmpty(intent.getExtras().getString("EntryPage"))||intent.hasExtra("orderDetail")||intent.hasExtra("payOrderDataJS"))){
                            Bundle bundle = new Bundle();
                            bundle.putString("orderid", orderId);
                            Routers.open(activity, RoutersAction.ORDER,bundle);
                        }
                        activity.finish();
                    }
                    @Override
                    public void onPayFailure(String resultInfo) {
                        App.toast("支付失败");
                    }
                    @Override
                    public void onPayConfirmimg(String resultInfo) {
                        App.toast("支付失败");
                    }
                    @Override
                    public void onPayCheck(String status) {
                        App.toast("支付失败");
                    }
                });//
        //3.发送支付宝支付请求
        PayAPI.getInstance().sendPayRequest(aliPayReq);
    }

    //微信支付
    /**
     * 微信支付
     *
     * @param orderId
     * @param price
     * @param type
     * @param crowducid
     */
    public void weixinPay(String orderId, Double price, int type, int crowducid, final DataResponse dr) {
//        mView.dialogShow();
        mcontrol.weixinPay(mContext, orderId, price, type,"", crowducid, new DataResponse() {
            @Override
            public void onSucc(Object response) {
                startWeiXinpayClient(response);
//                mView.dialogDismiss();
            }

            @Override
            public void onFail(String error) {
//                mView.dialogDismiss();
                dr.onFail(error);
            }
        });
    }
    /**
     * 微信支付操作
     */
    private void startWeiXinpayClient(Object response) {
        HashMap<String, String> r = (HashMap<String, String>) response;
        String partnerId = r.get("partnerId");
        String prepayId = r.get("prepayId");
        String nonceStr = r.get("nonceStr");
        String timeStamp = r.get("timeStamp");
        String packageValue = r.get("packageValue");
        String sign = r.get("sign");

//        /1.创建微信支付请求
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(activity) //activity实例
                .setAppId("") //微信支付AppID
                .setPartnerId("")//微信支付商户号
                .setPrepayId("")//预支付码
                .setNonceStr("")
                .setTimeStamp("")//时间戳
//                .setAppId(appid) //微信支付AppID
//                .setPartnerId(partnerid)//微信支付商户号
//                .setPrepayId(prepayid)//预支付码
//                .setNonceStr(noncestr)
//                .setTimeStamp(timestamp)//时间戳
                .setSign(sign)//签名
                .create();
//        /2.发送微信支付请求
        PayAPI.getInstance().sendPayRequest(wechatPayReq);
    }

    //微信的授权登录
    public void WXLoade(Context context,String APPID){
        IWXAPI iwxapi = null;
        iwxapi = WXAPIFactory.createWXAPI(context, APPID);
        iwxapi.registerApp(APPID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
        //回调写在WXEntryActivity中
    }
}
