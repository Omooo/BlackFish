package top.omooo.blackfish.MallPagerActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.omooo.blackfish.NewBaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.bean.UrlInfoBean;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/4/15.
 */

public class SubmitOrderActivity extends NewBaseActivity {

    @BindView(R.id.iv_goods)
    SimpleDraweeView mIvGoods;
    @BindView(R.id.btn_submit_order)
    Button mBtnSubmitOrder;

    private Context mContext;
    private static final String TAG = "SubmitOrderActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_order_layout;
    }

    @Override
    public void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorKeyBoardBg));
        mContext = SubmitOrderActivity.this;

        mIvGoods.setImageURI(UrlInfoBean.dialogImage);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_submit_order)
    public void onViewClicked() {
        CustomToast.show(mContext, "提交订单");
    }
}
