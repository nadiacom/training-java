package com.ebiz.cdb.persistence.dao.impl;

import com.ebiz.cdb.persistence.Computer;
import com.ebiz.cdb.persistence.Computer;
import com.ebiz.cdb.persistence.QCompany;
import com.ebiz.cdb.persistence.QComputer;
import com.ebiz.cdb.persistence.dao.ComputerDao;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.dml.SQLInsertClause;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ComputerDaoImpl implements ComputerDao {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("com.ebiz.cdb.persistence.dao.impl.ComputerDaoImpl");

    @Autowired
    private SQLQueryFactory query;

    @Override
    public Long create(Computer computer) {
        QComputer c = QComputer.computer;
        return query.insert(c)
                .set(c.name, computer.getName())
                .set(c.introduced, computer.getIntroduced())
                .set(c.discontinued, computer.getDiscontinued())
                .set(c.companyId, computer.getCompanyId()).executeWithKeys(c.id).get(0);
    }

    @Override
    public Computer findById(Long id) {
        SQLTemplates templates = new H2Templates();
        /* CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
        Root<Computer> computerRoot = cq.from(Computer.class);
        cq.where(cb.equal(computerRoot.get("id"), id));
        TypedQuery<Computer> q = em.createQuery(cq);*/
        return null;
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) {
        QComputer c = QComputer.computer;
        QCompany company = QCompany.company;
        return query.selectFrom(c).innerJoin(c.computerCompany1Fk, company).where(c.name.like(name).or(company.name.like(name)))
                .fetch();
    }

    @Override
    public Long countByName(String name) {
        /* CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Computer.class)));
        Root<Computer> computerRoot = cq.from(Computer.class);
        Root<Company> companyRoot = cq.from(Company.class);
        //Left join on computer.company = company
        Join<Computer, Company> company = computerRoot.join("company", JoinType.LEFT);
        company.on(
                cb.equal(company, cb.parameter(Company.class, "company"))
        );
        //Where computer or company name like
        cq.where(
                cb.or(
                        cb.like(computerRoot.get("name"), "%" + name + "%"),
                        cb.like(companyRoot.get("name"), "%" + name + "%")
                )
        ); */
        return null;
    }

    @Override
    public Long update(Computer computer) {
        QComputer c = QComputer.computer;
        query.update(c)
                .where(c.id.eq(computer.getId()))
                .set(c.name, computer.getName())
                .set(c.introduced, computer.getIntroduced())
                .set(c.discontinued, computer.getDiscontinued())
                .set(c.companyId, computer.getCompanyId())
                .execute();
        return computer.getId();
    }

    @Override
    public Long remove(Computer computer) {
        QComputer c = QComputer.computer;
        query.delete(c)
                .where(c.id.eq(computer.getId()))
                .execute();
        return computer.getId();
    }

    @Override
    public List<Computer> getAll() {
        QComputer c = QComputer.computer;
        QCompany company = QCompany.company;
        return query.selectFrom(c).innerJoin(c.computerCompany1Fk, company)
                .fetch();
    }

    @Override
    public List<Computer> getPageList(int page, int nbComputerByPage) {
        QComputer c = QComputer.computer;
        QCompany company = QCompany.company;
        return query.selectFrom(c).innerJoin(c.computerCompany1Fk, company).limit(nbComputerByPage).offset(page)
                .fetch();
    }

    @Override
    public Long count() {
        /* CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Computer.class))); */
        return null;
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
        QComputer c = QComputer.computer;
        return query.selectFrom(c).where(c.companyId.eq(companyId))
                .fetch();
    }

    @Override
    public List<Computer> getPageListNameOrderBy(int page, int nbComputerByPage, String name, String columnName, String orderBy) {

        List<Computer> listComputers = new ArrayList<>();
       /* CriteriaBuilder builder = em.getCriteriaBuilder();
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
        } */
        return listComputers;
    }

    @Override
    public List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String columnName, String orderBy) {

        List<Computer> listComputers = new ArrayList<>();
        /* CriteriaBuilder builder = em.getCriteriaBuilder();
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
        } */
        return listComputers;
    }

}
