package newhome.baselibrary.Routers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import newhome.baselibrary.Tools.ExtraTypes;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Bus.RoutersAction;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class Routers {
    private static ExtraTypes extraTypes;
    private static Path formatPath;
    private static SharedPreferences preferences;
    static String Load;
    // 通过传递带有标识的字符串，进行界面跳转
    /**
     * @param context
     * @param url     参数，带有需要跳转的目标activity的标识字符串
     */
    public static void open(Context context, String url) {
        Bundle bundle = new Bundle();
        open(context, url, bundle);
    }
    //通过传递带有标识的字符串，进行界面跳转
    /**
     * @param context
     * @param url     参数，带有需要跳转的目标activity的标识字符串
     * @param bundle     参数，跳转时带的参数
     */
    public static void open(Context context, String url, Bundle bundle) {
        Logs.Debug("gg===========RoutersUrl=="+url);
        if (!lodejudge(context, url)) {//判断是否登录,只有两个页面需要在这里判断，MSG,CHAT
            return;
        }
        inint(url, bundle);//特殊情况，需要跳转webView，Activity关键字冲突，增加关键字判断
        if (url.startsWith("http://")) {//http:开头的都跟换为https://
            url = "https://" + url.split("http://")[1];
        }
        Boolean loadWebview = false;
        if (bundle.containsKey("webview_key")) {//需要用webView打开，但是关键字与activity冲突
            if (bundle.getString("webview_key").equals(RoutersAction.WEBVIEW_CA)) {
                loadWebview = true;
            }
        }
        if (loadWebview) {//若为true，跳转到webview加载类
            intent(context, url, bundle);//webview打开
        } else if (judge(url, RoutersAction.ACTIVITY) && (!url.contains("https://huishou.9ji.com"))) {//回收的不用activity打开，判断参数url中是否包含activity关键字
            //打开网址对应的activity，网址后面的参数信息，在目标activity用getIntent获取，key为"path"
            open_activity(context, url, bundle);
        }else if (Routers.judge(url, RoutersAction.FRAGMENT))
        {
            if(url.contains("/store/shopdetail")){
                intent(context, url, bundle);
            }else {
                open_fragment(context, url, bundle);
            }
        }
        else {
            //目前需要加载的fragment
            //webView打开该网址页面或加载fragment界面
            intent(context, url, bundle);//若需要加载fragment,uri为带有需要加载fragment的标识，若直接加载页面，uri为网页链接字符串（不做任何操作直接打开）
        }
    }
    //直接加载webview或则fragment打开
    /**
     * @param context
     * @param path    网址形式的参数
     */
    public static void intent(Context context, String path, Bundle bundle) {
        bundle.putString("pathUrl", path);
        //用ExtrasActivity进行加载webView或者Fragment
        open(context, RoutersAction.INTENT_ACTIVITY_EXTRASACTIVITY, RoutersAction.INTENT_DATA_EXTRAS, bundle);
    }
    //用activity打开跳转
    /**
     * @param context  用activity打开跳转
     * @param url     网址字符串
     */
    public static void open_activity(Context context, String url, Bundle bundle1) {
        String last_part;
        if (isTrueNet(url)) {//url判断是否为网址形式
            Bundle bundle = new Bundle();
            Intent intent;
            last_part = url.split("\\.com")[1];//把字符串分为两部分
            if (!(last_part.equals("")||last_part.equals("\\"))) {
                bundle = parseExtras(last_part, bundle1);//把链接按照规则截取，以key,value形式存入bundle
                String item_path = "";
                int before_number = bundle.getInt("before_number");//？前面的字段有几个（标识)
                String befor_item = "";
                if (before_number != 0) {//若before_number为0，则表示链接为域名，跳转主页，若不为0则链接形式为域名+/../..形式
                    for (int i = 1; i < before_number + 1; i++) {//根据需要，把链接“？”号前面的字段按需求进行存储
                        String item = bundle.getString("key" + i);
                        befor_item = befor_item + "/" + item;
                        if (i == before_number) {//若为最后一个字段，根据需求进行判断存储
                            if (item.contains(".html")) {
                                bundle.remove("key" + i);
                                if (item.contains("-")) {
                                    bundle.putString("coll", item.replace(".html", "") + "_0");
                                } else if (item.contains("#detail=")) {
                                    bundle.putString("ppid", item.split("\\.html#detail=")[0]);//传递商品的ppit
                                    bundle.putString("detail", item.split("\\.html#detail=")[1]);//传递商品的ppit
                                } else {
                                    bundle.putString("ppid", item.replace(".html", ""));//传递商品的ppit
                                }
                            }
                            if(item.contains("#")){
                                if(item.contains("-")){
                                    String[] news_cid_id = item.split("-");
                                    String news_cid = news_cid_id[0].replace("#", "");
                                    String news_id = news_cid_id[1];
                                    bundle.remove("key" + i);
                                    bundle.putString("cid", news_cid);
                                    bundle.putString("id", news_id);
                                }else {
                                    String news_cid = item.replace("#", "");
                                    bundle.putString("cid", news_cid);
                                }
                            }
                            if (url.contains(RoutersAction.GoodsDetail) && url.contains(".")) {
                                String goodsId = item.split("\\.")[0];
                                bundle.remove("key" + i);
                                bundle.putString("goodsId", goodsId);
                                bundle.putString("homeTojsDetail", "0");
                            }
                        }
                    }
                    if (befor_item.contains(".aspx")) {
                        befor_item = befor_item.replace(".aspx", "");
                    } else if (befor_item.contains(".html")) {
                        befor_item = befor_item.replace(".html", "");
                    }
                    //若尾部包含.aspx或者.html，则去掉
                    if (judge_biaoshi(befor_item).equals("one")) {//对链接中截取的字符串进行判断，需要从该字符串中截取几个字段作为标识
                        item_path = "/" + befor_item.split("/")[1];
                    } else if (judge_biaoshi(befor_item).equals("two")) {
                        item_path = "/" + befor_item.split("/")[1] + "/" + befor_item.split("/")[2];
                    }
                } else {
                    item_path = "/main";
                }
                String new_uriPath = Uri.parse(url).getScheme() + "://" + Uri.parse(url).getHost() + item_path;//得到需要跳转目标的标识
                intent = new Intent(RoutersAction.INTENT_ACTIVITY_FLAGE, Uri.parse(new_uriPath));
                intent.putExtras(bundle);
            } else {//字符串以.com结尾,跳转主页
                intent = new Intent(RoutersAction.INTENT_ACTIVITY_FLAGE, Uri.parse(RoutersAction.INTENT_DATA_MAIN));
                intent.putExtras(bundle);
            }
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            try {
                if (last_part.equals("")||last_part.equals("\\")) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT);
            }
        } else {//不是链接形式，关键字形式，直接加入标识跳转
            String biaoshi = keyString(url);//不是链接形式，把传入字段变为可跳转标识，进行跳转
            Intent intent;
            intent = new Intent(RoutersAction.INTENT_ACTIVITY_FLAGE, Uri.parse(biaoshi));
            intent.putExtras(bundle1);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            try {
                if (url.equals(RoutersAction.MAIN)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT);
            }
        }
    }
    public static void open_fragment(Context context,String url,Bundle bundle){
        Intent intent;
        intent = new Intent(RoutersAction.INTENT_DATA_LOADFRAGMENT_FLAGE, Uri.parse("https://m.9ji.com"+RoutersAction.LOADFRAGMENT));
        bundle.putString("pathUrl",url);
        intent.putExtra("data", bundle);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            if (url.equals(RoutersAction.MAIN)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT);
        }
    }
    //判断是否登录
    protected static Boolean lodejudge(Context context, String url) {
//        preferences = context.getSharedPreferences("ch999_pre", MODE_PRIVATE);
        Load = preferences.getString("jiujiLood", "");
        Bundle bundle = new Bundle();
        if (Tools.isEmpty(Load) && (judge(url, RoutersAction.JL)) && (!url.equals(RoutersAction.ACOUNT_LOGIN))) {
            if (url.contains(RoutersAction.MSGCENTER)) {
                bundle.putString("key", "MSG");
                Routers.open_activity(context, RoutersAction.ACOUNT_LOGIN, bundle);
            } else if (url.contains(RoutersAction.CHAT)) {
                bundle.putString("key", "CHAT");
                Routers.open_activity(context, RoutersAction.ACOUNT_LOGIN, bundle);
            } else {
                Routers.open_activity(context, RoutersAction.ACOUNT_LOGIN, new Bundle());
            }
            return false;
        }
        return true;
    }
    //标识判断，是否需要activity，fragment打开，是否关键字冲突
    /**
     * @param value     参数网址字符串
     * @param character 标识符
     * @return
     */
    public static boolean judge(String value, String character) {
        String regex_string = "";
        if (character.equals(RoutersAction.CUSTOM)) {
            regex_string = RoutersAction.RCI;
        } else if (character.equals(RoutersAction.ACTIVITY)) {
            regex_string = RoutersAction.RAI;//打开activity标识
        } else if (character.equals("fragment")) {
            regex_string = RoutersAction.RFI;//打开fragment标识
        } else if (character.equals(RoutersAction.JL)) {
            regex_string = RoutersAction.RJL;
        }
        Pattern p_m = null;
        Matcher m_m = null;
        p_m = Pattern.compile(regex_string);
        m_m = p_m.matcher(value);
        boolean resule = m_m.find();
        return resule;
    }
    //根据判断获取标识,有的标识需要从链接字符串中截取两个，有的只需要一个，在该方法中添加判断
    /**
     * @param value
     * @return
     */
    public static String judge_biaoshi(String value) {
        Pattern p_m = null;
        Matcher m_m = null;
        String regex_string_one = RoutersAction.RAII;
        String regex_string_two = RoutersAction.RFII;
        p_m = Pattern.compile(regex_string_one);
        m_m = p_m.matcher(value);
        boolean resule = m_m.find();
        if (resule) {
            return "one";
        } else {
            p_m = Pattern.compile(regex_string_two);
            m_m = p_m.matcher(value);
            resule = m_m.find();
            if (resule) {
                return "two";
            }
            return "";
        }
    }
    //特殊情况,可能不跳activity，但是链接中含有activity关键字
    public static void inint(String url, Bundle bundle) {
        if (url.startsWith("https://m.9ji.com/goods/unsale.aspx?from=")) {
            if (!bundle.containsKey("webview_key")) {
                bundle.putString("webview_key", RoutersAction.WEBVIEW_CA);
            }
        }
    }
    //不是链接形式，把传入字段变为可跳转标识
    public static String keyString(String url){
        if(url.equals("/mproduct")){
            return "https://jishi.9ji.com" + url;
        }else{
            return "https://m.9ji.com" + url;
        }
    }
    /**
     * 获取 params，把链接中除了域名的字段进行截取
     *
     * @param lastpart
     * @return
     */
    public static Bundle parseExtras(String lastpart, Bundle bundle1) {
        Bundle bundle = bundle1;
        String item_beforeThePart = "";
        String item_lastThePart = "";
        String path = lastpart;
        int j = 0;
        //若带有？，把域名后面的参数变为key,value形式存储，？号前以key[i]，value形式存储，?号之后以连接自带的key,value形式存储
        if (path.contains("?")) {
            String[] str = path.split("\\?");
            item_beforeThePart = str[0];
            item_lastThePart = str[1];
            String[] item_beforePart_item = item_beforeThePart.split("/");
            if (item_beforePart_item.length > 0) {
                for (int i = 1; i < item_beforePart_item.length; i++) {
                    bundle.putString("key" + i, item_beforePart_item[i]);
                    j = i;
                }
            }
            String[] item_lastPart_item = item_lastThePart.split("&");
            if (item_lastPart_item.length > 0) {
                for (int i = 0; i < item_lastPart_item.length; i++) {
                    String[] key_value = item_lastPart_item[i].split("=");
                    String key = key_value[0];
                    String value = key_value[1];
                    bundle.putString(key, value);
                }
            }
        } else { //若不带有？，把域名后面的参数变为key,value形式存储
            String[] str = path.split("/");
//            11:cid
//            123456:id
            if (str.length > 0) {
                for (int i = 1; i < str.length; i++) {
                    bundle.putString("key" + i, str[i]);
                    j = i;
                }
            }
        }
        bundle.putInt("before_number", j);
        return bundle;
    }
    //判断链接形式是否为规则中的链接
    public static boolean isTrueNet(String Uri) {
        String regex_string_m = "http[s]{0,1}://m\\.9ji\\.com[a-zA-Z0-9\\.\\-=\\?&/#_%]*";
        String regex_string_jishi = "http[s]{0,1}://jishi\\.9ji\\.com[a-zA-Z0-9\\.\\-=\\?&/#_%]*";
        String regex_string_huishou = "http[s]{0,1}://huishou\\.9ji\\.com[a-zA-Z0-9\\.\\-=\\?&/#_%]*";
        Pattern p_m = null;
        Pattern p_jishi = null;
        Pattern p_huishou = null;
        Matcher m_m = null;
        Matcher m_jishi = null;
        Matcher m_huishou = null;
        p_m = Pattern.compile(regex_string_m);
        p_jishi = Pattern.compile(regex_string_jishi);
        p_huishou = Pattern.compile(regex_string_huishou);
        m_m = p_m.matcher(Uri);
        m_jishi = p_jishi.matcher(Uri);
        m_huishou = p_huishou.matcher(Uri);
        boolean resule = m_m.matches() || m_jishi.matches() || m_huishou.matches();
        return resule;
    }



    //一般情况用不到
    //自定义跳转，自己配置需要跳转的activity，不使用已有规则，使用自己的定义的形式，传入参数进行跳转
    /**
     * @param context
     * @param uri     目标activity，AndroidManifest中action android:name="android.intent.9ji.VIEW.extrasActivity"自己设置的标识
     * @param flage   <data android:scheme="@string/scheme" android:host="@string/host"  android:path="/Extras"/>  scheme+host+path字符串
     * @param bundle  Bundle 参数
     */
    public static void open(Context context, String uri, String flage, Bundle bundle) {
        if (!lodejudge(context, uri)) {//判断是否登录,只有两个页面需要在这里判断，MSG,CHAT
            return;
        }
        inint(uri, bundle);//特殊情况，需要跳转webView，Activity关键字冲突
        Intent intent = new Intent(uri, Uri.parse(flage));
        intent.putExtra("data", bundle);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT);
        }
    }
    //暂时没用到
    private static void put(Bundle bundle, String name, String value) {
        int type = extraTypes.getType(name);
        name = extraTypes.transfer(name);
        if (type == ExtraTypes.STRING) {
            type = extraTypes.getType(name);
        }
        switch (type) {
            case ExtraTypes.INT:
                bundle.putInt(name, Integer.parseInt(value));
                break;
            case ExtraTypes.LONG:
                bundle.putLong(name, Long.parseLong(value));
                break;
            case ExtraTypes.BOOL:
                bundle.putBoolean(name, Boolean.parseBoolean(value));
                break;
            case ExtraTypes.SHORT:
                bundle.putShort(name, Short.parseShort(value));
                break;
            case ExtraTypes.FLOAT:
                bundle.putFloat(name, Float.parseFloat(value));
                break;
            case ExtraTypes.DOUBLE:
                bundle.putDouble(name, Double.parseDouble(value));
                break;
            case ExtraTypes.BYTE:
                bundle.putByte(name, Byte.parseByte(value));
                break;
            case ExtraTypes.CHAR:
                bundle.putChar(name, value.charAt(0));
                break;
            default:
                bundle.putString(name, value);
                break;
        }
    }
    public static void open1(Context context, String url) {
        //  直接判断用户id为空跳登录页面就行
        if (url.contains("http:") && (!url.contains("https:"))) {
            url = url.replace("http:", "https:");
        }
        open(context, url);
    }//测试
}

