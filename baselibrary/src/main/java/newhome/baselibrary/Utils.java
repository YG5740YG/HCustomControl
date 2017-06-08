package newhome.baselibrary;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by scorpio on 2016/10/8.
 */

public class Utils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidth(Context context) {

//        android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = context.getResources().getDisplayMetrics().widthPixels;
        return screenW;
    }

    public static String getImei(Context context,int index){
        String imei = "";
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Class clazz = manager.getClass();
        try {
            Method getImei = clazz.getDeclaredMethod("getImei",int.class);
            imei  = (String) getImei.invoke(manager,index);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return imei ;
    }
}
