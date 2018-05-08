package top.omooo.blackfish.MinePageActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import top.omooo.blackfish.NewBaseActivity;
import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/5/8.
 */

public class ManagerAddressActivity extends NewBaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.btn_new_address)
    Button mBtnNewAddress;
    @BindView(R.id.ll_no_address)
    LinearLayout mLlNoAddress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_address_layout;
    }

    @Override
    public void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorWhite));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.btn_new_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finshActivity();
                break;
            case R.id.btn_new_address:
                skipActivity(new Intent(this, NewAddressActivity.class));
                break;
        }
    }
}
