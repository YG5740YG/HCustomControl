package newhome.baselibrary.ImageHandle.CompressImage;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class ImageData implements Serializable{
    public int imgId;
    public String imgUrl;//image原始路径
    public String filepath;//用于上传时候的路径,可以干别的事
    public String height;
    public String width;

    public int  getImgId(){return imgId;}
    public void setImgId(int imgId){this.imgId=imgId;}
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
    public void setImgUrl(String imgUrl){this.imgUrl=imgUrl;}
    public String getImgUrl(){return this.imgUrl;}
    public void setFilepath(String filepath){
        this.filepath=filepath;
    }
    public String getFilepath(){
        return this.filepath;
    }

    public Uri imgUri;//可能是content的或者是file的

    public ImageData() {}
    public ImageData(Uri uri) {
        imgUri = uri;
    }
    public ImageData(String filepath, int i) {
        this .filepath = filepath;
        this.imgId=i;
    }
}
