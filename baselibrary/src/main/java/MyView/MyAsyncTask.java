package MyView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

import io.github.mayubao.pay_library.MainActivity;
import newhome.baselibrary.R;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
/*
参数1：向后台任务的执行方法传递参数的类型 ；
参数2：在后台任务执行过程中，要求主UI线程处理中间状态，通常是一些UI处理中传递的参数类型；
参数3：后台任务执行完返回时的参数类型。
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class MyAsyncTask extends AsyncTask<Integer, String, Void> {
    // 实现抽象方法doInBackground()，代码将在后台线程中执行，由execute()触发
    protected Void doInBackground(Integer... params) {
        return null;
    }
    // 任务启动，可以在这里显示一个对话框，这里简单处理
    protected void onPreExecute() {
        super.onPreExecute();
    }
    // 取消
    protected void onCancelled() {
        super.onCancelled();
    }
    // 定义后台进程执行完后的处理
    protected void onPostExecute(Void result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            super.onPostExecute(result);
        }
//        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
    }
    // 更新进度,在UI主线程执行的内容，将item加入list中。方法中的参数为范式方式，实质为数组，由于我们只传递了item一个String，要获取，为values[0]
    protected void onProgressUpdate(String... values) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            super.onProgressUpdate(values);
        }
    }
//    检查网络状态
public boolean checkNetworkInfo(final Context context) {
    ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    // mobile 3G Data Network
    NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            .getState();
    // wifi
    NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            .getState();
    // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
    if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
        return true;
    if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
        return true;
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(context.getResources().getString(R.string.title))
            .setCancelable(false)
            .setPositiveButton(
                   context.getResources().getString(R.string.search_menu_title),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 进入无线网络配置界面
                            context.startActivity(new Intent(
                                    Settings.ACTION_WIRELESS_SETTINGS));
//                            MainActivity.this.finish();
                        }
                    })
            .setNegativeButton(context.getResources().getString(R.string.cube_mints_exit_tip),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            MainActivity.this.finish();
                        }
                    });
    builder.show();
    return false;
//    // 检查网络状态
//    if (!checkNetworkInfo()) {
//        return;
//    }
}
//让数据接受网页的格式
    private void dataRecieveInternetStyle(String s, Context context, TextView textview){
        URLEncoder.encode(s); //网址请求中文解析
//                &#8230;代表省略号
//        网址请求带中文
//        URLEncoder.encode("sdfa", "utf-8");
//        Html.fromHtml
        textview.setText(Html.fromHtml("<b>"+
               "gggg"+ "</b>"));
        textview.setText(Html.fromHtml("<font color=#FF0000>hello</font>"));
    }
}
//使用：new MyAsyncTask().execute(参数1);// 创建后台任务的对象


