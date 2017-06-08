package newhome.baselibrary.observer;

/**
 * Created by 20160330 on 2017/1/22.
 */
//首先我们需要先定义一个接口为:抽象观察者
public interface Watcher {
//再定义一个用来获取更新信息接收的方法
    public void updateNotify(MyContentData content);
}
