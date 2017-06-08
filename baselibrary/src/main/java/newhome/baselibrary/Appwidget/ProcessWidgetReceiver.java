package newhome.baselibrary.Appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/4/5 0005.
 */
/*
1、写一个广播接收类，继承AppWidgetProvider
2、写一个布局文件，文件包含小控件，如：button,textView,imageView,imageButton,listView,gridview,
注意：自定义view,recycleview是不支持的
3、在AndroidManifest里面注册广播接收类
4、写一个appwidget-provider文件，在xml文件夹下面，查看xml.xml
 */
public class ProcessWidgetReceiver extends AppWidgetProvider {
    public static final String BTNACTION="com.xinxue.action.TYPE_BTN";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetlds){
        super.onUpdate(context,appWidgetManager,appWidgetlds);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        //创建一个广播，点击按钮，发送该广播
        Intent intent=new Intent(BTNACTION);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget,pendingIntent);
        //如果添加了多个实例，需要进行下面的处理
        for (int i=0;i<appWidgetlds.length;i++){
            appWidgetManager.updateAppWidget(appWidgetlds[i],remoteViews);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
      switch (intent.getAction()){
          case BTNACTION:
              Toast.makeText(context,"点到我了",Toast.LENGTH_SHORT).show();
      }
    }
}
