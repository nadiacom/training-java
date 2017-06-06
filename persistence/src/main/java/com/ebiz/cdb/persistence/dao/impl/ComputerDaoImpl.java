package com.ebiz.cdb.persistence.dao.impl;

import com.ebiz.cdb.core.models.Computer;
import com.ebiz.cdb.core.models.PageRequest;
import com.ebiz.cdb.persistence.dao.ComputerDao;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ComputerDaoImpl implements ComputerDao {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("com.ebiz.cdb.persistence.dao.impl.ComputerDaoImpl");

    @PersistenceContext
    private EntityManager em;

    @Override
    public Computer create(Computer computer) {
        em.persist(computer);
        em.flush();
        return computer;
    }

    @Override
    public Computer findById(Long id) {
        Query query = em.createQuery("FROM Computer C where C.id = :id");
        query.setParameter("id", id);
        return (Computer) query.getSingleResult();
    }

    @Override
    public Computer update(Computer computer) {
        Computer c = em.find(Computer.class, computer.getId());
        c.setName(computer.getName());
        c.setIntroduced(computer.getIntroduced());
        c.setDiscontinued(computer.getDiscontinued());
        c.setCompany(computer.getCompany());
        return em.merge(c);
    }

    @Override
    public Long remove(Computer c) {
        Long id = c.getId();
        em.remove(c);
        return id;
    }

    @Override
    public void remove(Long[] ids) {
        for (Long id : ids) {
            Computer computer = em.find(Computer.class, id);
            em.remove(computer);
        }
    }

    @Override
    public List<Computer> getAll() {
        Query query = em.createQuery("FROM Computer C");
        return query.getResultList();
    }

    @Override
    public List<Computer> getPageList(int page, int nbComputerByPage) {
        Query query = em.createQuery("FROM Computer C");
        return query.setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
    }

    @Override
    public PageRequest<Computer> getPage(int page, int nbComputerByPage) {
        List<Computer> computerList = getPageList(page, nbComputerByPage);
        return new
                PageRequest.PageRequestBuilder()
                .nbComputerByPage(nbComputerByPage)
                .nbComputers(computerList.size())
                .currentPage(page)
                .listComputers(computerList)
                .build();
    }

    @Override
    public int count() {
        Query query = em.createQuery("FROM Computer C");
        return query.getResultList().size();
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) {
        Query query = em.createQuery("FROM Computer C left join C.company as company "
                + "WHERE C.name like :name "
                + "OR  company.name like :name ");
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public int countByName(String name) {
        Query query = em.createQuery("FROM Computer C left join C.company as company "
                + "WHERE C.name like :name "
                + "OR  company.name like :name ");
        query.setParameter("name", "%" + name + "%");
        return query.getResultList().size();
    }

    @Override
    public void deleteByCompanyId(Long companyId) {
        Query query = em.createQuery("DELETE FROM Computer C where C.company.id = :id");
        query.setParameter("id", companyId);
        query.executeUpdate();
    }

    @Override
    public List<Computer> findByCompanyId(Long companyId) {
        Query query = em.createQuery("FROM Computer C where C.company.id = :id");
        query.setParameter("id", companyId);
        return query.getResultList();
    }

    @Override
    public List<Computer> getPageListNameOrderBy(int page, int nbComputerByPage, String name, String column, String orderBy) {
        String order = getOrder(column);
        Query query = em.createQuery("FROM Computer C left join C.company as company "
                + "WHERE C.name like '%" + name + "%' "
                + "OR  company.name like '%" + name + "%' " + order + orderBy);
        return query.setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
    }

    @Override
    public List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String column, String orderBy) {
        String order = getOrder(column);
        Query query = em.createQuery("FROM Computer C left join C.company as company " + order + orderBy);
        return query.setFirstResult((page - 1) * nbComputerByPage).setMaxResults(nbComputerByPage).getResultList();
    }

    /**
     * Utilitary method to get column name for order by request.
     *
     * @param column column name
     * @return ORDER BY column name
     */
    private String getOrder(String column) {
        String order = " ORDER BY ";
        switch (column) {
            default:
            case "name":
                order += "C.name ";
                break;
            case "introduced":
                order += "C.introduced ";
                break;
            case "discontinued":
                order += "C.discontinued ";
                break;
            case "company":
                order += "company.name ";
                break;
        }
        return order;
    }

}
