package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;
import persistence.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static persistence.daos.DAOUtilitaire.initPreparedStatement;

/**
 * Created by ebiz on 14/03/17.
 */
public enum CompanyDaoImpl implements CompanyDao {

    INSTANCE;

    protected DAOFactory daoFactory = DAOFactory.INSTANCE;

    private Connection connexion;

    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ? LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM company";
    private static final String SQL_SELECT_PAGE = "SELECT * FROM company WHERE id LIMIT ? OFFSET ?";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id = ?";

    private static final int PAGE_SIZE = 10;

    private Company company = null;
    private List<Company> listCompanies;

    /**
     * Default constructor.
     */
    CompanyDaoImpl() {
    }

    /**
     * Utilitary method to map one row returned from database and company bean.
     *
     * @param resultSet (required) ResultSet from database request.
     * @return mapped company.
     * @throws SQLException SQL exception.
     */
    public static Company map(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong("id"));
        company.setName(resultSet.getString("name"));
        return company;
    }

    /**
     * Map company method.
     *
     * @param id   company id.
     * @param name company name.
     * @return commany.
     * @throws SQLException SQLException.
     */

    public static Company map(Long id, String name) throws SQLException {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        return company;
    }


    @Override
    public Company findById(Long id) throws DAOException {
        try {
            daoFactory.open();
            connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BY_ID, false, id);
            ResultSet resultSet = preparedStatement.executeQuery();
        /* Iterate over returned ResultSet */
            if (resultSet.next()) {
                company = map(resultSet.getLong("id"), resultSet.getString("name"));
            }
            daoFactory.close(resultSet, preparedStatement);
            daoFactory.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return company;
    }

    @Override
    public List<Company> findByName(String name, int page, int nbComputerByPage) throws DAOException {
        listCompanies = new ArrayList<Company>();
        try {
            daoFactory.open();
            connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BY_NAME, false, "%" + name + "%", PAGE_SIZE, (page - 1) * PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                company = map(resultSet.getLong("id"), resultSet.getString("name"));
                listCompanies.add(company);
            }
            daoFactory.close(resultSet, preparedStatement);
            daoFactory.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return listCompanies;
    }

    @Override
    public List<Company> getAll() throws DAOException {
        listCompanies = new ArrayList<Company>();
        try {
            daoFactory.open();
            connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_SELECT_ALL, false);

            ResultSet resultSet = preparedStatement.executeQuery();
        /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                company = map(resultSet.getLong("id"), resultSet.getString("name"));
                listCompanies.add(company);
            }
            daoFactory.close(resultSet, preparedStatement);
            daoFactory.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return listCompanies;
    }

    @Override
    public List<Company> getPageList(int page) throws DAOException {
        listCompanies = new ArrayList<Company>();
        try {
            daoFactory.open();
            connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_SELECT_PAGE, false, PAGE_SIZE, (page - 1) * PAGE_SIZE);
            ResultSet resultSet = preparedStatement.executeQuery();
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                company = map(resultSet.getLong("id"), resultSet.getString("name"));
                listCompanies.add(company);
            }
            daoFactory.close(resultSet, preparedStatement);
            daoFactory.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return listCompanies;
    }

    @Override
    public Long delete(Company company) throws DAOException {
        Long id = null;
        try {
            daoFactory.open();
            connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_DELETE, false, company.getId());
            int status = preparedStatement.executeUpdate();
           /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = company.getId();
            }
            daoFactory.close(preparedStatement);
            daoFactory.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            return id;
        }
    }


}
