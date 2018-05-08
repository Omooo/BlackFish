package top.omooo.blackfish.MinePageActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.Wheel3DView;
import com.cncoderx.wheelview.WheelView;

import butterknife.BindView;
import butterknife.OnClick;
import top.omooo.blackfish.NewBaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/5/8.
 */

public class NewAddressActivity extends NewBaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.btn_save)
    Button mBtnSave;

    private String mName;
    private String mPhone;
    private String mArea;
    private String mAddress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_address_layout;
    }

    @Override
    public void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorWhite));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back, R.id.tv_area, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finshActivity();
                break;
            case R.id.tv_area:
                CustomToast.show(this, "选择所在地区");
                break;
            case R.id.btn_save:
                mName = mEtName.getText().toString();
                mPhone = mEtPhone.getText().toString();
                mAddress = mEtAddress.getText().toString();
                if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mArea)) {
                    CustomToast.show(this, "保存");
                } else {
                    CustomToast.show(this, "请正确填写信息");
                }
                break;
        }
    }

    private void showPickDialog() {
        Wheel3DView wheel3DView = new Wheel3DView(this);
        wheel3DView.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {

            }
        });

    }
}
