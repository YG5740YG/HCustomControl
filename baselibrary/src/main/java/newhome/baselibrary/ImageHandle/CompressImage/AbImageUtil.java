package newhome.baselibrary.ImageHandle.CompressImage;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述：图片处理类.
 *
 * @author zhaoqp
 * @version v1.0
 * @date 2011-12-10
 */
public class AbImageUtil {
    /**
     * 描述：获取原图
     *
     * @param file File对象
     * @return Bitmap 图片
     */
    public static Bitmap originalImg(File file) {
        Bitmap resizeBmp = null;
        try {
            resizeBmp = BitmapFactory.decodeFile(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizeBmp;
    }

    /**
     * 用于缩放相册，内容uri
     *
     * @param uri
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImg(Context context, Uri uri, int newWidth, int newHeight) {

        Bitmap resizeBmp = null;
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        try {
            InputStream stream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(stream, null, opts);
            if (stream != null)
                stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //inSampleSize=2表示图片宽高都为原来的二分之一，即图片为原来的四分之一
        //缩放可以将像素点打薄
        // 获取图片的原始宽度高度
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;

        int destWidth = srcWidth;
        int destHeight = srcHeight;

        // 缩放的比例
        float scale = 0;
        // 计算缩放比例
        float scaleWidth = (float) srcWidth / newWidth;
        float scaleHeight = (float) srcHeight / newHeight;
        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }
        if (scale != 0) {
            destWidth = (int) (destWidth / scale);
            destHeight = (int) (destHeight / scale);
        }

        //默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //以下两个字段需一起使用：
        //产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        //位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;

        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        if (scale > 1) {
            //缩小
            opts.inSampleSize = (int) scale;
        } else {
            //放大
            opts.inSampleSize = 1;
        }

        // 设置大小
        opts.outHeight = destHeight;
        opts.outWidth = destWidth;
        //创建内存
        opts.inJustDecodeBounds = false;
        //使图片不抖动
        opts.inDither = false;
        //if(D) Log.d(TAG, "将缩放图片:"+file.getPath());
        try {
            InputStream stream = context.getContentResolver().openInputStream(uri);
            resizeBmp = BitmapFactory.decodeStream(stream, null, opts);
            if (stream != null)
                stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getBitmapDegree(uri.getPath()) >0){
            resizeBmp =  rotateBitmapByDegree(resizeBmp,getBitmapDegree(uri.getPath()));
        }

        return resizeBmp;
    }
    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
    /**
     * 描述：缩放图片.压缩
     *
     * @param file      File对象
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap scaleImg(File file, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        //inSampleSize=2表示图片宽高都为原来的二分之一，即图片为原来的四分之一
        //缩放可以将像素点打薄
        // 获取图片的原始宽度高度
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;


        int destWidth = srcWidth;
        int destHeight = srcHeight;

        // 缩放的比例
        float scale = 0;
        // 计算缩放比例
        float scaleWidth = (float) srcWidth / newWidth;
        float scaleHeight = (float) srcHeight / newHeight;
        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }
        if (scale != 0) {
            destWidth = (int) (destWidth / scale);
            destHeight = (int) (destHeight / scale);
        }

        //默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        //以下两个字段需一起使用：
        //产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        //位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;

        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        if (scale > 1) {
            //缩小
            opts.inSampleSize = (int) scale;
        } else {
            //放大
            opts.inSampleSize = 1;
        }

        // 设置大小
        opts.outHeight = destHeight;
        opts.outWidth = destWidth;
        //创建内存
        opts.inJustDecodeBounds = false;
        //使图片不抖动
        opts.inDither = false;
        Log.d("mydebug", "将缩放图片:" + file.getPath() + "w:" + destWidth + " h:" + destHeight);
        resizeBmp = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
        //缩小或者放大
        if (resizeBmp != null && scale != 1) {
            resizeBmp = scaleImg(resizeBmp, scale);
        }
        //if(D) Log.d(TAG, "缩放图片结果:"+resizeBmp);
        return resizeBmp;
    }
    /**
     * 描述：缩放图片,不压缩的缩放.
     *
     * @param bitmap    the bitmap
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap scaleImg(Bitmap bitmap, int newWidth, int newHeight) {

        Bitmap resizeBmp = null;
        if (bitmap == null) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
        }
        // 获得图片的宽高
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();

        if (srcWidth <= 0 || srcHeight <= 0) {
            return null;
        }
        // 缩放的比例
        float scale = 0;
        // 计算缩放比例
        float scaleWidth = (float) newWidth / srcWidth;
        float scaleHeight = (float) newHeight / srcHeight;
        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }

        //缩小或者放大
        if (bitmap != null && scale != 1) {
            resizeBmp = scaleImg(bitmap, scale);
        }
        return resizeBmp;
    }
    /**
     * 描述：根据等比例缩放图片.
     *
     * @param bitmap the bitmap
     * @param scale  比例
     * @return Bitmap 新图片
     */
    public static Bitmap scaleImg(Bitmap bitmap, float scale) {
        Bitmap resizeBmp = null;
        try {
            //获取Bitmap资源的宽和高
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();
            //注意这个Matirx是android.graphics底下的那个
            Matrix mt = new Matrix();
            //设置缩放系数，分别为原来的0.8和0.8
            mt.postScale(scale, scale);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, mt, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }
    /**
     * 描述：裁剪图片.
     *
     * @param file      File对象
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap cutImg(File file, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        //inSampleSize=2表示图片宽高都为原来的二分之一，即图片为原来的四分之一
        //缩放可以将像素点打薄,裁剪前将图片缩放到目标图2倍大小
        int srcWidth = opts.outWidth;  // 获取图片的原始宽度
        int srcHeight = opts.outHeight;// 获取图片原始高度
        int destWidth = 0;
        int destHeight = 0;

        int cutSrcWidth = newWidth * 2;
        int cutSrcHeight = newHeight * 2;

        // 缩放的比例,为了大图的缩小到2倍被裁剪的大小在裁剪
        double ratio = 0.0;
        //任意一个不够长就不缩放
        if (srcWidth < cutSrcWidth || srcHeight < cutSrcHeight) {
            ratio = 0.0;
            destWidth = srcWidth;
            destHeight = srcHeight;
        } else if (srcWidth > cutSrcWidth) {
            ratio = (double) srcWidth / cutSrcWidth;
            destWidth = cutSrcWidth;
            destHeight = (int) (srcHeight / ratio);
        } else if (srcHeight > cutSrcHeight) {
            ratio = (double) srcHeight / cutSrcHeight;
            destHeight = cutSrcHeight;
            destWidth = (int) (srcWidth / ratio);
        }

        //默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        //以下两个字段需一起使用：
        //产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        //位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        if (ratio > 1) {
            opts.inSampleSize = (int) ratio;
        } else {
            opts.inSampleSize = 1;
        }
        // 设置大小
        opts.outHeight = destHeight;
        opts.outWidth = destWidth;
        //创建内存
        opts.inJustDecodeBounds = false;
        //使图片不抖动
        opts.inDither = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
        if (bitmap != null) {
            resizeBmp = cutImg(bitmap, newWidth, newHeight);
        }
        return resizeBmp;
    }
    /**
     * 描述：裁剪图片.
     *
     * @param bitmap    the bitmap
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap cutImg(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) {
            return null;
        }

        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
        }

        Bitmap resizeBmp = null;

        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            if (width <= 0 || height <= 0) {
                return null;
            }
            int offsetX = 0;
            int offsetY = 0;

            if (width > newWidth) {
                offsetX = (width - newWidth) / 2;
            } else {
                newWidth = width;
            }

            if (height > newHeight) {
                offsetY = (height - newHeight) / 2;
            } else {
                newHeight = height;
            }

            resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, newWidth, newHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }
}
