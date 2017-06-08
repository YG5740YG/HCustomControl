package newhome.baselibrary.Broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/3/21.
 */
//广播使用，原始写法（可用BaseEvent，比较方便）
public class MyBroadcast extends AppCompatActivity{
    private LocalBroadcastManager localBroadcastManager;
    private MyBroadcastReceiver myBroadcastReceiver;
    public static final String BRAODCASTACTION="com.xinxue.LOCALBROADCAST";//广播接收的标识，该标识需要在广播接收器manifast里面进行注册
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);//注册
        myBroadcastReceiver=new MyBroadcastReceiver();

//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//网络变化的广播
// //       networkChangeReceiver = new NetworkChangeReceiver();
// //       registerReceiver(networkChangeReceiver, intentFilter);
//        sendBroadcast(intentFilter);//广播发送

//        发送有序广播只需要改动一行代码，即将 sendBroadcast()方法改成sendOrderedBroadcast()方法
//        sendOrderedBroadcast()方法接收两个参数，第一个参数仍然是
//        Intent，第二个参数是一个与权限相关的字符串，这里传入 null就行了。
//        sendOrderedBroadcast(intentFilter, null);
//        这个时候的广播接收器是有先后顺序的，而且前面的广播接收器还可以将广播截断，以阻止其继续传播。
//        如何设定广播接收器的先后顺序呢？当然是在注册的时候进行设定的了，修改AndroidManifest.xml 中的代码
//        <receiver android:name=".MyBroadcastReceiver">
//           <intent-filter android:priority="100" >
//              <action android:name="com.example.broadcasttest.MY_BROADCAST"/>
//           </intent-filter>
//        </receiver>

//        abortBroadcast();
//        在 onReceive()方法中调用了 abortBroadcast()方法，就表示将这条广播截断，后面的广播接收器将无法再接收到这条广播
    }
    //广播注册
    public void registerBroadCast(View view){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(BRAODCASTACTION);
        localBroadcastManager.registerReceiver(myBroadcastReceiver,intentFilter);
    }
    //广播停止
    public void unRegisterBroadCast(View view){
        localBroadcastManager.unregisterReceiver(myBroadcastReceiver);
    }
    //广播发送
    public void send(View view){
        localBroadcastManager.sendBroadcast(new Intent(BRAODCASTACTION));
    }
    //动态注册的广播接收器一定都要取消注册才行，这里我们是在 onDestroy()
//    方法中通过调用 unregisterReceiver()方法来实现的
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}

