package top.omooo.blackfish;

import android.view.View;

import com.airbnb.lottie.LottieAnimationView;


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
        LottieAnimationView animationView = findView(R.id.animation_view);
        animationView.playAnimation();
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
