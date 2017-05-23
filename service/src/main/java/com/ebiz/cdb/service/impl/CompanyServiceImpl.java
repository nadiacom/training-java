package com.ebiz.cdb.service.impl;

import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.persistence.dao.CompanyDao;
import com.ebiz.cdb.persistence.dao.ComputerDao;
import com.ebiz.cdb.service.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("service.CompanyService");
    private CompanyDao companyDao;
    private ComputerDao computerDao;

    /**
     * CompanyService constructor.
     *
     * @param companyDao  autowired companyDao
     * @param computerDao autowired computerDao
     */
    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao, ComputerDao computerDao) {
        this.companyDao = companyDao;
        this.computerDao = computerDao;
    }

    @Override
    public List<Company> getAll() {
        List<Company> companies = companyDao.getAll();
        return companies;
    }

    @Override
    public List<Company> getPageList(int page) {
        List<Company> companies = companyDao.getPageList(page);
        return companies;
    }

    @Override
    public Company findById(Long id) {
        Company company = companyDao.findById(id);
        //daoFactory.close();
        return company;
    }

    @Override
    public List<Company> findByName(String name, int page, int nbComputerByPage) {
        List<Company> companies = companyDao.findByName(name, page, nbComputerByPage);
        return companies;
    }

    @Override
    public Long delete(int id) {
         /* Retieve computer */
        Company c1 = companyDao.findById(Long.valueOf(id));
        Long companyId = companyDao.delete(c1);
        return companyId;
    }

    @Override
    public Long delete(Company company) {
        Long companyId = null;
        /*
        if (company != null) {
            //Delete company
            companyId = companyDao.delete(company);
            LOGGER.debug("Removed company with id: " + companyId);
            //Delete potential computers belonging to this company
            List<Computer> computers = computerDao.findByCompanyId(company.getId());
            if (!computers.isEmpty()) {
                computerDao.deleteByCompanyId(company.getId());
                LOGGER.debug("Removed following computer: " + computers);
            } else {
                LOGGER.debug("No computer belong to this company. No computer removed");
            }
        } else {
            LOGGER.debug("No company exists with the given id. Try another one.");
        }
        daoFactory.commit();
        daoFactory.close();*/
        return companyId;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public void setComputerDao(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }

}