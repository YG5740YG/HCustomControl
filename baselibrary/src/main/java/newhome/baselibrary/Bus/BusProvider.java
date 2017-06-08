package newhome.baselibrary.Bus;

import com.squareup.otto.Bus;

/**
 * Created by 20160330 on 2017/2/28.
 */

public final class BusProvider {
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
