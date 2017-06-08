package newhome.baselibrary.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import newhome.baselibrary.Bus.BusAction;
import newhome.baselibrary.Bus.BusEvent;
import newhome.baselibrary.Bus.BusProvider;

/**
 * Created by 20160330 on 2017/1/16.
 */

public class UITools {
    private static Toast strShorttoast; // 短，字符串提示
    private static Toast strLongtoast; // 长，字符串提示
    public static String cookieUrl = ".9ji.com";

    public static void msgBox(Context context, String msg) {
        new AlertDialog.Builder(context).setTitle("提示").setMessage(msg)
                .create().show();
    }

    /**
     * 短 字符串提示
     */
    public static void toastShowShort(Context context, String mes) {
        if (strShorttoast != null) {
            strShorttoast.setText(mes);
            strShorttoast.setDuration(Toast.LENGTH_SHORT);
        } else {
            strShorttoast = Toast.makeText(context, mes, Toast.LENGTH_SHORT);
        }
        strShorttoast.show();
    }

    /**
     * 长 字符串提示
     */
    public static void toastShowLong(Context context, String mes) {
        if (strLongtoast != null) {
            strLongtoast.setText(mes);
            strLongtoast.setDuration(Toast.LENGTH_SHORT);
        } else {
            strLongtoast = Toast.makeText(context, mes, Toast.LENGTH_SHORT);
        }

        strLongtoast.show();
    }
//
//    public static void showMsg(Context context, String s) {
//        if (!Tools.isEmpty(s)) {
//            CustomDialog.Builder builder = new CustomDialog.Builder(context);
//            builder.setTitle("小九提醒");
//            builder.setMessage(s);
//            // 更新
//            builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//
//            Dialog noticeDialog = builder.create();
//            noticeDialog.setCancelable(true);
//            noticeDialog.show();
//        }
//    }
//
//    public static Dialog showMsgAndClick(Context context, String title, String msg, String okButtonStr, String noButtonStr,
//                                         DialogInterface.OnClickListener okClickListener, DialogInterface.OnClickListener noClickListener) {
//        CustomDialog.Builder builder = new CustomDialog.Builder(context);
//        builder.setTitle(title).setMessage(msg).setPositiveButton(okButtonStr, okClickListener).setNegativeButton(noButtonStr, noClickListener);
//        Dialog noticeDialog = builder.create();
//        noticeDialog.setCancelable(true);
//        noticeDialog.show();
//        return noticeDialog;
//    }
//
//    public static Dialog showMsgAndClick_one(Context context, String title, String msg, String okButtonStr,
//                                             DialogInterface.OnClickListener okClickListener) {
//        CustomDialog.Builder builder = new CustomDialog.Builder(context);
//        builder.setTitle(title).setMessage(msg).setPositiveButton(okButtonStr, okClickListener);
//        Dialog noticeDialog = builder.create();
//        noticeDialog.setCancelable(true);
//        noticeDialog.show();
//        return noticeDialog;
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 取xml文件中定义的大小值，自动识别xml中定义的类型是dp还是sp
     */
    private static TypedValue mTmpValue = new TypedValue();

    public static int getXmlDef(Context context, int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return (int) TypedValue.complexToFloat(value.data);
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */

    public static Bitmap readBitMap(Context context, int resId) {

        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        //  获取资源图片

        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is, null, opt);

    }

    public static Bitmap activityShot(Activity activity) {
         /*获取windows中最顶层的view*/
        View view = activity.getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        WindowManager windowManager = activity.getWindowManager();

        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
                height - statusBarHeight);

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }
    /*
    图片变字节流

     */

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /*
    得到一个  "yyyy-MM-dd HH:mm:ss" 格式的字符串
        format:"yyyy-MM-dd HH:mm:ss"
     */
    public static String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String getTimestamp(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        String timestamp = formatter.format(date);
        return timestamp;
    }

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    public static String getCookieUrl() {
        return cookieUrl;
    }

    public static void setCookieUrl(String cookieUrl) {
        UITools.cookieUrl = cookieUrl;
    }

    public static void setCookie(Context context, String url, String t) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, t);
        CookieSyncManager.getInstance().sync();

    }

    public static void removeAllCookie(Context context) {
        // 清空cookie
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieManager.getInstance().removeAllCookie();
        CookieSyncManager.getInstance().sync();

        // 删除 webview 缓存
        context.deleteDatabase("WebView.db");
        context.deleteDatabase("WebViewCache.db");
    }


    public static String getCookie(Context context, String url, String t) {


        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();

        return cookieManager.getCookie(url);

    }
    /**
     * 权限 6.0 检测权限
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
    //获取手机自带的内置sd卡存储空间剩余的大小
    public void ExternalStorageDirectory() {
        long availableSpace = 0;
        String sdcard = Environment.getExternalStorageDirectory().getPath();         //即 sdcard = "/mnt/sdcard"
        StatFs statFs = new StatFs((new File(sdcard)).getPath());
        availableSpace = (statFs.getBlockSize() * ((long) statFs.getAvailableBlocks() - 4));    //剩余的空间大小
    }
    //获取外部SD卡剩余空间的大小
    public void AvailableBlocks(){
    long availableSpace2 = 0;
        String sdcard2 = "/mnt/sdcard2";       //暂时没有找到好的方法来获取路径，只能硬编码了
        StatFs statFs = new StatFs((new File(sdcard2)).getPath());
        availableSpace2 = (statFs.getBlockSize()*(long)statFs.getAvailableBlocks());    //剩余的空间大小
        }
        //经验证（Android4.0的系统），在这里不用减去4是剩余空间的大小；
    //获取文件的大小
        public void FileNum(){
            long fileSize = 0;
            try {
                String sdcard2 = "/mnt/sdcard2/2.xml";
                File copyf = new File((new File(sdcard2)).getPath());       //copyPath 为目标文件的全路径  例如要将a/b/文件夹下的c.xml复制到其他处 a/b/c.xml
                FileInputStream fis = null;
                fis = new FileInputStream(copyf);
                fileSize = (long)fis.available();     //文件大小
                 }catch(Exception e){

            }
        }

    BusEvent event_cityId;//在activity中使用
    //RecycleView是否到最后一行
    public void RecycleViewLastRow(final RecyclerView mrecycleview){
        mrecycleview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if(!mrecycleview.canScrollVertically(1)){
                        Logs.Debug("gg======"+"已经是最后一行");
                    }
                }
            }
        });
    }
    //广播发送
    //启动的时候需要初始化     BusProvider.getInstance().register(this);
    //销毁的时候需要使用    BusProvider.getInstance().unregister(this);
    public void broadcastPost(){//在.View.MyScrolllayout.MainActivity中进行了验证可以用
        event_cityId=new BusEvent();
        event_cityId.setAction(BusAction.TEST);
        event_cityId.setObject("发送的信息");
        BusProvider.getInstance().post(event_cityId);
    }
    //广播接收
    @Subscribe
    public void broadcastResevie(BusEvent event){
        int action = event.getAction();
        switch (action) {
            case BusAction.TEST:
                event.getObject();
                Logs.Debug("gg=============receview=="+event.getObject());
                break;
        }
    }
    //保存文本数据
    /**
     * Context 类中提供了一个 openFileOutput ()方法，可以用于将数据存储到指定的文件中。
     这个方法接收两个参数，第一个参数是文件名，在文件创建的时候使用的就是这个名称，注
     意这里指定的文件名不可以包含路径，因为所有的文件都是默认存储到/data/data/<package
     name>/files/ 目 录下 的 。第 二 个 参数 是 文件 的 操作 模 式 ，主 要 有两 种 模式 可 选 ，
     MODE_PRIVATE 和 MODE_APPEND。其中 MODE_PRIVATE 是默认的操作模式，表示当指
     定同样文件名的时候，所写入的内容将会覆盖原文件中的内容，而 MODE_APPEND 则表示
     如果该文件已存在就往文件里面追加内容，不存在就创建新文件。其实文件的操作模式本来
     还有另外两种，MODE_WORLD_READABLE 和 MODE_WORLD_WRITEABLE，这两种模
     式表示允许其他的应用程序对我们程序中的文件进行读写操作，不过由于这两种模式过于危
     险，很容易引起应用的安全性漏洞，现已在 Android 4.2 版本中被废弃。
     */
    public static void save(String inputText,Context context) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out =context.openFileOutput("New_data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //从文件中读取数据（文件流）
//    然后系统会自动到/data/data/<package name>/files/目录下去加载这个文件，并返回一个
//    FileInputStream 对象，得到了这个对象之后再通过 Java 流的方式就可以将数据读取出来
    public static String load(Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in =context.openFileInput("New_data");//文件名
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    //SharedPreferences 是使用键值对的方式来存储数据的,三种方法用于得到 SharedPreferences 对象
//    1. Context 类中的 getSharedPreferences()方法
    /**
     * 此方法接收两个参数，第一个参数用于指定 SharedPreferences文件的名称，如果指
     定的文件不存在则会创建一个，SharedPreferences 文件都是存放在/data/data/<package
     name>/shared_prefs/目录下的。第二个参数用于指定操作模式，主要有两种模式可以选
     择，MODE_PRIVATE 和 MODE_MULTI_PROCESS。MODE_PRIVATE 仍然是默认的操
     作模式，和直接传入 0 效果是相同的，表示只有当前的应用程序才可以对这个
     SharedPreferences文件进行读写。MODE_MULTI_PROCESS则一般是用于会有多个进程中
     对同一个 SharedPreferences文件进行读写的情况。
     */
//    2. Activity 类中的 getPreferences()方法
    /**
     * 这个方法和 Context 中的 getSharedPreferences()方法很相似，不过它只接收一个操
     作模式参数，因为使用这个方法时会自动将当前活动的类名作为 SharedPreferences 的文
     件名。
     */
//    3. PreferenceManager 类中的 getDefaultSharedPreferences()方法
    /**
     * 这是一个静态方法，它接收一个 Context 参数，并自动使用当前应用程序的包名作
     为前缀来命名 SharedPreferences 文件。
     */
    public static void PreferenceSaveData(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("data",context.MODE_PRIVATE).edit();
        editor.putString("name", "Tom");
        editor.putInt("age", 28);
        editor.putBoolean("married", false);
        editor.commit();// SharedPreferences 提交数据
    }
    //获取SharedPreferences文件中的数据
    public static String PreferenceGetData(Context context){
        SharedPreferences pref = context.getSharedPreferences("data",context.MODE_PRIVATE);
        String name = pref.getString("name", "");
        int age = pref.getInt("age", 0);
        boolean married = pref.getBoolean("married", false);
        return name+"=="+age+"=="+married;
    }
    //防止二次点击，重复打开界面
    //使用了开源库：  compile 'com.nineoldandroids:library:2.4.0'
    public static void prohibitRepeatClick(View view){
//        RxView.clicks(view)
//                .throttleFirst( 2 , TimeUnit.SECONDS )   //两秒钟之内只取一个点击事件，防抖操作
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();//点击触发的事件
    }
//                }) ;
//    }
}

