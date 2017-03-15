package main.java.services;

import main.java.daos.CompanyDao;
import main.java.models.Company;
import main.java.services.DAOFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ebiz on 15/03/17.
 */
public class CompanyService {

    private static final String ATT_DAO_FACTORY = "daofactory";
    /* Instantiation of our DAOFactory */
    private static DAOFactory daoFactory = DAOFactory.getInstance();
    /* Instantiation of companyDao and computerDao */
    private static CompanyDao companyDao = daoFactory.getCompanyDao();

    public void printAllCompanies(){

        System.out.println("list_companies :");
        List<Company> list_companies = companyDao.GetAll();
        for(int i=0; i< list_companies.size(); i++) {
            System.out.println(list_companies.get(i));
        }
    }
}
