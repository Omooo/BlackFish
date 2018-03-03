package top.omooo.blackfish.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import top.omooo.blackfish.R;
import top.omooo.blackfish.adapter.BannerAdapter;
import top.omooo.blackfish.adapter.GeneralVLayoutAdapter;
import top.omooo.blackfish.bean.BannerItemInfo;
import top.omooo.blackfish.bean.HomeSortInfo;
import top.omooo.blackfish.bean.UrlInfoBean;
import top.omooo.blackfish.listener.OnNetResultListener;
import top.omooo.blackfish.utils.AnalysisJsonUtil;
import top.omooo.blackfish.utils.OkHttpUtil;

/**
 * Created by Omooo on 2018/2/25.
 */

public class HomeFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
    private static final String TAG = "HomeFragment";
    private UrlInfoBean mUrlInfoBean = new UrlInfoBean();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        mRefreshLayout = view.findViewById(R.id.swipe_container);
        mRecyclerView = view.findViewById(R.id.rv_fragment_home_container);

        loadPager();
        return view;
    }

    //加载布局
    private void loadPager() {

        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);

        //吸边布局，用于加载标题
        final StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
        GeneralVLayoutAdapter adapter = new GeneralVLayoutAdapter(getActivity(), layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(50)), 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_title, parent, false));
            }
        };
        adapters.add(adapter);

        //通栏布局，用于加载CardView
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        GeneralVLayoutAdapter singleAdapter = new GeneralVLayoutAdapter(getActivity(), singleLayoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_cardview, parent, false);
                TextView textView = view.findViewById(R.id.tv_card_start_subtitle2);
                textView.setText(getRelativeSizeText());

                return new MainViewHolder(view);
            }

        };
        adapters.add(singleAdapter);

        //网格布局，用于加载主页一行网格布局
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        GeneralVLayoutAdapter gridAdapter = new GeneralVLayoutAdapter(getActivity(), gridLayoutHelper, 4) {
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_one_line_grid, parent, false));
            }

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imageView = holder.itemView.findViewById(R.id.iv_home_one_grid_icon);
                if (position == 0) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                } else if (position == 1) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                } else if (position == 2) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                } else if (position == 3) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                }
            }
        };
        adapters.add(gridAdapter);

        //通栏布局，用于加载home页banner
        SingleLayoutHelper singleLayoutHelper1 = new SingleLayoutHelper();
        GeneralVLayoutAdapter singleAdapter1 = new GeneralVLayoutAdapter(getActivity(), singleLayoutHelper1, 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_banner_layout, parent, false));

            }

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final RecyclerView recyclerView = holder.itemView.findViewById(R.id.rv_home_banner);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);

                List<BannerItemInfo> itemInfos = new ArrayList<>();
                itemInfos.add(new BannerItemInfo(R.drawable.home_banner_1, R.drawable.icon_home_banner_selected,R.drawable.icon_home_banner_unselected));
                itemInfos.add(new BannerItemInfo(R.drawable.home_banner_2, R.drawable.icon_home_banner_unselected, R.drawable.icon_home_banner_selected));
                recyclerView.setAdapter(new BannerAdapter(getActivity(), itemInfos));
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                            recyclerView.scrollToPosition(1);
                        } else {
                            recyclerView.scrollToPosition(0);
                        }
                    }
                },2500);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        Log.i(TAG, "onScrollStateChanged:");
//                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                            recyclerView.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
//                                        recyclerView.scrollToPosition(1);
//                                    } else {
//                                        recyclerView.scrollToPosition(0);
//                                    }
//                                }
//                            },2500);
//                        }
                    }

                    @Override
                    public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Log.i(TAG, "onScrolled:");
                        if (dx > 0) {
                            recyclerView.scrollToPosition(1);
                        } else {
                            recyclerView.scrollToPosition(0);
                        }
                        // TODO: 2018/3/2 逻辑待优化 RecycleView 循环滑动
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                                    recyclerView.scrollToPosition(1);
                                } else {
                                    recyclerView.scrollToPosition(0);
                                }
                            }
                        },5000);
                    }
                });
            }
        };
        adapters.add(singleAdapter1);

        //通栏布局，用于加载商品的主题
        SingleLayoutHelper singleTitleHelper1 = new SingleLayoutHelper();
        GeneralVLayoutAdapter singleTitleAdapter1 = new GeneralVLayoutAdapter(getActivity(), singleTitleHelper1, 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_goods_title_layout, parent, false);
                final TextView textTitle = view.findViewById(R.id.tv_home_goods_title_text);
                textTitle.setText("23333");
                OkHttpUtil.getInstance().startGet(mUrlInfoBean.homeGoodsUrl, new OnNetResultListener() {
                    @Override
                    public void onSuccessListener(String result) {
                        AnalysisJsonUtil jsonUtil = new AnalysisJsonUtil();
                        List<HomeSortInfo> infoList = jsonUtil.getDataFromJson(result, 0);
                        final String title = infoList.get(0).getTitle();
                        textTitle.post(new Runnable() {
                            @Override
                            public void run() {
                                textTitle.setText(title);
                            }
                        });
                    }

                    @Override
                    public void onFailureListener(String result) {
                        Log.i(TAG, "onFailureListener: " + result);
                    }
                });
                return new MainViewHolder(view);
            }
        };
        adapters.add(singleTitleAdapter1);

        delegateAdapter.setAdapters(adapters);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private SpannableString getRelativeSizeText() {
        SpannableString spannableString = new SpannableString("50,000.00");
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.5f);
        spannableString.setSpan(sizeSpan, 7, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
