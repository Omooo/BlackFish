package top.omooo.blackfish;

import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import top.omooo.blackfish.view.RecycleViewBanner;


/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";
    private RecycleViewBanner mRecycleViewBanner;

    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void initViews() {
//        startActivity(new Intent(this, ClassifyGoodsActivity.class));
        mRecycleViewBanner = findView(R.id.rvb_1);
        final List<Banner> banners = new ArrayList<>();
        banners.add(new Banner("https://i.loli.net/2018/04/07/5ac836b2d9a61.png"));
        banners.add(new Banner("https://i.loli.net/2018/04/07/5ac8373ebdbc1.png"));
        banners.add(new Banner("https://i.loli.net/2018/04/07/5ac836b2d9a61.png"));
        banners.add(new Banner("https://i.loli.net/2018/04/07/5ac8373ebdbc1.png"));
        banners.add(new Banner("https://i.loli.net/2018/04/07/5ac836b2d9a61.png"));
        banners.add(new Banner("https://i.loli.net/2018/04/07/5ac8373ebdbc1.png"));
        mRecycleViewBanner.setRvBannerData(banners);
        mRecycleViewBanner.setOnSwitchRvBannerListener(new RecycleViewBanner.OnSwitchRvBannerListener() {
            @Override
            public void switchBanner(int position, SimpleDraweeView simpleDraweeView) {
                simpleDraweeView.setImageURI(banners.get(position).getUrl());
            }
        });
        mRecycleViewBanner.setOnBannerClickListener(new RecycleViewBanner.OnRvBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(TestActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View view) {

    }

    @Override
    protected void initData() {

    }

    private class Banner {
        String url;

        public Banner(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
