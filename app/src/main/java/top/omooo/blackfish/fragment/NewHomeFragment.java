package top.omooo.blackfish.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import top.omooo.blackfish.R;
import top.omooo.blackfish.adapter.GeneralVLayoutAdapter;
import top.omooo.blackfish.adapter.HomeBannerAdapter;
import top.omooo.blackfish.bean.HomeSortInfo;
import top.omooo.blackfish.bean.HomeSortItemInfo;
import top.omooo.blackfish.bean.UrlInfoBean;
import top.omooo.blackfish.listener.OnNetResultListener;
import top.omooo.blackfish.listener.OnSetDataListener;
import top.omooo.blackfish.utils.AnalysisJsonUtil;
import top.omooo.blackfish.utils.OkHttpUtil;

/**
 * Created by SSC on 2018/3/16.
 */

public class NewHomeFragment extends BaseFragment{

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
    private static final String TAG = "NewHomeFragment";
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private DelegateAdapter delegateAdapter;

    private String[] bannerImageUri;

    private List<HomeSortInfo> mHomeSortInfos;
    private List<HomeSortItemInfo> mHomeSortItemInfos;

    private Drawable mHeaderDrawable;


    public static NewHomeFragment newInstance() {
        return new NewHomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void initViews() {
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#00000000"));

        mContext = getActivity();
        mRefreshLayout = findView(R.id.swipe_container);
        mRecyclerView = findView(R.id.rv_fragment_home_container);

        //RecycleView的子View
        addItemViews();

        //获取Header背景图
        mHeaderDrawable = ContextCompat.getDrawable(mContext, R.drawable.image_home_header_bg);
    }

    private void addItemViews() {
        layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);


        //加载标题
        final StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
        GeneralVLayoutAdapter adapter = new GeneralVLayoutAdapter(getActivity(), layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(65)), 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_title, parent, false);
                //初始背景为完全透明
                final RelativeLayout headerLayout = view.findViewById(R.id.rl_header_layout);
//                mHeaderDrawable.setAlpha(0);
//                headerLayout.setBackground(mHeaderDrawable);
                final TextView title = view.findViewById(R.id.tv_home_title);
                ImageView titleMessage = view.findViewById(R.id.iv_home_pager_message);
                titleMessage.setOnClickListener(new MyOnClick("iv_home_pager_message"));
                // TODO: 2018/3/20 处理HeaderLayout头布局颜色渐变，挖坑
//                mRecyclerView.addOnScrollListener(new OnScrollListener() {
//                    @Override
//                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                        super.onScrollStateChanged(recyclerView, newState);
//                    }
//
//                    @Override
//                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                        super.onScrolled(recyclerView, dx, dy);
//
//                        int headerHeight = headerLayout.getMeasuredHeight();
//                        if (mRecyclerView.computeVerticalScrollOffset() < headerHeight && dy > 0) {
//                            mHeaderDrawable.setAlpha(mRecyclerView.computeVerticalScrollOffset() / headerHeight);
//                            headerLayout.setBackground(mHeaderDrawable);
//                        } else {
//                            mHeaderDrawable.setAlpha(255);
//                            headerLayout.setBackground(mHeaderDrawable);
//                        }
//                    }
//                });
                return new MainViewHolder(view);
            }
        };
        adapters.add(adapter);

        //首页Banner轮播图
        SingleLayoutHelper bannerLayoutHelper = new SingleLayoutHelper();
        GeneralVLayoutAdapter bannerAdapter = new GeneralVLayoutAdapter(mContext, bannerLayoutHelper, 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.home_pager_banner_layout, parent, false);

                return new MainViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                final RecyclerView recyclerView = holder.itemView.findViewById(R.id.rv_home_banner);
                final LinearLayout linearLayout = holder.itemView.findViewById(R.id.linear_layout_points);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new HomeBannerAdapter(mContext,bannerImageUri));


                // TODO: 2018/3/17 RecycleView自动滑动
                recyclerView.addOnScrollListener(new OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.i(TAG, "newState: " + newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            int index = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            Log.i(TAG, "index: " + index);
                            if (index < 3) {
                                recyclerView.scrollToPosition(index + 1);
                            } else {
                                recyclerView.scrollToPosition(0);
                            }
                            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                ((ImageView) linearLayout.getChildAt(i)).setImageResource(i == index ? R.drawable.icon_home_banner_selected:R.drawable.icon_home_banner_unselected);
                            }
                        }

                    }

                    @Override
                    public void onScrolled(final RecyclerView recyclerView, final int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                    }
                });
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(1);
                    }
                }, 1500);
            }
        };
        adapters.add(bannerAdapter);

        //网格布局，用于加载主页两行网格布局
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        GeneralVLayoutAdapter gridAdapter = new GeneralVLayoutAdapter(getActivity(), gridLayoutHelper, 8) {
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_two_line_grid, parent, false));
            }

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                // TODO: 2018/3/18 最好别写死吧宝贝
                ImageView imageView = holder.itemView.findViewById(R.id.iv_home_one_grid_icon);
                if (position == 0) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_1"));
                } else if (position == 1) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_2"));
                } else if (position == 2) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_3"));
                } else if (position == 3) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_4"));
                }else if (position == 4) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_5"));
                }else if (position == 5) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_6"));
                }else if (position == 6) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_7"));
                }else if (position == 7) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_8"));

                }
            }
        };
        adapters.add(gridAdapter);

        // TODO: 2018/3/18 根据数据总数添加多少商品类别数
        for (int i = 0; i < 3; i++) {
            loadGoodsInfo(new OnSetDataListener() {
                @Override
                public void setData(View view, String typeId) {
                    switch (typeId) {
                        case "textTitle":
                            ((TextView) view).setText("2333");
                            break;
                    }

                }
            });

        }

        delegateAdapter.setAdapters(adapters);
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        bannerImageUri = new String[]{"https://i.loli.net/2018/03/16/5aabafe861905.jpg", "https://i.loli.net/2018/03/16/5aabb052ee397.jpg", "https://i.loli.net/2018/03/16/5aabafe861905.jpg", "https://i.loli.net/2018/03/16/5aabb052ee397.jpg"};
        mHomeSortInfos = new ArrayList<>();
        mHomeSortItemInfos = new ArrayList<>();
        final AnalysisJsonUtil jsonUtil = new AnalysisJsonUtil();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.homeGoodsUrl, new OnNetResultListener() {
            @Override
            public void onSuccessListener(String result) {
                mHomeSortInfos = jsonUtil.getDataFromJson(result, 0);
                Log.i(TAG, "onSuccessListener: " + "数据条数：" + mHomeSortInfos.size());
//                loadGoodsInfo(mHomeSortInfos);
                // TODO: 2018/3/18  数据持久化

            }

            @Override
            public void onFailureListener(String result) {
                Log.i(TAG, "onFailureListener: " + "网络请求失败" + result);
            }
        });
    }

    @Override
    public void processClick(View view) {

    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void loadGoodsInfo(final OnSetDataListener listener) {

        //通栏布局，用于加载商品的信息
        SingleLayoutHelper singleTitleHelper1 = new SingleLayoutHelper();
        GeneralVLayoutAdapter singleTitleAdapter1 = new GeneralVLayoutAdapter(getActivity(), singleTitleHelper1, 1) {
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_goods_layout, parent, false);
                //标题
                TextView textTitle = view.findViewById(R.id.tv_home_goods_title_text);
                listener.setData(textTitle, "textTitle");
                //标题的布局
                RelativeLayout titleLayout = view.findViewById(R.id.rl_goods_title_layout);
                titleLayout.setOnClickListener(new MyOnClick("goodsTitleLayout_1"));
                //标题下的大图片
                ImageView imageView = view.findViewById(R.id.iv_home_goods_big_image);

                imageView.setOnClickListener(new MyOnClick("iv_home_goods_big_image_1"));
                return new MainViewHolder(view);
            }
        };
        adapters.add(singleTitleAdapter1);

        //网格布局，加载Grid商品信息
        GridLayoutHelper goodsGridHelper = new GridLayoutHelper(2);
        goodsGridHelper.setVGap(1);
        goodsGridHelper.setHGap(1);
        GeneralVLayoutAdapter goodsGridAdapter = new GeneralVLayoutAdapter(getActivity(), goodsGridHelper, 4) {
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_goods_grid_item, parent, false));
            }

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                SimpleDraweeView imageview = holder.itemView.findViewById(R.id.iv_home_goods_grid_item);
                Uri uri = Uri.parse("https://i.loli.net/2018/03/03/5a9a73b8235a6.jpg");
                if (position == 0) {
                    imageview.setImageURI(uri);
                } else if (position == 1) {
                    imageview.setImageURI(uri);
                } else if (position == 2) {
                    imageview.setImageURI(uri);
                } else if (position == 3) {
                    imageview.setImageURI(uri);
                }
            }
        };
        adapters.add(goodsGridAdapter);
    }

    private class MyOnClick implements View.OnClickListener {

        private String id;

        public MyOnClick(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            switch (id) {
                case "iv_home_pager_message":
                    Log.i(TAG, "onClick: " + "标题 Message 被点击");
                    break;
                case "iv_home_one_grid_icon_1":
                    Log.i(TAG, "onClick: " + "首页GridItem 1被点击");
                    break;
                case "iv_home_one_grid_icon_2":
                    Log.i(TAG, "onClick: " + "首页GridItem 2被点击");
                    break;
                case "iv_home_one_grid_icon_3":
                    Log.i(TAG, "onClick: " + "首页GridItem 3被点击");
                    break;
                case "iv_home_one_grid_icon_4":
                    Log.i(TAG, "onClick: " + "首页GridItem 4被点击");
                    break;
                case "iv_home_one_grid_icon_5":
                    Log.i(TAG, "onClick: " + "首页GridItem 5被点击");
                    break;
                case "iv_home_one_grid_icon_6":
                    Log.i(TAG, "onClick: " + "首页GridItem 6被点击");
                    break;
                case "iv_home_one_grid_icon_7":
                    Log.i(TAG, "onClick: " + "首页GridItem 7被点击");
                    break;
                case "iv_home_one_grid_icon_8":
                    Log.i(TAG, "onClick: " + "首页GridItem 8被点击");
                    break;
                case "goodsTitleLayout_1":
                    Log.i(TAG, "onClick: " + "商品标题布局 1 被点击");
                    break;
                case "iv_home_goods_big_image_1":
                    Log.i(TAG, "onClick: " + "商品标题 1 下的大图片被点击");
            }
        }

    }


}
