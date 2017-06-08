package newhome.baselibrary.Save;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class PreferencesProcess {
    /**
     * 返回是否登录的状态
     *
     * @param context
     * @return
     */
    //自动登录
    public static boolean putPreaotoLogin(Context context) {
        return getPreferencesBoolean(context, "autologin", false);
    }

    public static void prePutaotoLogin(Context context, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", Context.MODE_WORLD_READABLE);
        preferences.edit().putBoolean("autologin", value).commit();
    }

    /**
     * 保存用户登录状态
     */
    public static void prePutUserdata(Context context, Boolean value) {
        putPreferencesBoolean(context, "userdata", value);
    }

    /**
     * 保存临时用户名，做登录时的预输入使用
     *
     * @param context
     * @param value
     */
    public static void prePutTempUsername(Context context, String value) {
        putPreferencesString(context, "tempusername", value);
    }

    /**
     * 返回临时用户名，做登录时的预输入使用
     *
     * @param context
     * @return
     */
    public static String preGetTempUsername(Context context) {
        return getPreferencesString(context, "tempusername", null);
    }

    /**
     * 登录类型 0：会员登录   1：第三方登录
     */
    public static void prePutLoginType(Context context, int value) {
        putPreferencesInt(context, "logintype", value);
    }

    /**
     * 返回登录类型 0：会员登录   1：第三方登录
     */
    public static int preGetLoginType(Context context) {
        int logintype = getPreferencesInt(context, "logintype", 0);
        return logintype;
    }


    /**
     * 返回匿名用户id
     */
    public static String preGetUUID(Context context) {
        String strUUID = getPreferencesString(context, "uuid", null);
        if (strUUID == null) {
            java.util.UUID newUUID = java.util.UUID.randomUUID();
            String newStrUUID = newUUID.toString().toUpperCase();
            putPreferencesString(context, "uuid", newStrUUID);
            return newStrUUID;
        }
        return strUUID;
    }

    /**
     * 返回用户id
     */
    public static String preGetUserid(Context context) {
        //return CommonFun.CheckIsLogin(context).getCh999MemberID();
        return getPreferencesString(context, "userid", "");  //"779164"
    }

    /**
     * 保存当前用户id
     */
    public static void prePutUserid(Context context, String value) {
        putPreferencesString(context, "userid", value);
    }

    /**
     * 返回用户名
     */
    public static String preGetUserName(Context context) {
        return getPreferencesString(context, "userName", "");
    }

    /**
     * 保存当前用户名
     */
    public static void prePutUserName(Context context, String value) {
        putPreferencesString(context, "userName", value);
    }


    /**
     * 保存当前城市id
     *
     * @param context
     * @param value
     */
    public static void prePutCityId(Context context, int value) {
        putPreferencesInt(context, "cityid", value);
    }
    /**
     * 保存是否第一次安装标识
     *
     * @param context
     * @param value
     */
    public static void PutVersionFlage(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ch999_pre",
                MODE_PRIVATE).edit();
        editor.putInt("VersionFlage", value);
        editor.commit();
    }
    public static int getVersionFlage(Context context, String name, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);
        return preferences.getInt(name, defaultValue);
    }
    /**
     * 返回最近一次选中的城市id
     *
     * @param context
     * @return
     */
    public static int preGetCityId(Context context) {
        int cityid = getPreferencesInt(context, "cityid", -1);
        return cityid;
    }
    /**
     * 返回最近一次选中的县id
     *
     * @param context
     * @return
     */
    public static int preGetCountyid(Context context) {
        int countyid = getPreferencesInt(context, "countyid", 39);
        return countyid;
    }

    /**
     * 保存当前县id
     *
     * @param context
     * @param value
     */
    public static void prePutCountyId(Context context, int value) {
        putPreferencesInt(context, "countyid", value);
    }

    public static Boolean getPreferencesBoolean(Context context, String name, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);
        return preferences.getBoolean(name, defaultValue);

    }

    public static boolean putPreferencesBoolean(Context context, String name, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);

        return preferences.edit().putBoolean(name, defaultValue).commit();
    }

    public static void putPreferencesInt(Context context, String name, int value) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        //提交更改
        editor.commit();
    }

    public static int getPreferencesInt(Context context, String name, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);

        return preferences.getInt(name, defaultValue);
    }

    public static void putPreferencesString(Context context, String name, String value) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        //提交更改
        editor.commit();
    }

    public static String getPreferencesString(Context context, String name, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);

        return preferences.getString(name, defaultValue);
    }

    public static String Pregetuserface(Context context) {
        return getPreferencesString(context, "face", "Error");

    }

    /**
     * 保存signTicket
     */
    public static void prePutsignTicket(Context context, String value) {
        putPreferencesString(context, "signTicket", value);
    }
/*
    */

    /**
     * 保存signTicket
     */
    public static void prePutUrl(Context context, String value) {
        putPreferencesString(context, "Url", value);
    }

    /**
     * 返回signTicket
     */
    public static String preGetUrl(Context context) {

        return getPreferencesString(context, "Url", "11");
    }

    /**
     * 返回用户移动电话g
     */
    public static String preGetUsermobile(Context context) {
        return getPreferencesString(context, "usermobile", "00");
    }

    /**
     * 获取消息推送设置状态
     */
    public static boolean preGetSettingSendnews(Context context) {
        return getPreferencesBoolean(context, "settingsendnews", true);
    }

    /**
     * 保存设置中心的消息推送
     * true为接受消息推送
     * false为不解收
     */
    public static void prePutSettingSendnews(Context context, Boolean value) {
        putPreferencesBoolean(context, "settingsendnews", value);
    }


    /**
     * 获取保存的联系人数据
     */
    public static String preGetSettingContacts(Context context, String key) {
        return getPreferencesString(context, "localContacts_" + key, "");
    }

    /**
     * 保存联系人数据
     */
    public static void prePutSettingContacts(Context context, String key, String value) {
        putPreferencesString(context, "localContacts_" + key, value);
    }

    /**
     * 保存版本提示时间
     * */
    public static void prePutVersionPrompt(Context context,Long value)
    {
        PutPreferencesLong(context,"versionprompt", value);
    }
    public static void PutPreferencesLong(Context context,String name,long value)
    {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(name, value);
        //提交更改
        editor.commit ();
    }
    /**
     *  返回最近一次版本提示时间
     * */
    public static Long preGetVersionPrompt(Context context)
    {
        return  GetPreferencesLong(context,"versionprompt", 0l);
    }
    public static Long GetPreferencesLong(Context context,String name,Long defaultValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);

        return preferences.getLong(name, defaultValue);

    }
}

