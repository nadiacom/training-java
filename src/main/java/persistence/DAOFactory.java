package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ebiz on 14/03/17.
 */
public enum DAOFactory {


    INSTANCE; //Single instance of DAOFactory
    private final String PROPERTIES_FILE = "/hikari.properties";
    private HikariDataSource ds;
    private ThreadLocal<Connection> cHolder;

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.DaoFactory");

    /**
     * Default constructor.
     */
    DAOFactory() {
        if (cHolder == null) {
            // Examines both filesystem and classpath for .properties file
            HikariConfig config = new HikariConfig(PROPERTIES_FILE);
            config.setMaximumPoolSize(100);

            ds = new HikariDataSource(config);
            cHolder = new ThreadLocal<>();
        }
    }

    /**
     * Start Transaction.
     * @throws SQLException if SQL bug
     */
    public void startTransaction() {
        try {
            cHolder.get().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Commit transaction.
     */
    public void commit() {
        try {
            cHolder.get().commit();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Rollback transaction.
     */
    public void roolback() {
        try {
            cHolder.get().rollback();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Open connection.
     */
    public void open() {
        try {
            Connection c = cHolder.get();
            if (c == null) {
                c = ds.getConnection();
                cHolder.set(c);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Close connexion.
     */
    public void close() {
        Connection connection = getConnection();
        cHolder.remove();
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Close statement and resultset.
     *
     * @param resultSet resultset.
     * @param statement statement.
     */
    public void close(ResultSet resultSet, Statement statement) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Close statement.
     *
     * @param statement statement.
     */
    public void close(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Get HikariDataSource connection.
     *
     * @return HikariDataSource connection.
     */
    public Connection getConnection() {
        try {
            Connection c = cHolder.get();
            if (c == null || c.isClosed()) {
                cHolder.set(c = ds.getConnection());
            }
            return c;
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get autocommit value.
     * @return autocommit.
     */
    public boolean isAutoCommit() {
        boolean autCommit = false;
        try {
            autCommit = cHolder.get().getAutoCommit();
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            e.printStackTrace();
        }
        return autCommit;
    }
}
