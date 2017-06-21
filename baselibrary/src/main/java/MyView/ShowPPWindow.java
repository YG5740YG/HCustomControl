package MyView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import newhome.baselibrary.R;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ShowPPWindow {
    PopupWindow mPopupBackground;
    public ShowPPWindow (Context context,View v) {
        final View bgView = View.inflate(context, R.layout.layout_switchbutton, null);
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (mPopupBackground == null) {
            mPopupBackground = new PopupWindow(bgView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mPopupBackground.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }
}
