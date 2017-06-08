package newhome.baselibrary.WebViewUse;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import newhome.baselibrary.Tools.Logs;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class RoutersWebViewClick {
    public boolean startUrl(Activity activity, final Context context, WebView view, final String url, Bundle bundle) {
        bundle.remove("webview_key");
        Logs.Debug("weburl===gg==" + url);
//        if (url.startsWith("tel:")) { // 电话号码
//            final String[] pernition = new String[]{Manifest.permission.CALL_PHONE};
//            CustomDialog.Builder builder = new CustomDialog.Builder(context);
//            builder.setTitle("系统提示");
//            builder.setMessage("您即将要拨打 " + url.substring(4) + ",\r\n" + GetResource.getString2resid(context, com.ch999.baseres.R.string.DialPrompt));
//            // 更新
//            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(final DialogInterface dialog, int which) {
//                    if (!Tools.checkoutPermissions(context,pernition)){
//                        RxPermissions.getInstance(context).request(Manifest.permission.CALL_PHONE)
//                                .subscribe(new Action1<Boolean>() {
//                                    @Override
//                                    public void call(Boolean aBoolean) {
//                                        if (aBoolean){
//                                            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse(url));
//                                            context.startActivity(phoneIntent);
//                                            dialog.dismiss();
//                                        }else{
//                                            UITools.showMsgAndClick_one(context, "小九提示：", "尊敬的用户您好，为了更好的为您服务，您需要开通一些必要权限，谢谢。", "确定", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                    dialogInterface.dismiss();
//                                                }
//                                            });
//                                        }
//                                    }
//                                });
//                    }else {
//                        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse(url));
//                        context.startActivity(phoneIntent);
//                        dialog.dismiss();
//                    }
//
//                }
//            });
//            // 稍后更新
//            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            Dialog noticeDialog = builder.create();
//            noticeDialog.setCancelable(true);
//            noticeDialog.show();
//            return true;
//        }
         if((url.equals("https://m.9ji.com/")||url.equals("https://m.9ji.com"))&&bundle.containsKey("pastingappoint")){
            activity.finish();
            return true;
        }
//        else if(url.equals("https://m.9ji.com/")||url.equals("https://m.9ji.com")){
//            Routers.open(context,RoutersAction.MAIN);
//            activity.finish();
//            Logs.Debug("weburl===gg==12==");
//            return true;
//        }
//        else if (url.contains("https://m.9ji.com/product/")&&url.contains(".html")){
//            Logs.Debug("gg========3="+url);
//            Routers.open(context,url);
//            return true;
//        }
//
//        else if (url.contains("https://m.9ji.com/store/shopdetail.aspx?id")){
//            int storeId= Integer.parseInt(url.split("=")[1]);
//            Bundle bundle1=new Bundle();
//            bundle1.putInt("id",storeId);
//            bundle1.putString("webview_key",RoutersAction.WEBVIEW_CF);
//            Routers.open(context,url,bundle1);
//            return true;
//        }
//
//        else if (url.contains("https://m.9ji.com/store/dianpudetail.aspx?")){//入店指引
//            bundle.putString("webViewTitle","");
//            bundle.putString("webview_key",RoutersAction.WEBVIEW_CF);
//            Routers.open(context,url,bundle);
//            return true;
//        }//        else if (url.contains("https://m.ch999.com/location/") || url.contains("https://m.9ji.com/location/")||url.contains("https://m.9ji.com/store/shopmap.aspx?wxid=")) { // 门店导航
//        else if (url.contains("https://m.9ji.com/store/shopmap.aspx?wxid=")||url.contains("https://m.ch999.com/location/") || url.contains("https://m.9ji.com/location/") && !url.contains("ParkingTrack")) { // 门店导航
////            https://m.9ji.com/location/43.html
//            int storeId=0;
//            int wxid=0;
//            Intent intent = new Intent(context, MapShowActivity.class);
//            if (!Tools.isEmpty(String.valueOf(bundle.getInt("id"))) && bundle.getInt("id") != 0){
//                storeId=bundle.getInt("id");
//                intent.putExtra("id",storeId);
//                context.startActivity(intent);
//                return true;
//            }else {
//                if (url.contains(".html")) {
//                    storeId = Integer.parseInt(url.split("location/")[1].split("\\.")[0]);
//                } else if (url.contains("?wxid=")) {
//                    storeId = 0;
//                    wxid=Integer.parseInt(url.split("wxid=")[1]);
//                }else {
//                    storeId = bundle.getInt("id");
//                }
//                if(wxid!=0) {
//                    intent.putExtra("wxid",wxid);
//                }else {
//                    intent.putExtra("id", storeId);
//                }
//                context.startActivity(intent);
//                return true;
//            }
//        }
//        else if(url.contains("https://m.9ji.com/store/?from=tb1")){
//            Routers.open(context,RoutersAction.STORE);
//            return true;
//        }
//        else if(url.equals("https://m.9ji.com/") || url.equals("http://m.9ji.com/")){
//            bundle.putInt("index",0);
//            Routers.open(context,RoutersAction.MAIN,bundle);
//            activity.finish();
//            return true;
//        } else if (url.equals("https://m.9ji.com/user/index.aspx")){
//            bundle.putInt("index",3);
//            Routers.open(context,RoutersAction.MAIN,bundle);
//            activity.finish();
//            return true;
//        }else if(url.startsWith("https://m.9ji.com/user/orderDetail.aspx?orderid=") || url.startsWith("http://m.9ji.com/user/orderDetail.aspx?orderid=")){ //订单详情
//            // 获取订单ID
//            String subid = url.substring(url.indexOf("orderid=")).replace("orderid=", "");
//            String payType="1";
////            if(url.contains("payType")){
////                payType= url.split("payType=")[1].split("&totalPrice")[0]);
////            }
//            Intent orderDetail = new Intent(context, OrderActivity.class);
//            orderDetail.putExtra("orderid",subid);
//            orderDetail.putExtra("payType",payType);
////            orderDetail.putExtra("lastUrl",bundle.getString("lastUrl"));
////            Logs.Debug("gg=============subId== "+subid+"==lastUrl=="+bundle.getString("lastUrl"));
//            context.startActivity(orderDetail);
////            activity.finish();
//            return true;
//        }else if(url.startsWith("https://m.9ji.com/cart/cartFinish.aspx?orderNum=")){
//            Bundle b = new Bundle();
//            b.putString("orderNo",url.substring(url.indexOf("=")+1));
//            Routers.open(context,url,b);
//            return true;
//        }
//        else if(url.startsWith("https://m.9ji.com/acount/login.aspx")){
//            Routers.open(context,RoutersAction.ACOUNT_LOGIN);
//            return true;
//        }else if (url.startsWith("http://chat.9ji.com/m/")){
////                view.goBack();
//            Routers.open(context,RoutersAction.CHAT);
//            return true;
//        } else if (url.equals("https://m.9ji.com/user/generalize.aspx")){
//            bundle.putString("webViewTitle","九机币 - 九机网");
//            Routers.open(context,url,bundle);
//            return true;
//        } else if (url.equals("https://m.9ji.com/topic/383.html")){
//            bundle.putString("webViewTitle","九机币非钻会员介绍页 - 九机网");
//            Routers.open(context,url,bundle);
//            return true;
//        }
//        else if (url.contains(" https://m.9ji.com/m/user/myportrait.aspx?addHash=1") || url.contains("https://m.9ji.com/user/myportrait.aspx")){
//            bundle.putString("webViewTitle","更换头像");
//            Routers.open(context,RoutersAction.UPIMAGE,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=nickname")){
//            bundle.putString("webViewTitle","修改个人信息");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=user")){
//            bundle.putString("webViewTitle","修改用户名");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=email")){
//            bundle.putString("webViewTitle","修改邮箱");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=mobile")){
//            bundle.putString("webViewTitle","修改手机号码");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=name")){
//            bundle.putString("webViewTitle","修改姓名");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=sex")){
//            bundle.putString("webViewTitle","修改性别");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=birthday")){
//            bundle.putString("webViewTitle","修改生日");
//            Routers.open(context,url,bundle);
//            return true;
//        }else if (url.equals("https://m.9ji.com/user/editinfo.aspx?m=password")){
////            ValueCallback<Boolean> valueCallback = null;
////            valueCallback.onReceiveValue(true);
////            UITools.setCookie(context, UITools.getCookieUrl(), "cityid=" + BaseInfo.getInstance(context).getInfo().getCityId());
////            bundle.putString("webViewTitle","修改密码");
////            PreferencesProcess.putPreferencesString(context,"pwd", "upDataPwd");
////            Routers.open(context,url,bundle);
//            Routers.open(context,RoutersAction.PWD);
//            return true;
//        }
//        else if (url.contains("https://m.9ji.com/user/myaddresslist.aspx")&&bundle.containsKey("shouldjump")){
//            bundle.putBoolean("need_request",true);
//            Routers.open(context, RoutersAction.RECEIVEADDRESS,bundle);
//            return true;
//        }else if (url.contains("https://m.9ji.com/order/cart.aspx?fm=app")){
//            Routers.open(context, RoutersAction.CART);
//            return true;
//        }else if (url.contains("https://m.9ji.com/order/cart.aspx?fm=app")){
//            Routers.open(context, RoutersAction.CART);
//            return true;
//        }else if(url.contains("https://m.9ji.com/order/pay.aspx")||url.contains("https://m.9ji.com/order/pay.aspx")){
//            if(url.contains("orderNum")) {
//                bundle.putString("orderNo", url.split("orderNum=")[1]);
//            }else if(url.contains("orderid")) {
//                bundle.putString("orderNo", url.split("orderid=")[1]);
//            }else if (url.contains("sub_id")) {
//                bundle.putString("orderNo", url.split("sub_id=")[1]);
//            }
//            Routers.open(context, RoutersAction.PAYMENT, bundle);
//            return true;
//        }else if(url.contains("/Cart")){
//            Routers.open(context, RoutersAction.CART, bundle);
//            return true;
//        }else if(url.contains("https://m.9ji.com/user/guestComments.aspx?")){
//            Routers.open(context, url,bundle);
//            return true;
//        }else if(url.contains("https://m.9ji.com/operator/Recharge.aspx")){
//            Routers.open(context, url,bundle);
//            return true;
//        }else if(url.contains("https://m.9ji.com/hottopic/?from=tb3")){
//            Routers.open(context,url,bundle);
//            return true;
//        }else if(url.startsWith("https://m.9ji.com/news/")&&url.contains(".html")){
//            if(url.contains("productid=")){
//                MsgToNewsFragment msgToNewsFragment = new MsgToNewsFragment(Integer.parseInt(url.split("\\?productid")[0].split("news/")[1].replace(".html", "")), context);
//            }else {
//                MsgToNewsFragment msgToNewsFragment = new MsgToNewsFragment(Integer.parseInt(url.split("news/")[1].replace(".html", "")), context);
//            }
//            return true;
//        }else if(url.equals("https://m.9ji.com/news/")||url.equals("https://m.9ji.com/news")){
//            Routers.open(context, RoutersAction.NEWS);
//            return true;
//        }else if(url.startsWith("https://m.9ji.com/news/")&&url.contains("#")){
//            Routers.open(context, url);
//            activity.finish();
//            return true;
//        }else if(url.equals("https://m.9ji.com/news/")&&url.equals("#")){
//            if(url.contains("-")){
//                String item=url.split("#")[1];
//                String[] news_cid_id = item.split("-");
//                String news_cid = news_cid_id[0];
//                String news_id = news_cid_id[1];
//                bundle.putString("cid", news_cid);
//                bundle.putString("id", news_id);
//            }else{
//                String item=url.split("#")[1];
//                bundle.putString("cid", item);
//            }
//            Routers.open(context, RoutersAction.NEWS,bundle);
//            return true;
//        }
        else{
            return false;
        }
    }
}
