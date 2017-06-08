package newhome.baselibrary.Single;

import android.util.Log;

import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/4/10.
 */
//能不使用单列，尽量不用
public enum  SingletonEnum {
    INSTANCE;
    private SingletonEnum(){}
    public String []getName(){
        final String []names=new String[3];
        names[0]="青椒";
        names[1]="白菜";
        names[2]="番茄";
        return names;
    }

}

