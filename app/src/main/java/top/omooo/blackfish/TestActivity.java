package top.omooo.blackfish;

import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends NewBaseActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.btn_bank_card_add)
    Button mBtnBankCardAdd;

    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void initViews() {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn_bank_card_add)
    public void onViewClicked() {
        Toast.makeText(this, "2333", Toast.LENGTH_SHORT).show();
    }
}
