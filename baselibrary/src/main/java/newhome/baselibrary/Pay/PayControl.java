package newhome.baselibrary.Pay;

import android.content.Context;

import java.util.HashMap;

import newhome.baselibrary.MyViewI.DataResponse;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class PayControl {
    public void aliPay(final Context context, String sub_id, Double price, int type, String otherDsc, int crowducid, final DataResponse dr) {
        Object response=new Object();
        HashMap<String, String> data = (HashMap<String, String>) response;
        int anInt = Integer.parseInt(data.get("stats"));
        if (anInt==1){
            dr.onSucc(data);
        }else {
            dr.onFail(data.get("msg"));
        }
    }
    public void weixinPay(final Context context, String sub_id, Double price, int type, String otherDsc, int crowducid, final DataResponse dr) {
        Object response=new Object();
        HashMap<String, String> data = (HashMap<String, String>) response;
        int anInt = Integer.parseInt(data.get("stats"));
        if (anInt==1){
            dr.onSucc(data);
        }else {
            dr.onFail(data.get("msg"));
        }
    }
}
