package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import persistence.DAOFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * Map list of companies
     *
     * @param rows list of companies (List<Map<String, Object>>)
     * @return list of companies (ArrayList)
     */
    public List<Company> map(List<Map<String, Object>> rows) {
        List<Company> companies = new ArrayList<>();

        for (Map<String, Object> map : rows) {
            Company company = new Company();
            company.setId((Long) rows.get(0).get("id"));
            company.setName((String) rows.get(0).get("name"));
            companies.add(company);
        }
        return companies;
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
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_ID, id);
        return map(rows).get(0);
    }

    @Override
    public List<Company> findByName(String name, int page, int nbComputerByPage) throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_NAME, "%" + name + "%", PAGE_SIZE, (page - 1) * PAGE_SIZE);
        return map(rows);
    }

    @Override
    public List<Company> getAll() throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL);
        return map(rows);
    }

    @Override
    public List<Company> getPageList(int page) throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_PAGE);
        return map(rows);
    }

    @Override
    public Long delete(Company company) throws DAOException {
        jdbcTemplate.update(SQL_DELETE, company.getId());
        return company.getId();
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }
}
