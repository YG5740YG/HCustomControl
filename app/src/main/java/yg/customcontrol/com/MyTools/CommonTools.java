package yg.customcontrol.com.MyTools;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class CommonTools {
    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
