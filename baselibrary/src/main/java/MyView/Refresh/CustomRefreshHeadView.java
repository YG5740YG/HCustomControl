package MyView.Refresh;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/4/13 0013.
 */
//swipeToLoadLayout提供了一套接口，刷新的头部自定义一个View实现SwipeTrigger和SwipeRefreshTrigger就行了，
// 刷新的尾部自定义一个View实现SwipeLoadMoreTrigger和SwipeTrigger，头部实现代码：
//    头部还分classic，above，blow，scale四种类型，还有自动刷新的效果
public class CustomRefreshHeadView extends AppCompatTextView implements SwipeRefreshTrigger, SwipeTrigger {
    public CustomRefreshHeadView(Context context) {
        super(context);
    }

    public CustomRefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {
        setText("正在拼命加载数据...");
        Logs.Debug("gg========onRefresh==1");
    }

    @Override
    public void onPrepare() {
        setText("释放刷新");
        Logs.Debug("gg========onPrepare==2");
    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        Logs.Debug("gg========onMove==3=="+i+"--"+b+"--"+b1);
    }

    @Override
    public void onRelease() {
        Logs.Debug("gg========onRelease==4==");
    }

    @Override
    public void onComplete() {
        setText("刷新成功");
        Logs.Debug("gg========onComplete==5==");
    }

    @Override
    public void onReset() {
        Logs.Debug("gg========onReset==6==");
    }
}
