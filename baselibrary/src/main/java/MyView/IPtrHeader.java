package MyView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2016/11/29.
 */

public class IPtrHeader extends FrameLayout implements PtrUIHandler {
    ImageView pull_to_refresh_image;
    View view;
    static int header_flage;
    Timer timer;
    /**
     * 状态识别
     */
    private int mState;

    /**
     * 重置
     * 准备刷新
     * 开始刷新
     * 结束刷新
     */
    public static final int STATE_RESET = -1;
    public static final int STATE_PREPARE = 0;
    public static final int STATE_BEGIN = 1;
    public static final int STATE_FINISH = 2;

    public static final int MARGIN_RIGHT = 100;

    private AnimationDrawable mAnimation;
    LinearLayout toolbar;


    public IPtrHeader(Context context, LinearLayout toolbar1) {
        super(context);
        toolbar=toolbar1;
        initView(context);
    }


    public IPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        view = inflate(context, R.layout.iptr_header, null);
        pull_to_refresh_image = (ImageView) view.findViewById(R.id.pull_to_refresh_image);

        timer=new Timer();
        addView(view);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        Logs.Debug("gg=====4");
        toolbar.setVisibility(VISIBLE);
        mState = STATE_RESET ;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        Logs.Debug("gg=====3");
        mState = STATE_PREPARE ;
        // 开启动画
        toolbar.setVisibility(GONE);
        pull_to_refresh_image.setBackgroundResource(R.drawable.refreshanim);
        mAnimation = (AnimationDrawable) pull_to_refresh_image.getBackground();
        if (!mAnimation.isRunning()){
            mAnimation.start();
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        Logs.Debug("gg=====2");
        mState = STATE_BEGIN;
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mState = STATE_FINISH;
        toolbar.setVisibility(VISIBLE);
        Logs.Debug("gg=====1");
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAnimation!=null) {
                    if (mAnimation.isRunning()) {
                        mAnimation.stop();
                    }
                }
            }
        },100);

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        Logs.Debug("gg=====5");
            timer.scheduleAtFixedRate(new myTask(),1,1000);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    pull_to_refresh_image.setImageResource(R.mipmap.resh1);
                    break;
                case 2:
                    pull_to_refresh_image.setImageResource(R.mipmap.resh2);
                    break;
                case 3:
                    pull_to_refresh_image.setImageResource(R.mipmap.resh3);
                    break;
                case 4:
                    pull_to_refresh_image.setImageResource(R.mipmap.resh4);
                    break;
                case 5:
                    pull_to_refresh_image.setImageResource(R.mipmap.resh5);
                    break;
                default:
                    pull_to_refresh_image.setImageResource(R.mipmap.resh1);
                    break;
            }
        }
    };
    private class myTask extends TimerTask {
        @Override
        public void run() {
            if(header_flage==-1){
                return;
            }
            if(header_flage>5){
                header_flage=0;
            }
            header_flage++;
            Message msg = new Message();
            msg.what = header_flage;
            mHandler.sendMessage(msg);
        }
    }
}
