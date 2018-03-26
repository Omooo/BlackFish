package top.omooo.blackfish;

import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import top.omooo.blackfish.utils.SqlOpenHelperUtil;

/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends BaseActivity {

    private static final String TAG = "TestActivity";
    private SqlOpenHelperUtil mSqlOpenHelperUtil = new SqlOpenHelperUtil();
    private Connection mConnection;

    private OnSqlFinsh mOnSqlFinsh=new OnSqlFinsh() {
        @Override
        public void onSuccess(String result) {
            Log.i(TAG, "onSuccess: " + result);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_set_pwd_layout;
    }

    @Override
    public void initViews() {
        doSql();
    }

    private void doSql() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //打印表内容
                mConnection = mSqlOpenHelperUtil.connDB();
                if (mConnection != null) {
                    Log.i(TAG, "/***********表内容************/");
                } else {
                    return;
                }
                String testSql = "select * from userinfo";
                ResultSet result = mSqlOpenHelperUtil.executeSql(mConnection, testSql);
                try {
                    if (null != result) {
                        String resultSet = "";
                        while (result.next()) {
                            String id = result.getString("id");
                            String phoneNumber = result.getString("phone");
                            String username = result.getString("username");
                            String password = result.getString("password");
                            String regDate = result.getString("date");
                            resultSet = id + " " + phoneNumber + " " + username + " " + password + " " + regDate;
                            mOnSqlFinsh.onSuccess(resultSet);
                        }
                    } else {
                        Log.i(TAG, "isExistPwd: ResultSet为空");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View view) {

    }

    @Override
    protected void initData() {

    }

    private interface OnSqlFinsh {
        void onSuccess(String result);
    }
}
