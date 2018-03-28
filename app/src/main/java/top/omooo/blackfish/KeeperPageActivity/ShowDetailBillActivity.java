package top.omooo.blackfish.KeeperPageActivity;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/3/28.
 */

public class ShowDetailBillActivity extends BaseActivity {

    private RelativeLayout mRelativeLayout1;
    private RelativeLayout mRelativeLayout2;
    private TextView mTextTitle,mTextCardNumber,mTextBillNumber,mTextPayMin,mTextBillLines,mTextBillDay,mTextPayBillDay, mTextNoLiXi;
    private ImageView mImageBack, mImageRefresh;

    private Context mContext;
    private static final String TAG = "ShowDetailBillActivity";
    @Override
    public int getLayoutId() {
        return R.layout.activity_show_detail_bill_layout;
    }

    @Override
    public void initViews() {

        //修改状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_VISIBLE);
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getColor(R.color.colorCardDetailHeader));

        mContext = ShowDetailBillActivity.this;

        mRelativeLayout1 = findView(R.id.rl_keeper_detail_bill_1);
        mRelativeLayout2 = findView(R.id.rl_keeper_detail_bill_2);
        mTextTitle = findView(R.id.tv_keeper_detail_card_title);
        mTextCardNumber = findView(R.id.tv_detail_card_number_name);
        mTextBillNumber = findView(R.id.tv_detail_card_bill_number);
        mTextPayMin = findView(R.id.tv_detail_card_pay_min);
        mTextBillLines = findView(R.id.tv_detail_card_bill_lines);
        mTextBillDay = findView(R.id.tv_detail_card_bill_day);
        mTextPayBillDay = findView(R.id.tv_detail_card_pay_bill_day);
        mTextNoLiXi = findView(R.id.tv_detail_card_no_li);
        mImageBack = findView(R.id.iv_keeper_detail_card_back);
        mImageRefresh = findView(R.id.iv_detail_card_refresh);

        layoutInAndOutAnim(false);

    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mImageRefresh.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_detail_card_back:
                exitActivity();
                break;
            case R.id.iv_detail_card_refresh:
                CustomToast.show(mContext, "刷新");
                break;
            default:break;
        }
    }

    @Override
    protected void initData() {

    }

    private void layoutInAndOutAnim(boolean isBack) {
        Log.i(TAG, "layoutInAndOutAnim: " + isBack);
        if (!isBack) {
            Animation animationTopIn = AnimationUtils.loadAnimation(this, R.anim.view_layout_top_in);
            animationTopIn.setFillEnabled(true);
            animationTopIn.setFillAfter(true);
            mRelativeLayout1.setAnimation(animationTopIn);
            Animation animationBottomIn = AnimationUtils.loadAnimation(this, R.anim.view_layout_bottom_in);
            animationBottomIn.setFillEnabled(true);
            animationBottomIn.setFillAfter(true);
            mRelativeLayout2.setAnimation(animationBottomIn);
        } else {
            Animation animationTopOut = AnimationUtils.loadAnimation(this, R.anim.view_layout_top_out);
            animationTopOut.setFillEnabled(true);
            animationTopOut.setFillAfter(true);
            mRelativeLayout1.setAnimation(animationTopOut);
            Animation animationBottomOut = AnimationUtils.loadAnimation(this, R.anim.view_layout_bottom_out);
            animationBottomOut.setFillEnabled(true);
            animationBottomOut.setFillAfter(true);
            mRelativeLayout2.setAnimation(animationBottomOut);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitActivity();
        }
        return false;
    }

    private void exitActivity() {
        mRelativeLayout1.clearAnimation();
        mRelativeLayout1.invalidate();
        mRelativeLayout2.clearAnimation();
        mRelativeLayout2.invalidate();
        layoutInAndOutAnim(true);
        mRelativeLayout1.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                //去掉返回后透明Activity退出闪烁问题
                overridePendingTransition(0, 0);
            }
        }, 500);
    }
}
