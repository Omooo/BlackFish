package top.omooo.blackfish.bean;

/**
 * Created by SSC on 2018/4/7.
 */

public class MallGoodsItemInfo {
    private String imageUrl;    //图片地址
    private String desc;    //描述信息
    private double singlePrice;    //分期单价
    private int periods;    //期数
    private double price;   //总价

    public MallGoodsItemInfo(String imageUrl, String desc, double singlePrice, int periods, double price) {
        this.imageUrl = imageUrl;
        this.desc = desc;
        this.singlePrice = singlePrice;
        this.periods = periods;
        this.price = price;
    }
}
