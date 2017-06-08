package newhome.baselibrary.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 20160330 on 2017/3/21.
 */

public class MybroadcastReceiverMore  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot Complete", Toast.LENGTH_LONG).show();//监听更过的广播，在manifast里面进行注册
    }
}
