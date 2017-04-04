package services;

import models.Company;
import models.Computer;
import persistence.DAOFactory;
import persistence.daos.CompanyDao;
import persistence.daos.CompanyDaoImpl;
import persistence.daos.ComputerDao;
import persistence.daos.ComputerDaoImpl;

import java.util.List;

/**
 * Created by ebiz on 20/03/17.
 */
public enum CompanyService {

    INSTANCE;

    /**
     * Default constructor.
     */
    CompanyService() {
    }

    private static CompanyDao companyDao = CompanyDaoImpl.INSTANCE;
    private static ComputerDao computerDao = ComputerDaoImpl.INSTANCE;

    protected DAOFactory daoFactory = DAOFactory.INSTANCE;

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
        daoFactory.close();
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
            System.out.println("Removed company with id: " + companyId);
            //Delete potential computers belonging to this company
            List<Computer> computers = computerDao.findByCompanyId(company.getId());
            if (!computers.isEmpty()) {
                computerDao.deleteByCompanyId(company.getId());
                System.out.println("Removed following computer: " + computers);
            } else {
                System.out.println("No computer belong to this company. No computer removed");
            }
        } else {
            System.out.println("No company exists with the given id. Try another one.");
        }
        daoFactory.commit();
        daoFactory.close();
        return companyId;
    }
}
