package newhome.baselibrary.ImageHandle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import newhome.baselibrary.R;
import newhome.baselibrary.Tools.Logs;
import newhome.baseres.view.BaseActivity;

/**
 * Created by 20160330 on 2017/2/10.
 */

public class OpenPhotoAlbum extends BaseActivity {
    private Button chooseFromAlbum;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PHOTOS = 3;
    private Button takePhoto;
    private ImageView picture;
    private Uri imageUri;
    Context mcontext;
    String path;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phomealbum);
        mcontext=this;
        findViewById();
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  创建File 对象，用于存储选择的照片
                File outputImage = new File(Environment.
                        getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //由所选取的图片的的保存路径，获取该路径的uri
                imageUri = Uri.fromFile(outputImage);
                Intent intent= new Intent("android.intent.action.GET_CONTENT");
                        intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //把所选取的图片存入imageUri中
                startActivityForResult(intent, PHOTOS); // 启动打开相册程序
            }
        });
    }
    //把从相册选择的图片进行操作，从相册activity返回前一个activity时启用onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logs.Debug("gg==========1-=="+requestCode);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //拍照完毕返回时，调用系统自带相片裁剪
                    intent.setDataAndType(imageUri, "image/*");
                    //相片file还是原来file
                    intent.putExtra("scale", true);
                    // 裁剪框的比例，1：1
                             intent.putExtra("aspectX", 1);
                             intent.putExtra("aspectY", 1);
                    // 裁剪后输出图片的尺寸大小
                            intent.putExtra("outputX", 250);
                            intent.putExtra("outputY", 250);
                    intent.putExtra("outputFormat", "JPEG");// 图片格式
                    intent.putExtra("noFaceDetection", true);// 取消人脸识别
                    intent.putExtra("return-data", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                    //参数传递CROP_PHOTO
                }
                break;
            case CROP_PHOTO://如果是裁剪返回
                if (resultCode == RESULT_OK) {

//                    Uri uri = data.getData();
//                    Log.e("uri", uri.toString());
                    try {
                /* 将Bitmap设定到ImageView */
                        Bitmap bitmap = BitmapFactory.decodeStream
                                (getContentResolver()
                                        .openInputStream(imageUri));
                        //通过相片的Uri,生成bitmap图片文件
                        picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTOS:
                Logs.Debug("gg==========3");
                if (data == null) {
                    return;
                }
                uri = data.getData();
                //此处为了解决android4.4 相册选择时的 选择最近图片 获取不到path问题
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){//4.4及以上
                    Logs.Debug("gg==========3==>4.4");
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String[] column = { MediaStore.Images.Media.DATA };
                    String sel = MediaStore.Images.Media._ID + "=?";
                    Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                            sel, new String[] { id }, null);
                    int columnIndex = cursor.getColumnIndex(column[0]);
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(columnIndex);
                    }
//                    try {
                /* 将Bitmap设定到ImageView */
//                        Bitmap bitmap = BitmapFactory.decodeStream
//                                (getContentResolver()
//                                        .openInputStream(Uri.parse(path)));
                        //通过相片的Uri,生成bitmap图片文件;
                        Glide.with(mcontext)
                                .load(path)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(picture);
//                        picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    Logs.Debug("gg==========="+path);
                    cursor.close();
                }else{
                    Logs.Debug("gg==========3==<4.4");
                    try {
                /* 将Bitmap设定到ImageView */
                        Bitmap bitmap = BitmapFactory.decodeStream
                                (getContentResolver()
                                        .openInputStream(imageUri));
                        //通过相片的Uri,生成bitmap图片文件
                        picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void findViewById() {
        chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        picture = (ImageView) findViewById(R.id.picture);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void refreshView() {

    }
}

/**
 *http://blog.csdn.net/lzq520210/article/details/50781033
 */
