package newhome.baselibrary.Bus;

/**
 * Created by 20160330 on 2017/2/28.
 */

public class BusAction {
    public static final int SHOP = 10004;
    public static final int SET_ADDR = 10006;
    public static final int CART_REFRESH = 10016;
    public static final int LOGIN = 10017;
    public static final int MAIN_BOTTOM = 10029;
    public static final int MAIN_CART_NUM = 10030;
    public static final int WXSEESION = 10039;
    public static final int TIMELINE = 10040;
    public static final int JISHI_CHAT = 10045;
    public static final int CUSTOMER_CHAT = 10046;
    public static final int DEFAULT_CHAT = 0x10046; //默认启动方式
    public static final int MY_SELL = 10047;
    public static final int MY_BUY = 10049;
    public static final int MY_SEND = 10050;
    public static final int MY_TUIKUAN = 10051;
    public static final int PAGESIZE = 15;

    //购物车与订单确认Action
    public static final int ON_CART_PARTS_SELECT = 11001;
    public static final int ON_CART_SERVICE_SELECT = 11002;
    public static final int CONFIRM_ORDER_DELIVERY_PAYMENT = 11003;
    public static final int SHOP_INFO_TYPE = 11004;
    public static final int ZTD_INFO_TYPE = 11005;
    public static final int CONFIRM_ORDER_SHOP_INFO = 11006;
    public static final int CONFIRM_ORDER_ZTD_INFO = 11007;
    public static final int CONFIRM_ORDER_RECEIVE_ADREESS= 11008;
    public static final int CONFIRM_ORDER_DISCOUNT_CODE= 11009;
    public static final int GAODE=10011;
    public static final int TENXUN=10012;
    //用户设置城市
    public static final int CHANGE_CITY=10013;
    public static final int CHANGE_CITY_Id=10019;
    //微信分享成功或者失败
    public static final int SUCCESS=10014;
    public static final int FAILE=10015;
    //用户设置城市
    public static final int OrderBack=10016;
    //求购刷新广播
    public static final int QiuGouRefresh=10017;
    public static final int PtrScroll=10018;
    //微信支付
    public static final int WXPAYSUCCESS=10019;
    public static final int WXPAYFAIL=10020;
    //支付宝支付
    public static final int ALIPAYSUCCESS=10021;
    public static final int ALIPAYFAIL=10022;
    //集市扫描条形码
    public static final int JSTXM=10023;
    //首页楼层12
    public static final int HOMEFLORT=10024;
    //预约商品
    public static final int YUYUE=10025;
    public static final int TEST=10026;
    public static final int PARKINLEFT=10027;

    public  enum  intent{
        VIEW ;
    }

//    recycle  View
public static int CRIME_RECYCLE_VIEW_TYPT=0;
public static int CRIME_RECYCLE_VIEW_TYPT_TWO=1;
}

