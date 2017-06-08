package newhome.baselibrary.GenericParadigm;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
// 此处泛型只能是数字类型
public class ClassInfoLimit<T extends Number> {
    private T var ;        // 定义泛型变量
    public void setVar(T var){
        this.var = var ;
    }
    public T getVar(){
        return this.var ;
    }
    public String toString(){    // 直接打印
        return this.var.toString() ;
    }
}
