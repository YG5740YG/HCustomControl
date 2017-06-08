package newhome.baselibrary.ImageHandle.CompressImage;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

/**
 *
 */
public class Compress {
    static String filepath="";
    /**
     *
     * @param context
     * @param outWidth 宽
     * @param outHeight 高
     * @param filecompresspath 压缩图片文件的完整路径  /store/m/jiuji/9.jpeg
     * @param imageUri 需要压缩图片的Uri
     * @return
     */
    public static String Compress(final Context context, final int outWidth, final int outHeight, final String filecompresspath, Uri imageUri) {
        AbImageUtil.getBitmapDegree(filecompresspath);
        Logs.Debug("fuck img====="+ AbImageUtil.getBitmapDegree(imageUri.getPath()));
        String ret = BitmapTo.uriCompressToFile(context, imageUri, filecompresspath, outWidth, outHeight);
        if (!ret.equals("0")) {
            filepath=filecompresspath;
            return ret;
        }else{
            filepath="";
            return  "0";
        }
    }

    public void Compress(final Context context, final int outWidth, final int outHeight, final String outFileName, final DataResponse dr, final Uri imageUri) {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPostExecute(Object o) {
                if (o == null) {
                    dr.onFail("压缩图片失败!");
                } else {
                    dr.onSucc(null);
                }
            }

            @Override
            protected Object doInBackground(Object... objects) {
                String filecompresspath = FileUtil.appSavePathFile(outFileName);
                String ret = BitmapTo.uriCompressToFile(context, imageUri, filecompresspath, outWidth, outHeight);
                if (ret.equals("0")) {
                    filepath=filecompresspath;
                    return "ok";
                }
                return null;
            }
        }.execute("");
    }
}
