package top.omooo.blackfish;

import android.content.Context;
import android.content.Intent;

/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends NewBaseActivity {

    private static final String TAG = "TestActivity";
    private Context mContext;

    @Override
    public int getLayoutId() {

        return R.layout.test;
    }

    @Override
    public void initViews() {
        mContext = TestActivity.this;
    }

    @Override
    protected void initData() {

    }

}
