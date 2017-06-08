package newhome.baselibrary.ImageHandle.Image;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Broadcast.MyBroadcastReceiver;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/4/5 0005.
 */

public class MyImageOne extends BaseActivity {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    ListView mlistView;
    ImageView imageView;
    int level=1;
    private LocalBroadcastManager localBroadcastManager;
    private MyBroadcastReceiver myBroadcastReceiver;
    public static final String BRAODCASTACTION="com.xinxue.LOCALBROADCAST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myimage);
        findViewById();
        mcontext=this;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.getDrawable().setLevel(level=level>=3?1:++level);
//                localBroadcastManager.sendBroadcast(new Intent(BRAODCASTACTION)); //  发送本地广播
            }
        });
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


