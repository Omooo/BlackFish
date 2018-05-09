package top.omooo.blackfish.MinePageActivity;

import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import top.omooo.blackfish.NewBaseActivity;
import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/5/8.
 */

public class ManagerAddressActivity extends NewBaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.btn_new_address)
    Button mBtnNewAddress;
    @BindView(R.id.ll_no_address)
    LinearLayout mLlNoAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;

    private static final int REQUEST_CODE = 0x01;
    private static final int RESULT_CODE = 0x02;

    private static final String TAG = "ManagerAddressActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_address_layout;
    }

    @Override
    public void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorWhite));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.btn_new_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finshActivity();
                break;
            case R.id.btn_new_address:
                Intent intent = new Intent(this, NewAddressActivity.class);
                intent.putExtra("isDefault", !(mLlAddress.getChildCount() > 0));
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            mLlNoAddress.setVisibility(View.INVISIBLE);
            addAddress(data.getStringExtra("name"), data.getStringExtra("phone"), data.getStringExtra("address"), data.getBooleanExtra("isDefault", false));
        }
    }

    private void addAddress(String name, String phone, String address, boolean isDefault) {

        int childCount = mLlAddress.getChildCount();
        View view = LayoutInflater.from(this).inflate(R.layout.view_address_layout, null);
        TextView textViewName = view.findViewById(R.id.tv_name);
        TextView textViewPhone = view.findViewById(R.id.tv_phone);
        TextView textViewAddress = view.findViewById(R.id.tv_address);
        AppCompatCheckBox checkBox = view.findViewById(R.id.checkbox);
        TextView textViewEdit = view.findViewById(R.id.tv_edit);
        TextView textViewDelete = view.findViewById(R.id.tv_delete);

        textViewName.setText(name);
        textViewPhone.setText(phone);
        textViewAddress.setText(address);
        checkBox.setChecked(isDefault);
        if (isDefault) {
            checkBox.setClickable(false);
        }
        textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 编辑按钮被点击");
            }
        });
        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 删除按钮被点击");
            }
        });
        mLlAddress.addView(view, childCount);


    }
}
