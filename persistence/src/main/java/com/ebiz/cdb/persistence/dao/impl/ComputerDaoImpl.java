package com.ebiz.cdb.persistence.dao.impl;


import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.core.models.Computer;
import com.ebiz.cdb.core.models.Computer_;
import com.ebiz.cdb.persistence.dao.ComputerDao;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ComputerDaoImpl implements ComputerDao {

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

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long create(Computer computer) {
        Long generatedComputerId = 0L;
        /*
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
        }*/

        return generatedComputerId;
    }

    @Override
    public Computer findById(Long id) {

        Computer computer = null;
        /*
        try {
            computer = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, new ComputerMapper());
        } catch (Exception e) {
            LOGGER.debug("Error retrieving computer with ID : " + id + e.getMessage() + e.getStackTrace());
        } */
        return computer;
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) {
        List<Computer> listComputers = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);

        Root<Computer> computerRoot = criteria.from(Computer.class);

        //Left join on computer.company = company
        Join<Company, Computer> computerCompany = computerRoot.join("company");

        criteria.where(
                builder.or(
                        builder.like(computerRoot.get("name"), "%" + name + "%"),
                        builder.like(computerCompany.get("name"), "%" + name + "%")
                )
        );
        criteria.select(computerRoot);

        List<Computer> computers = em.createQuery(criteria).setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
        for (Computer computer : computers) {
            listComputers.add(computer);
        }
        return listComputers;
    }

    @Override
    public int countByName(String name) {

        int count = 0;
        /*
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT_BY_NAME, new Object[]{"%" + name + "%", "%" + name + "%"}, Integer.class);
        } catch (Exception e) {
            LOGGER.debug("Error counting computers with computer or company like : " + name + e.getMessage() + e.getStackTrace());
        }*/
        return count;
    }

    @Override
    public Long update(Computer computer) {

        Long id = computer.getId();
         /*
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
        } */
        return id;
    }

    @Override
    @Transactional
    public Long remove(Computer computer) {
        Long id = computer.getId();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // create delete
        CriteriaDelete<Computer> delete = cb.
                createCriteriaDelete(Computer.class);

        // set the root class
        Root e = delete.from(Computer.class);

        // set where clause
        delete.where(cb.equal(e.get("id"), computer.getId()));

        // perform update
        em.createQuery(delete).executeUpdate();
        return id;
    }

    @Override
    public List<Computer> getAll() {
        List<Computer> listComputers = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
        Root<Computer> computerRoot = criteria.from(Computer.class);
        criteria.select(computerRoot);
        List<Computer> computers = em.createQuery(criteria).getResultList();
        for (Computer computer : computers) {
            listComputers.add(computer);
        }
        return listComputers;
    }

    @Override
    public List<Computer> getPageList(int page, int nbComputerByPage) {
        List<Computer> listComputers = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);

        Root<Computer> computerRoot = criteria.from(Computer.class);

        Join<Computer, Company> company = computerRoot.join(Computer_.company);
        criteria.select(computerRoot);
        List<Computer> computers = em.createQuery(criteria).setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
        for (Computer computer : computers) {
            listComputers.add(computer);
        }
        return listComputers;
    }

    @Override
    public int count() {
        int count = 0;
        /*
        try {
            count = jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
        } catch (Exception e) {
            LOGGER.debug("Error counting computers " + e.getMessage() + e.getStackTrace());
        }*/
        return count;
    }

    @Override
    public void deleteByCompanyId(Long companyId) {
        /*
        try {
            jdbcTemplate.update(SQL_DELETE_BY_COMPANY, new Object[]{companyId});
        } catch (Exception e) {
            LOGGER.debug("Error deleting computer with company id " + companyId + e.getMessage() + e.getStackTrace());
        }*/
    }

    @Override
    public List<Computer> findByCompanyId(Long companyId) {
        List<Computer> listComputers = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);

        Root<Computer> computerRoot = criteria.from(Computer.class);

        //Left join on computer.company = company
        Join<Company, Computer> computerCompany = computerRoot.join("company");

        criteria.where(
                builder.equal(computerCompany.get("id"), companyId)
        );
        criteria.select(computerRoot);

        List<Computer> computers = em.createQuery(criteria).getResultList();
        for (Computer computer : computers) {
            listComputers.add(computer);
        }
        return listComputers;
    }

    @Override
    public List<Computer> getPageListNameOrderBy(int page, int nbComputerByPage, String name, String columnName, String orderBy) {

        List<Computer> listComputers = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);

        Root<Computer> computerRoot = criteria.from(Computer.class);
        Root<Company> companyRoot = criteria.from(Company.class);
        //Left join on computer.company = company
        Join<Computer, Company> company = computerRoot.join("company", JoinType.LEFT);
        company.on(
                builder.equal(company, builder.parameter(Company.class, "company"))
        );
        //Where computer or company name like
        criteria.where(
                builder.or(
                        builder.like(computerRoot.get("name"), "%" + name + "%"),
                        builder.like(companyRoot.get("name"), "%" + name + "%")
                )
        );
        //Order by colmun name
        switch (orderBy) {
            case "ASC":
                criteria.orderBy(builder.asc(computerRoot.get(columnName)));
                break;
            case "DESC":
                criteria.orderBy(builder.desc(computerRoot.get(columnName)));
                break;
        }

        criteria.select(computerRoot);

        List<Computer> computers = em.createQuery(criteria).setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
        for (Computer computer : computers) {
            listComputers.add(computer);
        }
        return listComputers;
    }

    @Override
    public List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String columnName, String orderBy) {

        List<Computer> listComputers = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);

        Root<Computer> computerRoot = criteria.from(Computer.class);
        Root<Company> companyRoot = criteria.from(Company.class);
        //Left join on computer.company = company
        Join<Computer, Company> company = computerRoot.join("company", JoinType.LEFT);
        company.on(
                builder.equal(company, builder.parameter(Company.class, "company"))
        );

        //Order by colmun name
        switch (orderBy) {
            case "ASC":
                criteria.orderBy(builder.asc(computerRoot.get(columnName)));
                break;
            case "DESC":
                criteria.orderBy(builder.desc(computerRoot.get(columnName)));
                break;
        }

        criteria.select(computerRoot);

        List<Computer> computers = em.createQuery(criteria).setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
        for (Computer computer : computers) {
            listComputers.add(computer);
        }
        return listComputers;
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
                    .introduced(resultSet.getTimestamp("introduced") != null ? resultSet.getTimestamp("introduced").toLocalDateTime() : null)
                    .discontinued(resultSet.getTimestamp("discontinued") != null ? resultSet.getTimestamp("discontinued").toLocalDateTime() : null)
                    .company(company)
                    .build();
            return computer;
        }
    }

}
