package persistence.daos;

import persistence.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ebiz on 16/03/17.
 */
public abstract class Dao<T> {

    protected static DAOFactory daoFactory = DAOFactory.getInstance();

    protected Connection connexion = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;
    protected Object entity = null;
    protected Long id = null;

}
