package MyView.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import newhome.baselibrary.R;


/**
 * Created by Administrator on 2017/4/21 0021.
 */

public class ScoreActivity extends Activity{
    private LinearLayout scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editview);
//        scoreView = (LinearLayout) findViewById(R.id.score_View);
//        scoreView.addView(new ScoreView(this,120));
//            scoreView.addView(new PaintCanvas(this));
//        scoreView.addView(new PaintCanvaTwo(this));
//        scoreView.addView(new PaintCanvasThree(this));
//        scoreView.addView(new PaintCanvasFour(this));
//        scoreView.addView(new PaintCanvasFive(this));
//        scoreView.addView(new PaintCanvaSix(this));
//        scoreView.addView(new PaintCanvasSeven(this));
//        scoreView.addView(new PaintCanvasKeyBoard(this));//自定义键盘
//        scoreView.addView(new PaintCanvasKeyBoard(this));//自定义键盘
    }
}
