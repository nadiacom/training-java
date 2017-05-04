package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import persistence.DAOFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
@Transactional(readOnly = true)
public class CompanyDaoImpl implements CompanyDao {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.CompanyDaoImpl");

    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ? LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM company";
    private static final String SQL_SELECT_PAGE = "SELECT * FROM company WHERE id LIMIT ? OFFSET ?";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id = ?";

    private static final int PAGE_SIZE = 10;

    private JdbcTemplate jdbcTemplate;
    private static DAOFactory daoFactory;

    /**
     * Default constructor.
     */
    CompanyDaoImpl() {
    }


    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Company findById(Long id) throws DAOException {
        Company company = new Company();
        try {
            company = jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new Object[]{id}, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving company with ID : " + id + e.getMessage() + e.getStackTrace());
        }
        return company;
    }

    @Override
    public List<Company> findByName(String name, int page, int nbComputerByPage) throws DAOException {
        List<Company> listCompanies = new ArrayList<>();
        try {
            listCompanies = jdbcTemplate.query(SQL_SELECT_BY_NAME, new Object[]{"%" + name + "%", PAGE_SIZE, (page - 1) * PAGE_SIZE}, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving company with name like : " + name + e.getMessage() + e.getStackTrace());
        }
        return listCompanies;
    }

    @Override
    public List<Company> getAll() throws DAOException {
        List<Company> listCompanies = new ArrayList<>();
        try {
            listCompanies = jdbcTemplate.query(SQL_SELECT_ALL, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving all companies : " + e.getMessage() + e.getStackTrace());
        }
        return listCompanies;
    }

    @Override
    public List<Company> getPageList(int page) throws DAOException {
        List<Company> listCompanies = new ArrayList<>();
        try {
            listCompanies = jdbcTemplate.query(SQL_SELECT_PAGE, new Object[]{PAGE_SIZE, (page - 1) * PAGE_SIZE}, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving companies for page : " + page + "and page size :" + PAGE_SIZE + e.getMessage() + e.getStackTrace());
        }
        return listCompanies;
    }

    @Override
    public Long delete(Company company) throws DAOException {
        Long id = company.getId();
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception e) {
            LOGGER.debug("Error deleting company with ID : " + id + e.getMessage() + e.getStackTrace());
        }
        return id;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    class CompanyMapper implements RowMapper<Company> {

        /**
         * Utilitary method to map one row returned from JDBCTemplate row to company bean.
         *
         * @param resultSet (required) ResultSet from database request.
         * @param rowNum    row number.
         * @return mapped company.
         * @throws SQLException SQL exception.
         */
        public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Company company = new Company();
            company.setId(resultSet.getLong("id"));
            company.setName(resultSet.getString("name"));
            return company;
        }
    }
}
