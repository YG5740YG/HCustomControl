package newhome.baselibrary.MyHttpOld.JSON;

/**
 * Created by 20160330 on 2017/3/30 0030.
 */
//JSON数据格式

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import newhome.baselibrary.R;


/**
 * 简洁
 * [{"id":"5","version":"5.5","name":"Angry Birds"},
 {"id":"6","version":"7.0","name":"Clash of Clans"},
 {"id":"7","version":"3.5","name":"Hey Day"}]
 *
 * 较为复杂
 {
 "stats": 1,
 "data": {
 "focusimg": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703270538380.jpg",
 "url": "https://m.9ji.com/event/844.html?from=banner",
 "activitytitle": "果粉福利周重庆"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703211131400.jpg",
 "url": "https://m.9ji.com/event/827.html?from=banner",
 "activitytitle": "全区综合场重庆"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703170554320.jpg",
 "url": "https://m.9ji.com/event/801.html?from=banner",
 "activitytitle": "电脑办公重庆"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703230611550.jpg",
 "url": "https://m.9ji.com/event/835.html?from=banner",
 "activitytitle": "华为品牌周重庆"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703250957240.jpg",
 "url": "https://m.9ji.com/event/843.html?from=banner",
 "activitytitle": "huawei nova重庆"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703211040470.jpg",
 "url": "https://m.9ji.com/event/790.html?from=banner",
 "activitytitle": "分期重庆"
 }
 ],
 "products": [
 {
 "categoryname": "本周主推",
 "floorstyle": 9,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201703170704010.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703240433530.jpg",
 "url": "https://m.9ji.com/product/51839.html?from=zt1",
 "activitytitle": "红色iPhone",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703290451340.jpg",
 "url": "https://m.9ji.com/product/43868.html?from=zt2",
 "activitytitle": "JBL入耳式耳机",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703170710120.jpg",
 "url": "https://m.9ji.com/product/46281.html?from=zt3",
 "activitytitle": "华为荣耀盒子",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703170711410.jpg",
 "url": "https://m.9ji.com/product/25311.html?from=zt4",
 "activitytitle": "iPhone 6",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703240516070.jpg",
 "url": "https://m.9ji.com/product/50610.html?from=zt5",
 "activitytitle": "联想(Lenovo)小新Air13 Pro",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703240538290.jpg",
 "url": "https://m.9ji.com/product/46518.html?from=zt6",
 "activitytitle": "huawei 路由器",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "掌上专享",
 "floorstyle": 10,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201611090905480.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703220922570.jpg",
 "url": "https://m.9ji.com/topic/554.html?from=zx1",
 "activitytitle": "会员特卖汇",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210956130.jpg",
 "url": "https://m.9ji.com/event/659.html?from=zx2",
 "activitytitle": "huawei专场",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210956200.jpg",
 "url": "https://m.9ji.com/product/42436.html?from=zx3",
 "activitytitle": "小天才手表",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210956280.jpg",
 "url": "https://m.9ji.com/product/42858.html?from=zx4",
 "activitytitle": "三星液晶屏",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210956400.jpg",
 "url": "https://m.9ji.com/product/42986.html?from=zx5",
 "activitytitle": "JBL小金砖",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "今日爆款",
 "floorstyle": 8,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201606290358180.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703291039490.jpg",
 "url": "https://m.9ji.com/product/47575.html?from=bk1",
 "activitytitle": "iphone 7",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703150914120.jpg",
 "url": "https://m.9ji.com/product/46178.html?from=bk2",
 "activitytitle": "mate 9 月光银",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703131129130.jpg",
 "url": "https://m.9ji.com/product/42598.html?from=bk4",
 "activitytitle": "小米笔记本Air 13.3寸",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703290603520.jpg",
 "url": "https://m.9ji.com/product/46812.html?from=bk3",
 "activitytitle": "魅蓝 note5",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201702270143260.jpg",
 "url": "https://m.9ji.com/product/36738.html",
 "activitytitle": "华为手表抄底价 赠好礼",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "小九推荐",
 "floorstyle": 9,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201606290358240.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201609050451280.jpg",
 "url": "https://m.9ji.com/zt/2016/0824/",
 "activitytitle": "新人礼",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201610080947030.jpg",
 "url": "https://m.9ji.com/list/20-1-0-0-0-0-0-0-0-0-0-0-0-0-0-1.html",
 "activitytitle": "苹果MacBook Air",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201702071121310.jpg",
 "url": "https://m.9ji.com/product/45339.html",
 "activitytitle": "华为荣耀6X",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201612140242130.jpg",
 "url": "https://m.9ji.com/product/37642.html",
 "activitytitle": "小米九号平衡车",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201701240530100.jpg",
 "url": "https://m.9ji.com/product/42045.html",
 "activitytitle": "0元购路由",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703160450200.jpg",
 "url": "https://m.9ji.com/product/51675.html",
 "activitytitle": "雷柏机械键盘",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "总有惊喜",
 "floorstyle": 10,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201606290358300.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703081005070.jpg",
 "url": "https://m.9ji.com/product/49976.html",
 "activitytitle": "华为荣耀 V9",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703140517040.jpg",
 "url": "https://m.9ji.com/product/51618.html",
 "activitytitle": "华为二代手表",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703091203150.jpg",
 "url": "https://m.9ji.com/product/44218.html",
 "activitytitle": "戴尔燃7000 15-R1545S",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201701170330120.jpg",
 "url": "https://m.9ji.com/product/45986.html",
 "activitytitle": "魅蓝 5",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100553400.jpg",
 "url": "https://m.9ji.com/product/51621.html",
 "activitytitle": "华硕电脑V555",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "智趣营",
 "floorstyle": 11,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201606290358340.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201610111118190.jpg",
 "url": "https://m.9ji.com/list/114-0-0-0-0-0-1-0_0.html",
 "activitytitle": "耳机大全",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201702141020330.jpg",
 "url": "https://m.9ji.com/list/146-0-0-0-0-0-1.html",
 "activitytitle": "智能手环",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703160211220.jpg",
 "url": "https://m.9ji.com/product/49253.html",
 "activitytitle": "千兆路由器",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201610120135510.jpg",
 "url": "https://m.9ji.com/product/44972.html",
 "activitytitle": "九机定制膜",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703170325110.jpg",
 "url": "https://m.9ji.com/product/42976.html",
 "activitytitle": "公牛插线板",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201611140146450.jpg",
 "url": "https://m.9ji.com/product/42858.html",
 "activitytitle": "三星 23.5英寸曲面显示器",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201611250305540.jpg",
 "url": "https://m.9ji.com/product/42436.html",
 "activitytitle": "儿童手表",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703140520540.jpg",
 "url": "https://m.9ji.com/list/316-0-0-0-1-0_0.html",
 "activitytitle": "健康生活",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201609180529170.jpg",
 "url": "https://m.9ji.com/product/44127.html",
 "activitytitle": "beats EP",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201612281134530.jpg",
 "url": "https://m.9ji.com/product/46533.html",
 "activitytitle": "大疆精灵4pro",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "九机集市",
 "floorstyle": 8,
 "FontColor": "",
 "titleImg": "https://img2.ch999img.com/pic/clientimg/201702061004530.png",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210624450.jpg",
 "url": "https://jishi.9ji.com/mProduct/Detail/6211",
 "activitytitle": "宝贝详情",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210625220.jpg",
 "url": "https://jishi.9ji.com/mProduct/Detail/6546",
 "activitytitle": "宝贝详情",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703210625530.jpg",
 "url": "https://jishi.9ji.com/mProduct/Detail/6473",
 "activitytitle": "宝贝详情",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703270525410.jpg",
 "url": "https://jishi.9ji.com/mProduct/Detail/3916",
 "activitytitle": "宝贝详情",
 "price": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201702061235380.jpg",
 "url": "https://jishi.9ji.com/mproduct?from=banner",
 "activitytitle": "更多二手商品",
 "price": ""
 }
 ]
 },
 {
 "categoryname": "更多供您考虑的商品",
 "floorstyle": 12,
 "FontColor": "",
 "titleImg": "",
 "productlist": [
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20160630101844141.jpg",
 "url": "https://m.9ji.com/product/42960.html",
 "activitytitle": "乐2全网通原力金 32G",
 "price": "999.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20160322035125544.jpg",
 "url": "https://m.9ji.com/product/38429.html",
 "activitytitle": "iPhone SE 玫瑰金 64GB",
 "price": "2950.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20170308140412782.jpg",
 "url": "https://m.9ji.com/product/50670.html",
 "activitytitle": "戴尔灵越14-3467-R1525",
 "price": "3199.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20161207132918629.jpg",
 "url": "https://m.9ji.com/product/46436.html",
 "activitytitle": "联想 IdeaPad 710S 银色",
 "price": "4899.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20160926165846197.jpg",
 "url": "https://m.9ji.com/product/44679.html",
 "activitytitle": "联想110-15",
 "price": "3499.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20161024130435318.jpg",
 "url": "https://m.9ji.com/product/45305.html",
 "activitytitle": "戴尔新游匣7566 Ins15",
 "price": "6650.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20160929183351939.jpg",
 "url": "https://m.9ji.com/product/44821.html",
 "activitytitle": "360 巴迪龙儿童手表",
 "price": "299.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20170306173601224.jpg",
 "url": "https://m.9ji.com/product/50367.html",
 "activitytitle": "MUID多功能化妆镜",
 "price": "179.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/2016101714584023.jpg",
 "url": "https://m.9ji.com/product/44159.html",
 "activitytitle": "Beats Solo3 Wireless头戴耳机",
 "price": "2188.00"
 },
 {
 "imgurl": "https://img2.ch999img.com//pic/product/160x160/20161114093152703.jpg",
 "url": "https://m.9ji.com/product/38683.html",
 "activitytitle": "JBL万花筒蓝牙小音箱",
 "price": "899.00"
 }
 ]
 }
 ],
 "iconlink": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201702170250360.png",
 "url": "https://jishi.9ji.com/mproduct?from=dilan",
 "activitytitle": "九机集市"
 }
 ],
 "icon": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100913060.png",
 "url": "https://m.9ji.com/store/?from=tb1",
 "activitytitle": "附近门店"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100913140.png",
 "url": "https://m.9ji.com/vipclub/?from=tb2",
 "activitytitle": "会员俱乐部"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100913220.png",
 "url": "https://m.9ji.com/hottopic/?from=tb3",
 "activitytitle": "稀奇集中营"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100913370.png",
 "url": "https://m.9ji.com/bargain/?from=tb4",
 "activitytitle": "一手优品"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100913450.png",
 "url": "https://huishou.9ji.com/m/?from=tb5",
 "activitytitle": "旧机回收"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100913520.png",
 "url": "https://m.9ji.com/user/fixreport.aspx?from=tb6",
 "activitytitle": "九机快修"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703130331380.png",
 "url": "https://m.9ji.com/tryout/?from=tb7",
 "activitytitle": "试用中心"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100914270.png",
 "url": "https://m.9ji.com/goods/unsale.aspx?from=tb8",
 "activitytitle": "为我优选"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703270451290.png",
 "url": "https://m.9ji.com/pastingappoint.aspx?from=tb9",
 "activitytitle": "免费贴膜"
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703100914450.png",
 "url": "https://m.9ji.com/menu.aspx?from=tb10",
 "activitytitle": "全部"
 }
 ],
 "hotinfo": [
 {
 "url": "https://m.9ji.com/news/#11-12489",
 "title": "红色版iPhone 7颜值几何？红、黑、亮黑三色对比",
 "fenglei": "图赏"
 },
 {
 "url": "https://m.9ji.com/news/#13-12488",
 "title": "三星S8正式发布，有哪些是你没猜到的？",
 "fenglei": "资讯"
 },
 {
 "url": "https://m.9ji.com/news/#12-12478",
 "title": "【小九速评】乐心Mambo2代手环 功能从未如此丰富",
 "fenglei": "评测"
 },
 {
 "url": "https://m.9ji.com/news/#3-12477",
 "title": "【哈曼卡顿 · 品鉴会】九机网 · 哈曼卡顿成功举办‘一件关于声音的艺术’品鉴会",
 "fenglei": "公告"
 },
 {
 "url": "https://m.9ji.com/news/#12-12467",
 "title": "【小九速评】开车去兜风，怎能少了它",
 "fenglei": "评测"
 },
 {
 "url": "https://m.9ji.com/news/#12-12463",
 "title": "【九机评测】首款小米自产芯片手机 小米5c轻体验",
 "fenglei": "评测"
 },
 {
 "url": "https://m.9ji.com/news/#12-12429",
 "title": "3个徕卡镜头的华为P10带你欣赏翠湖美景",
 "fenglei": "评测"
 },
 {
 "url": "https://m.9ji.com/news/#12-12433",
 "title": "【小九速评】360°家庭安防摄像头 看看上班后家里发生了啥",
 "fenglei": "评测"
 },
 {
 "url": "https://m.9ji.com/news/#12-12416",
 "title": "【小九速评】40公里续航的折叠电动车，还能做到这些",
 "fenglei": "评测"
 },
 {
 "url": "https://m.9ji.com/news/#12-12392",
 "title": "【小九速评】一个推不倒的水杯",
 "fenglei": "评测"
 }
 ],
 "floaticon": [],
 "iconbackground": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703270451080.jpg",
 "url": "",
 "activitytitle": ""
 }
 ],
 "sidebaricon": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200154320.png",
 "url": "https://m.9ji.com/",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200154440.png",
 "url": "https://m.9ji.com/",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200154540.png",
 "url": "/goods/category.aspx",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200154590.png",
 "url": "/goods/category.aspx",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200155050.png",
 "url": "https://m.9ji.com/order/cart.aspx",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200155100.png",
 "url": "https://m.9ji.com/order/cart.aspx",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200155180.png",
 "url": "https://m.9ji.com/user/",
 "activitytitle": ""
 },
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201703200155230.png",
 "url": "https://m.9ji.com/user/",
 "activitytitle": ""
 }
 ],
 "sidebarColor": [
 {
 "imgurl": "https://img2.ch999img.com/pic/clientimg/201607200135210.png",
 "url": "",
 "activitytitle": "245,245,245"
 }
 ],
 "freephone": "023-62923877",
 "searchKey": "iPhone 7 Plus",
 "isshow": 0,
 "jishiProduct": [
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/545/1af3b460d3de82.jpg?width=240&height=240",
 "ProductName": "iPhone 6s 国行 金色 64GB",
 "Price": 3200,
 "Link": "https://jishi.9ji.com/mProduct/Detail/6032"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/546/1c9507d7540f02.jpg?width=240&height=240",
 "ProductName": "iPhone 7 国行 黑色 128GB",
 "Price": 4300,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7090"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/545/1c8ce3d6d52776.jpg?width=240&height=240",
 "ProductName": "iPhone 7 Plus 港版 金色 128GB",
 "Price": 1,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7061"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/547/1c561bcdc29105.jpg?width=240&height=240",
 "ProductName": "华为 Mate8 国行 月光银 32GB",
 "Price": 1600,
 "Link": "https://jishi.9ji.com/mProduct/Detail/6900"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/542/1c93ef28f55f29.jpg?width=240&height=240",
 "ProductName": "iPhone 7 Plus 国行 玫瑰金 32GB",
 "Price": 4800,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7077"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/547/1c8f7f90154315.jpg?width=240&height=240",
 "ProductName": "iPhone 6s Plus 国行 金色 64GB",
 "Price": 3500,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7067"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/547/1c93ff390d193c.jpg?width=240&height=240",
 "ProductName": "乐视 乐2 国行 原力金 3GB+32GB",
 "Price": 900,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7078"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/544/1c93d7681058d3.jpg?width=240&height=240",
 "ProductName": "iPhone 6 Plus 其他渠道 深空灰 128GB",
 "Price": 3500,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7075"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/543/1c92d436c74814.jpg?width=240&height=240",
 "ProductName": "三星 Galaxy S7 edge 国行 金色 64GB",
 "Price": 2950,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7073"
 },
 {
 "ProductUrl": "https://img2.ch999img.com/newstatic/547/1c8e6dbd07cfe6.jpg?width=240&height=240",
 "ProductName": "iPhone 6 港版 金色 16GB",
 "Price": 2250,
 "Link": "https://jishi.9ji.com/mProduct/Detail/7063"
 }
 ],
 "newshop": {
 "show": 0,
 "link": "",
 "img": ""
 },
 "actMainHall": [],
 "homePopupAd": [],
 "payTip": ""
 }
 }
 */
public class JSONObjectTestView extends Activity implements View.OnClickListener {
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private TextView responseText;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
// 在这里进行UI操作，将结果显示到界面上
//                    responseText.setText(response);
                    parseJSONWithJSONObject(response);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_httpconnection);
        sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response);
        sendRequest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithHttpURLConnection();
        }
    }
    /*
    开启了一个子线程，然后在子线程里使用HttpURLConnection
发出一条 HTTP 请求，请求的目标地址就是百度的首页。接着利用 BufferedReader 对服务器
返回的流进行读取，并将结果存放到了一个 Message 对象中。这里为什么要使用 Message 对
象呢？当然是因为子线程中无法对 UI 进行操作了。我们希望可以将服务器返回的内容显示
到界面上，所以就创建了一个 Message 对象，并使用 Handler 将它发送出去。之后又在 Handler
的 handleMessage()方法中对这条 Message 进行处理，最终取出结果并设置到 TextView 上。
     */
    private void sendRequestWithHttpURLConnection() {
// 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    //一般只需 new 出一个 URL 对象，并传入 目标的网络地址，然后调用一下 openConnection()方法即可
                    URL url = new URL("https://10.0.2.2/get_data.xml");
                    connection = (HttpURLConnection) url.openConnection();
                    //得到了 HttpURLConnection 的实例之后，我们可以设置一下 HTTP 请求所使用的方法。常用的方法主要有两个，GET 和 POST
//                    connection.setRequestMethod("GET");
                    connection.setRequestMethod("POST");
                    // 设置允许输出
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    // 设置不用缓存
                    connection.setUseCaches(false);
                    // 设置维持长连接
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    // 设置文件字符集:
                    connection.setRequestProperty("Charset", "UTF-8");
                    //转换为字节数组
//                    byte[] data = (obj.toString()).getBytes();
                    // 设置文件长度
                    connection.setRequestProperty("Content-Length", String.valueOf(3000));
                    // 设置文件类型:
                    connection.setRequestProperty("contentType", "application/json");

                    /***************************************/
//                    提交数据给服务器
//                    只需要将 HTTP 请求的方法改成 POST，并在获取输入流之前把要提交的数据写出即可
//每条数据都要以键值对的形式存在，数据与数据之间用&符号隔开，比如说我们想要向服务器提交用户名和密码
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("username=admin&password=123456");
                    /***************************************/

                    //进行一些自由的定制了，比如设置连接超时、读取超时的毫秒数，以及服务器希望得到的一些消息头等。这部分内容根据自己的实际情况进行编写
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //再调用 getInputStream()方法就可以获取到服务器返回的输入流了
                    InputStream in = connection.getInputStream();
// 下面对获取到的输入流进行读取,输入流进行读取
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //调用 disconnect()方法将这个 HTTP 连接关闭掉
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    //调用 parseJSONWithJSONObject()方法来解析数据
    private void parseJSONWithJSONObject(String jsonData) {
        try {
//            由于我们在服务器中定义的是一个 JSON 数组，因此这里首先是将服务器返回的数据传入到了一个 JSONArray 对象中
            JSONArray jsonArray = new JSONArray(jsonData);
//            循环遍历这个 JSONArray，从中取出的每一个元素都是一个 JSONObject 对象
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                每个 JSONObject 对象中又会包含 id、name 和version 这些数据。接下来只需要调用 getString()方法将这些数据取出
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity", "id is " + id);
                Log.d("MainActivity", "name is " + name);
                Log.d("MainActivity", "version is " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

