package main.java.daos;

import main.java.exceptions.DAOException;
import main.java.models.Company;
import main.java.services.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static main.java.daos.DAOUtilitaire.fermeturesSilencieuses;
import static main.java.daos.DAOUtilitaire.initialisationRequetePreparee;

/**
 * Created by ebiz on 14/03/17.
 */
public class CompanyDaoImpl implements CompanyDao {
    private DAOFactory daoFactory;

    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM company";

    public CompanyDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Company findById(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Company company = null;
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
        /* Iterate over returned ResultSet */
            if (resultSet.next()) {
                company = map(resultSet);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return company;
    }

    @Override
    public List<Company> GetAll() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Company company = null;
        List<Company> list_companies = new ArrayList<Company>();
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false);

            resultSet = preparedStatement.executeQuery();
        /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                company = map(resultSet);
                list_companies.add(company);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return list_companies;
    }

    /*
     * Utilitary method to map one row returned from database and company bean
     */
        private static Company map( ResultSet resultSet ) throws SQLException {
        Company company = new Company();
        company.setId( resultSet.getLong( "id" ) );
        company.setName( resultSet.getString( "name" ) );
        return company;
    }

}
