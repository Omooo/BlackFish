package top.omooo.blackfish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/4/10.
 */

public abstract class NewBaseActivity extends FragmentActivity {

    private Unbinder mUnbinder;

    public abstract int getLayoutId();

    public abstract void initViews();

    protected abstract void initData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViews();
        initData();
    }

    @Override
    public void onBackPressed() {
        CustomToast.cancelToast();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
