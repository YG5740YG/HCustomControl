package MyTools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class CommonTools {
    Context mContext;
    Activity mActivity;
    public CommonTools(Context context) {
        mContext=context;
    }
    public CommonTools(Context context, Activity activity) {
        mContext=context;
        mActivity=activity;
    }
    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 常规权限获取，获取位置权限为例
     */
    public void getPermission(){
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_COARSE_LOCATION
                );
        //判断这个权限是否已经授权过
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            //判断是否需要 向用户解释，为什么要申请该权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(mContext, "Need bluetooth permission.",
                        Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(mActivity, new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION}, 10007);
                return;
            }
        }
    }
    /**
     * 存储数据
     */
    public void SetSharedPreference(String key,String Value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MySharePre", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,Value.trim());
        editor.commit();
    }
    /**
     * 存储数据
     */
    public String  getSharedPreference(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MySharePre", MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
