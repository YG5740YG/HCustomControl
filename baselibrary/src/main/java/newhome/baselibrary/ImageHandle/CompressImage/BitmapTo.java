package newhome.baselibrary.ImageHandle.CompressImage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.ImageHandle.CompressImage.AbImageUtil;
import newhome.baselibrary.Model.SplitImageData;
import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.R;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 20160330 on 2017/3/3.
 */

public class BitmapTo {
    static Context context;
    static FileUtil fileUtil = new FileUtil();
    static Bitmap bitmap;
    public BitmapTo(Context context) {
        this.context = context;
        fileUtil.init("myhome");
    }
    /**
     * Bitmap
     */
    public Bitmap getDrawToBitmap(int draw) {
        Resources res = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, draw);
        return bmp;
    }

    public Drawable getBitmapToDraw(Bitmap bm) {
        Resources res = context.getResources();
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
        return bd;
    }

    //    Drawable缩放
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = getDrawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newbmp);
    }
    public byte[] getBitmapToByte(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    public Bitmap getByteToBitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    //缩放
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
    //    将Drawable转化为Bitmap
    public static Bitmap getDrawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
    //将Drawable转换为Bitmap
    public static Bitmap getDrawableToBitmap(Drawable drawable,int i) {
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),drawable.getOpacity()!=
                PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565);
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //    获得圆角图片
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    //    获得带倒影的图片
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
    /**
     * Bitmap对象转换Drawable对象.
     *
     * @param bitmap 要转化的Bitmap对象
     * @return Drawable 转化完成的Drawable对象
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable mBitmapDrawable = null;
        try {
            if (bitmap == null) {
                return null;
            }
            mBitmapDrawable = new BitmapDrawable(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }
    /**
     * Bitmap对象转换TransitionDrawable对象.
     *
     * @param bitmap 要转化的Bitmap对象
     *               imageView.setImageDrawable(td);
     *               td.startTransition(200);
     * @return Drawable 转化完成的Drawable对象
     */
    public static TransitionDrawable bitmapToTransitionDrawable(Bitmap bitmap, Context context) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            if (bitmap == null) {
                return null;
            }
            mBitmapDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(context.getResources().getColor(android.R.color.transparent)), new BitmapDrawable(bitmap)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }
    /**
     * Drawable对象转换TransitionDrawable对象.
     *
     * @param drawable 要转化的Drawable对象
     *                 imageView.setImageDrawable(td);
     *                 td.startTransition(200);
     * @return Drawable 转化完成的Drawable对象
     */
    public static TransitionDrawable drawableToTransitionDrawable(Drawable drawable, Context context) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            if (drawable == null) {
                return null;
            }
            mBitmapDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(context.getResources().getColor(android.R.color.transparent)), drawable});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    /**
     * 把bitmap数据存入本地手机文件夹
     * @param bitmap  需要存入的bitmap
     */
    public static void saveBitmap(Bitmap bitmap, String fileName) {
        Log.e("gg==", "保存图片");
        String filePath=fileUtil.appSavePathFile(fileName);//生成文件并返回文件完整路径
        File f = new File(filePath);//生成file类实例
//        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.i("gg====", "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 把bitmap数据存入本地手机文件夹
     * @param bitmap  需要存入的bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        Log.e("gg==", "保存图片");
        String fileName=fileUtil.randomPath();//生成文件名，.jpg格式
        String filePath=fileUtil.appSavePathFile(fileName);//生成文件并返回文件完整路径
        File f = new File(filePath);//生成file类实例
//        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i("gg====", "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取分享的 bitmap,网络图片链接，转换成bitmap
     */
    static class GetBitmapTask implements Runnable {
        private String uri;
        private DataResponse dr;
        public GetBitmapTask(String url, DataResponse dr) {
            this.uri = url;
            this.dr = dr;
        }
        @Override
        public void run() {
            try {
                URL url = new URL(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                dr.onSucc(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                dr.onFail(e.toString());
            }
        }
    }

    //使用方法,通过网络链接，获取bitmap
    public static Bitmap getBitmap(String internetUrl){
        new Thread(new GetBitmapTask(internetUrl, new DataResponse() {
            @Override
            public void onSucc(Object o) {
                bitmap = (Bitmap) o;
            }
            @Override
            public void onFail(String s) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
            }
        })).start();
        return bitmap;
    }
    //把本地的资源转换成bitmap,R.mipmap.logo
    public Bitmap ResourceToBitmap(int resorce){
        return BitmapFactory.decodeResource(context.getResources(), resorce);
    }
    //把bitmap进行压缩，系统压缩
    public static byte[] bmpToByteArray(Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);//JPEG质量最小
        byte[] b = output.toByteArray();//获取压缩后文件大小
        double mid = b.length/1024;
            bmp=BitmapFactory.decodeByteArray(b, 0, b.length);
        Logs.Debug("gg===========bmp=="+mid);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    //把bitmap进行压缩，系统压缩
    public static byte[] bmpToByteArray(Bitmap bmp,Bitmap.CompressFormat compressFormat, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(compressFormat, 100, output);//JPEG质量最小
        byte[] b = output.toByteArray();//获取压缩后文件大小
        double mid = b.length/1024;
        bmp=BitmapFactory.decodeByteArray(b, 0, b.length);
        Logs.Debug("gg===========bmp=="+mid);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    //获取bitmap的大小,kb
    public int BitmapSize(Bitmap bitmap, Bitmap.CompressFormat mCompressFormat){
        int size = 0;
        ByteArrayOutputStream output = null;
        try {
            output = new ByteArrayOutputStream();
            bitmap.compress(mCompressFormat, 100, output);
            byte[] result = output.toByteArray();
            size = result.length/1024;//把字节转换成kb
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }
    //把压缩后的字节，转换成bitmap
    public Bitmap ByteToBitmap(byte[] bytes){
        if(bytes.length!=0){
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else {
            return null;
        }
    }
    //bitmap保存为file
    public static void bitmapToFile(Bitmap bm, String mFile){
          Logs.Debug("保存图片");
        String picName="hellow";
//            File f = new File("/sdcard/namecard/", picName);
            File f = new File(mFile);
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                Logs.Debug("已经保存");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    /**
     * 将ImageView转换为Bitmap.
     *
     * @param view 要转换为bitmap的View
     * @return byte[] 图片的byte[]
     */
    public static Bitmap imageView2Bitmap(ImageView view) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * 将View转换为Drawable.需要最上层布局为Linearlayout
     *
     * @param view 要转换为Drawable的View
     * @return BitmapDrawable Drawable
     */
    public static Drawable view2Drawable(View view) {
        BitmapDrawable mBitmapDrawable = null;
        try {
            Bitmap newbmp = view2Bitmap(view);
            if (newbmp != null) {
                mBitmapDrawable = new BitmapDrawable(newbmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    /**
     * 将View转换为Bitmap.需要最上层布局为Linearlayout
     *
     * @param view 要转换为bitmap的View
     * @return byte[] 图片的byte[]
     */
    public static Bitmap view2Bitmap(View view) {
        Bitmap bitmap = null;
        try {
            if (view != null) {
                view.setDrawingCacheEnabled(true);
                view.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                view.buildDrawingCache();
                bitmap = view.getDrawingCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * 将View转换为byte[].
     *
     * @param view           要转换为byte[]的View
     * @param compressFormat the compress format
     * @return byte[] View图片的byte[]
     */
    public static byte[] view2Bytes(View view, Bitmap.CompressFormat compressFormat) {
        byte[] b = null;
        try {
            Bitmap bitmap = view2Bitmap(view);
            b = bmpToByteArray(bitmap, true);
            compressFormat= Bitmap.CompressFormat.JPEG;
            b=bmpToByteArray(bitmap,compressFormat,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     * @param imagePath
     * 图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }
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
     * 释放Bitmap对象.
     *
     * @param bitmap 要释放的Bitmap
     */
    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            } catch (Exception e) {
            }
            bitmap = null;
        }
    }

    /**
     * 释放Bitmap数组.
     *
     * @param bitmaps 要释放的Bitmap数组
     */
    public static void releaseBitmapArray(Bitmap[] bitmaps) {
        if (bitmaps != null) {
            try {
                for (Bitmap bitmap : bitmaps) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     *
     * @param context
     * @param name  转换为bitmap的图片完整路径
     * @return
     */
    public static Bitmap getBitmapFromAsset(Context context, String name) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Bitmap getBitmapFromFile(Context context, String filepath) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(new File(filepath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将uri转换为bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    /**
     * 将uri的图片，按最大尺寸比例压缩到文件中
     * 如果图片缩放的尺寸和原始尺寸一致，则不会进行压缩，而是直接复制图片到输出目录
     * @param context
     * @param uri
     * @param outfilepath
     * @param outwidth
     * @param outheight
     * @return 0:fail 1:succ 2:not need
     */
    public static String uriCompressToFile(Context context, Uri uri, String outfilepath, int outwidth, int outheight) {
        Bitmap compressBitmap=null;
        compressBitmap=AbImageUtil.scaleImg(context,uri,outwidth,outheight);
        if (compressBitmap==null) {
            return "0";
        }else {
            return saveBitmapToPNGFile_compressionAgain(context, compressBitmap, new File(outfilepath), new DataResponse() {
                @Override
                public void onSucc(Object response) {
                }
                @Override
                public void onFail(String error) {

                }
            })?(compressBitmap.getWidth()+","+compressBitmap.getHeight()):"0";
        }
    }
    /**
     * 使头像变灰
     *
     * @param drawable
     */
    public static void porBecomeGrey(ImageView imageView, Drawable drawable) {
        drawable.mutate();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        drawable.setColorFilter(cf);
        imageView.setImageDrawable(drawable);
    }

    /**
     * bitmap转换成byte字节数组
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 字节数组byte转换成bitmap
     * @param temp
     * @return
     */
    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }
    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    /**
     * 计算缩放比
     *
     * @param oldWidth
     * @param oldHeight
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static int reckonThumbnail(int oldWidth, int oldHeight,
                                      int newWidth, int newHeight) {
        if ((oldHeight > newHeight && oldWidth > newWidth)
                || (oldHeight <= newHeight && oldWidth > newWidth)) {
            int be = (int) (oldWidth / (float) newWidth);
            if (be <= 1)
                be = 1;
            return be;
        } else if (oldHeight > newHeight && oldWidth <= newWidth) {
            int be = (int) (oldHeight / (float) newHeight);
            if (be <= 1)
                be = 1;
            return be;
        }

        return 1;
    }
    /**
     * 保存bitmap到JPG文件
     *
     * @param bitmap
     * @param desFile
     * @return
     */
    public static boolean saveBitmapToJPGFile(Bitmap bitmap, File desFile) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] photoBytes = baos.toByteArray();

            if (desFile.exists()) {
                desFile.delete();
            }
            desFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(desFile);
            fos.write(photoBytes);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            if (desFile.exists()) {
                desFile.delete();
            }
            return false;
        }
    }
    /**
     * 保存bitmap到PNG文件
     *
     * @param bitmap
     * @param desFile
     * @return
     */
    public static boolean saveBitmapToPNGFile(Bitmap bitmap, File desFile) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
            byte[] photoBytes = baos.toByteArray();

            if (desFile.exists()) {
                desFile.delete();
            }
            desFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(desFile);
            fos.write(photoBytes);
            fos.flush();
            fos.close();

            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            if (desFile.exists()) {
                desFile.delete();
            }
//            Logs.Debug("saveBitmapToPNGFile Fail", e1.toString());
            return false;
        }
    }
    /**
     * @param photoPath --原图路经
     * @param aFile     --保存缩图
     * @param newWidth  --缩图宽度
     * @param newHeight --缩图高度
     */
    public static boolean bitmapToFile(String photoPath, File aFile,
                                       int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        options.inJustDecodeBounds = false;

        // 计算缩放比
        options.inSampleSize = reckonThumbnail(options.outWidth,
                options.outHeight, newWidth, newHeight);

        bitmap = BitmapFactory.decodeFile(photoPath, options);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] photoBytes = baos.toByteArray();

            if (aFile.exists()) {
                aFile.delete();
            }
            aFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(aFile);
            fos.write(photoBytes);
            fos.flush();
            fos.close();

            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            if (aFile.exists()) {
                aFile.delete();
            }
            Log.e("Bitmap To File Fail", e1.toString());
            return false;
        }
    }

    /**
     * 缩放图片
     *
     * @param bmp
     * @param width
     * @param height
     * @return
     */
    public static Bitmap PicZoom(Bitmap bmp, int width, int height) {
        int bmpWidth = bmp.getWidth();
        int bmpHeght = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);

        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
    }

    /**
     * 生成适合网络调用的图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }
    /**
     * 保存bitmap到PNG文件
     *
     * @param bitmap
     * @param desFile
     * @return
     */
    public static boolean saveBitmapToPNGFile_compressionAgain(Context context,Bitmap bitmap, File desFile,final DataResponse dr) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            Logs.Debug("bitmap size ==="+ baos.size());
            byte[] photoBytes = baos.toByteArray();

            if (desFile.exists()) {
                desFile.delete();
            }
            desFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(desFile);
            fos.write(photoBytes);
            fos.flush();
            fos.close();
            lubanCompress(context, desFile, new DataResponse() {
                @Override
                public void onSucc(Object response) {
//                    File file = (File) response;
//                    dr.onSucc(file.getPath() + "," + "200,200");
                }
                @Override
                public void onFail(String error) {

                }
            });
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            if (desFile.exists()) {
                desFile.delete();
            }
//            Logs.Debug("saveBitmapToPNGFile Fail", e1.toString());
            return false;
        }
    }
//调用luban方法
    public static void lubanCompress(Context context, File file, final DataResponse dr) {
        Logs.Debug("imagelist=============18=="+file.getPath());
        Luban.get(context)
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        //TODO 压缩成功后调用，返回压缩后的图片文件
                        Logs.Debug("file====="+file);
                        dr.onSucc(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO 当压缩过去出现问题时调用

                    }
                }).launch();
    }
    //调用luban方法，RxJava 调用方式请自行随意控制线程
    public static void lubanCompressRx(Context context, File file, final DataResponse dr) {
        Logs.Debug("imagelist=============18=="+file.getPath());
        Luban.get(context)
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                    @Override
                    public Observable<? extends File> call(Throwable throwable) {
                        return Observable.empty();
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        Logs.Debug("file====="+file);
                        dr.onSucc(file);
                        //TODO 压缩成功后调用，返回压缩后的图片文件
                    }
                });
    }

    /**
     * 图片拆分
     * @param bitmap  需要拆分的图片
     * @param xPiece  横向拆分份数
     * @param yPiece  纵向拆分的份数
     * @return  返回拆分后的bitmap列表
     */
    public static List<SplitImageData> ImageSplitter(Context mContext,Bitmap bitmap, int xPiece, int yPiece,String bitmapName){
        List<SplitImageData> pieces = new ArrayList<SplitImageData>(xPiece * yPiece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / xPiece;
        int pieceHeight = height / yPiece;
        for (int i = 0; i < yPiece; i++) {
            for (int j = 0; j < xPiece; j++) {
                SplitImageData piece = new SplitImageData();
                piece.setIndex(j + i * xPiece);
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                Logs.Debug("gg============data-=="+xValue+"=="+yValue+"=="+
                        pieceWidth+"=="+pieceHeight);
                Bitmap bitmap1=Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                BitmapTo bitmapTo=new BitmapTo(mContext);
                bitmapTo.saveBitmap(bitmap1,bitmapName+i+""+j+".jpg");
                piece.setBitmap(bitmap1);
                pieces.add(piece);
            }
        }
        return pieces;
    }
}
