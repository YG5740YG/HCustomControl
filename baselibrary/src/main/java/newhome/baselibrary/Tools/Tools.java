package newhome.baselibrary.Tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
//import android.support.v4.app.ActivityCompat;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

//import com.scorpio.mylib.imageManage.MD5;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyView.MyDialog;
import newhome.baselibrary.Activity.MyScrollLayout.MainActivity;
import newhome.baselibrary.R;
import rx.functions.Action1;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by scorpio on 2016/11/3.
 */

public class Tools {

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void getVersionInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            Logs.Debug("ggg================" + pkName + " ==  " + versionName + " == " + versionCode);
        } catch (Exception e) {
        }
    }

    public static String currentVersion(Context context, String appPackage) {
        String ver = null;
        try {
            appPackage = context.getPackageName();
            ver = context.getPackageManager().getPackageInfo(appPackage, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ver;
    }

    /**
     * 获取软件版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取手机的IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String deviceId = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
        return deviceId;
    }

    /**
     * 判断null和""的情况
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            if (str.length() > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 整形转字符串
     */
    public static String int2string(int s) {
        try {
            return Integer.toString(s);
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }

    /**
     * 字符串转整形
     */
    public static int string2int(String s, int defvalue) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            // TODO: handle exception
            return defvalue;
        }
    }

    /**
     * 双精度转字符串
     */
    public static String double2string(double s) {
        try {
            return Double.toString(s);
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }

    /**
     * 字符串 转双精度
     */
    public static double string2double(String s, double defvalue) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            // TODO: handle exception
            return defvalue;
        }
    }

    /**
     * object转整形
     */
    public static int obj2int(Object s, int defvalue) {
        try {
            return Integer.parseInt(s.toString());
        } catch (Exception e) {
            // TODO: handle exception
            return defvalue;
        }
    }

    /**
     * object转双精度
     */
    public static double obj2double(Object s, double defvalue) {
        try {
            return Double.parseDouble(s.toString());
        } catch (Exception e) {
            // TODO: handle exception
            return defvalue;
        }
    }

    /**
     * object转string
     */
    public static String obj2string(Object s, String defvalue) {
        try {
            return s.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return defvalue;
        }
    }

    /**
     * 数字保留小数点后两位
     */
    public static String valueFormat(Object price) {
        try {
            double dou = string2double(price.toString(), 0);
            if (dou == 0) {
                return "0.00";
            }
            DecimalFormat df2 = new DecimalFormat("##0.00");// 保留2位
            return df2.format(dou);
        } catch (Exception e) {
            // TODO: handle exception
            return "0.00";
        }

    }

    /**
     * 数字保留小数点后两位
     *
     * @param rule
     */
    public static String valueFormat(Object price, String rule) {
        String format = "##0.00";
        if (!Tools.isEmpty(rule)) {
            format = rule;
        }
        try {
            double dou = string2double(price.toString(), 0);
            if (dou == 0) {
                return "0.00";
            }
            DecimalFormat df2 = new DecimalFormat(format);// 保留2位
            return df2.format(dou);
        } catch (Exception e) {
            // TODO: handle exception
            return "0.00";
        }

    }

    /**
     * 不保留小数点
     */
    public static String noPointFormat(Object price) {
        try {
            double dou = string2double(price.toString(), 0);
            if (dou == 0) {
                return "0";
            }
            DecimalFormat df2 = new DecimalFormat("##0");// 保留2位
            return df2.format(dou);
        } catch (Exception e) {
            // TODO: handle exception
            return "0";
        }

    }

    /**
     * 把字符串转为字符数组/
     *
     * @param str
     * @return
     */

    public static String[] StrToArray(String str) {
        String[] str_array = new String[str.length()];
        for (int cnt = 0; cnt < str.length(); cnt++) {
            if (cnt < str.length() - 1) {
                str_array[cnt] = str.substring(cnt, cnt + 1);
            } else {
                str_array[cnt] = str.substring(cnt);
            }

        }
        return str_array;
    }

    /**
     * Base64加密
     *
     * @param xml
     * @return
     */
    @SuppressLint("NewApi")
    public static String encodeBase64(String xml) {
        return Base64.encodeToString(xml.getBytes(), Base64.DEFAULT);
    }

    /**
     * Base64解密
     *
     * @param rexml
     * @return
     */
    @SuppressLint("NewApi")
    public static String decodeBase64(String rexml) {
        byte b[] = Base64.decode(rexml, Base64.DEFAULT);
        String reString = new String(b);
        return reString;
    }

    /**
     * 明文转url编码
     */
    public static String encode(String strRes) {
        return URLEncoder.encode(strRes);
    }

    /**
     * 将url编码转明文
     */
    public static String decode(String strRes) {
        return URLDecoder.decode(strRes);
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern
                .compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为手机号
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证是否为固定电话
     */
    public static boolean isPhone(String str) {
        Pattern pattern = Pattern.compile("^(\\d{4}|\\d{3}|\\d{2}|\\d{0})-?\\d{7,8}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证是否为邮编
     */
    public static boolean isPostCode(String str) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{5}$");
        return pattern.matcher(str).matches();
    }


    /**
     * 设置cookie
     *
     * @param context
     * @param url
     * @param t
     */
    public static void setCookie(Context context, String url, String t) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, t);
        CookieSyncManager.getInstance().sync();

    }

    /**
     * 判断是否是中国移动
     * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成，
     * 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。
     *
     * @param context
     * @return
     */
    public static boolean isChianMM(Context context) {
        boolean isChina = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                isChina = true;
            }
        } else {
            isChina = false;
        }
        return isChina;
    }

    //判断是否为飞行模式
    public boolean AirplaneMode(Context context) {
        boolean isEnabled = Settings.System.getInt(
                context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        return isEnabled;
    }

    /**
     * 百度地图经纬度转换
     *
     * @param lat
     * @param lng
     */
    public static String Convert_BD09_To_GCJ02(double lat, double lng) {
        double x_pi = Math.PI * 3000.0 / 180.0;
        double x = lng - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        lng = z * Math.cos(theta);
        lat = z * Math.sin(theta);
        return lat + "," + lng;
    }

    /**
     * 检测网络是否有效
     *
     * @param context
     * @return ture 有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络状态
     *
     * @return 1:无线网  2：手机网络   3：无网络连接
     */
    public static int getNetState(Context context) {
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            // 手机网络连接成功
            return 2;
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            // 手机没有任何的网络
            return 3;
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            // 无线网络连接成功
            return 1;
        }
        return 3;
    }

    //判断GPS是否打开
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }


    //三、判断WIFI是否打开
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
                || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    //四、判断是否是3G网络
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    //五、判断是wifi还是3g网络,用户的体现性在这里了，wifi就可以建议下载或者在线播放。
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 匹配用户名：只能为数字、字母或者汉字,4-20
     */
    public static boolean CheckUsername(String str) {
//			^[0-9a-zA-Z\u4e00-\u9fa5]{4,20}$
//			^[a-zA-Z|\\d|\\u0391-\\uFFE5]*$
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z\u4e00-\u9fa5]{4,20}$");
        return pattern.matcher(str).matches();
    }


    /**
     * 匹用户名长度
     */
    public static boolean CheckUsernameLength(String str) {

        if (str.length() < 4 || str.length() > 20) {

            return false;
        } else {
            return true;
        }
    }

    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long date = new Date().getTime();
        return sdf.format(date);
    }

    public static String getNowtime() {
        return new Date().getTime() + "";
    }

    /**
     * 获取时间毫秒
     *
     * @param format 时间格式如“yyyy-MM-dd HH:mm:ss”
     * @param data   相应的时间  2017-4-2 0:00:00
     * @return
     */
    public long gettiemMS(String format, String data) {
        long ms = 0;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(data);
            ms = date.getTime();
        } catch (Exception d) {
            ms = 0;
        }
        return ms;
    }

    //获取当前时间ms
    public long time() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //PROCESSING
        long diff = curDate.getTime();
        return diff;
    }

    /**
     * 获取当前日期
     *
     * @param isTime true：返回yyyy-MM-dd HH:mm:ss格式，      false：返回yyyy-MM-dd格式
     */
    public static String getDate(boolean isTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 获取当前日期  毫秒
     *
     * @param isTime true：返回yyyy-MM-dd HH:mm:ss格式，      false：返回yyyy-MM-dd格式
     */
    public static Long getDateMS(boolean isTime) {
        long startTime = (long) (Calendar.getInstance().getTimeInMillis());
        return startTime;// new Date()为获取当前系统时间
    }

    /**
     * 获取当前日期
     */
    public static String GetDate() {
        Date dt = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
        String s = matter1.format(dt);
        return s;
    }

    /**
     * 获取时间 年月 周
     *
     * @return
     */
    public static String getWeekStr(int day) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK) + day);
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "周" + mWay;
    }

    /**
     * 把分钟换算为天,小时,分钟的方法
     * 4天23小时42分钟以前
     *
     * @param time
     * @return
     */
    public static String getDetailTime(int time) {
        int day = time / 1440;
        int hour = (time % 1440) / 60;
        int min = (time % 1440) % 60;
        String detailTime;
        if (day == 0 && hour == 0 && min == 0) {
            detailTime = "刚刚";
            return detailTime;
        }
        if (day == 0 && hour == 0) {
            detailTime = min + "分钟前";
            return detailTime;
        } else if (day == 0) {
            detailTime = hour + "小时前";
            return detailTime;
        } else if (day == 1) {
            detailTime = "昨天" + hour + ":" + min;
            return detailTime;
        } else if (day == 2) {
            detailTime = "前天" + hour + "小时" + min + "分钟前";
            return detailTime;
        } else if (day > 2) {
            detailTime = day + " " + hour + "-" + min;
            return detailTime;
        }
        return null;
    }

    /**
     * 计算两个时间相差的分钟数
     * time1-需要计算的时间，time2-当前时间
     *
     * @return
     */
    public static String getTimeMinuteDiffer(String time1, String time2) {
        int time = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//时间形式

        try {
            long date = (format.parse(time1)).getTime();
            long now = (format.parse(time2)).getTime();

            time = (int) (now - date) / (1000 * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = time / 1440;
        int hour = (time % 1440) / 60;
        int min = (time % 1440) % 60;
        String detailTime;
        String[] sellerTime = time1.split(" ");
        String passTime = "未知时间";
        if (sellerTime.length >= 2) {
            passTime = sellerTime[1];
        }
        if (day == 0 && hour == 0 && min == 0) {
            detailTime = "刚刚";
            return detailTime;
        }
        if (day == 0 && hour == 0) {
            detailTime = min + "分钟前";
            return detailTime;
        }
        if (day == 0) {
            detailTime = hour + "小时前";
            return detailTime;
        }
        if (day == 1) {
            detailTime = "昨天 " + passTime.substring(0, 5);
            return detailTime;
        }
        if (day == 2) {
            detailTime = "前天 " + passTime.substring(0, 5);
            return detailTime;
        }
        if (day > 2) {
            detailTime = time1.substring(0, 16);
            return detailTime;
        }
        return null;
    }

    /**
     * 根据参数计算毫秒数
     *
     * @param d 天数
     * @param h 小时
     * @param m 分钟
     * @param s 秒
     */
    public static long getMillis(int d, int h, int m, int s) {
        return d * 86400000l + h * 3600000l + m * 60000l + s * 1000l;
    }

    /**
     * 获取距离显示规范
     *
     * @return
     */
    public static String getDistanceNorm(double distance) {
        String disStr = "";
        distance = distance / 10;
        if (distance > 1) {
            if (distance > 500) {
                disStr = ">500KM";
            } else {
                disStr = valueFormat(distance, "##0.0") + "KM";
            }
        } else {
            if (distance * 10 < 1 || distance == 0) {
                //小于100M
                disStr = "<100M";
            } else {
                disStr = (int) (distance * 1000) + "M";
            }
        }
        return disStr;
    }

    /**
     * 发送短信
     *
     * @param context
     * @param to
     * @param content
     */
    public static void sendSms(Context context, String to, String content) {
        Uri uri = Uri.parse("smsto:" + to);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", content);
        context.startActivity(it);
    }

    /**
     * 打电话
     *
     * @param context
     * @param phone
    //     */
//    public static void tel(Context context, String phone) {
//        Uri uri = Uri.parse("tel:" + phone);
//        Intent it = new Intent(Intent.ACTION_CALL, uri);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
////        context.startActivity(it);
//    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    // 获取ActionBar的高度
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))// 如果资源是存在的、有效的
        {
            actionBarHeight = context.getResources().getDisplayMetrics().heightPixels;
        }
        return actionBarHeight;
    }

    // 获取ActionBar的宽度
    public static int getActionBarWidth(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarWidth = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))// 如果资源是存在的、有效的
        {
            actionBarWidth = context.getResources().getDisplayMetrics().widthPixels;
        }
        return actionBarWidth;
    }

    /**
     * 根据包名 和类名 打开应用程序
     *
     * @param pkg
     * @param cls
     */
    public static void startapp(Context context, String pkg, String cls) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName componentName = new ComponentName(pkg, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            try {
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }


//    /**
//     * 权限 6.0 检测权限
//     * @param context
//     * @param permissions
//     * @return
//     */
//    public static boolean checkoutPermissions(final Context context, String[] permissions) {
//        // 进行权限请求
//        boolean result = true;
//        int count = 0;
//        for (int i = 0; i < permissions.length; i++) {
//            final String permission = permissions[i];
//            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
//                count = count + 1;
//            }
//        }
//        if (count == permissions.length) {
//            result = true;
//        } else {
//            result = false;
//        }
//        return result;
//    }


    /**
     * 重写editView
     */
    public static void rewriteEditView(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 20) {
                    editText.setText(charSequence.subSequence(0, charSequence.length() - 1));
                    editText.setSelection(charSequence.length() - 1);
                    editText.setError("最多可输入20个字符");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 测量控件的尺寸
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }

//    /**
//     * 将图片路径和图片尺寸进行md5加密并作为图片名称
//     * */
//    public static String imgCacheName(String imgUrl, int imgWidth, int imgHeight) {
//        String url = MD5.getMD5(imgUrl + imgWidth + "_" + imgHeight)
//                + imgUrl.substring(imgUrl.lastIndexOf(".")); // 将图片路径和图片尺寸进行md5加密并作为图片名称
//        return url;
//    }


    public static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 设置window的透明度
     *
     * @param activity
     * @param alpha
     */
    public static void setWindowhalph(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    private class DataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Logs.Debug("gg=========");
            }
        }
    }

    //延迟执行方法
    private void DelayRun() {
        final DataHandler dataHandler = new DataHandler();
        final Message message = new Message();
        message.what = 0;
        dataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataHandler.sendMessage(message);
            }
        }, 700);//告诉主线程执行任
    }

    public int getX(MotionEvent event) { // 得到的相对于View初始xy轴位置的距离
        return (int) event.getX();
    }

    public int getY(MotionEvent event) { // 得到的相对于View初始xy轴位置的距离
        return (int) event.getY();
    }

    public void myTextView(Context mcontext) {
        ColorStateList redColors = ColorStateList.valueOf(0xffff0000);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("新闻" + " " + "things");
        //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
        //size  为0 即采用原始的正常的 size大小
        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, UITools.dip2px(mcontext, 14), redColors, null), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置特殊字符颜色
        TextView tv = new TextView(mcontext);
        tv.setText(spanBuilder);
        tv.setMaxLines(1);//一行
        tv.setEllipsize(TextUtils.TruncateAt.END);//字数多了显示省略号
        tv.setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * view     简单动画，透明度，大小变化，移动，旋转
     *
     * @param view
     * @param Duration 动画的时间
     */
    //view透明度由alphaValueStard变化为alphaValueEnd  @param alphaValueStard  alphaValueEnd   变化前后的透明值（百分比）
    public static void animationUserAlpha(View view, int Duration, float alphaValueStard, float alphaValueEnd) {
        AnimationSet animationSet = new AnimationSet(true);
        //透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(alphaValueStard, alphaValueEnd);
        alphaAnimation.setDuration(Duration);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }

    //大小改变
    //* @param zoomXStart         变化前宽度（百分比：改变前一般为1f）    * @param zoomYStart         变化前高度（百分比：改变前一般为1f）
    //* @param zoomXStart         变化后宽度（百分比）    * @param zoomYStart         变化后高度（百分比）
    //
    public static void animationUserScale(View view, int Duration, float zoomXStart, float zoomYStart, float zoomXEnd, float zoomYEnd) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(zoomXStart, zoomXEnd, zoomYStart, zoomYEnd, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(Duration);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
        //参数0.5，说明以view的中心轴为变化轴
    }

    //移动
    //  * @param displacementXEnd 变化后相对于宽度，偏移X（百分比）   * @param displacementYEnd 变化后相对于高度，偏移Y（百分比）
    //  * @param displacementXStart 变化前相对于宽度，偏移X（百分比：开始变化，一般设为0f）
    //   * @param displacementYStart 变化后相对于高度，偏移Y（百分比:开始变化一般设为0f）
    public static void animationUserTranslate(View view, int Duration, float displacementXStart, float displacementXEnd, float displacementYStart, float displacementYEnd) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, displacementXStart,
                Animation.RELATIVE_TO_SELF, displacementXEnd, Animation.RELATIVE_TO_SELF, displacementYStart,
                Animation.RELATIVE_TO_SELF, displacementYEnd);
        translateAnimation.setDuration(Duration);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }

    //rotatstartAngle  轴心一般为0   rotatAngle  旋转角度，360，顺时针旋转360度，-360 ，逆时针旋转360度
    public static void animationUserRotate(View view, int Duration, float rotatstartAngle, float rotatAngle) {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(rotatstartAngle, rotatAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(Duration);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }

    /**
     * 简单动画，透明度，大小变化，移动，旋转综合
     *
     * @param view
     * @param zoom          true 缩小  透明    false 扩大，不透明
     * @param Duration      动画开始到结束的时间
     * @param alphaValue    变化后的透明值（百分比）
     * @param zoomX         变化后宽度（百分比）
     * @param zoomY         变化后高度（百分比）
     * @param displacementX 变化后相对于宽度，偏移X（百分比）
     * @param displacementY 变化后相对于高度，偏移Y（百分比）
     */
    public static void animationUser(View view, boolean zoom, int Duration, float alphaValue, float zoomX, float zoomY, float displacementX, float displacementY, Boolean rotat, float rotatstartAngle, float rotatAngle) {
        AnimationSet animationSet = new AnimationSet(true);
        if (zoom) {
            //透明
            AlphaAnimation alphaAnimation = new AlphaAnimation(1f, alphaValue);
            alphaAnimation.setDuration(Duration);
            animationSet.addAnimation(alphaAnimation);
            //宽高
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, zoomX, 1f, zoomY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(Duration);
            animationSet.addAnimation(scaleAnimation);
            //位移
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, displacementX, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, displacementY);
            translateAnimation.setDuration(Duration);
            animationSet.addAnimation(translateAnimation);
            //旋转
            if (rotat) {
                RotateAnimation rotateAnimation = new RotateAnimation(rotatstartAngle, rotatAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(Duration);
                animationSet.addAnimation(rotateAnimation);
            }
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(alphaValue, 1f);
            alphaAnimation.setDuration(Duration);
            animationSet.addAnimation(alphaAnimation);
            ScaleAnimation scaleAnimation = new ScaleAnimation(zoomX, 1f, zoomY, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(Duration);
            animationSet.addAnimation(scaleAnimation);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, displacementX, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, displacementY, Animation.RELATIVE_TO_SELF, 0f);
            translateAnimation.setDuration(Duration);
            animationSet.addAnimation(translateAnimation);
            //旋转
            if (rotat) {
                RotateAnimation rotateAnimation = new RotateAnimation(rotatstartAngle, rotatAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(Duration);
                animationSet.addAnimation(rotateAnimation);
            }
        }
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }

    //监听按下的键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            dialog();
        }
        return false;
    }

    //    //获取收集里所有图片
//    public ArrayList<HashMap<String, Object>> ScanPhoto() {
//        //生成动态数组
//        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
//        //查询媒体数据库
//        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
//    }
//判断是否存在SD卡
    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    //查看SD卡剩余空间
    public long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    //查看SD卡总容量
    public long getSDAllSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    //判断不可以卸载程序
    public List<ApplicationInfo> Nounload(Activity activity) {
        PackageManager mPm = activity.getPackageManager();
        List<ApplicationInfo> installedAppList = mPm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        List<ApplicationInfo> appList = new ArrayList<>();
        for (ApplicationInfo appInfo : installedAppList) {
            boolean flag = false;
            if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                flag = true;
            } else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                flag = true;
            }
            if (flag) {
                appList.add(appInfo);
            }
        }
        return appList;
    }

    //读文件
    public String ReadSettings(Context context) {
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        char[] inputBuffer = new char[255];
        String data = null;
        try {
//            fIn = openFileInput("settings.dat");
            isr = new InputStreamReader(fIn);
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            Toast.makeText(context, "Settingsread", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Settings notread", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                isr.close();
                fIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    //写文件
    public void WriteSettings(Context context, String data) {
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        try {
//            fOut = openFileOutput("settings.dat",MODE_PRIVATE);

            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            Toast.makeText(context, "Settingssaved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Settingsnotsaved", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //打开此Activity从栈中放到栈顶层而不是从新打开Activity
    public void intent(Context context) {
//　　intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    }

    //获取手机壁纸
    public Drawable get(Context context) {
        WallpaperManager wm = WallpaperManager.getInstance(context);
        Drawable wallpaper = wm.getDrawable();
        return wallpaper;
    }

    //SpanableString的使用，在类NewFiveViewTestOne中进行了使用
    public SpannableString SpannableStringUse(String valuem, Context context) {
        SpannableString ss = new SpannableString("你好啊，今天是星期五了！我已经做好的放假的准备！");
//        第一个参数为需要设定的样式，有很多个类可以选择
//        第二参数是开始的位置，0表示第一个字符
//        第三个参数是结束的位置
//        第四个参数是一个表示替换的位置是否会影响开头和结尾，可以为null
        //前景色
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //背景色
        ss.setSpan(new BackgroundColorSpan(Color.RED), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //下划线
        ss.setSpan(new UnderlineSpan(), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //放置图片
        ss.setSpan(new ImageSpan(context, R.mipmap.e5), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //可点击触发事件
        ss.setSpan(new TextClickSpan(context), 9, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //触发事件的话，要加上下面这句话
        //tvTest.setMovementMethod(LinkMovementMethod.getInstance());
        return ss;
    }

    class TextClickSpan extends ClickableSpan {
        Context context;

        public TextClickSpan(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View widget) {
            Logs.Debug("gg=================click");
            Toast.makeText(context, "aaaa", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 权限 6.0 检测权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkoutPermissions(final Context context, String[] permissions) {
        // 进行权限请求
        boolean result = true;
        int count = 0;
        for (int i = 0; i < permissions.length; i++) {
            final String permission = permissions[i];
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                count = count + 1;
            }
        }
        if (count == permissions.length) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public void reTextView(final TextView textView, final TextView txt_number) {
        textView.setVerticalScrollBarEnabled(true);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txt_number.setText("您还可以输入" + (200 - charSequence.length()) + "个字符");
                if (charSequence.length() > 200) {
                    textView.setText(charSequence.subSequence(0, charSequence.length() - 1));
                    textView.setMovementMethod(ScrollingMovementMethod.getInstance());
//                    product_describle.setSelection(charSequence.length() - 1);
                    textView.setError("您好，您最多可输入200个字符。");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * 删除文件夹下的文件
     *para File f=new File("/storage/emulated/0/ch999");
     */
    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    public void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    //地理位置权限判断
    public void checkoutPermissionsJduge(final Context context, String[] mPermissions) {
        String[] mPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if (!Tools.checkoutPermissions(context, mPermissions)) {
            RxPermissions.getInstance(context).request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                            } else {
                                MyDialog myDialog = new MyDialog(context, "小九提示：", "尊敬的用户您好，为了更好的为您服务，您需要开通一些必要权限，谢谢。", "确认",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = null;
                                                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                                                if (Build.VERSION.SDK_INT > 10) {
                                                    intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                                } else {
                                                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                                }
                                                context.startActivity(intent);
                                                dialogInterface.dismiss();
                                            }
                                        }, R.mipmap.edit_icon);
                            }
                        }
                    });
        }
    }

    /**
     * 商品购买成功后半小时后不显示评价
     */
    public void pingJiaShow() {
//        if (!Tools.isEmpty(mOrderData.getSub().getTradeDate())) {
//            long nowtime = time();
//            long gettime = getMs(mOrderData.getSub().getTradeDate().replace("T", " "));
//            boolean pingjiashow = (nowtime - gettime) >= (30 * 1000 * 60) ? false : true;
//            long chatime = (nowtime - gettime) / 30 / 1000 / 60;
//            if(pingjiashow){
//                pingjia.setVisibility(View.VISIBLE);
//            }else{
//                pingjia.setVisibility(View.GONE);
//            }
//        }
    }

    public void myProgressDialog(Context context) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("请稍等...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        progress.dismiss();
    }

    /**
     * 发送短信
     *
     * @param smsBody
     */
    private void sendSMS(String smsBody, Context context) {
//Uri smsToUri = Uri.parse("smsto:10000"); //如果想指定发送人
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    /**
     * 跳转市场搜索某款软件
     */
    private void searchSoft(Context context) {
        Intent intent = new Intent(
                "android.intent.action.VIEW");
        intent.setData(Uri
                .parse("market://details?id=com.adobe.flashplayer"));
        context.startActivity(intent);
    }

    //检测系统中是否已经安装了adobe flash player插件，插件的packageName是com.adobe.flashplayer：
    private boolean check(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> infoList = pm
                .getInstalledPackages(PackageManager.GET_SERVICES);
        for (PackageInfo info : infoList) {
            if ("com.adobe.flashplayer".equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }

    private void isAvilible(String packageName, Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null) {
//1、通过包名
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            }
            context.startActivity(intent);
//2、通过类名：
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName(packageName, "com.joe.internet.Main"));
            context.startActivity(intent2);
        }
    }

    //MD5加密
    public String Md5(String plainText) {
        String result = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(plainText.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        result = buf.toString().toUpperCase();// 32位的加密（转成大写）
        buf.toString().substring(8, 24);// 16位的加密
        return result;
    }

    //    设置自动跳转页面,三秒后自动跳转
    Timer timer = new Timer();

    private void timerOne(final Context context) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent goIntent = new Intent();
                goIntent.setClass(context, MainActivity.class);
                context.startActivity(goIntent);
            }
        }, 3 * 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(new Intent(context,
                        MainActivity.class));
            }
        }, 1000);
    }

    //    取随机数
    private void getRandomData() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int v : list) {
            Log.d("wxl", "V===" + v);
        }
//方法二
//        Random random = new Random();
//        int ran = random.nextInt(keywordsList.size());
//        String tmp = keywordsList.get(ran).get("keyword").toString();
    }

    //    关闭键盘
    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                ((InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(activity.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
    }

    //    android:windowSoftInputMode="adjustResize"
//    android:windowSoftInputMode="adjustPan|stateAlwaysVisible"
//windowSoftInputMode各值的含义：
//    stateUnspecified：软键盘的状态并没有指定，系统将选择一个合适的状态或依赖于主题的设置
//    stateUnchanged：当这个activity出现时，软键盘将一直保持在上一个activity里的状态，无论是隐藏还是显示
//    stateHidden：用户选择activity时，软键盘总是被隐藏
//    stateAlwaysHidden：当该Activity主窗口获取焦点时，软键盘也总是被隐藏的
//    stateVisible：软键盘通常是可见的
//    stateAlwaysVisible：用户选择activity时，软键盘总是显示的状态
//    adjustUnspecified：默认设置，通常由系统自行决定是隐藏还是显示
//    adjustResize：该Activity总是调整屏幕的大小以便留出软键盘的空间
//    adjustPan：当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖和用户能总是看到输入内容的部分
//获取版本名称 VersionName
    public String getVersionNameNew(Context context) {
        PackageManager manager = context.getPackageManager();
        String packageName = context.getPackageName();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
        return info.versionName;
    }
    //    获取Android手机设备的IMSI / IMEI 信息
    public static String getIMSI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        String imei = mTelephonyMgr.getDeviceId();
        Log.i("wxl", "imsi="+imsi);
        Log.i("wxl", "imei="+imei);
        return imei;
    }
    public static int getScreenWith(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }
    public static int getScreenHeight(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.heightPixels;
        return screenWidth;
    }
}

