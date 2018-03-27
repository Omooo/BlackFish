package top.omooo.blackfish.KeeperPageActivity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.listener.OnSuperEditClickListener;
import top.omooo.blackfish.view.CustomToast;
import top.omooo.blackfish.view.SuperEditText;

/**
 * Created by SSC on 2018/3/26.
 */

public class CreateCreditBillActivity extends BaseActivity {

    private Context mContext;
    private ImageView mImageBack;
    private SuperEditText mSuperCardNumber, mSuperBank, mSuperCardType,mSuperUsername,mSuperLines,mSuperBill,mSuperBillDay, mSuperPayBillDay;
    private Button mButtonSave;

    private static final String TAG = "CreateCreditActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_credit_bill_layout;
    }

    @Override
    public void initViews() {
        mContext = CreateCreditBillActivity.this;
        mImageBack = findView(R.id.tv_keeper_create_credit_bill_back);
        mSuperCardNumber = findView(R.id.super_edit_card_number);
        mSuperBank = findView(R.id.super_edit_belong_to_bank);
        mSuperCardType = findView(R.id.super_edit_card_type);
        mSuperUsername = findView(R.id.super_edit_username);
        mSuperLines = findView(R.id.super_edit_lines);
        mSuperBill = findView(R.id.super_edit_bill);
        mSuperBillDay = findView(R.id.super_edit_bill_day);
        mSuperPayBillDay = findView(R.id.super_edit_pay_bill_day);

        mButtonSave = findView(R.id.btn_create_save);
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);

        mSuperCardNumber.setOnSuperEditClickListener(new SuperListener());
        mSuperBank.setOnSuperEditClickListener(new SuperListener());
        mSuperCardType.setOnSuperEditClickListener(new SuperListener());
        mSuperUsername.setOnSuperEditClickListener(new SuperListener());
        mSuperLines.setOnSuperEditClickListener(new SuperListener());
        mSuperBill.setOnSuperEditClickListener(new SuperListener());
        mSuperBillDay.setOnSuperEditClickListener(new SuperListener());
        mSuperPayBillDay.setOnSuperEditClickListener(new SuperListener());
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_keeper_create_credit_bill_back:
                finish();
                break;
            case R.id.btn_create_save:
                CustomToast.show(mContext, "保存");
                break;
            default:break;
        }
    }

    @Override
    protected void initData() {

    }

    class SuperListener implements OnSuperEditClickListener {

        @Override
        public void onSuperEditClick(String id) {
            Log.i(TAG, "onSuperEditClick: " + id);
            switch (id) {
                case "卡号":
                    CustomToast.show(mContext, "卡号 被点击" + mSuperCardNumber.getData());
                    break;
                case "所属银行":
                    CustomToast.show(mContext, "所属银行 被点击" + mSuperCardNumber.getData());
                    break;
                case "卡片类型":
                    CustomToast.show(mContext, "卡片类型 被点击" + mSuperCardNumber.getData());
                    break;
                case "用户名":
                    CustomToast.show(mContext, "用户名 被点击" + mSuperCardNumber.getData());
                    break;
                case "信用额度":
                    CustomToast.show(mContext, "信用额度 被点击" + mSuperCardNumber.getData());
                    break;
                case "账单金额":
                    CustomToast.show(mContext, "账单金额 被点击" + mSuperCardNumber.getData());
                    break;
                case "账单日":
                    CustomToast.show(mContext, "账单日 被点击" + mSuperCardNumber.getData());
                    break;
                case "还款日":
                    CustomToast.show(mContext, "还款日 被点击" + mSuperCardNumber.getData());
                    break;
                default:break;
            }
        }
    }
}
