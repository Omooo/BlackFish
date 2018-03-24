package top.omooo.blackfish.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import top.omooo.blackfish.bean.BankCardsInfo;
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
    private JSONObject mJSONObject;
    private JSONArray mJSONArray;

    /**
     *
     * @param json
     * @param type  标识Json类别
     * @return
     */
    public List getDataFromJson(String json,int type) {
        try {
            if (type == 0) {
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
            } else if (type == 1) {
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
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
