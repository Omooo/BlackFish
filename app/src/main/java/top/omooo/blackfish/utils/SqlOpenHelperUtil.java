package top.omooo.blackfish.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by SSC on 2018/3/20.
 */

public class SqlOpenHelperUtil {

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

    public ResultSet executeSql(Connection conn, String sql) {
        if (conn != null) {
            try {
                Statement statement = conn.createStatement();

                return statement.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean updateDB(Connection conn, String sql) {
        if (conn != null) {
            try {
                Statement statement = conn.createStatement();
                statement.executeUpdate(sql);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
