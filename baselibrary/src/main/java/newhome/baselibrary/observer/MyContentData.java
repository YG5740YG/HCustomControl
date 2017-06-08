package newhome.baselibrary.observer;

import java.io.Serializable;

/**
 * Created by 20160330 on 2017/1/25.
 */

public class MyContentData implements Serializable{
    private String Data;
    boolean flage;
    public void setData(String address){
        this.Data = address;
    }
    public String getData(){
        return Data;
    }
    public void setflage(boolean flage){
        this.flage = flage;
    }
    public boolean getflage(){
        return flage;
    }
}
