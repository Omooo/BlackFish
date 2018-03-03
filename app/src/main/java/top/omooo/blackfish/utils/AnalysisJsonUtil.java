package top.omooo.blackfish.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            mJSONObject = new JSONObject(json);
            if (type == 0) {
                //首页的商品信息
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
