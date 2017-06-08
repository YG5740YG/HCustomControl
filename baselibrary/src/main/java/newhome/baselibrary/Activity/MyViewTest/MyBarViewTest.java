package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/4/14 0014.
 */

public class MyBarViewTest extends BaseActivity {
    Context mcontext;
    private TextView showText;
    private RatingBar rb;
    private ProgressBar pb3;
    private SeekBar sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybarview_test);
        findViewById();
        mcontext=this;
    }
    @Override
    public void findViewById() {
        showText=(TextView)findViewById(R.id.showText);
        sb=(SeekBar)findViewById(R.id.seekBar);
        rb=(RatingBar)findViewById(R.id.ratingBar);
        pb3=(ProgressBar)findViewById(R.id.progressBar3);
    }

    @Override
    public void setUp() {
        //为seekBar绑定事件
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                //改变showText字体大小
                showText.setTextSize(progress);
            }
        });

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(mcontext, "你选择了"+rating+"星", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //开始下载[注意:除了ProgressBar外，所有的UI都必须在UI主线程中操作]
    boolean isRun=true;
    public void doStart(View view) throws Exception{
        isRun=true;
        //构建一个执行进度条变化的子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时操作不能放在主线程中执行
                while(isRun){
                    if(pb3.getProgress()<100){
                        pb3.setProgress(pb3.getProgress()+1);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        isRun=false;
                        //最简单方法实现在多线程更新UI
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(mcontext, "下载完毕",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }
    //结束下载
    public void doStop(View view){
        isRun=false;
    }
    @Override
    public void refreshView() {

    }

//    　　1）除了ProgressBar外，所有的UI都必须在UI主线程中操作，否则会报错
//　　2）耗时操作不能放在主线程中执行，否则会报错
//        　　3）Google工程师让Android4.0以后的版本都不支持以上两点了，那么有人就要纠结了，那位了维护低版本，要怎么办~~自己想，很简单的问题！
}

