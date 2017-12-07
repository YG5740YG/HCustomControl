package yg.customcontrol.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import SimpleControls.MScrollControl.DrawerAdapter;
import SimpleControls.MScrollControl.DrawerContentModel;
import SimpleControls.MScrollControl.SlideView;
import SimpleControls.MSpinner.MSpinner;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class TestActivity extends Activity implements SlideView.MoveListen, MSpinner.ClickItemlisten {
    LinearLayout mContent;
    RecyclerView mRecycleView;
    DrawerAdapter mDrawerAdapter;
    DrawerContentModel drawerContentModel;
    List<DrawerContentModel> drawerContentModelList=new ArrayList<>();
    SlideView mSlideView;
    MSpinner mSpinner;
    TextView mBlueTooth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slide_main);
        mContent=(LinearLayout)findViewById(R.id.content);
        mSpinner=(MSpinner)findViewById(R.id.spinner);
//        mBlueTooth=(TextView)findViewById(R.id.blue_tooth);
//        mBlueTooth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(this, BluetoothPrint.class);
//                startActivity(intent);
//            }
//        });
        initSpinner();
//        initDrawerData();
//        mSlideView=new SlideView(getApplicationContext(),200);
//        mSlideView.setMoveListen(this);
//        View viewSlideBottom= LayoutInflater.from(getApplicationContext()).inflate(R.layout.slide_bottom_item,null);
//        View viewSlideMiddle= LayoutInflater.from(getApplicationContext()).inflate(R.layout.slide_middle_item,null);
//        mSlideView.getBottomView().addView(viewSlideBottom);
//        mSlideView.getMiddleVIew().addView(viewSlideMiddle);
//        mRecycleView=(RecyclerView)viewSlideBottom.findViewById(R.id.recyclerview);
//        mRecycleView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
//        mDrawerAdapter=new DrawerAdapter(getApplicationContext(),drawerContentModelList,false);
//        mRecycleView.setAdapter(mDrawerAdapter);
//        mContent.addView(mSlideView.getSlideContent());
    }
    private void initSpinner(){
        final List<String>indexs=new ArrayList<>();
        indexs.add("数学");
        indexs.add("语文");
        indexs.add("英语");
        indexs.add("政治");
        List<Integer>lists=new ArrayList<>();
        lists.add(R.mipmap.e1);
        lists.add(R.mipmap.e2);
        lists.add(R.mipmap.e3);
        lists.add(R.mipmap.e4);
        int titleImage=R.mipmap.e5;
        mSpinner.setAdapter(indexs,0);
//        mSpinner.setAdapter(indexs,1);
//        mSpinner.setAdapter(indexs,2);
//        mSpinner.setAdapter(indexs,3);
//        mSpinner.setAdapter(indexs,2).setImageList(lists);
//        mSpinner.setAdapter(indexs,3).setImageList(lists).setTitleImage(titleImage);
        mSpinner.setClickListen(this);
    }
    private void initDrawerData(){
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
        drawerContentModel=new DrawerContentModel();
        drawerContentModel.setIcon(R.mipmap.ic_launcher);
        drawerContentModel.setContent("y1");
        drawerContentModelList.add(drawerContentModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSlideView.onDestroy();
    }

    @Override
    public void motionMoveListen(int nowMoveHeight) {
        mDrawerAdapter.refresh(nowMoveHeight);
    }

    @Override
    public void motionUpListen() {

    }

    @Override
    public void setClickItem(int index) {

    }

    @Override
    public void setNothingSelector() {

    }
}

