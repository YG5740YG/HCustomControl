package newhome.baselibrary.ImageHandle.ImageUse;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import newhome.baselibrary.MyViewI.DataResponse;
import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Logs;


/**
 * Created by lonaever on 14-7-23.
 */
public class ImageData {
    public int imgId;
    public String imgUrl;
    public Uri imgUri;//可能是content的或者是file的
    public String filepath;//用于上传时候的路径,可以干别的事

    public String height;
    public String width;

    public String getWidth(){
        return this.width;
    }
    public String getHeight(){
        return this.height;
    }
    public void setWidth(String width){
        this.width=width;
    }
    public void setHeight(String height){
        this.height=height;
    }


    public ImageData() {
    }

    public ImageData(Uri uri) {
        imgUri = uri;
    }

    public ImageData(String url) {
        imgUrl = url;
    }
    public ImageData(String filepath1, int i) {
        filepath = filepath1;
    }

    public String compress(final Context context, final int outWidth, final int outHeight, final String outFileName) {
        String filecompresspath = FileUtil.appSavePathFile(outFileName);
        AbImageUtil.getBitmapDegree(filecompresspath);
        Logs.Debug("fuck img====="+ AbImageUtil.getBitmapDegree(imgUri.getPath()) );
        String ret = ImageUtil.uriCompressToFile(context, imgUri, filecompresspath, outWidth, outHeight);
        if (!ret.equals("0")) {
            filepath=filecompresspath;
            return ret;
        }else{
            filepath="";
            return  "0";
        }
    }

    public void compress(final Context context, final int outWidth, final int outHeight, final String outFileName, final DataResponse dr) {
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
                String ret = ImageUtil.uriCompressToFile(context, imgUri, filecompresspath, outWidth, outHeight);
                if (ret.equals("0")) {
                    filepath=filecompresspath;
//                    if (ret == 1) {
//                        filepath = filecompresspath;
//                    } else if (ret == 2) {
//                        filepath = FileUtil.getFilePathFromUri(context,imgUri);
//                    }
                    return "ok";
                }
                return null;
            }
        }.execute("");
    }

}
