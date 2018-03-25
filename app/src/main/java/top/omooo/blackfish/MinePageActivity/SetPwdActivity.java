package top.omooo.blackfish.MinePageActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import top.omooo.blackfish.BaseActivity;
import top.omooo.blackfish.R;
import top.omooo.blackfish.utils.SqlOpenHelperUtil;
import top.omooo.blackfish.view.CustomToast;

/**
 * Created by SSC on 2018/3/25.
 */

public class SetPwdActivity extends BaseActivity {

    private ImageView mImageBack;
    private TextView mTextTitle,mTextSubtitle, mTextForgetPwd;
    private EditText mEditPwd;
    private Button mButtonSubmit;

    private String phone;
    private SqlOpenHelperUtil mSqlOpenHelperUtil = new SqlOpenHelperUtil();
    private Connection mConnection;
    private boolean isExist = false;

    private static final String TAG = "SetPwdActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_set_pwd_layout;
    }

    @Override
    public void initViews() {
        mImageBack = findView(R.id.tv_mine_set_pwd_back);
        mTextTitle = findView(R.id.tv_mine_set_pwd_title);
        mTextSubtitle = findView(R.id.tv_mine_set_pwd_subtitle);
        mTextForgetPwd = findView(R.id.tv_mine_forget_pwd);
        mEditPwd = findView(R.id.et_mine_set_pwd);
        mButtonSubmit = findView(R.id.btn_mine_set_pwd_submit);

        phone = getIntent().getStringExtra("phoneNumber");
        isExist = isExistPwd(phone);
        Log.i(TAG, "initViews: " + phone + " " + isExist);
        if (isExist) {
            //修改密码
            Log.i(TAG, "initViews: 修改密码");
            mTextTitle.setText("修改登录密码");
            mTextSubtitle.setText("请输入旧登录密码");
            mEditPwd.setHint("输入当前登录密码");
            mButtonSubmit.setText("下一步");
            mTextForgetPwd.setVisibility(View.VISIBLE);
        } else {
            //设置密码
            Log.i(TAG, "initViews: 设置密码");
            mTextTitle.setText("设置登录密码");
            mTextSubtitle.setText("请设置登录密码");
            mEditPwd.setHint("输入登录密码");
            mButtonSubmit.setText("提交");
        }
    }

    @Override
    public void initListener() {
        mImageBack.setOnClickListener(this);
        mTextForgetPwd.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mine_set_pwd_back:
                finish();
                break;
            case R.id.tv_mine_forget_pwd:
                CustomToast.show(this,"忘记密码");
                break;
            case R.id.btn_mine_set_pwd_submit:
                String pwd = mEditPwd.getText().toString();
                if (null == pwd || pwd.length() < 8) {
                    CustomToast.show(this, "密码不少于八位，请重新填写");
                    break;
                } else {
                    if (isExist) {
                        Log.i(TAG, "processClick: 下一步");
                    } else {
                        if (updatePwd(phone, pwd)) {
                            CustomToast.show(this, "设置密码成功");
                        } else {
                            CustomToast.show(this, "设置密码失败");
                        }
                    }
                }
                break;
            default:break;
        }
    }

    @Override
    protected void initData() {

    }

    // TODO: 2018/3/25 有记录查询表却为空 
    private boolean isExistPwd(String phone) {
        mConnection = mSqlOpenHelperUtil.connDB();

        //打印表内容
        Log.i(TAG, "/***********表内容************/");
        String testSql = "select * from userinfo";
        ResultSet result = mSqlOpenHelperUtil.executeSql(mConnection, testSql);
        try {
            if (null != result) {
                while (result.next()) {
                    String id = result.getString("id");
                    String phoneNumber = result.getString("phone");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String regDate = result.getString("date");
                    Log.i(TAG, "" + id + " " + phoneNumber + " " + username + " " + password + " " + regDate);
                }
            } else {
                Log.i(TAG, "isExistPwd: ResultSet为空");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mConnection != null) {
            String sql = "select password from userinfo where phone= '" + phone + "'";
            ResultSet resultSet = mSqlOpenHelperUtil.executeSql(mConnection, sql);
            String pwd = null;
            try {
                pwd = resultSet.getString("password");
                if (null == pwd||pwd.equals("")) {
                    Log.i(TAG, "isExistPwd: 不存在密码");
                    return false;
                } else {
                    Log.i(TAG, "isExistPwd: 存在密码");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private boolean updatePwd(String phone,String pwd) {
        mConnection = mSqlOpenHelperUtil.connDB();
        if (null != mConnection) {
            String sql = "update userinfo set password='" + pwd + "' where phone='" + phone + "'";
            return mSqlOpenHelperUtil.updateDB(mConnection, sql);
        }
        return false;
    }
}
