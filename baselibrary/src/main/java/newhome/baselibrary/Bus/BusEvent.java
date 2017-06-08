package newhome.baselibrary.Bus;

/**
 * Created by 20160330 on 2017/2/28.
 */

public class BusEvent {
    int action;
    String content;
    Object object;

    public BusEvent() {
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

