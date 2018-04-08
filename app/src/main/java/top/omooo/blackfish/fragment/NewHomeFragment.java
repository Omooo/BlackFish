package top.omooo.blackfish.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import top.omooo.blackfish.bean.BannerInfo;
import top.omooo.blackfish.bean.HomeSortInfo;
import top.omooo.blackfish.bean.HomeSortItemInfo;
import top.omooo.blackfish.bean.UrlInfoBean;
import top.omooo.blackfish.listener.OnNetResultListener;
import top.omooo.blackfish.utils.AnalysisJsonUtil;
import top.omooo.blackfish.utils.OkHttpUtil;
import top.omooo.blackfish.view.RecycleViewBanner;

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

    private List<BannerInfo> mBannerInfos;

    private List<HomeSortInfo> mHomeSortInfos;
    private List<HomeSortItemInfo> mHomeSortItemInfos;
    private Handler mHandler;

    private Toolbar mToolbar;

    private LinearLayout mLinearGoodsLayout1;
    private LinearLayout mLinearGoodsLayout2;
    private LinearLayout mLinearGoodsLayout3;

    private ImageView mImageHeaderMsg;

    private RecycleViewBanner mRecycleViewBanner;

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

        mContext.getDrawable(R.drawable.icon_home_header_msg_black);
    }

    private void addItemViews() {
        layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);

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
                mRecycleViewBanner = holder.itemView.findViewById(R.id.rvb_home_header);
                //Banner数据
                mBannerInfos = new ArrayList<>();
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/06/5ac733bc51d0a.png"));
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/06/5ac735502effe.png"));
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/07/5ac8459fc9b6a.png"));
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/06/5ac7339ee876e.jpg"));
                mRecycleViewBanner.setRvBannerData(mBannerInfos);
                mRecycleViewBanner.setOnSwitchRvBannerListener(new RecycleViewBanner.OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, SimpleDraweeView simpleDraweeView) {
                        simpleDraweeView.setImageURI(mBannerInfos.get(position).getUrl());
                    }
                });
                mRecycleViewBanner.setOnBannerClickListener(new RecycleViewBanner.OnRvBannerClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
                    }
                });
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

                // TODO: 2018/3/18 加载网络图片
                ImageView imageView = holder.itemView.findViewById(R.id.iv_home_one_grid_icon);
                TextView textView = holder.itemView.findViewById(R.id.title);
                if (position == 0) {
                    textView.setText("充值中心");
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_1"));
                } else if (position == 1) {
                    textView.setText("手机通讯");
                    imageView.setImageResource(R.drawable.icon_phone);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_2"));
                } else if (position == 2) {
                    textView.setText("电影票");
                    imageView.setImageResource(R.drawable.icon_movie);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_3"));
                } else if (position == 3) {
                    textView.setText("全民游戏");
                    imageView.setImageResource(R.drawable.icon_game);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_4"));
                }else if (position == 4) {
                    textView.setText("代还信用卡");
                    imageView.setImageResource(R.drawable.icon_pay_card);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_5"));
                }else if (position == 5) {
                    textView.setText("现金分期");
                    imageView.setImageResource(R.drawable.icon_cash_fenqi);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_6"));
                }else if (position == 6) {
                    textView.setText("办信用卡");
                    imageView.setImageResource(R.drawable.icon_ban_card);
                    imageView.setOnClickListener(new MyOnClick("iv_home_one_grid_icon_7"));
                }else if (position == 7) {
                    textView.setText("全部分类");
                    imageView.setImageResource(R.drawable.icon_all_classify);
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

        //商品数据
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
