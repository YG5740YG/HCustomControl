package newhome.baselibrary.Model;

import java.util.List;

/**
 * Created by 20160330 on 2017/3/30 0030.
 */

public class LimitBuy {

    /**
     * data : {"now":"2017-03-30 17:11:30","goods":[{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":5150,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20160909184720312.jpg","name":"iPhone 7双网通版亮黑色 128GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":5180},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":14,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20160929163037981.gif","name":"洛克苹果数据线 白色","url":"https://m.9ji.com/appRush.aspx","yanPrice":18},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":4799,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20161207132918629.jpg","name":"联想IdeaPad 710S银色 256G","url":"https://m.9ji.com/appRush.aspx","yanPrice":4899},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":29,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161027133133924.jpg","name":"出风口车载支架 灰色","url":"https://m.9ji.com/appRush.aspx","yanPrice":35},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":4199,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20161216173315771.jpg","name":"华为荣耀Magic 全网通玄金黑 64GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":4299},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":14,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161110154250337.jpg","name":"超大鼠标垫 盲僧","url":"https://m.9ji.com/appRush.aspx","yanPrice":19},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":1630,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20170109144311280.jpg","name":"诺基亚6 全网通黑色 32GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":1699},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":36,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/2017030711321343.png","name":"柠檬创意加湿器","url":"https://m.9ji.com/appRush.aspx","yanPrice":49},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":730,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/2016082413390911.jpg","name":"荣耀畅玩5+全网通版白色 16GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":780},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":49,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20170122141139896.jpg","name":"九机定制加长数据线","url":"https://m.9ji.com/appRush.aspx","yanPrice":75},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":49,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161008181609725.jpg","name":"闪迪U盘16G","url":"https://m.9ji.com/appRush.aspx","yanPrice":59},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":20,"Tag_Text":"","productImg":"https://img2.ch999img.com/pic/product/160x160/2017011715501033.jpg","name":"凡尚（fshang） iPhone 7 Plus 手机壳保护套七镀系列软壳","url":"https://m.9ji.com/appRush.aspx","yanPrice":49},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":39,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/2016110917522480.jpg","name":"华为 P9 plus手机壳 金色","url":"https://m.9ji.com/appRush.aspx","yanPrice":59},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":39,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20131227105457410.jpg","name":"iPhone 5/5s 手机壳 藕白色","url":"https://m.9ji.com/appRush.aspx","yanPrice":90},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":59,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20160929173417803.jpg","name":"迅捷无线路由器","url":"https://m.9ji.com/appRush.aspx","yanPrice":69},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":90,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161215134757125.jpg","name":"智能加湿器 天青色","url":"https://m.9ji.com/appRush.aspx","yanPrice":98},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":379,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161206131956146.jpg","name":"360行车记录仪套装升级版","url":"https://m.9ji.com/appRush.aspx","yanPrice":399}],"begintime":"2017-03-30 10:00:00","page":"https://m.9ji.com/appRush.aspx","title":"限时抢购"}
     * stats : 1
     */
    private DataEntity data;
    private int stats;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStats() {
        return stats;
    }

    public class DataEntity {
        /**
         * now : 2017-03-30 17:11:30
         * goods : [{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":5150,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20160909184720312.jpg","name":"iPhone 7双网通版亮黑色 128GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":5180},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":14,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20160929163037981.gif","name":"洛克苹果数据线 白色","url":"https://m.9ji.com/appRush.aspx","yanPrice":18},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":4799,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20161207132918629.jpg","name":"联想IdeaPad 710S银色 256G","url":"https://m.9ji.com/appRush.aspx","yanPrice":4899},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":29,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161027133133924.jpg","name":"出风口车载支架 灰色","url":"https://m.9ji.com/appRush.aspx","yanPrice":35},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":4199,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20161216173315771.jpg","name":"华为荣耀Magic 全网通玄金黑 64GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":4299},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":14,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161110154250337.jpg","name":"超大鼠标垫 盲僧","url":"https://m.9ji.com/appRush.aspx","yanPrice":19},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":1630,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/20170109144311280.jpg","name":"诺基亚6 全网通黑色 32GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":1699},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":36,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/2017030711321343.png","name":"柠檬创意加湿器","url":"https://m.9ji.com/appRush.aspx","yanPrice":49},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":730,"Tag_Text":"特价","productImg":"https://img2.ch999img.com/pic/product/160x160/2016082413390911.jpg","name":"荣耀畅玩5+全网通版白色 16GB","url":"https://m.9ji.com/appRush.aspx","yanPrice":780},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":49,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20170122141139896.jpg","name":"九机定制加长数据线","url":"https://m.9ji.com/appRush.aspx","yanPrice":75},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":49,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161008181609725.jpg","name":"闪迪U盘16G","url":"https://m.9ji.com/appRush.aspx","yanPrice":59},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":20,"Tag_Text":"","productImg":"https://img2.ch999img.com/pic/product/160x160/2017011715501033.jpg","name":"凡尚（fshang） iPhone 7 Plus 手机壳保护套七镀系列软壳","url":"https://m.9ji.com/appRush.aspx","yanPrice":49},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":39,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/2016110917522480.jpg","name":"华为 P9 plus手机壳 金色","url":"https://m.9ji.com/appRush.aspx","yanPrice":59},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":39,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20131227105457410.jpg","name":"iPhone 5/5s 手机壳 藕白色","url":"https://m.9ji.com/appRush.aspx","yanPrice":90},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":59,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20160929173417803.jpg","name":"迅捷无线路由器","url":"https://m.9ji.com/appRush.aspx","yanPrice":69},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":90,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161215134757125.jpg","name":"智能加湿器 天青色","url":"https://m.9ji.com/appRush.aspx","yanPrice":98},{"tagImg":"https://img2.ch999img.com/pic/clientimg/dijia.png","xianPrice":379,"Tag_Text":"超值","productImg":"https://img2.ch999img.com/pic/product/160x160/20161206131956146.jpg","name":"360行车记录仪套装升级版","url":"https://m.9ji.com/appRush.aspx","yanPrice":399}]
         * begintime : 2017-03-30 10:00:00
         * page : https://m.9ji.com/appRush.aspx
         * title : 限时抢购
         */
        private String now;
        private List<GoodsEntity> goods;
        private String begintime;
        private String page;
        private String title;

        public void setNow(String now) {
            this.now = now;
        }

        public void setGoods(List<GoodsEntity> goods) {
            this.goods = goods;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNow() {
            return now;
        }

        public List<GoodsEntity> getGoods() {
            return goods;
        }

        public String getBegintime() {
            return begintime;
        }

        public String getPage() {
            return page;
        }

        public String getTitle() {
            return title;
        }

        public class GoodsEntity {
            /**
             * tagImg : https://img2.ch999img.com/pic/clientimg/dijia.png
             * xianPrice : 5150
             * Tag_Text : 特价
             * productImg : https://img2.ch999img.com/pic/product/160x160/20160909184720312.jpg
             * name : iPhone 7双网通版亮黑色 128GB
             * url : https://m.9ji.com/appRush.aspx
             * yanPrice : 5180
             */
            private String tagImg;
            private int xianPrice;
            private String Tag_Text;
            private String productImg;
            private String name;
            private String url;
            private int yanPrice;

            public void setTagImg(String tagImg) {
                this.tagImg = tagImg;
            }

            public void setXianPrice(int xianPrice) {
                this.xianPrice = xianPrice;
            }

            public void setTag_Text(String Tag_Text) {
                this.Tag_Text = Tag_Text;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setYanPrice(int yanPrice) {
                this.yanPrice = yanPrice;
            }

            public String getTagImg() {
                return tagImg;
            }

            public int getXianPrice() {
                return xianPrice;
            }

            public String getTag_Text() {
                return Tag_Text;
            }

            public String getProductImg() {
                return productImg;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }

            public int getYanPrice() {
                return yanPrice;
            }
        }
    }
}
