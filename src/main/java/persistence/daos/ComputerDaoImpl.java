package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;
import models.Computer;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import persistence.DAOFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Long create(Computer computer) throws DAOException {
        Long generatedComputerId = 0L;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                            ps.setString(1, computer.getName());
                            ps.setString(2, computer.getIntroduced() != null ? computer.getIntroduced().toString() : null);
                            ps.setString(3, computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : null);
                            ps.setString(4, computer.getCompany() != null ? computer.getCompany().getId().toString() : null);
                            return ps;
                        }
                    },
                    keyHolder);
            generatedComputerId = (Long) keyHolder.getKey();
        } catch (Exception e) {
            LOGGER.debug("Error creating computer : " + e.getMessage() + e.getStackTrace());
        }

        return generatedComputerId;
    }

    @Override
    public Computer findById(Long id) throws DAOException {
        Computer computer = null;
        try {
            computer = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, new ComputerMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving computer with ID : " + id + e.getMessage() + e.getStackTrace());
        }
        return computer;
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) throws DAOException {
        List<Computer> listComputers = new ArrayList<>();
        try {
            listComputers = jdbcTemplate.query(SQL_SELECT_BY_NAME, new Object[]{"%" + name + "%", "%" + name + "%", nbComputerByPage, (page - 1) * nbComputerByPage}, new ComputerMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving computers with computer or company like : " + name + e.getMessage() + e.getStackTrace());
        }
        return listComputers;
    }

    @Override
    public int countByName(String name) throws DAOException {
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_BY_NAME, new Object[]{"%" + name + "%", "%" + name + "%"}, Integer.class);
        } catch (Exception e) {
            LOGGER.debug("Error counting computers with computer or company like : " + name + e.getMessage() + e.getStackTrace());
        }
        return count;
    }

    @Override
    public Long update(Computer computer) throws DAOException {
        Long id = computer.getId();
        try {
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
                            ps.setString(1, computer.getName());
                            ps.setString(2, computer.getIntroduced() != null ? computer.getIntroduced().toString() : null);
                            ps.setString(3, computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : null);
                            ps.setString(4, computer.getCompany() != null ? computer.getCompany().getId().toString() : null);
                            ps.setString(5, id.toString());
                            return ps;
                        }
                    });
        } catch (Exception e) {
            LOGGER.debug("Error updating computer with ID : " + id + e.getMessage() + e.getStackTrace());
        }
        return id;
    }

    @Override
    public Long remove(Computer computer) throws DAOException {
        Long id = computer.getId();
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception e) {
            LOGGER.debug("Error deleting computer with ID : " + id + e.getMessage() + e.getStackTrace());
        }
        return id;
    }

    @Override
    public List<Computer> getAll() throws DAOException {
        List<Computer> listComputers = new ArrayList<>();
        try {
            listComputers = jdbcTemplate.query(SQL_SELECT_ALL, new ComputerMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving all computers : " + e.getMessage() + e.getStackTrace());
        }
        return listComputers;
    }

    @Override
    public List<Computer> getPageList(int page, int nbComputerByPage) throws DAOException {
        List<Computer> listComputers = new ArrayList<>();
        try {
            listComputers = jdbcTemplate.query(SQL_SELECT_PAGE, new Object[]{nbComputerByPage, (page - 1) * nbComputerByPage}, new ComputerMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving computers for page : " + page + e.getMessage() + e.getStackTrace());
        }
        return listComputers;
    }

    @Override
    public int count() throws DAOException {
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
        } catch (Exception e) {
            LOGGER.debug("Error counting computers " + e.getMessage() + e.getStackTrace());
        }
        return count;
    }

    @Override
    public void deleteByCompanyId(Long companyId) throws DAOException {
        try {
            jdbcTemplate.update(SQL_DELETE_BY_COMPANY, new Object[]{companyId});
        } catch (Exception e) {
            LOGGER.debug("Error deleting computer with company id " + companyId + e.getMessage() + e.getStackTrace());
        }
    }

    @Override
    public List<Computer> findByCompanyId(Long companyId) {
        List<Computer> listComputers = new ArrayList<>();
        try {
            listComputers = jdbcTemplate.query(SQL_SELECT_BY_COMPANY, new Object[]{companyId}, new ComputerMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving computer with company id " + companyId + e.getMessage() + e.getStackTrace());
        }
        return listComputers;
    }

    @Override
    public List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String name, String columnName, String orderBy) throws DAOException {
        List<Computer> listComputers = new ArrayList<>();
        try {
            String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, company.name AS company_name FROM computer AS c LEFT JOIN company ON " +
                    "c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? ORDER BY ";
            sql += columnName + " " + orderBy;
            sql += " LIMIT ? OFFSET ?";
            listComputers = jdbcTemplate.query(sql, new Object[]{"%" + name + "%", "%" + name + "%", nbComputerByPage, (page - 1) * nbComputerByPage}, new ComputerMapper());

        } catch (Exception e) {
            LOGGER.debug("Error retrieving computer for page " + page + "and computer/company name like " + name + e.getMessage() + e.getStackTrace());
        }
        return listComputers;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }


    class ComputerMapper implements RowMapper<Computer> {

        /**
         * Utilitary method to map one row returned from JDBCTemplate row to computer bean.
         *
         * @param resultSet (required) ResultSet from database request.
         * @param rowNum    row number.
         * @return mapped computer.
         * @throws SQLException SQL exception.
         */
        public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
    }

}
