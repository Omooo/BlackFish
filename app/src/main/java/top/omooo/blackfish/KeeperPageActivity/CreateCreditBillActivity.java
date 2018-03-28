package top.omooo.blackfish.KeeperPageActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.listener.OnSuperEditClickListener;
import top.omooo.blackfish.listener.OnSuperEditLayoutClickListener;
import top.omooo.blackfish.utils.SelectCardActivity;
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

        // TODO: 2018/3/27 脏！
        /**
         * 为了解决点击EditText导致整体Layout点击事件不响应
         * 所以写了两个监听器，一个监听Layout，一个监听EditText
         * 还好一个点击事件不会被两个监听器同时响应
         * 而且，对于Layout的响应，其实没什么可做
         * 不过，代码着实脏！
         */
        mSuperCardNumber.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperBank.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperCardType.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperUsername.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperLines.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperBill.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperBillDay.setOnSuperEditClickListener(new SuperListenerLayout());
        mSuperPayBillDay.setOnSuperEditClickListener(new SuperListenerLayout());

        mSuperBank.setOnSuperClickListener(new OnSuperClick());
        mSuperCardType.setOnSuperClickListener(new OnSuperClick());
        mSuperBillDay.setOnSuperClickListener(new OnSuperClick());
        mSuperPayBillDay.setOnSuperClickListener(new OnSuperClick());
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

    class SuperListenerLayout implements OnSuperEditLayoutClickListener {

        @Override
        public void onSuperEditClick(String id) {
            Log.i(TAG, "onSuperEditClick: " + id);
            switch (id) {
                case "卡号":
//                    CustomToast.show(mContext, "卡号 被点击" + mSuperCardNumber.getData());
                    break;
                case "所属银行":
                    selectBank();
                    break;
                case "卡片类型":
                    selectCard();
                    break;
                case "用户名":
//                    CustomToast.show(mContext, "用户名 被点击" + mSuperCardNumber.getData());
                    break;
                case "信用额度":
//                    CustomToast.show(mContext, "信用额度 被点击" + mSuperCardNumber.getData());
                    break;
                case "账单金额":
//                    CustomToast.show(mContext, "账单金额 被点击" + mSuperCardNumber.getData());
                    break;
                case "账单日":
                    selectBillDay();
                    break;
                case "还款日":
                    selectPayBillDay();
                    break;
                default:break;
            }
        }
    }

    class OnSuperClick implements OnSuperEditClickListener {

        @Override
        public void onSuperClick(String id) {
            switch (id) {
                case "所属银行":
                    selectBank();
                    break;
                case "卡片类型":
                    selectCard();
                    break;
                case "账单日":
                    selectBillDay();
                    break;
                case "还款日":
                    selectPayBillDay();
                    break;
                default:break;
            }
        }
    }

    //选择所属银行
    private void selectBank() {
        startActivityForResult(new Intent(mContext, SelectCardActivity.class), 0x001);
    }

    //选择卡片类型
    private void selectCard() {
        CustomToast.show(mContext,"选择卡片类型");
    }

    //选择账单日
    private void selectBillDay() {
        CustomToast.show(mContext,"选择账单日");
    }

    //选择还款日
    private void selectPayBillDay() {
        CustomToast.show(mContext,"选择还款日");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("cardName");
            Log.i(TAG, "onActivityResult: " + name);
        }
    }
}
