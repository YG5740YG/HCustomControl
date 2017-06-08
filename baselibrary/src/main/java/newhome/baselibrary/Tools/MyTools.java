package newhome.baselibrary.Tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.GenericParadigm.ClassInfo;
import newhome.baselibrary.Model.MyData;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class MyTools {
    //Json,常用的json解析方法
    static Gson gson = new Gson();
    public static void jsonToModel(String jsonData){
        //法一，简单的单行数据
        JSONObject jsonObject;//{"FirstName":"Peter","LastName":"Griffin","Age":"35"}
        try {
            jsonObject = new JSONObject(jsonData);
            Logs.Debug("gg===========json=2=");
            Logs.Debug("gg=========="+jsonObject.getInt("Age")+"=="+jsonObject.getString("FirstName")+"=="+jsonObject.getString("LastName"));
        }catch (Exception extraTypes){
            Logs.Debug("gg===========json=1=");
        }
        //法二，简单的多行数据
        JSONArray jsonArray;
        try {
            jsonArray=new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                Logs.Debug("gg===========json=2=");
                Logs.Debug("gg=========="+jsonObject.getInt("Age")+"=="+jsonObject.getString("FirstName")+"=="+jsonObject.getString("LastName"));
            }
        }catch (Exception extraTypes){
            Logs.Debug("gg===========json=1=");
        }
        //法三，json转换成model////{"FirstName":"Peter","LastName":"Griffin","Age":"35"}
        MyData myData=new MyData();
        myData=gson.fromJson(jsonData,MyData.class);//对象json数据解析
        Logs.Debug("gg=========age=="+myData.getAge());

        List<MyData> appList = gson.fromJson(jsonData, new
                TypeToken<List<MyData>>() {}.getType());
    }
    //泛型的使用，把json数据转换成需要的model数据
    public static <ClassInfo>ClassInfo jsonToModel(String jsonData,Class<ClassInfo> tClass){
        //法四，使用泛型传递数据
        return gson.fromJson(jsonData,tClass);

    }
    //把model数据转换成需要的json
    public String toJson(Object object) {
        return gson.toJson(object);
    }
    //把字节转换成需要类型的数据
    public <ClassInfo> ClassInfo toObject(byte[] bytes, Class<ClassInfo> claxx) {
        return gson.fromJson(new String(bytes), claxx);
    }
    //把jsonlist转换成modellist
    public <ClassInfo> List<ClassInfo> toList(String json, Class<ClassInfo> claxx) {
        Type type = new TypeToken<ArrayList<ClassInfo>>() {}.getType();
        List<ClassInfo> list = gson.fromJson(json, type);
        return list;
    }
    //泛型使用
    public static void fun(ClassInfo<?> temp){        // 可以接收任意的泛型对象
        System.out.println("内容：" + temp) ;
    }
    //泛型使用,只能接收Number及其Number的子类
    public static void fun(ClassInfo<? extends Number> temp,String s){
        // 只能接收String或Object类型的泛型
        //public static void fun(Info<? super String> temp){
        System.out.println("内容："+temp);
        ClassInfo<String> i1 = new ClassInfo<String>() ;        // 定义泛型类型为String
    }
    //注意：子类无法使用父类的泛型类型进行接受。
    public static <T extends Number> ClassInfo<T> fun(T param){
        ClassInfo<T> temp = new ClassInfo<T>() ;        // 根据传入的数据类型实例化Info
        temp.setVar(param) ;        // 将传递的内容设置到Info对象的var属性之中
        return temp ;    // 返回实例化对象
    }
}
