package newhome.baselibrary.Model;

import java.io.Serializable;
import java.util.List;

import MyView.ListViewDeleteSlideView;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class LsWeiBoData implements Serializable {
    private int img;
    private String title;
    private int mType;
    private List<FruitInfo>fruitInfos;
    private topData mTopData;
    public List<FruitInfo> getFruitInfos(){
        return this.fruitInfos;
    }
    public void setFruitInfos(List<FruitInfo> fruitInfos){
        this.fruitInfos=fruitInfos;
    }

    public topData getmTopData(){
        return this.mTopData;
    }
    public void setmTopData(topData mTopData){
        this.mTopData=mTopData;
    }

    public LsWeiBoData(int type,int img, String title) {
        this.mType=type;
        this.img = img;
        this.title = title;
    }
    public int getmType(){
        return mType;
    }

    public void setmType(int type){
        this.mType=type;
    }
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//模式一需要的数据
    public static class FruitInfo implements Serializable{
        private int fruitImageId;
        private String fruitImageName;
        public ListViewDeleteSlideView listViewDeleteSlideView;
        public FruitInfo(String name, int imageId) {
            this.fruitImageName = name;
            this.fruitImageId = imageId;
        }
        public int getFruitImageId(){
            return fruitImageId;
        }
        public void setFruitImageId(int imageId){
            this.fruitImageId=imageId;
        }
        public String getFruitImageName(){
            return fruitImageName;
        }
        public void setFruitImageName(String imageName){
            this.fruitImageName=imageName;
        }
    }
    //模式4需要的数据
    public static class topData implements Serializable{
        private int img;
        public topData(int img) {
            this.img = img;
        }
        public int getImg() {
            return img;
        }
        public void setImg(int img) {
            this.img = img;
        }
    }

}
