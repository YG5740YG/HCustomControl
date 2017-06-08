package MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/2/6.
 */

public class ListViewDelete extends ListView {
    private static final String TAG = "ListViewDelete";
    private ListViewDeleteSlideView listViewDeleteSlideView;

    public ListViewDelete(Context context) {
        super(context);
    }

    public ListViewDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewDelete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);
        if (item != null) {
            try {
                ((ListViewDeleteSlideView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                int x=(int)event.getX();//获取view距离父容器的x距离
                int y=(int)event.getY();//获取view距离父容器的y距离
                Log.d("listView x==y:", x + ":" + y);
                int position=pointToPosition(x,y);//根据触摸点的xy坐标，计算出是点击的是哪个item
                Log.e(TAG, "postion=" + position);
                if(position!=INVALID_POSITION){//position不等于-1,手指按在了item上
                    Logs.Debug("postion=gg========="+position);
                    FruitInfoListMoel fruitInfoListMoel=(FruitInfoListMoel)getItemAtPosition(position);
                    listViewDeleteSlideView=fruitInfoListMoel.listViewDeleteSlideView;
                }
                break;
            }
            default:
                break;
        }
        if(listViewDeleteSlideView!=null){
            listViewDeleteSlideView.onRequireTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}
/**
 * 重写了onTouchEvent，当ACTION_DOWN时通过pointToPosition(x, y)找到当前touch的是哪一个item，然后通过data.slideView找到对应的 slideView，并且回调slideView的onRequireTouchEvent(event)方法，就把事件传给了每一个ItemView。这样就完成了一个类似QQ左滑动删除功能的ListView。

 里面有个比较难的地方就是Scroller的使用，它的每一次滚动都要不停地刷新，要重写computeScroll方法。这是我比较难掌握的地方，特意整理出来做个笔记，供自己参考使用，也给看到这篇文章的读者参考使用!
 以上如果不对的地方，还望大家多多指正，谢谢！
 附本篇博客源码http://download.csdn.net/detail/u014763302/9330909
 * */
