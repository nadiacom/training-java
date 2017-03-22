package persistence;

import exceptions.daos.DAOConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ebiz on 14/03/17.
 */
public class DAOFactory {
    private static final String PROPERTIES_FILE = "dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "userName";
    private static final String PROPERTY_PASSWORD = "psswd";

    private String url;
    private String username;
    private String password;

    /**
     * Default constructor.
     *
     * @param url (required) jdbc:mysql url
     * @param username (required) mysql username
     * @param password (required) mysql password
     */
    DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Database connexion, throw back single Factory instance method.
     *
     * @return DAOFactory instance.
     * @throws DAOConfigurationException Dao configuration exception.
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String userName;
        String psswd;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (fichierProperties == null) {
            throw new DAOConfigurationException("File properties " + PROPERTIES_FILE + " cannot be found.");
        }

        try {
            properties.load(fichierProperties);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            userName = properties.getProperty(PROPERTY_USERNAME);
            psswd = properties.getProperty(PROPERTY_PASSWORD);
        } catch (IOException e) {
            throw new DAOConfigurationException("Cannot load file properties " + PROPERTIES_FILE, e);
        }

        try {
            //Class.forName(driver);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("driver: ").append(driver).toString());
            throw new DAOConfigurationException("Cannot find driver in classpath.", e);
        }

        DAOFactory instance = new DAOFactory(url, userName, psswd);
        return instance;
    }

    /** get database connexion.
     *
     * @return Connection database connexion.
     * @throws SQLException SQL exception.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
