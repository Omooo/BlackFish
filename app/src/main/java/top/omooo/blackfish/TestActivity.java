package top.omooo.blackfish;

import android.util.Log;
import android.view.View;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.WheelView;

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
        WheelView wheelView = findViewById(R.id.wheel);
        wheelView.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                CharSequence text = view.getItem(newIndex);
                Log.i("WheelView", String.format("index: %d, text: %s", newIndex, text));
            }
        });
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
