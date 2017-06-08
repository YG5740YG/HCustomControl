package newhome.baselibrary.ImageHandle.MyPhoneAbl;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import newhome.baselibrary.Adapter.FruitListViewAdapter;
import newhome.baselibrary.Model.FruitInfoListMoel;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/3/3.
 */

public class MyPhotoList extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Context mcontext;
    List<FruitInfoListMoel> fruitInfoList;
    FruitListViewAdapter fruitListViewAdapter;
    GridView mgridView;
    int count=0;

    private Bitmap bitmap = null;
    private byte[] mContent = null;
    private ListView listView = null;
    private SimpleCursorAdapter simpleCursorAdapter = null;
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_TAKEN

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        findViewById();
        mcontext=this;
//        initFruits();
        findViewById();
//        fruitListViewAdapter=new FruitListViewAdapter(mcontext,fruitInfoList);
//        mgridView.setAdapter(fruitListViewAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            simpleCursorAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.fruitlistitem_adapter,
                    null,
                    STORE_IMAGES,
                    new int[] { R.id.fruitImage, R.id.fruitName},
                    0
            );
        }
        simpleCursorAdapter.setViewBinder(new ImageLocationBinder());
        mgridView.setAdapter(simpleCursorAdapter);
        // 注意此处是getSupportLoaderManager()，而不是getLoaderManager()方法。
        getSupportLoaderManager().initLoader(0, null, this);
        setGridView_limit(mgridView);
//        　如果是在Android3.0中则需要通过如下代码进行初始化。
//        getLoaderManager().initLoader(0, null, this);
        // 单击显示图片
//        mgridView.setOnItemClickListener(new MyDevicePhotoActivity.ShowItemImageOnClickListener());
    }
    //动态设置gridview的属性
    private void setGridView_limit(GridView gv) {
        int size = count;
        int row=count/2;//把列表中的数据分为三行显示
        int width=mcontext.getResources().getDisplayMetrics().widthPixels;//屏幕的宽度
        int callwidth=width/2;//设置每列的宽度
        int rowwidth=  (count/2) * (width/3);//设置每行的长度，屏幕宽度的三分之一乘以一半的列表数据量
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                (fruitInfoList.size()/2) * (width/3), LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
        gv.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gv.setColumnWidth(callwidth-50); // 设置列表项宽
        gv.setNumColumns(2); // 设置列数量,总数量分为三列
//        gv.setNumColumns(size); // 设置列数量=列表集合数
    }
    // 将图片的位置绑定到视图
    private class ImageLocationBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int arg2) {
            // TODO Auto-generated method stub
            /*
            arg2:0   返回Screenshot_2016-05-24-11-21-48.jpeg==_display_name==[Ljava.lang.String;@427545c8=====
            [B@4275b490==5==532=====0.0==Bundle[EMPTY_PARCEL]==0=====0
              arg2:1   null==latitude==[Ljava.lang.String;@427545c8=====null==5==532=====0.0==Bundle[EMPTY_PARCEL]==0=====0
              arg2:2  null==longitude==[Ljava.lang.String;@427545c8=====null==5==532=====0.0==Bundle[EMPTY_PARCEL]==0=====0
              cursor.getPosition()表示是第几个
             */
            Logs.Debug("gg==========arg2=="+arg2+"====="+cursor.getString(arg2)+"=="+cursor.getColumnName(arg2)+"=="+cursor.getColumnNames()
//                    +"====="+cursor.getBlob(arg2)+"=="+cursor.getColumnCount()+"=="+cursor.getCount()
                            +"====="+cursor.getDouble(arg2)+"=="+cursor.getExtras()+"=="+cursor.getShort(arg2)
                            +"====="+cursor.getPosition()+"==view=="+view.getId()+"==bundel"+"=="+cursor.getExtras().keySet().size()
            );
            Bundle bundle=cursor.getExtras();
            if(count==0) {
                count = cursor.getCount();
            }
            if(arg2==0){
                Drawable drawable = ContextCompat.getDrawable(mcontext,R.drawable.check_box);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                ((ImageView)view).setImageDrawable(drawable);
                return true;
            }else if(arg2==1){
                ((TextView)view).setText(cursor.getString(0)+cursor.getPosition());
                return true;
            }
                return false;
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,//cha zhao tu pian
                null,
                null,
                null);
        return cursorLoader;
//        return null;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // 使用swapCursor()方法，以使旧的游标不被关闭．
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            simpleCursorAdapter.swapCursor(data);
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            simpleCursorAdapter.swapCursor(null);
        }
    }
    @Override
    public void refreshView() {

    }
    @Override
    public void findViewById() {
        mgridView =(GridView)findViewById(R.id.gv);
    }
    @Override
    public void setUp() {

    }
}
