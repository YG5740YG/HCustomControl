package newhome.baselibrary.MyHttpOld.JSON.Data;

/**
 * Created by 20160330 on 2017/3/31 0031.
 */
//泛型的使用
public class DataType<T> {
    private T data;
    public DataType(){

    }
    public DataType(T data){
        this.data=data;
    }
    public T getData(){
        return this.data;
    }
    public void setData(T data){
        this.data=data;
    }
}
