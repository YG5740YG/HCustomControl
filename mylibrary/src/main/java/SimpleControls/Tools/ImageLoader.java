package SimpleControls.Tools;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Administrator on 2017/11/24.
 */

public class ImageLoader {
    public static void load(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int errorResourceID){
        Glide.with(context)
                .load(url)
                .crossFade()
                .error(errorResourceID)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void load(Activity activity, String url, ImageView imageView){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()){
                Glide.with(activity)
                        .load(url)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        }
    }

    public static void load(Activity activity, String url, ImageView imageView, int errorResourceID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()){
                Glide.with(activity)
                        .load(url)
                        .crossFade()
                        .error(errorResourceID)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        }
    }

    public static void load(Fragment fragment, String url, ImageView imageView){
        if (!(fragment == null)){
            Glide.with(fragment)
                    .load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    public static void load(Fragment fragment, String url, ImageView imageView, int errorResourceID){
        if (!(fragment == null)){
            Glide.with(fragment)
                    .load(url)
                    .crossFade()
                    .error(errorResourceID)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

}
