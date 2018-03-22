package top.omooo.blackfish.MinePageActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;

/**
 * Created by lenovo on 2018/3/22.
 */

public class MineSettingActivity extends BaseActivity {

    private TextView mTextLoginPwd;
    private TextView mTextPayPwd;
    private TextView mTextManagerAddress;
    private TextView mTextExitLogin;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_setting_layout;
    }

    @Override
    public void initViews() {
        mTextLoginPwd = findView(R.id.tv_setting_login_pwd);
        mTextPayPwd = findView(R.id.tv_setting_pay_pwd);
        mTextManagerAddress = findView(R.id.tv_setting_manger_address);
        mTextExitLogin = findView(R.id.btn_setting_exit_login);

    }

    @Override
    public void initListener() {
        mTextLoginPwd.setOnClickListener(this);
        mTextPayPwd.setOnClickListener(this);
        mTextManagerAddress.setOnClickListener(this);
        mTextExitLogin.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting_login_pwd:
                break;
            case R.id.tv_setting_pay_pwd:
                break;
            case R.id.tv_setting_manger_address:
                break;
            case R.id.btn_setting_exit_login:
                break;
            default:break;
        }
    }

    @Override
    protected void initData() {

    }
}
