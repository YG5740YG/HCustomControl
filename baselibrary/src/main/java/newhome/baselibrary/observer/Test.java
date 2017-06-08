package newhome.baselibrary.observer;

import android.content.Context;

import org.w3c.dom.Text;

import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/1/22.
 */

public class Test {
    public Test(){
        Logs.Debug("gg===========obverser");
        //实例一个被观察者
        ConcreteWatched concreteWatched=new ConcreteWatched();
        //实例一个观察者
       ConcreteWatcher concreteWatcher=new ConcreteWatcher();
        //实例第二个观察者
       ConcreteWatcher concreteWatcher1=new ConcreteWatcher();
        //实例第三个观察者
       ConcreteWatcher concreteWatcher2=new ConcreteWatcher();

        //加入对其观察
        concreteWatched.add(concreteWatcher);
        concreteWatched.add(concreteWatcher1);
        concreteWatched.add(concreteWatcher2);
        //当被观察者发生变化时：调用其信息方法 
        ContentData contentData=new ContentData();
        contentData.setId(11);
        contentData.setName("Test1");
        contentData.setAddress("kunming");
        //观察者内容展示
//        concreteWatched.notifyWatcher(contentData);
        //被观察者可以调用删除方法

    }
}
