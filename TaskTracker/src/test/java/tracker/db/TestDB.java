package tracker.db;

import org.junit.Assert;
import org.junit.Test;
import tracker.utils.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class TestDB {


    private Connection con = DBHelper.getConnection();

    @Test
    public void testConnection() {
        Assert.assertNotNull("Connection successful", con);
    }

    @Test
    public void testAutoIncrementProjectTable() {
        ResultSet resultSet = null;
        try {
            resultSet = con.createStatement().executeQuery("SELECT * FROM Projects");
            for (int i = 1; resultSet.next(); i++) {
                int projectId = resultSet.getInt(1);
                Assert.assertEquals(i, projectId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(resultSet).close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
