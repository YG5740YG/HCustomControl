package newhome.baselibrary.Model;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public abstract class ObjectHandle <T>{
        public Type mType ;
        public String key ;

        public ObjectHandle(String stats){
            key = stats ;
            mType =  getSuperclassTypeParameter(getClass());
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
        public abstract void onFailur(String  msg,Exception e);
        public abstract void onSuccess(int  code, T t);
        public void onCaceh(T t){};
        public void onProgress(int progress){};
}
