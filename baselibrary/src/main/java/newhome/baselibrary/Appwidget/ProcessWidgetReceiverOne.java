package newhome.baselibrary.Appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;

import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/4/6 0006.
 */

public class ProcessWidgetReceiverOne extends AppWidgetProvider {
    public static final String BTNACTION="com.xinxue.action.TYPE_BTN";
    public static final String ITEMCLICK="com.xinxue.action.TYPE_List";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetlds){
        super.onUpdate(context,appWidgetManager,appWidgetlds);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widgetlist_layout);
          //绑定service用来填充listview中的视图
               Intent intent=new Intent(context,RemoteViewServicelmp.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            remoteViews.setRemoteAdapter(R.id.widget_listview,intent);
        }
//      并增加item点击事件
        Intent intent1=new Intent(ITEMCLICK);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,1,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            remoteViews.setPendingIntentTemplate(R.id.widget_listview,pendingIntent);
        }
        //如果添加了多个实例，需要进行下面的处理
        for (int i=0;i<appWidgetlds.length;i++){
            appWidgetManager.updateAppWidget(appWidgetlds[i],remoteViews);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
                if(intent.getAction().equals(ITEMCLICK)){
                    Toast.makeText(context,intent.getIntExtra("position",0)+"",Toast.LENGTH_SHORT).show();
                }
    }
}

