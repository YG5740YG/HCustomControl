package newhome.baselibrary.GenericParadigm;

import android.view.View;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class ClassInfoSon <T> implements ClassInfoInterface<T>{    // 定义泛型接口的子类
    private T var ;                // 定义属性
    public ClassInfoSon(T var){        // 通过构造方法设置属性内容
        this.setVar(var) ;
    }
    public void setVar(T var){
        this.var = var ;
    }
    public T getVar(){
        return this.var ;
    }
}
