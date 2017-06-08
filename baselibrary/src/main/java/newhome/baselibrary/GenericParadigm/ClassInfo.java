package newhome.baselibrary.GenericParadigm;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
//泛型的定义，泛型的使用见MyTools
public class ClassInfo<T> {
    private T var;// 定义泛型变量
    public void setVar(T var){
        this.var=var;
    }
    public T getVar (){
        return this.var;
    }
    //例子
    public static void ClassInfo(Class<?> temp){        // 可以接收任意的泛型对象
        System.out.println("内容：" + temp) ;
    }
}
