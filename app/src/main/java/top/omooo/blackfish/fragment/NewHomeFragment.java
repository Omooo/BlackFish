package top.omooo.blackfish.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

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
    private static final String TAG = "HomeFragment";
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
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new HomeBannerAdapter(mContext,bannerImageUri));

            }
        };
        adapters.add(bannerAdapter);

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
}
