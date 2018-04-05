package top.omooo.blackfish;

import android.content.Intent;
import android.view.View;

import top.omooo.blackfish.MallPagerActivity.ClassifyGoodsActivity;


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
        startActivity(new Intent(this, ClassifyGoodsActivity.class));
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
