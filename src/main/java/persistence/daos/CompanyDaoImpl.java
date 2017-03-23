package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static persistence.daos.DAOUtilitaire.close;
import static persistence.daos.DAOUtilitaire.initPreparedStatement;

/**
 * Created by ebiz on 14/03/17.
 */
public class CompanyDaoImpl extends Dao implements CompanyDao {



    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM company";

    private static final String SQL_SELECT_BETWEEN = "SELECT * FROM company WHERE id >= ? AND id < ?";

    private static final int PAGE_SIZE = 10;

    private Company company = null;
    private List<Company> list_companies;

    /**
     * Default constructor.
     */
    private CompanyDaoImpl() {
    }

    private static class SingletonHelper {
        private static final CompanyDao INSTANCE = new CompanyDaoImpl();
    }

    public static CompanyDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Utilitary method to map one row returned from database and company bean.
     *
     * @param resultSet (required) ResultSet from database request.
     * @throws SQLException SQL exception.
     * @return mapped company.
     */
    public static Company map(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong("id"));
        company.setName(resultSet.getString("name"));
        return company;
    }

    @Override
    public Company findById(Long id) throws DAOException {
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
        /* Iterate over returned ResultSet */
            if (resultSet.next()) {
                company = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return company;
    }

    @Override
    public List<Company> getAll() throws DAOException {
        list_companies = new ArrayList<Company>();
        try {
        /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_ALL, false);

            resultSet = preparedStatement.executeQuery();
        /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                company = map(resultSet);
                list_companies.add(company);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return list_companies;
    }

    @Override
    public List<Company> getPageList(int page) throws DAOException {
        list_companies = new ArrayList<Company>();
        try {
         /* Get connexion back from Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BETWEEN, false, page, page + PAGE_SIZE);
            resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                company = map(resultSet);
                list_companies.add(company);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(resultSet, preparedStatement, connexion);
        }
        return list_companies;
    }


}