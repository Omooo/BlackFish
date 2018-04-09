package top.omooo.blackfish;

import android.content.Intent;
import android.view.View;

import top.omooo.blackfish.aliPay.PayDemoActivity;


/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";


    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void initViews() {
        startActivity(new Intent(this, PayDemoActivity.class));

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
