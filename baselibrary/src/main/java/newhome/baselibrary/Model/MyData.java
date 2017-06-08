package newhome.baselibrary.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MyData implements Serializable {

    /**
     * FirstName : Peter
     * LastName : Griffin
     * Age : 35
     */

    private String FirstName;
    private String LastName;
    private String Age;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }
}
