package services;

import models.Company;
import persistence.daos.CompanyDao;
import persistence.daos.CompanyDaoImpl;

import java.util.List;

/**
 * Created by ebiz on 20/03/17.
 */
public class CompanyService {

    /**
     * Default constructor.
     */
    private CompanyService() {
    }

    private static class SingletonHelper {
        private static final CompanyService INSTANCE = new CompanyService();
    }

    public static CompanyService getInstance() {
        return CompanyService.SingletonHelper.INSTANCE;
    }


    /* Instantiation of companyDao */
    private static CompanyDao companyDao = CompanyDaoImpl.getInstance();

    /**
     * Get all companies.
     *
     * @return all companies.
     */
    public List<Company> getAllCompanies() {
        return companyDao.getAll();
    }

    /**
     * Get all companies by page.
     *
     * @param page (required) page number.
     * @return companies list by page.
     */
    public List<Company> getCompaniesByPage(int page) {
        return companyDao.getPageList(page);
    }

    /**
     * Get company by id.
     *
     * @param id (required) company id.
     * @return company.
     */
    public Company getCompanyById(Long id) {
        return companyDao.findById(id);
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
        return companyDao.findByName(name, page, nbComputerByPage);
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
        return companyDao.delete(c1);
    }
}
