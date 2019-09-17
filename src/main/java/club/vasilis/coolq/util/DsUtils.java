package club.vasilis.coolq.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vasilis
 * @date 2019/6/11 -15:43
 */

public class DsUtils {
    private static DataSource ds;

    static {
        ds = new ComboPooledDataSource();
    }

    public static DataSource getDataSource(){
        return ds;
    }
    public static Connection getConnection() {
        Connection conn ;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;

    }


}
