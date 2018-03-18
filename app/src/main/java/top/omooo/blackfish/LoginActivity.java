package top.omooo.blackfish;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SSC on 2018/3/18.
 */

public class LoginActivity extends BaseActivity implements View.OnTouchListener,TextWatcher{

    private ImageView mImageExitActivity;
    private TextView mTextMessage,mTextToSmsLogin,mTextForgetPwd;
    private Button mButtonLogin;
    private EditText mEditPhone, mEditPwd;

    private RelativeLayout mPwdLayout;

    private static final int maxLength = 11;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    public void initViews() {
        mImageExitActivity = findView(R.id.iv_login_cancel);
        mTextMessage = findView(R.id.tv_hint_message);
        mTextToSmsLogin = findView(R.id.tv_to_sms_login);
        mTextForgetPwd = findView(R.id.tv_forget_pwd);
        mButtonLogin = findView(R.id.btn_login);
        mEditPhone = findView(R.id.et_login_phone);
        mEditPwd = findView(R.id.et_login_pwd);

        mPwdLayout = findView(R.id.rl_pwd_layout);

        // TODO: 2018/3/18 更改背景导致按钮按下效果丢失
        mButtonLogin.setBackground(getDrawable(R.drawable.shape_btn_pressed));
        mButtonLogin.setClickable(false);
    }

    @Override
    public void initListener() {
        mImageExitActivity.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mTextMessage.setOnClickListener(this);
        mTextToSmsLogin.setOnClickListener(this);
        mTextForgetPwd.setOnClickListener(this);

        mEditPhone.setOnTouchListener(this);
        mEditPhone.addTextChangedListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_cancel:
                finish();
                break;
            case R.id.tv_hint_message:
                mTextMessage.setVisibility(View.GONE);
                mPwdLayout.setVisibility(View.VISIBLE);
                mButtonLogin.setText("登录");
                break;
            case R.id.btn_login:
                Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_to_sms_login:
                mTextMessage.setVisibility(View.VISIBLE);
                mPwdLayout.setVisibility(View.GONE);
                mButtonLogin.setText("下一步");
                break;
            case R.id.tv_forget_pwd:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Drawable drawable = mEditPhone.getCompoundDrawables()[2];
        if (drawable == null) {
            return false;
        }
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return false;
        } else {
            if (event.getX() > mEditPhone.getWidth() - mEditPhone.getPaddingRight() - drawable.getIntrinsicWidth()) {
                mEditPhone.setText("");
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditPhone.getText().length() == 11) {
            mButtonLogin.setClickable(true);
            mButtonLogin.setPressed(true);
            mButtonLogin.setBackground(getDrawable(R.drawable.shape_btn_no_pressed));
        } else {
            mButtonLogin.setClickable(false);
            mButtonLogin.setPressed(false);
            mButtonLogin.setBackground(getDrawable(R.drawable.shape_btn_pressed));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
