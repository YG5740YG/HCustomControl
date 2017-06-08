package newhome.baselibrary.Activity.SwipeLayout;

import android.app.Activity;
import android.os.Bundle;

import com.daimajia.swipe.SwipeLayout;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
//http://hao.jobbole.com/androidswipelayout/
//    https://www.oschina.net/news/73836/15-android-general-popular-frameworks
public class SwipeLayoutActivity extends Activity {
    SwipeLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_swipe);
        findViewById();
        setUp();
    }
    public void findViewById() {
        swipeLayout=(SwipeLayout)findViewById(R.id.swipeLayout);
    }

    public void setUp() {
//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//set drag edge.
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                Logs.Debug("gg=========1");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
                Logs.Debug("gg=========2");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                Logs.Debug("gg=========3");
            }

            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
                Logs.Debug("gg=========4");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
                Logs.Debug("gg=========5");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
                Logs.Debug("gg=========6");
            }
        });
    }
    public void refreshView() {

    }
}
