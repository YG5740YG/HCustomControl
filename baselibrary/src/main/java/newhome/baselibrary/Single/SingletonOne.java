package newhome.baselibrary.Single;

/**
 * Created by Administrator on 2017/4/10.
 */
//懒汉写法
public class SingletonOne {
//    volatile  解决多处理器变量共享问题
    private volatile static SingletonOne singletonOne;
    private SingletonOne(){

    }
    public static SingletonOne getInstance(){
        //双重校验，解决性能问题
        if(singletonOne==null){
//            synchronized 具有同步的作用
            synchronized (SingletonOne.class){
                if(null==singletonOne){
                    singletonOne=new SingletonOne();
                }
            }
        }
        return singletonOne;
    }
}
