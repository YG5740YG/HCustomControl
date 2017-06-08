package newhome.baselibrary.ImageHandle.Image;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baselibrary.ImageHandle.CompressImage.BitmapTo;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/4/5 0005.
 */
//drawble转换为bitmap
public class MyImageTwo extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    ListView mlistView;
    ImageView imageView;
    int level=1;
    BitmapTo bitmapTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myimage);
        findViewById();
        mcontext=this;
        bitmapTo=new BitmapTo(mcontext);
//        Drawable drawable=mcontext.getDrawable(R.drawable.layer_list);//api大于21使用
//        imageView.setImageBitmap(bitmapTo.getDrawableToBitmap(drawable,0));
    }

    @Override
    public void findViewById() {
        imageView=(ImageView) findViewById(R.id.myImageTest);
    }
    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}



