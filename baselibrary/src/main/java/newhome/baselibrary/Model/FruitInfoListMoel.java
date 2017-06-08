package newhome.baselibrary.Model;

import java.io.Serializable;

import MyView.ListViewDeleteSlideView;


/**
 * Created by 20160330 on 2017/2/4.
 */

public class FruitInfoListMoel implements Serializable{
    private int fruitImageId;
    private String fruitImageName;
    public ListViewDeleteSlideView listViewDeleteSlideView;
    public FruitInfoListMoel(String name, int imageId) {
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
