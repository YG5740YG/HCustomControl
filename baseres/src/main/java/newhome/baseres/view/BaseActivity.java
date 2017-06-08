package newhome.baseres.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 20160330 on 2017/1/16.
 */
//AppCompatActivity和Activity的区别

//首先是AppCompatActivity默认带标题
//        但Activity不带
//        而且AppCompatActivity和
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        有冲突
//        当活动继承AppCompatActivity时再设置取消标题则程序会直接挂掉
    /*
    在API22之前我们使用标题栏基本都是在ActionBarActivity的Activity中处理的，而API22之后，谷歌遗弃了ActionBarActivity，推荐我们也可以说是强制我们使用AppCompatActivity。
然而ActionBarActivity与AppCompatActivity的使用大同小异
     */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected Context context;
    public abstract void findViewById();
    public abstract void setUp();
    public abstract void refreshView();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        TextView title_back=(TextView)findViewById(R.id.title_back);
    }
    public static List<Activity> activities=new ArrayList<Activity>();
    public static void addActivity(Activity activity){//新建活动时添加到活动列表
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
        activity.finish();
    }//移除活动列表中的活动
    public static void finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }//移除活动列表中的所有活动
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.title_back:
//                this.finish();break;
//            case R.id.title_exit:
//                finishAll();
//                break;
            default:break;
        }
    }
}
