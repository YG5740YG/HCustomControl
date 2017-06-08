package newhome.baselibrary.ImageHandle.MyCamear;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by 20160330 on 2017/3/3.
 */

//自定义相机调用
    public class MyCameraTest extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    MySurfaceViewOne mySurface;
    boolean isClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurface = new MySurfaceViewOne(this);
        setContentView(mySurface);
        mySurface.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {//点击后虎报错，代码没写完
        // TODO Auto-generated method stub
        if (!isClicked) {
            mySurface.tackPicture();
            isClicked = true;
        } else {
            mySurface.voerTack();
            isClicked = false;
        }
    }
}
