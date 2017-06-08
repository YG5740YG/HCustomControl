package newhome.baselibrary.Appwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import newhome.baselibrary.R;

/**
 * Created by 20160330 on 2017/4/6 0006.
 */
//要使用带有子布局的情况需要使用remoteviewsService和RemoteViewsFactory,所以我们需要新建一个类，让这个类继承RemoteviewsService
//    然后在里面实现方法，再在manifest文件里面添加注册
//    remoteviewsservice的任务都交给factory去完成，实现了一个内部类，让它实现RemoteViewsFactory的接口，然后重写里面的方法
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RemoteViewServicelmp extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteviewFactorylmp(this,intent);
    }
    public static ArrayList<String> data=new ArrayList<>();
    public static void loadData(){
        data.clear();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<6;i++) {
                    data.add("测试   "+i);//可以从网络中获取数据
                }
            }
        });
        thread.start();
        try {
            thread.join();//用法：作用是在线程执行完run以后再执行join后面的代码，这里使用的目的是做个同步操作，在data数据得到完成后执行后面的代码。
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //在factory里面绑定listview 的视图
    class RemoteviewFactorylmp implements RemoteViewsFactory{
        private Intent requestIntent;
        private Context requestContext;
        public RemoteviewFactorylmp(Context context,Intent intent){
            requestContext=context;
            requestIntent=intent;
        }
        @Override
        public void onCreate() {
            loadData();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews=new RemoteViews(requestContext.getPackageName(), R.layout.widgetlistvieitem_layout);//填充数据
            //listView的点击事件
            Intent intent=new Intent(ProcessWidgetReceiverOne.ITEMCLICK);
            intent.putExtra("position",position);
            remoteViews.setOnClickFillInIntent(R.id.widget_listview_item,intent);
            remoteViews.setTextViewText(R.id.widget_listview_item,data.get(position));
         return remoteViews;
//            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
//            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
//            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
