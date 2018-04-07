package top.omooo.blackfish.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import top.omooo.blackfish.bean.BankCardsInfo;
import top.omooo.blackfish.bean.BannerInfo;
import top.omooo.blackfish.bean.ClassifyGoodsInfo;
import top.omooo.blackfish.bean.ClassifyGridInfo;
import top.omooo.blackfish.bean.GridInfo;
import top.omooo.blackfish.bean.HomeSortInfo;
import top.omooo.blackfish.bean.HomeSortItemInfo;
import top.omooo.blackfish.bean.MallGoodsInfo;
import top.omooo.blackfish.bean.MallGoodsItemInfo;
import top.omooo.blackfish.bean.MallPagerInfo;

/**
 * Created by SSC on 2018/3/3.
 */

/**
 * 解析Json数据工具类
 */
public class AnalysisJsonUtil {

    private List<HomeSortInfo> mHomeSortInfos;
    private List<HomeSortItemInfo> mHomeSortItemInfos;
    private List<BankCardsInfo> mBankCardsInfos;

    private List<ClassifyGoodsInfo> mClassifyGoodsInfos;

    private List<MallPagerInfo> mMallPagerInfos;

    private JSONObject mJSONObject;
    private JSONArray mJSONArray;

    private static final int HOME_GOODS_INFO = 0;
    private static final int BANK_CARD_INFO = 1;
    private static final int CLASSIFY_GOODS_INFO = 2;
    private static final int MALL_GOODS_INFO = 3;

    private static final String TAG = "AnalysisJsonUtil";

    /**
     *
     * @param json
     * @param type  标识Json类别
     * @return
     */
    public List getDataFromJson(String json,int type) {
        try {
            if (type == HOME_GOODS_INFO) {
                //首页的商品信息
                mJSONObject = new JSONObject(json);
                mHomeSortInfos = new ArrayList<>();
                mHomeSortItemInfos = new ArrayList<>();
                mJSONArray = mJSONObject.getJSONArray("home_sort");
                for (int i = 0; i < mJSONArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJSONArray.get(i);
                    String title = jsonObject.getString("title");
                    String sortImageUrl = jsonObject.getString("sortImageUrl");
                    JSONArray jsonArray = jsonObject.getJSONArray("goods");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        String id = object.getString("id");
                        String goodsImageUrl = object.getString("goodsImageUrl");
                        mHomeSortItemInfos.add(new HomeSortItemInfo(id, goodsImageUrl));
                    }
                    mHomeSortInfos.add(new HomeSortInfo(title, sortImageUrl, mHomeSortItemInfos));
                }
                return mHomeSortInfos;
            } else if (type == BANK_CARD_INFO) {
                //银行卡信息
                mBankCardsInfos = new ArrayList<>();
                mJSONArray = new JSONArray(json);
                mJSONObject = (JSONObject) mJSONArray.get(0);
                JSONArray jsonArray = mJSONObject.getJSONArray("bank_list");
                JSONObject jsonObject = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = (JSONObject) jsonArray.get(i);
                    String abbr = jsonObject.getString("abbr");
                    String name = jsonObject.getString("name");
                    String logo_url = jsonObject.getString("logo_uri");
                    mBankCardsInfos.add(new BankCardsInfo(logo_url, name, abbr));
                }
                return mBankCardsInfos;
            } else if (type == CLASSIFY_GOODS_INFO) {
                //商品分类信息
                mClassifyGoodsInfos = new ArrayList<>();
                mJSONObject = new JSONObject(json);
                mJSONArray = mJSONObject.getJSONArray("classifyTitle");
                for (int i = 0; i < mJSONArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJSONArray.get(i);
                    String title = jsonObject.getString("title");
                    String headerImageUrl = jsonObject.getString("headerImageUrl");
                    String subtitle1 = jsonObject.getString("subTitle1");
                    String subtitle2 = jsonObject.getString("subTitle");
                    JSONArray jsonArray1 = ((JSONObject) mJSONArray.get(i)).getJSONArray("gridImageUrls1");

                    List<ClassifyGridInfo> mGridInfos1=new ArrayList<>();
                    List<ClassifyGridInfo> mGridInfos2=new ArrayList<>();

                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray1.get(j);
                        int id = jsonObject1.getInt("id");
                        String desc = jsonObject1.getString("desc");
                        String imageUrl = jsonObject1.getString("iamgeUrl");
                        mGridInfos1.add(new ClassifyGridInfo(id, desc, imageUrl));
                    }
                    JSONArray jsonArray2 = ((JSONObject) mJSONArray.get(i)).getJSONArray("gridImageUrls2");
                    for (int j = 0; j < jsonArray2.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray2.get(j);
                        int id = jsonObject1.getInt("id");
                        String desc = jsonObject1.getString("desc");
                        String imageUrl = jsonObject1.getString("iamgeUrl");
                        mGridInfos2.add(new ClassifyGridInfo(id, desc, imageUrl));
                    }
                    mClassifyGoodsInfos.add(new ClassifyGoodsInfo(title, headerImageUrl, subtitle1, subtitle2, mGridInfos1, mGridInfos2));
                }
                return mClassifyGoodsInfos;
            } else if (type == MALL_GOODS_INFO) {
                mMallPagerInfos = new ArrayList<>();
                List<BannerInfo> bannerInfos = new ArrayList<>();
                List<GridInfo> gridInfos = new ArrayList<>();
                List<BannerInfo> goodsInfos = new ArrayList<>();
                List<MallGoodsInfo> mallGoodsInfos = new ArrayList<>();
                mJSONObject = new JSONObject(json);

                String singleImageUrl = mJSONObject.getString("single_image");

                mJSONArray = mJSONObject.getJSONArray("banners");
                for (int i = 0; i < mJSONArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) mJSONArray.get(i);
                    bannerInfos.add(new BannerInfo(jsonObject.getString("banner_url")));
                }

                JSONArray jsonArrayGrid = mJSONObject.getJSONArray("classifyGridItems");
                for (int i = 0; i < jsonArrayGrid.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArrayGrid.get(i);
                    gridInfos.add(new GridInfo(jsonObject.getString("desc"), jsonObject.getString("grid_url")));
                }

                JSONArray jsonArrayGoods = mJSONObject.getJSONArray("four_goods_image");
                for (int i = 0; i < jsonArrayGoods.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArrayGoods.get(i);
                    goodsInfos.add(new BannerInfo(jsonObject.getString("four_image_url")));
                }

                JSONArray jsonArrayHotSorts = mJSONObject.getJSONArray("hotSort");
                for (int i = 0; i < jsonArrayHotSorts.length(); i++) {
                    List<MallGoodsItemInfo> mallGoodsItemInfos = new ArrayList<>();

                    JSONObject jsonObject = (JSONObject) jsonArrayHotSorts.get(i);
                    String headerImageUrl = jsonObject.getString("headerBigImage");
                    JSONArray jsonArray = jsonObject.getJSONArray("threeGoods");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(j);
                        mallGoodsItemInfos.add(new MallGoodsItemInfo(jsonObject1.getString("goodsItemImage"), jsonObject1.getString("desc"), jsonObject1.getDouble("singePrice"), jsonObject1.getInt("numPeriods"), jsonObject1.getDouble("price")));
                    }
                    mallGoodsInfos.add(new MallGoodsInfo(headerImageUrl, mallGoodsItemInfos));
                }
                mMallPagerInfos.add(new MallPagerInfo(bannerInfos, gridInfos, singleImageUrl, goodsInfos, mallGoodsInfos));
                return mMallPagerInfos;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
