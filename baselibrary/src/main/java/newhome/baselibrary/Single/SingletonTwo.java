package newhome.baselibrary.Single;

/**
 * Created by Administrator on 2017/4/10.
 */
//饥汉写法，不存在多线程，所以不考虑多线程
    //static 修饰的全局变量在类加载的时候就被初始化了，所以不存在多线程
public class SingletonTwo {
    private static SingletonTwo singletonTwo=new SingletonTwo();
    private SingletonTwo(){}
    public static SingletonTwo getInstance(){
//        return singletonTwo;
        return SinletonHolder.SINGLETONTWO;
    }
    //我们在类被加载的时候不想实例化这个变量，采用内部类
    private static class SinletonHolder{
        private static SingletonTwo SINGLETONTWO=new SingletonTwo();
    }
}
