package top.omooo.blackfish;

import android.inputmethodservice.KeyboardView;
import android.view.View;


/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";

    private KeyboardView mKeyboardView;

    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void initViews() {
//        startActivity(new Intent(this, SearchActivity.class));

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
