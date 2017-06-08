package newhome.baselibrary.ImageHandle.ImageUse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class TestUse {
    public TestUse(){

    }
    //压缩图片
    public void compressImage_two(final Context context, final ImageData imagedata, final DataResponse dr) {
        if (imagedata != null) {
            String time1 = new Date().getTime() + ".jpg";
            final String btm = imagedata.compress(context, 480, 800, time1);
            Logs.Debug("gg==========btm=====" + btm);
            String  filecompresspath = FileUtil.appSavePathFile(time1);
            dr.onSucc(filecompresspath + "," + btm);
        }
    }
    Bitmap bitmap;
    Context context;
    //判断图片是否被旋转，并把图片旋转回去
    public Uri readPictureDegree(String path, Uri mUri) {
        int degree  = 0;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Logs.Debug("gg====orientation=="+orientation+"="+ExifInterface.ORIENTATION_ROTATE_90+"="+ExifInterface.ORIENTATION_ROTATE_180);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Uri imgUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), resizedBitmap, null,null));
        return imgUri;
    }

}
