package newhome.baselibrary.Activity.MyViewTest;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import newhome.baselibrary.ImageHandle.CreateQRImage;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/10.
 */
//生成自定义的二维码
public class MyQRImage extends BaseActivity{
    Context mcontext;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myqrimage);
        findViewById();
        mcontext=this;
       // 二维码
        CreateQRImage myQRImage=new CreateQRImage("https://www.baidu.com",imageView);
        myQRImage.create();
        //一维码
//        CreateQRImageOne myQRImageOne=new CreateQRImageOne("123456",imageView);
//        myQRImageOne.create();
    }
    @Override
    public void findViewById() {
        imageView=(ImageView)findViewById(R.id.QRImage);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}
