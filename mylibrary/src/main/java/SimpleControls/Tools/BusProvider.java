package SimpleControls.Tools;

import com.squareup.otto.Bus;

/**
 * Created by Administrator on 2017/11/27.
 */

public class BusProvider {
    private static Bus BUS = null;

    public BusProvider() {
    }

    public static Bus getInstance() {
        if(BUS == null) {
            BUS = new Bus();
        }

        return BUS;
    }
}
