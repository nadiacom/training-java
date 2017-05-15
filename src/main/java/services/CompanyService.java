package services;

import models.Company;
import models.Computer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.DAOFactory;
import persistence.daos.CompanyDao;
import persistence.daos.ComputerDao;

import java.util.List;

@Service
public class CompanyService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("services.CompanyService");
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ComputerDao computerDao;
    @Autowired
    private DAOFactory daoFactory;

    /**
     * Default constructor.
     */
    public CompanyService() {
    }

    /**
     * Get all companies.
     *
     * @return all companies.
     */
    public List<Company> getAll() {
        List<Company> companies = companyDao.getAll();
        daoFactory.close();
        return companies;
    }

    /**
     * Get all companies by page.
     *
     * @param page (required) page number.
     * @return companies list by page.
     */
    public List<Company> getPageList(int page) {
        List<Company> companies = companyDao.getPageList(page);
        daoFactory.close();
        return companies;
    }

    /**
     * Get company by id.
     *
     * @param id (required) company id.
     * @return company.
     */
    public Company findById(Long id) {
        Company company = companyDao.findById(id);
        //daoFactory.close();
        return company;
    }

    /**
     * Find companies by name.
     *
     * @param name             company name.
     * @param page             page number.
     * @param nbComputerByPage number of companies displayed by page.
     * @return list of companies.
     */
    public List<Company> findByName(String name, int page, int nbComputerByPage) {
        List<Company> companies = companyDao.findByName(name, page, nbComputerByPage);
        daoFactory.close();
        return companies;
    }

    /**
     * Delete company by id.
     *
     * @param id (required) company id.
     * @return deleted company id.
     */
    public Long delete(int id) {
         /* Retieve computer */
        Company c1 = companyDao.findById(Long.valueOf(id));
        Long companyId = companyDao.delete(c1);
        daoFactory.close();
        return companyId;
    }

    /**
     * Delete company : used by CLI only.
     *
     * @param company (required) company.
     * @return deleted company id.
     */
    public Long delete(Company company) {
        daoFactory.startTransaction();
        Long companyId = null;
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
        daoFactory.close();
        return companyId;
    }
}


