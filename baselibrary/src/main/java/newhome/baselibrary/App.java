package newhome.baselibrary;

import android.app.Application;
import android.widget.Toast;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by scorpio on 2016/11/8.
 */

public class App {
    public static final Application INSTANCE;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (final Exception e) {
            Logs.Debug("Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Logs.Debug("Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            INSTANCE = app;
        }
    }

    public static void toast(String msg) {
        Toast.makeText(INSTANCE, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int msgId) {
        Toast.makeText(INSTANCE, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String msg) {
        Toast.makeText(INSTANCE, msg, Toast.LENGTH_LONG).show();
    }
}
