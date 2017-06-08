package newhome.baselibrary.Broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/3/21.
 */
//为了能够简单地解决广播的安全性问题，Android 引入了一套本地广播机制，使用这个
//机制发出的广播只能够在应用程序的内部进行传递，并且广播接收器也只能接收来自本应用
//        程序发出的广播，这样所有的安全性问题就都不存在了。
//        本地广播的用法并不复杂，主要就是使用了一个 LocalBroadcastManager 来对广播进行
//        管理，并提供了发送广播和注册广播接收器的方法。
public class MyBroadcastLocal  extends Activity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
//  获取实例
        EditText button = (EditText) findViewById(R.id.price);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                        localBroadcastManager.sendBroadcast(intent); //  发送本地广播
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
//  注册本地广播监听器
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
//本地广播是无法通过静态注册的方式来接收的。其实这也完全
//        可以理解，因为静态注册主要就是为了让程序在未启动的情况下也能收到广播，而发送本地
//        广播时，我们的程序肯定是已经启动了，因此也完全不需要使用静态注册的功能。
//        最后我们再来盘点一下使用本地广播的几点优势吧。
//        1. 可以明确地知道正在发送的广播不会离开我们的程序，因此不需要担心机密数据泄
//        漏的问题。
//        2. 其他的程序无法将广播发送到我们程序的内部，因此不需要担心会有安全漏洞的隐
//        患。
//        3. 发送本地广播比起发送系统全局广播将会更加高效。
