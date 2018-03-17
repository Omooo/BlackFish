package top.omooo.blackfish.fragment;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.LinkedList;
import java.util.List;

import top.omooo.blackfish.R;
import top.omooo.blackfish.adapter.GeneralVLayoutAdapter;
import top.omooo.blackfish.adapter.HomeBannerAdapter;

/**
 * Created by SSC on 2018/3/16.
 */

public class NewHomeFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
    private static final String TAG = "NewHomeFragment";
    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;
    private DelegateAdapter delegateAdapter;

    private String[] bannerImageUri;

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
    }

    private void addItemViews() {
        layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);


        //吸边布局，用于加载标题
        final StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
        GeneralVLayoutAdapter adapter = new GeneralVLayoutAdapter(getActivity(), layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(65)), 1){
            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_pager_title, parent, false);
                final TextView title = view.findViewById(R.id.tv_home_title);
                //消息中心 icon 点击事件
                view.findViewById(R.id.iv_home_pager_message).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "onClick: 消息中心 icon 点击事件");
                    }
                });
                mRecyclerView.addOnScrollListener(new OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 0) {
                            title.setText("测试");
                        }
                    }
                });
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

        //网格布局，用于加载主页一行网格布局
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        GeneralVLayoutAdapter gridAdapter = new GeneralVLayoutAdapter(getActivity(), gridLayoutHelper, 8) {
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
                }else if (position == 4) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                }else if (position == 5) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                }else if (position == 6) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                }else if (position == 7) {
                    imageView.setImageResource(R.drawable.icon_voucher_center);
                }
            }
        };
        adapters.add(gridAdapter);

        delegateAdapter.setAdapters(adapters);
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        bannerImageUri = new String[]{"https://i.loli.net/2018/03/16/5aabafe861905.jpg", "https://i.loli.net/2018/03/16/5aabb052ee397.jpg", "https://i.loli.net/2018/03/16/5aabafe861905.jpg", "https://i.loli.net/2018/03/16/5aabb052ee397.jpg"};

    }

    @Override
    public void processClick(View view) {

    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
