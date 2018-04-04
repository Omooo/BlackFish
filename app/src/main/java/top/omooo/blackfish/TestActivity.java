package top.omooo.blackfish;

import android.view.View;

import top.omooo.blackfish.view.NumberKeyBoardView;


/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";

    private NumberKeyBoardView mKeyboardView;

    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void initViews() {
//        startActivity(new Intent(this, SearchActivity.class));
        mKeyboardView = findView(R.id.view_key_board_number);
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
