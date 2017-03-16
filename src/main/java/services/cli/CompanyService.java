package main.java.services.cli;

import main.java.persistence.daos.CompanyDao;
import main.java.models.Company;
import main.java.persistence.DAOFactory;
import main.java.persistence.daos.CompanyDaoImpl;
import java.util.List;


/**
 * Created by ebiz on 15/03/17.
 */
public class CompanyService {

    private static final String ATT_DAO_FACTORY = "daofactory";
    /* Instantiation of our DAOFactory */
    private static DAOFactory daoFactory = DAOFactory.getInstance();
    /* Instantiation of companyDao and computerDao */
    private static CompanyDao companyDao = CompanyDaoImpl.getInstance();

    public void printAllCompanies(){

        System.out.println("list_companies :");
        List<Company> list_companies = companyDao.GetAll();
        for(int i=0; i< list_companies.size(); i++) {
            System.out.println(list_companies.get(i));
        }
    }

    public void printCompaniesByPage(int page){
        List<Company> list_computers;
        System.out.println("Here is the list of the registered companies :");
        list_computers = companyDao.getPageList(page);
        for(int i=0; i< list_computers.size(); i++) {
            System.out.println(list_computers.get(i));
        }
    }


    public Company getCompanyById(Long id){
        Company c = companyDao.findById(id);
        return c;
    }
}
