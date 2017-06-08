package newhome.baselibrary.Model;

import java.io.Serializable;
import java.util.List;
/*
{
    "act": "SaveTXL",
    "tx1": [
        {
            "name": "陶通梅",
            "phone": [
                "135-7800-6072",
                "135-7800-6072"
            ]
        },
        {
            "name": "陶通梅",
            "phone": [
                "135-7800-6072",
                "135-7800-6072"
            ]
        }
    ],
    "userId": "1717482"
}
 */

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class UserBolmData implements Serializable {
    /**
     * tx1 : [{"name":"陶通梅","phone":["135-7800-6072","135-7800-6072"]},{"name":"陶通梅","phone":["135-7800-6072","135-7800-6072"]}]
     * userId : 1717482
     */

    private String userId;
    private List<Tx1Bean> tx1;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Tx1Bean> getTx1() {
        return tx1;
    }

    public void setTx1(List<Tx1Bean> tx1) {
        this.tx1 = tx1;
    }

    public static class Tx1Bean {
        /**
         * name : 陶通梅
         * phone : ["135-7800-6072","135-7800-6072"]
         */

        private String name;
        private List<String> phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getPhone() {
            return phone;
        }

        public void setPhone(List<String> phone) {
            this.phone = phone;
        }
    }

//        private String name;
//        private List<String> phone;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<String> getPhone() {
//            return phone;
//        }
//
//        public void setPhone(List<String> phone) {
//            this.phone = phone;
//        }

}
