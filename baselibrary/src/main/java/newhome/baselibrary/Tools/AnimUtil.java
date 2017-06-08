package newhome.baselibrary.Tools;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by scorpio on 2016/12/23.
 * 动画类
 */
public class AnimUtil {

    Context context ;
    public AnimUtil(Context context) {
        this.context = context ;
    }

    public static AnimUtil get(Context context){
        return new AnimUtil(context);
    }

    public  void alpha(View view) {
        Animation animation = new AlphaAnimation(0.3f,1f);
        animation.setDuration(800);
        view.startAnimation(animation);
    }


    /**
     *
     * @param view
     * @param zoom true 缩小  透明    false 扩大，不透明
     * @param Duration  动画开始到结束的时间
     * @param alphaValue  变化后的透明值（百分比）
     * @param zoomX  变化后宽度（百分比）
     * @param zoomY  变化后高度（百分比）
     * @param displacementX  变化后相对于宽度，偏移X（百分比）
     * @param displacementY  变化后相对于高度，偏移Y（百分比）
     */
    public static void animationUser(View view,boolean zoom,int Duration,float alphaValue,float zoomX,float zoomY,float displacementX,float displacementY,Boolean rotat,float rotatstartAngle,float rotatAngle){
        AnimationSet animationSet = new AnimationSet(true);
        if(zoom) {
            //透明
            AlphaAnimation alphaAnimation = new AlphaAnimation(1f, alphaValue);
            alphaAnimation.setDuration(Duration);
            animationSet.addAnimation(alphaAnimation);
            //宽高
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, zoomX, 1f, zoomY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(Duration);
            animationSet.addAnimation(scaleAnimation);
            //位移
            TranslateAnimation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,displacementX,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,displacementY);
            translateAnimation.setDuration(Duration);
            animationSet.addAnimation(translateAnimation);
            //旋转
            if(rotat) {
                RotateAnimation rotateAnimation = new RotateAnimation(rotatstartAngle, rotatAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(Duration);
                animationSet.addAnimation(rotateAnimation);
            }
        }else{
            AlphaAnimation alphaAnimation=new AlphaAnimation(alphaValue,1f);
            alphaAnimation.setDuration(Duration);
            animationSet.addAnimation(alphaAnimation);
            ScaleAnimation scaleAnimation=new ScaleAnimation(zoomX,1f,zoomY,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            scaleAnimation.setDuration(Duration);
            animationSet.addAnimation(scaleAnimation);
            TranslateAnimation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,displacementX,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,displacementY,Animation.RELATIVE_TO_SELF,0f);
            translateAnimation.setDuration(Duration);
            animationSet.addAnimation(translateAnimation);
            //旋转
            if(rotat) {
                RotateAnimation rotateAnimation = new RotateAnimation(rotatstartAngle,rotatAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(Duration);
                animationSet.addAnimation(rotateAnimation);
            }
        }
        animationSet.setFillAfter(true);
        view.startAnimation(animationSet);
    }
}
