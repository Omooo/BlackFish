package top.omooo.blackfish;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends BaseActivity {

    private LinearLayout mLinearLayout;
    private static final String TAG = "TestActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_layout;
    }

    @Override
    public void initViews() {
        mLinearLayout = findView(R.id.ll_test);

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
