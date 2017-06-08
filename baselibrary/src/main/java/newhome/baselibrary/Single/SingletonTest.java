package newhome.baselibrary.Single;

import android.app.Activity;

import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SingletonTest extends Activity {
  public SingletonTest(){
      //调用方法
      Logs.Debug("测试你喜欢吃的食物:"+SingletonEnum.INSTANCE.getName()[0]+"");
    }
}
