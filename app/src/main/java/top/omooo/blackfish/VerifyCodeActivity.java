package top.omooo.blackfish;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import top.omooo.blackfish.listener.InputCompleteListener;
import top.omooo.blackfish.listener.OnVerifyCodeResultListener;
import top.omooo.blackfish.utils.CountDownUtil;
import top.omooo.blackfish.utils.SqlOpenHelperUtil;
import top.omooo.blackfish.view.VerifyCodeView;

/**
 * Created by SSC on 2018/3/19.
 */

public class VerifyCodeActivity extends BaseActivity{

    private TextView mTextPhoneNumber,mTextVerifyTimer, mTextVerifyResult;
    private VerifyCodeView mVerifyCodeView;
    private ImageView mImageBack;

    private OnVerifyCodeResultListener mCodeResultListener=new OnVerifyCodeResultListener() {
        @Override
        public void sendCodeSuccess() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextVerifyTimer.setClickable(false);
                    new CountDownUtil(60000, 1000, mTextVerifyTimer).start();
                    mTextVerifyTimer.setClickable(true);
                }
            });
        }

        @Override
        public void sendCodeFailure() {

        }

        @Override
        public void submitCodeSuccess(String phoneNumber,String date) {
            SqlOpenHelperUtil sqlOpenHelperUtil = new SqlOpenHelperUtil();
            Connection connection = sqlOpenHelperUtil.connDB();
            if (connection != null) {
                Log.i(TAG, "submitCodeSuccess: 数据库连接成功");
                // TODO: 2018/3/20 手机号重复添加
                String insert = "insert into userinfo(phone,username,password,date) values('" + phoneNumber + "','','','" + date + "')";
                sqlOpenHelperUtil.updateDB(connection, insert);
//                String sql = "select id from userinfo where phone= '" + phoneNumber + "'";
//
//                ResultSet set = sqlOpenHelperUtil.executeSql(connection, sql);
//                try {
//                    String id = set.getString("id");
//                    if (id == null || id.equals("")) {
//                        String insert = "insert into userinfo(phone,username,password,date) values('" + phoneNumber + "','','','" + date + "')";
//                        boolean result = sqlOpenHelperUtil.updateDB(connection, insert);
//                        if (result) {
//                            Log.i(TAG, "submitCodeSuccess: 新记录添加成功");
//                        } else {
//                            Log.i(TAG, "submitCodeSuccess: 新记录添加失败");
//                        }
//                    } else {
//                        Log.i(TAG, "submitCodeSuccess: 已存在该手机号");
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }

                //打印表内容
                Log.i(TAG, "/***********表内容************/");
                String testSql = "select * from userinfo";
                ResultSet resultSet = sqlOpenHelperUtil.executeSql(connection, testSql);
                try {
                    while (resultSet.next()) {
                        String id = resultSet.getString("id");
                        String phone = resultSet.getString("phone");
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String regDate = resultSet.getString("date");
                        Log.i(TAG, "" + id + " " + phone + " " + username + " " + password + " " + regDate);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i(TAG, "submitCodeSuccess: 数据库连接失败");
            }
        }

        @Override
        public void submitCodeFailure() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextVerifyResult.setText("验证失败");
                    mTextVerifyResult.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTextVerifyResult.setText("");
                        }
                    }, 2000);
                    mVerifyCodeView.setEditContentEmpty();
                }
            });
        }
    };

    private String phoneNumber = "";

    private static final String TAG = "VerifyCodeActivity";
    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_code_layout;
    }

    @Override
    public void initViews() {
        mTextPhoneNumber = findView(R.id.tv_verify_phone_number);
        mTextVerifyTimer = findView(R.id.tv_verify_timer);
        mTextVerifyResult = findView(R.id.tv_verify_result);
        mVerifyCodeView = findView(R.id.verify_code_view);
        mImageBack = findView(R.id.iv_verify_back);

        mTextVerifyTimer.setText("");
        mTextVerifyResult.setText("");

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phone_number");

        sendCode("86", phoneNumber);

        mTextPhoneNumber.setText("短信已发送至 " + phoneNumber);

        mVerifyCodeView.setInputCompleteListener(new InputCompleteListener() {
            @Override
            public void inputComplete() {
                Toast.makeText(VerifyCodeActivity.this, "inputComplete: " + mVerifyCodeView.getEditContent(), Toast.LENGTH_SHORT).show();
                submitCode("86", phoneNumber, mVerifyCodeView.getEditContent());
            }

            @Override
            public void invalidContent() {

            }
        });
    }

    @Override
    public void initListener() {
        mTextVerifyTimer.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_verify_result:
                Log.i(TAG, "processClick: 点击重新获取验证码");
                sendCode("86", phoneNumber);
                break;
            case R.id.iv_verify_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

    }

    //请求验证码
    private void sendCode(String country, String phoneNumber) {
        SMSSDK.registerEventHandler(new EventHandler(){
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    Log.i(TAG, "sendCode: 发送成功");
                    mCodeResultListener.sendCodeSuccess();
                } else {
                    Log.i(TAG, "sendCode: 发送失败");
                    mCodeResultListener.sendCodeFailure();
                }
            }
        });
        SMSSDK.getVerificationCode(country,phoneNumber);
    }

    //提交验证码
    private void submitCode(String country, final String phoneNumber, String code) {
        SMSSDK.registerEventHandler(new EventHandler(){
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    Log.i(TAG, "submitCode: 验证成功");
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    mCodeResultListener.submitCodeSuccess(phoneNumber, df.format(new Date()));
                } else {
                    Log.i(TAG, "submitCode: 验证失败");
                    mCodeResultListener.submitCodeFailure();
                }
            }
        });
        SMSSDK.submitVerificationCode(country, phoneNumber, code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销短信验证回调事件，避免内存泄漏
        SMSSDK.unregisterAllEventHandler();
    }

}
