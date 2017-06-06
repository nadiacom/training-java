package com.ebiz.cdb.service.impl;

import com.ebiz.cdb.core.models.Computer;
import com.ebiz.cdb.core.models.PageRequest;
import com.ebiz.cdb.persistence.dao.CompanyDao;
import com.ebiz.cdb.persistence.dao.ComputerDao;
import com.ebiz.cdb.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("service.ComputerService");
    private ComputerDao computerDao;
    private CompanyDao companyDao;

    /**
     * ComputerService constructor.
     *
     * @param computerDao autowired computerDao
     * @param companyDao  autowired companyDao
     */
    @Autowired
    public ComputerServiceImpl(ComputerDao computerDao, CompanyDao companyDao) {
        this.computerDao = computerDao;
        this.companyDao = companyDao;
    }

    @Override
    public Computer create(Computer computer) {
        return computerDao.create(computer);
    }

    @Override
    public Computer update(Computer computer) {
        return computerDao.update(computer);
    }

    @Override
    public Long delete(int id) {
        /* Retieve */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Delete and return id */
        Long computerId = computerDao.remove(c1);
        return computerId;
    }

    @Override
    public Computer findById(Long id) {
        Computer c = computerDao.findById(id);
        return c;
    }

    @Override
    public List<Computer> findByName(String name, int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.findByName(name, page, nbComputerByPage);
        return computers;
    }


    @Override
    public List<Computer> getAll() {
        List<Computer> computers = computerDao.getAll();
        return computers;
    }

    @Override
    public List<Computer> getByPage(int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.getPageList(page, nbComputerByPage);
        return computers;
    }

    @Override
    public PageRequest<Computer> getPage(int page, int nbComputerBypage) {
        return computerDao.getPage(page, nbComputerBypage);
    }

    @Override
    public List<Computer> findByOrder(String colmunName, String orderBy, int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.getPageListOrderBy(page, nbComputerByPage, colmunName, orderBy);
        return computers;
    }

    @Override
    public List<Computer> findByNameAndOrder(String name, String colmunName, String orderBy, int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.getPageListNameOrderBy(page, nbComputerByPage, name, colmunName, orderBy);
        return computers;
    }

    @Override
    public List<Computer> findByCompanyId(int id) {
        List<Computer> computers = computerDao.findByCompanyId(Long.valueOf(id));
        return computers;
    }


    @Override
    public void deleteByCompanyId(int id) {
        computerDao.deleteByCompanyId(Long.valueOf(id));
    }

    @Override
    public int count() {
        int count = computerDao.count();
        return count;
    }

    @Override
    public int countByName(String name) {
        int count = computerDao.countByName(name);
        return count;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public void setComputerDao(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }
}
