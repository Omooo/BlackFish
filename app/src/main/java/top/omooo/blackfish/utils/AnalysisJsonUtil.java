package top.omooo.blackfish.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import top.omooo.blackfish.bean.BankCardsInfo;
import top.omooo.blackfish.bean.ClassifyGoodsInfo;
import top.omooo.blackfish.bean.ClassifyGridInfo;
import top.omooo.blackfish.bean.HomeSortInfo;
import top.omooo.blackfish.bean.HomeSortItemInfo;

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

    private JSONObject mJSONObject;
    private JSONArray mJSONArray;

    private static final int HOME_GOODS_INFO = 0;
    private static final int BANK_CARD_INFO = 1;
    private static final int CLASSIFY_GOODS_INFO = 2;

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
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
