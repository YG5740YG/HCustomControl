package newhome.baselibrary.ThreadAndService;

import android.os.Handler;
import android.os.Message;

/**
 * Created by 20160330 on 2017/4/6 0006.
 */

public class MyThreadChangeUI {
    public static final int UPDATE_TEXT = 1;
    public void startThreadtwo() {//启动这个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPDATE_TEXT;
                message.obj="nihao";
                handler.sendMessage(message); //  将Message
// 处理具体的逻辑
            }
        }).start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
//  在这里可以进行UI 操作
//                    text.setText("Nice to meet you"+msg.obj.tostring);
                    break;
                default:
                    break;
            }
        }
    };
}
