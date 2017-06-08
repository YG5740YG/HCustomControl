package MyView.CustomView;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import newhome.baselibrary.R;


/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class CleanEditText extends AppCompatEditText {

    private final Context mContext;
    private Drawable mDeleteDrawable;

    public CleanEditText(Context context) {
        this(context, null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        // 设置右侧删除图标
        mDeleteDrawable = getResources().getDrawable(R.mipmap.gray_right_arrow);
        // 添加监听
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                setDeleteDrawable();
            }
        });
        setDeleteDrawable();
    }

    private void setDeleteDrawable() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, length() > 0 ? mDeleteDrawable : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(mDeleteDrawable != null){
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                Rect rect = new Rect();
                getGlobalVisibleRect(rect);
                rect.left = rect.right - 50;
                if(rect.contains(rawX, rawY)) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
