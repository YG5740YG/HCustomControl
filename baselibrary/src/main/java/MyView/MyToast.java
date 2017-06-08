package MyView;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import newhome.baselibrary.R;


/**
 * Created by 20160330 on 2017/2/9.
 */

//自定义Toast
public class MyToast {
    /**
     * 弹出默认Toast
     * @param context 需要弹出的context
     * @param mytoasttitle  弹出提示的标题
     * @param Duration  弹出显示的时间
     */
    //自定义Toast
    public MyToast(Context context, String mytoasttitle, int Duration){
        Toast toast=new Toast(context);
        Toast.makeText(context,mytoasttitle,Duration).show();
    }
    /**
     * 弹出自定义Toast
     * @param activity 需要弹出的activity
     * @param mytoasttitle  弹出提示的标题
     * @param Duration  弹出显示的时间
     */
    public MyToast(Activity activity, String mytoasttitle, int Duration){
        Toast toast=new Toast(activity);
        LayoutInflater inflater =activity.getLayoutInflater();
        View Layout = inflater.inflate(R.layout.mytoastlayout,null);
        ImageView image = (ImageView) Layout
                .findViewById(R.id.toast_image);
        image.setImageResource(R.mipmap.chat_bq1);
        TextView title = (TextView) Layout.findViewById(R.id.toast_text);
        title.setText(mytoasttitle);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER ,0, 0);
        toast.setDuration(Duration);
        toast.setView(Layout);
        toast.show();
    }
    public MyToast(Activity activity, String mytoasttitle, int Duration,int gravity){
        Toast toast=new Toast(activity);
        LayoutInflater inflater =activity.getLayoutInflater();
        View Layout = inflater.inflate(R.layout.mytoastlayout,null);
        ImageView image = (ImageView) Layout
                .findViewById(R.id.toast_image);
        image.setImageResource(R.mipmap.chat_bq1);
        TextView title = (TextView) Layout.findViewById(R.id.toast_text);
        title.setText(mytoasttitle);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(gravity ,0, 0);
        toast.setDuration(Duration);
        toast.setView(Layout);
        toast.show();
    }
    /**
     * 弹出自定义Toast
     * @param activity 需要弹出的activity
     * @param mytoasttitle  弹出提示的标题
     * @param Duration  弹出显示的时间
     * @param layout  需要弹出的自定义布局
     * @param toastImageId  需要弹出的自定义布局中主要展示的图片的Image控件ID
     * @param toastImage  需要弹出的自定义布局中主要展示的图片的Image
     * @param toastTextId  需要弹出的自定义布局中主要展示的文本的toastTextId
     * @param gravity  需要弹出的自定义布局在activity的位置 gravity，Gravity
     */
    public MyToast(Activity activity, String mytoasttitle, int Duration,int layout,int toastImageId,int toastImage,int toastTextId,int gravity){
        Toast toast=new Toast(activity);
        LayoutInflater inflater =activity.getLayoutInflater();
        View Layout = inflater.inflate(layout,null);
        ImageView image = (ImageView) Layout
                .findViewById(toastImageId);
        image.setImageResource(toastImage);
        TextView title = (TextView) Layout.findViewById(toastTextId);
        title.setText(mytoasttitle);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(gravity,0, 0);
        toast.setDuration(Duration);
        toast.setView(Layout);
        toast.show();
    }
}
