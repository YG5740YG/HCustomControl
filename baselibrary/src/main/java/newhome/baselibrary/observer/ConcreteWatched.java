package newhome.baselibrary.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 20160330 on 2017/1/22.
 */

public class ConcreteWatched implements Watched {
    //定义一个List来封装Watcher
    private List<Watcher> list = new ArrayList<Watcher>();
    @Override
    public void add(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void remove(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatcher(MyContentData content) {
        for(Watcher watcher : list){
            watcher.updateNotify(content);
        }
    }
}
