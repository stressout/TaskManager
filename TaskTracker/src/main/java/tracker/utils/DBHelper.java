package tracker.utils;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class contains methods to initializing H2 in-memory database and get connection
 */

public final class DBHelper {

    private static final String url;

    private static final String username;

    private static final String password;

    private static final JdbcDataSource ds;

    private static Connection connection;

    static {
        url = "jdbc:h2:mem:TaskManagerDB;INIT=runscript from 'src/main/resources/initDB.sql';DB_CLOSE_DELAY=-1";
        username = "sa";
        password = "";
        ds = getJdbcDataSource();
    }

    private DBHelper() {
    }

    private static JdbcDataSource getJdbcDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl(url);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = ds.getConnection();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            return connection;
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
