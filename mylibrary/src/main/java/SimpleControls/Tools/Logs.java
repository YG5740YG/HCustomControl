package SimpleControls.Tools;

/**
 * Created by Administrator on 2017/11/27.
 */

public class Logs {

    public static String Tag = "Debug";
    public static boolean isDebug = true;

    public static String getTag() {
        return Tag;
    }

    public static void setTag(String tag) {
        Tag = tag;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        Logs.isDebug = isDebug;
    }

    public static void Debug(String str) {
        if (isDebug) {
            android.util.Log.d(Tag, str);
        }
    }

    public static void Error(String str) {
        if (isDebug) {
            android.util.Log.e(Tag, str);
        }
    }
}
