package top.omooo.blackfish.MallPagerActivity;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.adapter.ClassifyCommonAdapter;
import top.omooo.blackfish.adapter.ClassifyTitleAdapter;
import top.omooo.blackfish.bean.ClassifyGoodsInfo;
import top.omooo.blackfish.bean.ClassifyGridInfo;
import top.omooo.blackfish.bean.UrlInfoBean;
import top.omooo.blackfish.listener.OnNetResultListener;
import top.omooo.blackfish.utils.AnalysisJsonUtil;
import top.omooo.blackfish.utils.OkHttpUtil;
import top.omooo.blackfish.view.GridViewForScroll;

/**
 * Created by SSC on 2018/4/5.
 */

public class ClassifyGoodsActivity extends BaseActivity {

    private Context mContext;

    private RecyclerView mRecyclerViewLeft;
    private SimpleDraweeView mDraweeViewHeader;
    private GridViewForScroll mGridCommon;
    private GridViewForScroll mGridHot;

    private ArrayList<String> mListTitles;
    private List<ClassifyGridInfo> mGridInfosCommon;
    private List<ClassifyGridInfo> mGridInfosHot;
    private String headerImageUrl;

    private List<ClassifyGoodsInfo> mClassifyGoodsInfos;


    private static final String TAG = "ClassifyGoodsActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_classify_goods_layout;
    }

    @Override
    public void initViews() {
        mContext = ClassifyGoodsActivity.this;

        mRecyclerViewLeft = findView(R.id.rv_classify_goods_left_title);
        mRecyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewLeft.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mDraweeViewHeader = findView(R.id.iv_classify_goods_details_header);
        mGridCommon = findView(R.id.gv_classify_common);
        mGridHot = findView(R.id.gv_classify_hot);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View view) {

    }

    @Override
    protected void initData() {

        // TODO: 2018/4/5 数据懒加载 
        mClassifyGoodsInfos = new ArrayList<>();
        mListTitles = new ArrayList<>();
        mGridInfosCommon = new ArrayList<>();
        mGridInfosHot = new ArrayList<>();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.classifyGoodsUrl, new OnNetResultListener() {
            @Override
            public void onSuccessListener(String result) {
                AnalysisJsonUtil jsonUtil = new AnalysisJsonUtil();
                mClassifyGoodsInfos = jsonUtil.getDataFromJson(result, 2);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: " + mClassifyGoodsInfos.size());
                        for (int i = 0; i < mClassifyGoodsInfos.size(); i++) {
                            String title = mClassifyGoodsInfos.get(i).getTitle();
                            Log.i(TAG, "onSuccessListener: " + title);
                            mListTitles.add(title);
                            headerImageUrl = mClassifyGoodsInfos.get(i).getHeaderImageUrl();
                            for (int j = 0; j < mClassifyGoodsInfos.get(i).getGridImageUrls1().size(); j++) {
                                mGridInfosCommon.add(mClassifyGoodsInfos.get(i).getGridImageUrls1().get(j));
                            }
                            for (int j = 0; j < mClassifyGoodsInfos.get(i).getGridImageUrls2().size(); j++) {
                                mGridInfosHot.add(mClassifyGoodsInfos.get(i).getGridImageUrls2().get(j));
                            }
                        }
                        mDraweeViewHeader.setImageURI(headerImageUrl);
                        mRecyclerViewLeft.setAdapter(new ClassifyTitleAdapter(mContext, mListTitles));
                        mGridCommon.setAdapter(new ClassifyCommonAdapter(mContext, mGridInfosCommon));
                        mGridHot.setAdapter(new ClassifyCommonAdapter(mContext, mGridInfosHot));
                    }
                });
            }

            @Override
            public void onFailureListener(String result) {
                Log.i(TAG, "onFailureListener: 网络请求失败");
            }
        });
    }
}
