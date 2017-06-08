package newhome.baselibrary.Bus;

/**
 * Created by 20160330 on 2016/12/9.
 */

public class RoutersAction {
        //跳转配置文件的相关信息
        public static String INTENT_ACTIVITY_FLAGE = "android.intent.action.9ji.VIEW";//跳转目标activity配置文件action标识（常规）
        public static String INTENT_ACTIVITY_EXTRASACTIVITY = "android.intent.9ji.VIEW.extrasActivity";//跳转目标activity配置文件标识(自定义）
        public static String INTENT_DATA_EXTRAS = "https://m.9ji.com/Extras";//跳转目标activity配置文件data--ExtrasActivity
        public static String INTENT_DATA_HOME = "https://m.9ji.com/home";//跳转目标activity配置文件data--homeActivity
        public static String INTENT_DATA_MAIN= "https://m.9ji.com/main";//跳转目标activity配置文件data--homeActivity
        public static String INTENT_DATA_LOADFRAGMENT_FLAGE= "android.intent.9ji.VIEW.loadfragmentActivity";//跳转目标activity配置文件data--homeActivity
        //跳转配置文件关键字，相关信息
        //--（activity）
        public static String MAIN = "/main"; //主页
        public static String NEWS = "/news";//新闻页模块
        public static String MPRODUCT = "/mproduct";//集市
        public static String HOME = "/home";//homeActivity
        public static String PRODUCT = "/product";//商品
        public static String LIST = "/list";//商品列表
        public static String ACOUNT_LOGIN = "/acount/login";//登录
        public static String UPIMAGE = "//upimage";//更换头像
        public static String CONTACTS = "/contacts";//同步联系人
        public static String PUSH = "/push";//
        public static String OA_PUSH = "/oa_push";//
        public static String SCAN = "/scan";//扫描
        public static String WEXXSCAN = "/wexx_scan"; //wexxlib的扫描
        public static String GOODS_NUNSALE = "/goods/unsale";
        public static String GOODS = "/goods";//
        public static String PAYMENT = "/payment";//
        public static String CHAT = "/chat";//
        public static String OA_CHAT = "/oa_chat";//
        public static String SHOPANDZTD = "/ShopAndZtd";
        public static String RECEIVEADDRESS = "/ReceiveAddress";
        public static String ORDER = "/orderDes"; //订单详情
        public static String CHECKREPORT = "/checkreport"; //质检报告
        public static String EVALUATION = "/evaluation"; //查看评价详情页
        public static String REFUND = "/AppealRefund"; //申请退款页面
        public static String ORDERDETAIL = "/OrderDetailJS"; //订单详情页  集市
        public static String PRODUCT_SEARCH = "/productSearch";
        public static String CONFIRMORDER = "/ConfirmOrder"; //订单确认界面
        public static String PRODUCTPARTS = "/ProductParts"; //配件选择界面
        public static String MSGCENTER = "/msg";//聊天
        public static String GOOSSIP = "/gossip";//
        public static String WEEX = "/weex";//homeActivity
        public static String LOOKFOR = "/lookfor";//求购
        public static String LOADFRAGMENT="/LoadFragment";//加载fragment
        //        public static String GUIDE_MAP = "/map";//
        public static String GoodsDetail = "/mProduct/Detail"; //集市宝贝详情页
        public static String GUIDE_MAP = "/location";//导航
        public static String BILL = "/bill";//运单填写
        public static String CITYSELECT = "/citySelect";//城市选择
        public static String KUAICHUAN = "/Kuaichuan"; //快传
        public static String ZHIHUAN = "/zh";
        public static String COMPLAINTS = "/Complaints";//集市举报
        public static String STORESHOPDETAIL="/store/shopdetail";//店面详情
        public static String PWD = "/updatepwd"; //个人信息 修改密码
        //--（fragment）
        public static String STORE = "/store";//附近门店
        public static String HOTTOPIC = "/hottopic";//稀奇集中营
        public static String RECHARGE = "/Recharge";//充值中心
        public static String JIUJIBUY = "/9jibuy";//9jibuy
        public static String CART = "/cart";   //购物车
        public static String ORDEREVALUATE="/guestComments";//跳转加载评价fragment
        //跳转的一些判断条件，静态化
        public static String WEBVIEW_CA = "webview_activity";//网页需要用webView打开，但是网址链接中有与activity标识冲突字段，跳转时需要传递bundle,key:webview_key,value:RoutersActivity.WEBVIEW_CA
        public static String WEBVIEW_CF = "webview_fragment";//网页需要用webView打开，但是网址链接中有与fragment标识冲突字段,跳转时需要传递bundle,key:webview_key,value:RoutersActivity.WEBVIEW_CF
        public static String FRAGMENT = "fragment";
        public static String ACTIVITY = "activity";
        public static String CUSTOM = "custom";  //跳转到自定义目标
        public static String WEBVIEWTITLE = "webViewTitle";//需要用webview打开，并且传递标题，传递bundle,key:RoutersActivity.WEBVIEW_CA,value:标题
        //跳转自定义目标标识，如打电话
        public static String TEL = "tel:";
        public static String ORDERID = "https://m.9ji.com/user/orderDetail.aspx?orderid=";
        public static String LOGIN = "https://m.9ji.com/acount/login.aspx";
        public static String ADDRESSLIST = "https://m.9ji.com/user/myaddresslist.aspx";
        public static String MAINPAGE = "//m.9ji.com/";
        public static String ACTIVITY_CHAT = "http://chat.9ji.com/m/index.aspx?SourceUrl=https://m.9ji.com/user/warrantCard.aspx?imei=";
        public static String ACTIVITY_USERCENTER = "/user/index.aspx";

        //登录判断
        public static String JL = "judgeLoad";

        //判断标识集合：
        //需要登录才能跳转的标识
        public static String RJL = MSGCENTER+"|"+CHAT;

        //--activity
        public static String RAI = PUSH + "|" + SCAN + "|" + NEWS + "|" + MPRODUCT + "|" + PRODUCT + "|" + HOME + "|" + LIST
                + "|" + GOODS + "|" + ACOUNT_LOGIN + "|" + UPIMAGE + "|" + PAYMENT + "|" + CHAT + "|" + SHOPANDZTD + "|" + CONFIRMORDER
                + "|" + PRODUCTPARTS + "|" + RECEIVEADDRESS + "|" + MSGCENTER + "|" + GUIDE_MAP + "|" + MAIN
                + "|" + GOOSSIP + "|" + GoodsDetail + "|" + BILL + "|" + CONTACTS + "|" + CITYSELECT + "|" + KUAICHUAN
                + "|" + ORDER + "|" + CHECKREPORT + "|" + EVALUATION + "|" + REFUND + "|" + ORDERDETAIL + "|"//路由需要跳转到相应的activity标识集合(RoutersActivityItems)
                + LIST + "|" + GOODS + "|" + ACOUNT_LOGIN + "|" + PAYMENT + "|" + CHAT + "|" + SHOPANDZTD
                + "|" + CONFIRMORDER + "|" + PRODUCTPARTS + "|" + RECEIVEADDRESS + "|" + MSGCENTER + "|" + GUIDE_MAP
                + "|" + BILL + "|" + CONTACTS + "|" + CITYSELECT + "|" + KUAICHUAN + "|" + PRODUCT_SEARCH + "|" + WEEX
                + "|" + WEXXSCAN + "|" + ZHIHUAN + "|" + OA_PUSH + "|" + OA_CHAT+"|"+LOOKFOR+"|"+COMPLAINTS+"|"+STORESHOPDETAIL
                + "|" + PWD;
        //路由需要跳转到相应的activity标识集合(RoutersActivityItems)

        //路由需要跳转到相应的activity标识集合(RoutersActivityItems)
        //--fragment
        public static String RFI =//CITYSELECT+"|"+
                STORE + "|" + HOTTOPIC + "|" + RECHARGE + "|" + JIUJIBUY + "|" + CART+"|"+ORDEREVALUATE;//路由需要跳转到相应的fragment标识集合(RoutersFragmentItems)

        //跳转自定义目标标识结合(RoutersCustomItems)
        public static String RCI = TEL + "|" + LOGIN + "|" + ORDERID + "|" + ADDRESSLIST + "|" + MAINPAGE + "|" + ACTIVITY_CHAT + "|" + ACTIVITY_USERCENTER;

        //--activity url截取字段个数标识(一般截取网络链接需要）
        public static String RAII = NEWS + "|" + MPRODUCT + "|" + PRODUCT + "|" + HOME + "|" + LIST + "|" + GUIDE_MAP + "|" + ACOUNT_LOGIN + "|" + WEEX;
        //路由需要跳转到相应的activity，url需要截取字段个数的判断标识集合（RoutersActivityItemsInterception),截取一个
        //--fragment url截取字段个数标识
        //路由需要跳转到相应的fragment，url需要截取字段个数的判断标识集合（RoutersActivityItemsInterception),截取两个个
        //--activity url截取字段个数标识(一般截取网络链接需要）
        //路由需要跳转到相应的activity，url需要截取字段个数的判断标识集合（RoutersActivityItemsInterception),截取一个
        //--fragment url截取字段个数标识
        public static String RFII = ACOUNT_LOGIN + "|" + GOODS_NUNSALE + "|" + GoodsDetail+"|"+STORESHOPDETAIL;
        //路由需要跳转到相应的fragment，url需要截取字段个数的判断标识集合（RoutersActivityItemsInterception),截取两个个

}
