package top.omooo.blackfish.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import top.omooo.blackfish.KeeperPageActivity.AddBillActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.utils.AdjustViewUtil;
import top.omooo.blackfish.utils.DensityUtil;
import top.omooo.blackfish.utils.SpannableStringUtil;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by Omooo on 2018/2/25.
 */

public class HouseKeeperFragment extends BaseFragment {

    private ImageView mImageAdd, mImageShowEyes;
    private TextView mTextMoney,mTextGrid1, mTextGrid2, mTextGrid3, mTextGrid4,mTextGift;
    private CardView mCardView;
    private Button mButtonAddBill;
    private Context mContext;

    private AdjustViewUtil mAdjustViewUtil;
    private boolean isShowMoney = false;
    private String money = "233.00";


    public static HouseKeeperFragment newInstance() {
        return new HouseKeeperFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_housekeeper_layout;
    }

    @Override
    public void initViews() {
        mContext = getActivity();
        mTextMoney = findView(R.id.tv_keeper_money);
        mImageAdd = findView(R.id.iv_keeper_add);
        mImageShowEyes = findView(R.id.tv_keeper_show_money);
        mTextGrid1 = findView(R.id.tv_keeper_grid_1);
        mTextGrid2 = findView(R.id.tv_keeper_grid_2);
        mTextGrid3 = findView(R.id.tv_keeper_grid_3);
        mTextGrid4 = findView(R.id.tv_keeper_grid_4);
        mTextGift = findView(R.id.tv_keeper_gift);
        mCardView = findView(R.id.cv_keeper);
        mButtonAddBill = findView(R.id.btn_keeper_add_bill);

        //调整TextView的DrawableTop的大小
        mAdjustViewUtil = new AdjustViewUtil();
        mAdjustViewUtil.adjustTextViewPic(mTextGrid1, 1, 0, 0, 180, 180);
        mAdjustViewUtil.adjustTextViewPic(mTextGrid2, 1, 0, 0, 180, 180);
        mAdjustViewUtil.adjustTextViewPic(mTextGrid3, 1, 0, 0, 180, 180);
        mAdjustViewUtil.adjustTextViewPic(mTextGrid4, 1, 0, 0, 180, 180);

        mAdjustViewUtil.adjustTextViewPic(mTextGift, 0, 0, 0, 50, 50);
    }

    @Override
    public void initListener() {
        mImageAdd.setOnClickListener(this);
        mImageShowEyes.setOnClickListener(this);
        mTextGrid1.setOnClickListener(this);
        mTextGrid2.setOnClickListener(this);
        mTextGrid3.setOnClickListener(this);
        mTextGrid4.setOnClickListener(this);
        mCardView.setOnClickListener(this);
        mButtonAddBill.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_add:
                break;
            case R.id.tv_keeper_show_money:
                if (!isShowMoney) {
                    mTextMoney.setText(money);
                    SpannableStringUtil.setRelativeSizeText(mTextMoney, 0, mTextMoney.getText().length() - 3, 1.3f, mTextMoney.getText().toString());
                    isShowMoney = true;
                    mImageShowEyes.setImageResource(R.drawable.icon_open_eyes);
                } else {
                    mTextMoney.setText("*****");
                    isShowMoney = false;
                    mImageShowEyes.setImageResource(R.drawable.icon_close_eyes);
                }
                break;
            case R.id.tv_keeper_grid_1:
                showAddBillDialog(R.drawable.icon_dialog_add_bill_1, "您还没有账单，快去添加吧！", 0, new DialogListener());
                break;
            case R.id.tv_keeper_grid_2:
                showAddBillDialog(R.drawable.icon_dialog_add_bill_2, "免息期告诉你今天刷哪张卡最划算", 1, new DialogListener());
                break;
            case R.id.tv_keeper_grid_3:
                CustomToast.show(mContext,"办信用卡");
                break;
            case R.id.tv_keeper_grid_4:
                CustomToast.show(mContext,"我要贷款");
                break;
            case R.id.cv_keeper:
                CustomToast.show(mContext, "缩放效果");
                break;
            case R.id.btn_keeper_add_bill:
                startActivity(new Intent(mContext, AddBillActivity.class));
                break;
            default:break;
        }
    }

    private void showAddBillDialog(int drawableId, String text, final int dialogIndex, final OnDialogPosBtnClickListener listener) {
        final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_keeper_custom_dialog_layout, null);
        ImageView imageExit = view.findViewById(R.id.iv_dialog_exit);
        ImageView imageIcon = view.findViewById(R.id.iv_dialog_icon);
        imageIcon.setImageResource(drawableId);
        TextView textTitle = view.findViewById(R.id.tv_dialog_title);
        textTitle.setText(text);
        TextView textCancel = view.findViewById(R.id.tv_dialog_cancel);
        TextView textAddBill = view.findViewById(R.id.tv_dialog_add_bill);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClick(dialogIndex);
                dialog.dismiss();
            }
        });
        imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);

        //曾经跪在这，Mark下
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = DensityUtil.dip2px(mContext, 250);
        layoutParams.width = DensityUtil.dip2px(mContext, 260);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        dialog.show();
    }

    private interface OnDialogPosBtnClickListener {
        void onBtnClick(int index);
    }

    private class DialogListener implements OnDialogPosBtnClickListener {
        @Override
        public void onBtnClick(int index) {
            if (index == 0) {
                startActivity(new Intent(mContext, AddBillActivity.class));
            } else if (index == 1) {
                CustomToast.show(mContext, "添加信用卡账单");
            } else {
                return;
            }
        }
    }
}
