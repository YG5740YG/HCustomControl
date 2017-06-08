package newhome.baselibrary.ImageHandle.CompressImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

import newhome.baselibrary.Tools.FileUtil;
import newhome.baselibrary.Tools.Tools;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

/**
 * 压缩使用
 */
public class MyImageCompress {
    private Context mContext;
    private String mUrl;
    private String mFilePath="";//压缩后的文件路径
    private Uri mUri;
    /**
     * 标识，网络路径，手机路径，uri
     */
    private int type=0;

    /**
     * 获取压缩后的文件路径
     * @return
     */
    public String getmFilePath(){
        return this.mFilePath;
    }
    /**
     *
     * @param context
     * @param url 完整路径
     */
    public MyImageCompress(Context context,String url){
        this .mUrl=url;
        this.mContext=context;
        if(url.startsWith("https://") || url.startsWith("http://")){
            type=1;//网络路径
            CompressIntentImage();
        }else if(url.startsWith("/")){
            type=2;//手机路径
            File f = new File(url);
            mUri=Uri.fromFile(f);
            CompressPhoneImage();
        }else if(url.contains("file://")||url.contains("content://")){
            type=3;//uri
            mUri = Uri.parse(url);
            CompressPhoneImage();
        }
    }
    public String CompressIntentImage(){
        Bitmap bitmap;
        bitmap=BitmapTo.getBitmap(mUrl);
        String time = Tools.getNowtime() + ".jpg";
        mFilePath = FileUtil.appSavePathFile(time);
        BitmapTo.bitmapToFile(bitmap,mFilePath);
        final String btm = Compress.Compress(mContext, 400, 400, mFilePath,mUri);//压缩，返回压缩后图片的长宽
        return mFilePath+","+btm;
    }
    public String CompressPhoneImage(){
        Bitmap bitmap;
        String time = Tools.getNowtime() + ".jpg";
        mFilePath = FileUtil.appSavePathFile(time);
        final String btm = Compress.Compress(mContext, 400, 400, mFilePath,mUri);//压缩，返回压缩后图片的长宽
        return mFilePath+","+btm;
    }
}
