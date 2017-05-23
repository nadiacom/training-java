package com.ebiz.cdb.persistence.dao.impl;

import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.persistence.dao.CompanyDao;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CompanyDaoImpl implements CompanyDao {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("com.ebiz.cdb.persistence.dao.impl.CompanyDaoImpl");

    private static final String SQL_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ? LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM company";
    private static final String SQL_SELECT_PAGE = "SELECT * FROM company WHERE id LIMIT ? OFFSET ?";
    private static final String SQL_DELETE = "DELETE FROM company WHERE id = ?";

    private static final int PAGE_SIZE = 10;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Company findById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Company> cq = cb.createQuery(Company.class);
        Root<Company> companyRoot = cq.from(Company.class);
        cq.where(cb.equal(companyRoot.get("id"), id));
        TypedQuery<Company> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public List<Company> findByName(String name, int page, int nbComputerByPage) {
        List<Company> companiesList = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
        Root<Company> companyRoot = criteria.from(Company.class);
        criteria.where(
                builder.like(companyRoot.get("name"), "%" + name + "%")
        );
        criteria.select(companyRoot);
        List<Company> companies = em.createQuery(criteria).getResultList();
        for (Company computer : companies) {
            companiesList.add(computer);
        }
        return companiesList;
    }

    @Override
    public List<Company> getAll() {
        List<Company> companyList = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
        Root<Company> computerRoot = criteria.from(Company.class);
        criteria.select(computerRoot);
        List<Company> computers = em.createQuery(criteria).getResultList();
        for (Company computer : computers) {
            companyList.add(computer);
        }
        return companyList;
    }

    @Override
    public List<Company> getPageList(int page) {
        List<Company> companiesList = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);

        Root<Company> companyRoot = criteria.from(Company.class);

        criteria.select(companyRoot);
        List<Company> companies = em.createQuery(criteria).setFirstResult((page - 1) * PAGE_SIZE).setMaxResults(PAGE_SIZE).getResultList();
        for (Company computer : companies) {
            companiesList.add(computer);
        }
        return companiesList;
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
