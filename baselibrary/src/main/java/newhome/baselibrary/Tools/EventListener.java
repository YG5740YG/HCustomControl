package newhome.baselibrary.Tools;

import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by scorpio on 2016/10/8.
 */

public class EventListener implements View.OnClickListener {

    private Object handler;
    private String clickMethod;

    public EventListener(Object handler) {
        this.handler = handler;
    }

    public EventListener click(String method){
        this.clickMethod = method;
        return this;
    }

    @Override
    public void onClick(View view) {
        invokeClickMethod(handler,clickMethod,view);
    }

    private Object invokeClickMethod(Object handler, String methodName, Object params) {
        if(handler == null) return null;
        Method method = null;
        try{
            method = handler.getClass().getDeclaredMethod(methodName,View.class);
            if(method!=null)
                return method.invoke(handler, params);
            else
                throw new Exception("no such method:"+methodName);
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
