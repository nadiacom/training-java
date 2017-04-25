package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;
import models.Computer;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import persistence.DAOFactory;
import services.validators.inputs.Input;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static persistence.daos.DAOUtilitaire.initPreparedStatement;

/**
 * Created by ebiz on 14/03/17.
 */
@Transactional(readOnly = true)
public class ComputerDaoImpl implements ComputerDao {


    private static DAOFactory daoFactory;
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.ComputerDaoImpl");

    private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id AS company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id AS company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? LIMIT ? OFFSET ?";
    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) as total FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ?";
    private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id =? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id AS company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id";
    private static final String SQL_COUNT = "SELECT COUNT(*) as total FROM computer";
    private static final String SQL_SELECT_PAGE = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id AS company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id LIMIT ? OFFSET ?";
    private static final String SQL_DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id = ?";
    private static final String SQL_SELECT_BY_COMPANY = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id AS company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE company_id = ?";
    private static final String SQL_ORDER_BY = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id AS company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? ORDER BY ? ? LIMIT ? OFFSET ?";

    private JdbcTemplate jdbcTemplate;

    /**
     * Default constructor.
     */
    ComputerDaoImpl() {
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Map list of companies.
     *
     * @param rows list of companies (List<Map<String, Object>>).
     * @return list of companies (ArrayList).
     */
    public List<Computer> map(List<Map<String, Object>> rows) {
        Input computerUtils = new Input();
        List<Computer> computers = new ArrayList<>();
        for (Map<String, Object> map : rows) {
            Company company = new Company();
            company.setId((Long) rows.get(0).get("company_id"));
            company.setName((String) rows.get(0).get("company_name"));
            Computer computer = new
                    Computer.ComputerBuilder()
                    .id((Long) rows.get(0).get("id"))
                    .name((String) rows.get(0).get("name"))
                    .introduced((String) rows.get(0).get("introduced") != null ? computerUtils.getLocalDate((String) rows.get(0).get("introduced")) : null)
                    .discontinued((String) rows.get(0).get("introduced") != null ? computerUtils.getLocalDate((String) rows.get(0).get("discontinued")) : null)
                    .company(company)
                    .build();
            computers.add(computer);
        }
        return computers;
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
        company.setId(resultSet.getLong("company_id"));
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
        try {
        /* Get connexion back from Factory */
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced().toString(), computer.getDiscontinued().toString(), computer.getCompany() != null ? computer.getCompany().getId() : null);
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
            daoFactory.close(preparedStatement);
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        }
        return id;
    }

    @Override
    public Computer findById(Long id) throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_ID, id);
        return map(rows).get(0);
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_NAME, "%" + name + "%", "%" + name + "%", nbComputerByPage, (page - 1) * nbComputerByPage);
        return map(rows);
    }

    @Override
    public int countByName(String name) throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_COUNT_BY_NAME, "%" + name + "%", "%" + name + "%");
        return (int) rows.get(0).get("total");
    }

    @Override
    public Long update(Computer computer) throws DAOException {
        Long id = null;
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_UPDATE, false, computer.getName(), computer.getIntroduced().toString(), computer.getDiscontinued().toString(), computer.getCompany() != null ? computer.getCompany().getId() : null, computer.getId());
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
            /* Analyze status returned from update request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = computer.getId();
            }
            daoFactory.close(preparedStatement);
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        }
        return id;
    }

    @Override
    public Long remove(Computer computer) throws DAOException {
        Long id = null;
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_DELETE, false, computer.getId());
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
           /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            } else {
                id = computer.getId();
            }
            daoFactory.close(preparedStatement);
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        }
        return id;
    }

    @Override
    public List<Computer> getAll() throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL);
        return map(rows);
    }

    @Override
    public List<Computer> getPageList(int page, int nbComputerByPage) throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_PAGE, nbComputerByPage, (page - 1) * nbComputerByPage);
        return map(rows);
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
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = initPreparedStatement(connexion, SQL_DELETE_BY_COMPANY, false, companyId);
            int status = preparedStatement.executeUpdate();
            LOGGER.debug(preparedStatement.toString());
           /* Analyze status returned from insert request */
            if (status == 0) {
                throw new DAOException("Failed to update table, no row were modified.");
            }
            daoFactory.close(preparedStatement);
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> findByCompanyId(Long companyId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_COMPANY, companyId);
        return map(rows);
    }

    @Override
    public List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String name, String columnName, String orderBy) throws DAOException {
        List<Computer> listComputer = new ArrayList<>();
        Computer computer = null;
        try {
            Connection connexion = daoFactory.getConnection();
            String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON " +
                    "c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? ORDER BY ";
            sql += columnName + " " + orderBy;
            sql += " LIMIT ? OFFSET ?";
            LOGGER.debug(sql);
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + name + "%");
            preparedStatement.setLong(3, nbComputerByPage);
            preparedStatement.setLong(4, (page - 1) * nbComputerByPage);

            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.debug(preparedStatement.toString());
         /* Iterate over returned ResultSet */
            while (resultSet.next()) {
                computer = map(resultSet);
                listComputer.add(computer);
            }
            daoFactory.close(resultSet, preparedStatement);
        } catch (SQLException e) {
            LOGGER.debug(e.toString());
            throw new DAOException(e);
        }
        return listComputer;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

}
