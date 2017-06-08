package MyView.Refresh;


import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by 20160330 on 2017/4/13 0013.
 */

public class CustomRefreshFooterView extends AppCompatTextView implements SwipeLoadMoreTrigger,SwipeTrigger {
    public CustomRefreshFooterView(Context context) {
        super(context);
    }

    public CustomRefreshFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRefreshFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onLoadMore() {
        setText("正在拼命加载数据...");
    }

    @Override
    public void onPrepare() {
        setText("释放刷新4");
    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        setText("释放刷新3");
    }

    @Override
    public void onRelease() {
        setText("释放刷新2");
    }

    @Override
    public void onComplete() {
        setText("刷新成功");
    }

    @Override
    public void onReset() {
        setText("释放刷新1");
    }
}
