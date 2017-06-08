package newhome.baselibrary.Tools;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by scorpio on 2016/10/8.
 */

public abstract class FinalActivity extends Activity{
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initInjectedView(this);
    }

    public static void initInjectedView(Activity activity) {
        initInjectedView(activity,activity.getWindow().getDecorView());
    }

    public static void initInjectedView(Object injectedSource, View decorView) {
        Field[] fields = injectedSource.getClass().getDeclaredFields();
        if (fields!=null && fields.length>0){
            for (Field field :fields){
                field.setAccessible(true);
                try {
                    if (field.get(injectedSource)!=null){
                        continue;
                    }
                    ViewInject viewInject = field.getAnnotation(ViewInject.class);
                    if (viewInject!=null){
                        int viewId = viewInject.id();
                        field.set(injectedSource,decorView.findViewById(viewId));
                        setListener(injectedSource,field,viewInject.click(), Method.Click);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setListener(Object injectedSource, Field field, String methodName, Method method) throws IllegalAccessException {
        if (methodName == null || methodName.trim().length() == 0){
            return;
        }
        Object obj = field.get(injectedSource);
        switch (method){
            case Click:
                if (obj instanceof  View){
                    ((View) obj).setOnClickListener(new EventListener(injectedSource).click(methodName));
                }
                break;
        }
    }

    public enum Method{
        Click
    }
}
