package newhome.baselibrary.observer;

import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/1/22.
 */

public class ConcreteWatcher implements Watcher {
    @Override
        //并覆盖其方法
        public void updateNotify(MyContentData content){
//            int id = content.getId();
//            String name = content.getName();
            String Data = content.getData();
//        Logs.Debug("gg===id:" +id + "/n name:" + name + "/n address:" + address);
        Logs.Debug("gg===id:" +Data );
        }
}
