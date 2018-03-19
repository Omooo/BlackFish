package top.omooo.blackfish;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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

    private static final String TAG = "LoginActivity";
    private boolean isPwdVisible = false;


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

        mEditPhone.addTextChangedListener(this);
        mEditPhone.setOnTouchListener(this);
        mEditPwd.setOnTouchListener(this);
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
                Bundle bundle = new Bundle();
                bundle.putString("phone_number", mEditPhone.getText().toString());
                Intent intent = new Intent(LoginActivity.this, VerifyCodeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = mEditPhone.getCompoundDrawables()[2];
            Drawable drawable1 = mEditPwd.getCompoundDrawables()[2];
            Drawable[] drawables = mEditPwd.getCompoundDrawables();
            // TODO: 2018/3/19 按下垂直距离的处理
            if (drawable != null && event.getX() > mEditPhone.getWidth() - mEditPhone.getPaddingRight() - drawable.getIntrinsicWidth()) {
                Log.i(TAG, "onTouch: 0");
                mEditPhone.setText("");
            }
            if (drawable1 != null && event.getX() > mEditPwd.getWidth() - mEditPwd.getPaddingRight() - drawable1.getIntrinsicWidth()) {
                Log.i(TAG, "onTouch: 1");
                if (!isPwdVisible) {
                    Log.i(TAG, "onTouch: " + "密码可见");
                    mEditPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Drawable pwdDrawableRight = getDrawable(R.drawable.icon_login_pwd_visiable);
                    pwdDrawableRight.setBounds(drawable1.getBounds());
                    mEditPwd.setCompoundDrawables(drawables[0], drawables[1], pwdDrawableRight, drawables[3]);
                    isPwdVisible = true;
                } else {
                    Log.i(TAG, "onTouch: " + "密码不可见");
                    mEditPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Drawable pwdDrawableRight = getDrawable(R.drawable.icon_login_pwd_right);
                    pwdDrawableRight.setBounds(drawable1.getBounds());
                    mEditPwd.setCompoundDrawables(drawables[0], drawables[1], pwdDrawableRight, drawables[3]);
                    isPwdVisible = false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
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
