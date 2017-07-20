package home.mymodel.Criminal.MyRecycleView;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class ViewItemType {
    private int mItemType;
    public ViewItemType(int mType){
        switch (mType){
            case 0:
                mItemType=1;
                break;
            default:
                mItemType=1;
        }
    }
    public int getItemType(){
        return mItemType;
    }
}
