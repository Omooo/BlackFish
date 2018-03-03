package top.omooo.blackfish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;

import top.omooo.blackfish.adapter.NavigationViewPagerAdapter;
import top.omooo.blackfish.fragment.FinancialFragment;
import top.omooo.blackfish.fragment.HomeFragment;
import top.omooo.blackfish.fragment.HouseKeeperFragment;
import top.omooo.blackfish.fragment.MallFragment;
import top.omooo.blackfish.fragment.MineFragment;
import top.omooo.blackfish.utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private MenuItem mMenuItem;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //初始化底部导航栏和
        initView();

        //初始化VLayout
        initVLayout();
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_main_content);
        mNavigationView = findViewById(R.id.bottom_navigation_view);
        //默认Item大于3的选中效果会影响ViewPager的滑动切换时的效果，故用反射去掉.
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_nav_mall:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_nav_financial:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.bottom_nav_housekeeper:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.bottom_nav_mine:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mNavigationView.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        NavigationViewPagerAdapter adapter = new NavigationViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.newInstance());
        adapter.addFragment(MallFragment.newInstance());
        adapter.addFragment(FinancialFragment.newInstance());
        adapter.addFragment(HouseKeeperFragment.newInstance());
        adapter.addFragment(MineFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

    private void initVLayout() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

    }

    private void loadHomePage() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
//        mRefreshLayout=manager
    }


}
