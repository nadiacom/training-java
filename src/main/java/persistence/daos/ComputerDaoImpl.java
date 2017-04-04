package persistence.daos;

import cli.CompanyCli;
import exceptions.daos.DAOException;
import models.Company;
import models.Computer;
import org.slf4j.LoggerFactory;
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
public enum ComputerDaoImpl implements ComputerDao {

    INSTANCE;

    protected DAOFactory daoFactory = DAOFactory.INSTANCE;
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.ComputerDaoImpl");

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? LIMIT ? OFFSET ?";
    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) as total FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ?";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id =? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id";
    private static final String SQL_COUNT = "SELECT COUNT(*) as total FROM computer";
    private static final String SQL_SELECT_PAGE = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id LIMIT ? OFFSET ?";
    private static final String SQL_DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id = ?";
    private static final String SQL_SELECT_BY_COMPANY = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE company_id = ?";
    private static final String SQL_ORDER_BY = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id ORDER BY ? ? LIMIT ? OFFSET ?";

    private static CompanyCli companyService = new CompanyCli();

    /**
     * Default constructor.
     */
    ComputerDaoImpl() {
    }

    /**
     * Utilitary method to map one row returned from database and computer bean.
     *
     * @param resultSet (required) ResultSet from database request.
     * @return mapped computer.
     * @throws SQLException SQL exception.
     */
    private static Computer map(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong("id"));
        company.setName(resultSet.getString("company_name"));
        Computer computer = new
                Computer.ComputerBuilder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .introduced(resultSet.getTimestamp("introduced") != null ? resultSet.getTimestamp("introduced").toLocalDateTime().toLocalDate() : null)
                .discontinued(resultSet.getTimestamp("discontinued") != null ? resultSet.getTimestamp("discontinued").toLocalDateTime().toLocalDate() : null)
                .company(company)
                .build();
        return computer;
    }

    @Override
    public Long create(Computer computer) throws DAOException {
        Long id = null;
        PreparedStatement preparedStatement = null;
        try {
        /* Get connexion back from Factory */
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced().toString(), computer.getDiscontinued().toString(), computer.getCompany() != null ? computer.getCompany().getId() : null);
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
        /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to create company, no row were created.");
            }
        /* Get auto-generated id from insert request */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                /* Initialize id property of computer bean */
                computer.setId(resultSet.getLong(1));
                id = resultSet.getLong(1);
            } else {
                LOGGER.debug("Failed to create company, no auto generated ID returned.");
                throw new DAOException("Failed to create company, no auto generated ID returned.");
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(preparedStatement);
        }
        return id;
    }

    @Override
    public Computer findById(Long id) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
         /* Iterate over returned ResultSet */
            if (resultSet.next()) {
                computer = map(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return computer;
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) throws DAOException {
        List<Computer> listComputer = new ArrayList<Computer>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BY_NAME, false, "%" + name + "%", "%" + name + "%", nbComputerByPage, (page - 1) * nbComputerByPage);
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return listComputer;
    }

    @Override
    public int countByName(String name) throws DAOException {
        int nb = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_COUNT_BY_NAME, false, "%" + name + "%", "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
            if (resultSet.next()) {
                nb = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return nb;
    }

    @Override
    public Long update(Computer computer) throws DAOException {
        Long id = null;
        PreparedStatement preparedStatement = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_UPDATE, false, computer.getName(), computer.getIntroduced().toString(), computer.getDiscontinued().toString(), computer.getCompany() != null ? computer.getCompany().getId() : null, computer.getId());
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
            /* Analyze status returned from update request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = computer.getId();
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(preparedStatement);
        }
        return id;
    }

    @Override
    public Long remove(Computer computer) throws DAOException {
        Long id = null;
        PreparedStatement preparedStatement = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_DELETE, false, computer.getId());
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
           /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = computer.getId();
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(preparedStatement);
        }
        return id;
    }

    @Override
    public List<Computer> getAll() throws DAOException {
        List<Computer> listComputer = new ArrayList<Computer>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return listComputer;
    }

    @Override
    public List<Computer> getPageList(int page, int nbComputerByPage) throws DAOException {
        List<Computer> listComputer = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_PAGE, false, nbComputerByPage, (page - 1) * nbComputerByPage);
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug("PAGE LIST : " + preparedStatement.toString());
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return listComputer;
    }

    @Override
    public int count() throws DAOException {
        int nb = 0;

        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_COUNT, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.debug("COUNT : " + preparedStatement.toString());
            LOGGER.debug("COUNT RESULT SET : " + resultSet.toString());
            if (resultSet.next()) {
                nb = resultSet.getInt("total");
            }
            daoFactory.close(resultSet, preparedStatement);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            throw new DAOException(e);
        }
        return nb;
    }

    @Override
    public void deleteByCompanyId(Long companyId) throws DAOException {
        List<Computer> listComputer = new ArrayList<Computer>();
        PreparedStatement preparedStatement = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_DELETE_BY_COMPANY, false, companyId);
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
           /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(preparedStatement);
        }
    }

    @Override
    public List<Computer> findByCompanyId(Long companyId) {
        List<Computer> listComputer = new ArrayList<Computer>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_BY_COMPANY, false, companyId);
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return listComputer;
    }

    @Override
    public List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String columnName, String orderBy) throws DAOException {
        List<Computer> listComputer = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            preparedStatement = initPreparedStatement(connexion, SQL_SELECT_PAGE, false, columnName, orderBy, nbComputerByPage, (page - 1) * nbComputerByPage);
            resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        } finally {
            daoFactory.close(resultSet, preparedStatement);
        }
        return listComputer;
    }


}
