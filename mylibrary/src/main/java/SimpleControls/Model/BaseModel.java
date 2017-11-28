package SimpleControls.Model;

/**
 * @describe:
 * @author: yg
 * @date: 2016/8/10.
 */

public class BaseModel {

    private int index;

    private String type;

    public BaseModel(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
