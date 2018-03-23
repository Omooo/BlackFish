package top.omooo.blackfish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by SSC on 2018/3/16.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private SparseArray<View> mView;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void processClick(View view);

    protected abstract void initData();

    public void onClick(View view) {
        processClick(view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new SparseArray<>();
        setContentView(getLayoutId());
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViews();
        initListener();
        initData();
    }

    /**
     * 通过ID找到View
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findView(int viewId) {
        E view = (E) mView.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            mView.put(viewId,view);
        }
        return view;
    }

    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }
}
