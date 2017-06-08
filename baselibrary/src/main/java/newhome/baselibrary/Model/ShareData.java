package newhome.baselibrary.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class ShareData implements Serializable {
    /**
     * title,descriptiom，bmp,imagerUrl，均为分享框显示的内容。
     * url为如果用户点击是一个要跳转到一个网页就填。如果只是一段话+一个url，展示的是一段文本里面有一个url的话，url，放在descriptiom里面。
     */
    String title;//标题
    String description;//内容,必填
    String url;//url
    int sort; //1.文字，   2.图片   3.网页
    String imagerUrl="http://78rc7f.com2.z0.glb.clouddn.com/9ji.png";
    byte[] bitmap;

    public  byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap( byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getImagerUrl() {
        return imagerUrl;
    }

    public void setImagerUrl(String imagerUrl) {
        this.imagerUrl = imagerUrl;
    }

    public ShareData(String description, int sort) {

        this.description = description;
        this.sort = sort;//1.文字，2.图片3.网页
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}

