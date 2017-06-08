package newhome.baselibrary.GenericParadigm;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

interface  ClassInfoInterface<T> { // 在接口上定义泛型
    public T getVar() ;    // 定义抽象方法，抽象方法的返回值就是泛型类型
}
