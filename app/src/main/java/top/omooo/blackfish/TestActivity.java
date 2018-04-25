package top.omooo.blackfish;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SSC on 2018/3/26.
 */

public class TestActivity extends NewBaseActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.btn_conn)
    Button mBtnConn;
    private Context mContext;

    @Override
    public int getLayoutId() {

        return R.layout.test_2;
    }

    @Override
    public void initViews() {
        mContext = TestActivity.this;
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn_conn)
    public void onViewClicked() {
        Log.i(TAG, "onViewClicked: 点击按钮");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = connDB();
                if (null == connection) {
                    Log.i(TAG, "run: 玛德爆炸");
                } else {
                    Log.i(TAG, "run: 美的不行");
                }
            }
        }).start();
    }
    private String url = "jdbc:mysql://104.224.166.118:3306/bfdatabase";
    public Connection connDB() {
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                connection = DriverManager.getConnection(url, "Admin", "Test2333");
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
