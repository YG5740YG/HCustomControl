package SimpleControls.horizontalImage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.List;

import yg.customcontrol.com.mylibrary.R;

public class HorizontalImage extends LinearLayout {

    private RecyclerView recyclerView;
    public HorizontalImageAdapter adapter;

    public HorizontalImage(Context context) {
        this(context, null);
    }

    public HorizontalImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_horizontal_image, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.horizontal_recycle_view);
        adapter = new HorizontalImageAdapter(context);
        recyclerView.setAdapter(adapter);
    }

    public void setUrl(List<String> urlList){
        adapter.setData(urlList);
    }

    public void setUrl(String url){
        adapter.setData(url);
    }

}
