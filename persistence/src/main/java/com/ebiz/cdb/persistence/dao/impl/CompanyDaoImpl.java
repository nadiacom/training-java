package com.ebiz.cdb.persistence.dao.impl;

import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.persistence.dao.CompanyDao;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CompanyDaoImpl implements CompanyDao {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.CompanyDaoImpl");

    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ? LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM company";
    private static final String SQL_SELECT_PAGE = "SELECT * FROM company WHERE id LIMIT ? OFFSET ?";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id = ?";

    private static final int PAGE_SIZE = 10;

    @Override
    public Company findById(Long id) {
        Company company = new Company();
        /*
        try {
            company = jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new Object[]{id}, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving company with ID : " + id + e.getMessage() + e.getStackTrace());
        }*/
        return company;
    }

    @Override
    public List<Company> findByName(String name, int page, int nbComputerByPage) {
        List<Company> listCompanies = new ArrayList<>();
        /*
        try {
            listCompanies = jdbcTemplate.query(SQL_SELECT_BY_NAME, new Object[]{"%" + name + "%", PAGE_SIZE, (page - 1) * PAGE_SIZE}, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving company with name like : " + name + e.getMessage() + e.getStackTrace());
        }*/
        return listCompanies;
    }

    @Override
    public List<Company> getAll() {
        List<Company> listCompanies = new ArrayList<>();
        /*
        try {
            listCompanies = jdbcTemplate.query(SQL_SELECT_ALL, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving all companies : " + e.getMessage() + e.getStackTrace());
        }*/
        return listCompanies;
    }

    @Override
    public List<Company> getPageList(int page) {
        List<Company> listCompanies = new ArrayList<>();
        /*
        try {
            listCompanies = jdbcTemplate.query(SQL_SELECT_PAGE, new Object[]{PAGE_SIZE, (page - 1) * PAGE_SIZE}, new CompanyMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving companies for page : " + page + "and page size :" + PAGE_SIZE + e.getMessage() + e.getStackTrace());
        }*/
        return listCompanies;
    }

    @Override
    public Long delete(Company company) {
        Long id = company.getId();
        /*
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception e) {
            LOGGER.debug("Error deleting company with ID : " + id + e.getMessage() + e.getStackTrace());
        }*/
        return id;
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
