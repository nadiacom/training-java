package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ebiz on 14/03/17.
 */
public enum DAOFactory {


    INSTANCE; //Single instance of DAOFactory
    private final String PROPERTIES_FILE = "/hikari.properties";
    private HikariDataSource ds;

    /**
     * Default constructor.
     */
    DAOFactory() {
        // Examines both filesystem and classpath for .properties file
        HikariConfig  config = new HikariConfig(PROPERTIES_FILE);
        ds = new HikariDataSource(config);
    }

    /**
     * Get HikariDataSource connection.
     *
     * @return HikariDataSource connection.
     * @throws SQLException SQLException.
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
