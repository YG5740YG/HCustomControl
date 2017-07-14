package newhome.baselibrary.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class SplitImageData implements Serializable {
    private int index = 0;
    private Bitmap bitmap = null;
    public void setIndex(int index){
        this.index=index;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
    }
    public int getIndex(){
        return index;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
}
