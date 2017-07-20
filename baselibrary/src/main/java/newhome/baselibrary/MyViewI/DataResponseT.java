package newhome.baselibrary.MyViewI;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import newhome.baselibrary.GenericParadigm.ClassInfo;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public abstract class DataResponseT<T> {
    public Type mType ;
    public String mSats;
    public DataResponseT(String stats){
        mType =  getSuperclassTypeParameter(getClass());
        this.mSats=stats;
    }
    /**
     * 把type转换成对应的类，这里不用看明白也行。
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
    public abstract void onSucc(T claxx);
    public abstract void onCache(T claxx);
    public abstract void onFail(String error);
}
