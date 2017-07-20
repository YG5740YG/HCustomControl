package newhome.baselibrary.Animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class AnimatorTestActivity {
//    createCircularReveal 方法创建了一个 Animator 。首先，指定要显
//示或隐藏的 View ，然后是动画的中心位置、起始半径和结束半径。
    public void AnimationOne(final View mShowAnswer){
        int cx = mShowAnswer.getWidth() / 2;
        int cy = mShowAnswer.getHeight() / 2;
        float radius = mShowAnswer.getWidth();
        Animator anim = null;
//        Build.VERSION.SDK_INT 常量代表了Android设备的版本号。可将该常量同代表Lollipop版本
//        的常量进行比较。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils
                    .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        }else{
            mShowAnswer.setVisibility(View.INVISIBLE);
        }
    }
}
