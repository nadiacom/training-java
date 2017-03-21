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
}
