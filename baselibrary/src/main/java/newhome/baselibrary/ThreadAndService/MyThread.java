package newhome.baselibrary.ThreadAndService;

/**
 * Created by 20160330 on 2017/4/6 0006.
 */

public class MyThread  extends Thread {
    @Override
    public void run() {
// 处理具体的逻辑
    }
    //只需要 new 出 MyThread 的实例，然后调
//    用它的 start()方法，这样 run()方法中的代码就会在子线程当中运行了
    public void startThread(){//启动这个线程
        new MyThread().start();
    }

    //使用继承的方式耦合性有点高，更多的时候我们都会选择使用实现 Runnable 接
//    口的方式来定义一个线程
    class MyThreadOne implements Runnable {
        @Override
        public void run() {
// 处理具体的逻辑
        }
    }
    //启动线程的方法也需要进行相应的改变
    MyThreadOne myThread = new MyThreadOne();
    public void startThreadOne(){//启动这个线程
        new Thread(myThread).start();
    }
    //Thread 的构造函数接收一个 Runnable 参数，而我们 new出的 MyThread 正是
//一个实现了 Runnable接口的对象，所以可以直接将它传入到 Thread 的构造函数里
//    匿名类
    public void startThreadtwo() {//启动这个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
// 处理具体的逻辑
            }
        }).start();
    }
}
