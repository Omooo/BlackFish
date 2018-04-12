package top.omooo.blackfish.bean;

/**
 * Created by SSC on 2018/3/3.
 */

/**
 * 获取各种Json数据的Url接口
 */
public class UrlInfoBean {
    //首页商品信息
    public static final String homeGoodsUrl = "http://wooyun.site/blackfish/home_sort.json";

    //银行卡信息
    public static final String bankCardsInfo = "https://api.51datakey.com/conf/api/v3/banks/all";

    //商品分类信息
    public static final String classifyGoodsUrl = "http://www.wooyun.site/blackfish/classify_goods.json";

    //商城页数据
    public static final String mallGoodsUrl = "http://www.wooyun.site/blackfish/mall_goods.json";

    //理财 WebView
    public static final String financialUrl = "https://h5.shanhulicai.cn/main/#/home?entry=bar&_k=ra1im0";

    //Home页轮播图跳转Url
    public static final String[] homeBannerUrls = new String[]{"https://h5.blackfish.cn/m/promotion/1/136?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/4/133?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/1/138?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/3/106?memberId=18800209572&deviceId=f39498916c9b5cda"};

    public static final String[] hotGoodsHeaderUrls = new String[]{"https://h5.blackfish.cn/m/promotion/4/143?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/5/101?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/5/103?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/5/98?memberId=18800209572&deviceId=f39498916c9b5cda",
            "https://h5.blackfish.cn/m/promotion/5/95?memberId=18800209572&deviceId=f39498916c9b5cda"};

}
