package main.java.controllers;

import main.java.daos.CompanyDao;
import main.java.daos.ComputerDao;
import main.java.models.Company;
import main.java.services.DAOFactory;

/**
 * Created by ebiz on 14/03/17.
 */
public class Main {

    private static final String ATT_DAO_FACTORY = "daofactory";
    private static DAOFactory daoFactory;
    private static CompanyDao companyDao;


    public static void main(String [ ] args){
        /* Instanciation de notre DAOFactory */
        daoFactory = DAOFactory.getInstance();
        Company company = new Company();
        companyDao = daoFactory.getCompanyDao();
        companyDao.create(company);
    }
}
