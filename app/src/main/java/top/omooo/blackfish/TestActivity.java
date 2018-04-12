package top.omooo.blackfish;

import android.content.Intent;
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
        Intent intent = new Intent(this, BaseWebViewActivity.class);
        intent.putExtra("loadUrl", "https://h5.blackfish.cn/m/promotion/2/120?line&memberId=18800209572&deviceId=f39498916c9b5cda");
        startActivity(intent);
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn_bank_card_add)
    public void onViewClicked() {
        Toast.makeText(this, "2333", Toast.LENGTH_SHORT).show();
    }
}
