package top.omooo.blackfish;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import top.omooo.blackfish.adapter.NavigationViewPagerAdapter;
import top.omooo.blackfish.fragment.BaseFragment;
import top.omooo.blackfish.fragment.HomeFragment;
import top.omooo.blackfish.utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private MenuItem mMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
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
        adapter.addFragment(BaseFragment.newInstance());
        adapter.addFragment(BaseFragment.newInstance());
        adapter.addFragment(BaseFragment.newInstance());
        adapter.addFragment(BaseFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

}
