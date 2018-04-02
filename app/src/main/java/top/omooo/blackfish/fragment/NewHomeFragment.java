package top.omooo.blackfish.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
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
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
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

    private String[] bannerImageUri={"https://i.loli.net/2018/03/20/5ab10bb02ca5e.jpg","https://i.loli.net/2018/03/20/5ab10bbf58326.jpg","https://i.loli.net/2018/03/20/5ab10bb02ca5e.jpg","https://i.loli.net/2018/03/20/5ab10bbf58326.jpg"};

    private List<HomeSortInfo> mHomeSortInfos;
    private List<HomeSortItemInfo> mHomeSortItemInfos;
    private Handler mHandler;

    private Toolbar mToolbar;

    private Drawable mHeaderDrawable;

    private LinearLayout mLinearGoodsLayout1;
    private LinearLayout mLinearGoodsLayout2;
    private LinearLayout mLinearGoodsLayout3;

    private ImageView mImageHeaderMsg;

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

        mImageHeaderMsg = findView(R.id.iv_home_header_msg);

        mToolbar = findView(R.id.toolbar_home);
        mToolbar.getBackground().setAlpha(0);

        RelativeLayout headerLayout = (RelativeLayout) mToolbar.getChildAt(0);


        TextView textTitle = (TextView) headerLayout.getChildAt(1);
        textTitle.setVisibility(View.GONE);

        mContext = getActivity();
        mRefreshLayout = findView(R.id.swipe_container);
        mRecyclerView = findView(R.id.rv_fragment_home_container);

        //RecycleView的子View
        addItemViews();

    }

    private void addItemViews() {
        layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);


//        //加载标题
//        final StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
//        GeneralVLayoutAdapter adapter = new GeneralVLayoutAdapter(getActivity(), layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(65)), 1){
//            @Override
//            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_title, parent, false);
//                final TextView title = view.findViewById(R.id.tv_home_title);
//                ImageView titleMessage = view.findViewById(R.id.iv_home_pager_message);
//                titleMessage.setOnClickListener(new MyOnClick("iv_home_pager_message"));
//                return new MainViewHolder(view);
//            }
//        };
//        adapters.add(adapter);

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
            loadGoodsInfo(i);
            Log.i(TAG, "addItemViews: 加载布局：" + i);
        }

        loadData();

        delegateAdapter.setAdapters(adapters);
    }

    private void loadData() {

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RelativeLayout titleLayout;
                TextView textView;
                SimpleDraweeView bigTitleImage;
                LinearLayout linearLayoutItem1;
                LinearLayout linearLayoutItem2;
                switch (msg.what) {
                    case 10001:
                        HomeSortInfo homeSortInfo1 = (HomeSortInfo) msg.getData().get("homeSortInfo");
                        Log.i(TAG, "handleMessage: 触发 10001   " + homeSortInfo1.getTitle());

                        //标题
                        titleLayout = (RelativeLayout) mLinearGoodsLayout1.getChildAt(0);
                        titleLayout.setOnClickListener(new MyOnClick("goodsTitleLayout_1"));
                        textView = (TextView) titleLayout.getChildAt(1);
                        textView.setText(homeSortInfo1.getTitle());

                        //标题图片
                        bigTitleImage = (SimpleDraweeView) mLinearGoodsLayout1.getChildAt(1);
                        bigTitleImage.setOnClickListener(new MyOnClick("iv_home_goods_big_image_1"));
                        bigTitleImage.setImageURI(homeSortInfo1.getSortImageUrl());

                        //GridItem
                        mHomeSortItemInfos = homeSortInfo1.getItemInfos();
                        Log.i(TAG, "handleMessage: sortItemsSize " + mHomeSortItemInfos.size());
                        linearLayoutItem1 = (LinearLayout) mLinearGoodsLayout1.getChildAt(2);
                        for (int i = 0; i < linearLayoutItem1.getChildCount(); i++) {
                            SimpleDraweeView imageView = (SimpleDraweeView) linearLayoutItem1.getChildAt(i);
                            HomeSortItemInfo itemInfo = mHomeSortItemInfos.get(i);
                            imageView.setImageURI(itemInfo.getGoodsImageUrl());
                        }
                        linearLayoutItem2 = (LinearLayout) mLinearGoodsLayout1.getChildAt(3);
                        for (int i = 0; i < linearLayoutItem2.getChildCount(); i++) {
                            SimpleDraweeView imageView = (SimpleDraweeView) linearLayoutItem2.getChildAt(i);
                            HomeSortItemInfo itemInfo = mHomeSortItemInfos.get(i + 2);
                            imageView.setImageURI(itemInfo.getGoodsImageUrl());
                        }
                        break;
                    case 10002:
                        HomeSortInfo homeSortInfo2 = mHomeSortInfos.get(1);
                        Log.i(TAG, "handleMessage: 触发 10002   " + homeSortInfo2.getTitle());
                        // TODO: 2018/3/25 /*********空指针************/ 
                        if (mLinearGoodsLayout2 == null) {
                            Log.i(TAG, "handleMessage: mLinearGoodsLayout2 为空");
                            break;
                        }
                        //标题
                        titleLayout = (RelativeLayout) mLinearGoodsLayout2.getChildAt(0);
                        titleLayout.setOnClickListener(new MyOnClick("goodsTitleLayout_1"));
                        textView = (TextView) titleLayout.getChildAt(1);
                        textView.setText(homeSortInfo2.getTitle());

                        //标题图片
                        bigTitleImage = (SimpleDraweeView) mLinearGoodsLayout1.getChildAt(1);
                        bigTitleImage.setOnClickListener(new MyOnClick("iv_home_goods_big_image_1"));
                        bigTitleImage.setImageURI(homeSortInfo2.getSortImageUrl());

                        //GridItem
                        mHomeSortItemInfos = homeSortInfo2.getItemInfos();
                        Log.i(TAG, "handleMessage: sortItemsSize " + mHomeSortItemInfos.size());
                        linearLayoutItem1 = (LinearLayout) mLinearGoodsLayout1.getChildAt(2);
                        for (int i = 0; i < linearLayoutItem1.getChildCount(); i++) {
                            SimpleDraweeView imageView = (SimpleDraweeView) linearLayoutItem1.getChildAt(i);
                            HomeSortItemInfo itemInfo = mHomeSortItemInfos.get(i);
                            imageView.setImageURI(itemInfo.getGoodsImageUrl());
                        }
                        linearLayoutItem2 = (LinearLayout) mLinearGoodsLayout1.getChildAt(3);
                        for (int i = 0; i < linearLayoutItem2.getChildCount(); i++) {
                            SimpleDraweeView imageView = (SimpleDraweeView) linearLayoutItem2.getChildAt(i);
                            HomeSortItemInfo itemInfo = mHomeSortItemInfos.get(i + 2);
                            imageView.setImageURI(itemInfo.getGoodsImageUrl());
                        }
                        break;
                    case 10003:
                        Log.i(TAG, "handleMessage: 触发 10003");
                        HomeSortInfo homeSortInfo3 = mHomeSortInfos.get(2);

                        //标题
                        titleLayout = (RelativeLayout) mLinearGoodsLayout1.getChildAt(0);
                        titleLayout.setOnClickListener(new MyOnClick("goodsTitleLayout_1"));
                        textView = (TextView) titleLayout.getChildAt(1);
                        textView.setText(homeSortInfo3.getTitle());

                        //标题图片
                        bigTitleImage = (SimpleDraweeView) mLinearGoodsLayout1.getChildAt(1);
                        bigTitleImage.setOnClickListener(new MyOnClick("iv_home_goods_big_image_1"));
                        bigTitleImage.setImageURI(homeSortInfo3.getSortImageUrl());

                        //GridItem
                        mHomeSortItemInfos = homeSortInfo3.getItemInfos();
                        Log.i(TAG, "handleMessage: sortItemsSize " + mHomeSortItemInfos.size());
                        linearLayoutItem1 = (LinearLayout) mLinearGoodsLayout1.getChildAt(2);
                        for (int i = 0; i < linearLayoutItem1.getChildCount(); i++) {
                            SimpleDraweeView imageView = (SimpleDraweeView) linearLayoutItem1.getChildAt(i);
                            HomeSortItemInfo itemInfo = mHomeSortItemInfos.get(i);
                            imageView.setImageURI(itemInfo.getGoodsImageUrl());
                        }
                        linearLayoutItem2 = (LinearLayout) mLinearGoodsLayout1.getChildAt(3);
                        for (int i = 0; i < linearLayoutItem2.getChildCount(); i++) {
                            SimpleDraweeView imageView = (SimpleDraweeView) linearLayoutItem2.getChildAt(i);
                            HomeSortItemInfo itemInfo = mHomeSortItemInfos.get(i + 2);
                            imageView.setImageURI(itemInfo.getGoodsImageUrl());
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }

        });
    }


    @Override
    public void initListener() {
        mImageHeaderMsg.setOnClickListener(this);
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
                for (int i = 0; i < 2; i++) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("homeSortInfo", (Serializable) mHomeSortInfos.get(i));
                    Message message = Message.obtain();
                    message.what = 10001 + i;
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onFailureListener(String result) {
                Log.i(TAG, "onFailureListener: " + "网络请求失败" + result);
            }
        });
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home_header_msg:
                Log.i(TAG, "processClick: 标题 Message 被点击");
                break;
            default:break;
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void loadGoodsInfo(int index) {

        if (index == 0) {
            Log.i(TAG, "loadGoodsInfo: " + index);
            SingleLayoutHelper singleTitleHelper1 = new SingleLayoutHelper();
            GeneralVLayoutAdapter singleTitleAdapter1 = new GeneralVLayoutAdapter(getActivity(), singleTitleHelper1, 1) {
                @Override
                public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_goods_layout, parent, false);
                    mLinearGoodsLayout1 = view.findViewById(R.id.linear_home_pager_goods_layout);
                    return new MainViewHolder(view);
                }
            };
            adapters.add(singleTitleAdapter1);
        } else if (index == 1) {
            Log.i(TAG, "loadGoodsInfo: " + index);
            SingleLayoutHelper singleTitleHelper1 = new SingleLayoutHelper();
            GeneralVLayoutAdapter singleTitleAdapter2 = new GeneralVLayoutAdapter(getActivity(), singleTitleHelper1, 1) {
                @Override
                public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_goods_layout, parent, false);
                    mLinearGoodsLayout2 = view.findViewById(R.id.linear_home_pager_goods_layout);
                    return new MainViewHolder(view);
                }
            };
            adapters.add(singleTitleAdapter2);
        } else {
            return;
        }

    }

    private class MyOnClick implements View.OnClickListener {

        private String id;

        public MyOnClick(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            switch (id) {
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
                    break;
                case "goodsItem1":
                    Log.i(TAG, "onClick: " + "商品标题 goodItem1被点击");
                    break;
                default:break;
            }
        }

    }


}
