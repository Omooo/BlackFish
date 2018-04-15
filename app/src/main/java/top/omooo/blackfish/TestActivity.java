package top.omooo.blackfish;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import top.omooo.blackfish.view.AmountView;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends NewBaseActivity {

    private static final String TAG = "TestActivity";
    private Context mContext;

    @BindView(R.id.btn_bank_card_add)
    Button mBtnBankCardAdd;

    @BindView(R.id.amount_view)
    AmountView mAmountView;
    @Override
    public int getLayoutId() {

        return R.layout.test;
    }

    @Override
    public void initViews() {
        mContext = TestActivity.this;

        mAmountView.setMaxNumber(20);
        mAmountView.setOnNumChangeListener(new AmountView.OnNumChangeListener() {
            @Override
            public void onChange(int num) {
                CustomToast.show(mContext, num + "");
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn_bank_card_add)
    public void onViewClicked() {
        startActivity(new Intent(this, GoodsDetailActivity.class));
    }
}
