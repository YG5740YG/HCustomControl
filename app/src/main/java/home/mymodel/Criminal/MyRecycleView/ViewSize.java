package home.mymodel.Criminal.MyRecycleView;

import android.view.View;

import java.util.List;

import home.mymodel.Criminal.CrimeData;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class ViewSize {
    int mSize;
    public ViewSize(int mType,Object mDatas){
        switch (mType) {
            case 0:   mSize= ((List<CrimeData>) mDatas).size();
                break;
            case 1:mSize=((List<CrimeData>) mDatas).size();
                break;
            default:
                mSize=0;
        }
    }
    public int getSize(){
        return mSize;
    }
}
