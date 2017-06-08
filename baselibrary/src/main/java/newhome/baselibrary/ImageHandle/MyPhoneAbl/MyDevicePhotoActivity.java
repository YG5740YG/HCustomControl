package newhome.baselibrary.ImageHandle.MyPhoneAbl;

import android.app.Dialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by 20160330 on 2017/3/3.
 */

public class MyDevicePhotoActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_folder);
        listView = (ListView)findViewById(R.id.folder_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            simpleCursorAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.listitem_folder,
                    null,
                    STORE_IMAGES,
                    new int[] { R.id.folder_name, R.id.folder_info,R.id.folder_img},
                    0
            );
        }
        simpleCursorAdapter.setViewBinder(new ImageLocationBinder());
        listView.setAdapter(simpleCursorAdapter);
        // 注意此处是getSupportLoaderManager()，而不是getLoaderManager()方法。
        getSupportLoaderManager().initLoader(0, null, this);
//        　如果是在Android3.0中则需要通过如下代码进行初始化。
//        getLoaderManager().initLoader(0, null, this);
        // 单击显示图片
        listView.setOnItemClickListener(new ShowItemImageOnClickListener());
    }

/*
　我们在上面的代码中可以使用回调方法onCreateLoader()来创建一个新的加载器。
LoaderManager.LoaderCallbacks：是一个用于客户端与LoaderManager交互的回调接口。
LoaderManager：是一个抽像类，并关联到一个Activity或Fragment，管理一个或多个装载器的实例。
这能够帮助一个应用管理那些与Activity或Fragment的生命周期相关的运行长时间的的操作。
最常见的方式是它与一个CursorLoader一起使用，然而应用是可以自由的写它们自己的装载器以加载其它类型的数据。
　　Loader：是一个执行异步数据加载的抽象类。它是加载器的基类。
我们可以使用典型的CursorLoader，但是我们也可以实现自己的子类。如果加载器被激活，它们将监视它们的数据源并且当数据改变时发送新的结果。
　　AsyncTaskLoader：是一个抽象类，能够提供一个AsyncTask来执行异步工作。
　　CursorLoader：它是AsyncTaskLoader的子类，它可以查询ContentResolver然后返回一个Cursor。
该类实现了加载器的协议并能够以标准的方式查询cursors.以AsyncTaskLoader为基础，在后台线程中执行cursor查询以便不阻塞程序的UI。
使用这个加载器是从ContentProvider中异步查询数据的最好的方式，而不是使用Activity或Fragment的API去执行一个被管理的查询。
　一个使用加载器的应用一般的会包括以下：
　　1. An Activity or Fragment.
　　2. An instance of the LoaderManager.
　　3. 一个被ContentProvider所支持的加载器，该加载器用以加载数据。当然了，我们也可以继承Loader或AsyncTaskLoader以实现自己的子类，去加载其他数据源的数据。
　　4. 实现LoaderManager.LoaderCallbacks接口，我们可以创建新的装载器以及管理已有的加载器的引用。
　　5. 一种展现加载器数据的方式 ，比如可以使用SimpleCursorAdapter。
　　6. 一个数据源，比如ContentProvider。
　　LoaderManager用以管理一个Activiry或Fragment中的一个或多个装载器．但每个Activity或Fragment只能一个LoaderManager．
 */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub
        // 为了查看信息，需要用到CursorLoader。
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,//cha zhao tu pian
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            simpleCursorAdapter.swapCursor(null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        // TODO Auto-generated method stub
        // 使用swapCursor()方法，以使旧的游标不被关闭．
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            simpleCursorAdapter.swapCursor(cursor);
        }
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
                    +"====="+cursor.getPosition()+"=="+cursor.getLong(arg2)
            );
            if (arg2 == 1) {
                // 图片经度和纬度
                double latitude = cursor.getDouble(arg2);
                double longitude = cursor.getDouble(arg2 + 1);
                if (latitude == 0.0 && longitude == 0.0) {
                    ((TextView)view).setText("位置：未知");
                } else {
                    ((TextView)view).setText("位置：" + latitude + ", " + longitude);
                }
                // 需要注意：在使用ViewBinder绑定数据时，必须返回真；否则，SimpleCursorAdapter将会用自己的方式绑定数据。
                return true;
            } else {
                return false;
            }
        }
    }
    // 单击项显示图片事件监听器
    private class ShowItemImageOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            final Dialog dialog = new Dialog(MyDevicePhotoActivity.this);
            // 以对话框形式显示图片
            dialog.setContentView(R.layout.image_show);
            dialog.setTitle("图片显示");
            ImageView ivImageShow = (ImageView) dialog.findViewById(R.id.ivImageShow);
            TextView btnClose = (TextView) dialog.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    // 释放资源
                    if(bitmap != null){
                        bitmap.recycle();
                    }
                }
            });
            Logs.Debug("gg============Path=="+id+"==position"+position);
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().
                    appendPath(Long.toString(id)).build();
            FileUtil file = new FileUtil();
            ContentResolver resolver = getContentResolver();
            // 从Uri中读取图片资源
            try {
                mContent = readInputStream(resolver.openInputStream(Uri.parse(uri.toString())));
                bitmap = getBitmapFromBytes(mContent, null);
                ivImageShow.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(bitmap != null){
            bitmap.recycle();
        }
    }
//我通过Uri的形式查询到图片，代码如下：
    /**
     * InputStream to byte
     * @param inStream
     * @return
     * @throws Exception
     */
    public byte[] readInputStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }
    /**
     * Byte to bitmap
     * @param bytes
     * @param opts
     * @return
     */
    public Bitmap getBitmapFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null){
            if (opts != null){
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
            }
            else{
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }
        return null;
    }
}
