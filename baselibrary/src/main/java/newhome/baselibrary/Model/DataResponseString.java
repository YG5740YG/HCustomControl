package newhome.baselibrary.Model;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public abstract class DataResponseString {
    public abstract void onSucc(Object claxx);
    public abstract void onFail(String error);
}
