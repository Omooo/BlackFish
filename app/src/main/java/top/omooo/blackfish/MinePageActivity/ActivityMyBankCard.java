package top.omooo.blackfish.MinePageActivity;

import android.support.design.widget.TabLayout;
import android.view.View;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/3/21.
 */

public class ActivityMyBankCard extends BaseActivity {

    private TabLayout mTabLayout;
    private TabLayout.Tab oneTab;
    private TabLayout.Tab twoTab;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_bank_card;
    }

    @Override
    public void initViews() {
        mTabLayout = findView(R.id.tab_layout_my_back_card);
        oneTab = mTabLayout.newTab();
        oneTab.setText("储蓄卡");
        twoTab = mTabLayout.newTab();
        twoTab.setText("信用卡");
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
}
