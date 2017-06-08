package newhome.baselibrary.Bus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.squareup.otto.Subscribe;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.UITools;

/**
 * Created by 20160330 on 2017/2/28.
 */
//用于接收
public class test extends Activity {
    BusEvent event;//发送注册
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_share);
        BusProvider.getInstance().register(this);//调用注册，接收初始化

        event = new BusEvent();//发送初始化
        event.setAction(BusAction.CHANGE_CITY);//发送初始化，设置标识
        event.setObject("北京");//设置需要发送的值
        BusProvider.getInstance().post(event);//发送
    }
    //监听分享的回调//接收
    @Subscribe
    public void onPostEvent(BusEvent event) {
        int action = event.getAction();
        switch (action) {
            case BusAction.SUCCESS:
                UITools.toastShowShort(this, event.getObject().toString());

                break;
            case BusAction.FAILE:
                UITools.toastShowShort(this, event.getObject().toString());
                break;
        }
    }
}
