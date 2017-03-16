package main.java.persistence;

import main.java.persistence.daos.CompanyDao;
import main.java.persistence.daos.CompanyDaoImpl;
import main.java.persistence.daos.ComputerDao;
import main.java.persistence.daos.ComputerDaoImpl;
import main.java.exceptions.DAOConfigurationException;

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
    private static final String PROPERTIES_FILE = "main/resources/dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "nomutilisateur";
    private static final String PROPERTY_PASSWORD = "motdepasse";

    private String url;
    private String username;
    private String password;

    DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * Database connexion, load driver and throw back single Factory instance method
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (fichierProperties == null) {
            throw new DAOConfigurationException("File properties " + PROPERTIES_FILE + " cannot be found.");
        }

        try {
            properties.load(fichierProperties);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            nomUtilisateur = properties.getProperty(PROPERTY_USERNAME);
            motDePasse = properties.getProperty(PROPERTY_PASSWORD);
        } catch (IOException e) {
            throw new DAOConfigurationException("Cannot load file properties " + PROPERTIES_FILE, e);
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Cannot find driver in classpath.", e);
        }

        DAOFactory instance = new DAOFactory(url, nomUtilisateur, motDePasse);
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
     /* package */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
