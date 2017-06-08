package newhome.baselibrary.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by 20160330 on 2017/3/21.
 */
//广播接收
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //广播接收是否有网络
        ConnectivityManager connectionManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {//用 NetworkInfo 的 isAvailable()方法，就可以判断
//            出当前是否有网络
            Toast.makeText(context, "network is available",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "network is unavailable",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
