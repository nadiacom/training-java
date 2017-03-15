package main.java.controllers;

import main.java.daos.CompanyDao;
import main.java.daos.ComputerDao;
import main.java.models.Company;
import main.java.models.Computer;
import main.java.services.DAOFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
public class Main {

    private static final String ATT_DAO_FACTORY = "daofactory";
    private static DAOFactory daoFactory;
    private static CompanyDao companyDao;
    private static ComputerDao computerDao;

    public static void main(String [ ] args) throws SQLException {

      /* Instanciation de notre DAOFactory */
        daoFactory = DAOFactory.getInstance();

        /* Get company and print it */
        /* Company c = companyDao.findById(id);
        System.out.println(c.toString()); */


        /* Create a computer */
        Computer computer = new Computer("Computer test 2",Timestamp.valueOf("2017-03-15 11:45:00"),Timestamp.valueOf("2017-03-15 11:45:00"), 1L);
        computerDao = daoFactory.getComputerDao();
        Long id_computer = computerDao.create(computer);
        System.out.println(id_computer);
        Computer c1 = computerDao.findById(id_computer);
        System.out.println(c1.toString());

        /* Update a computer */
        c1.setName("COMPUTER TEST");
        Long id1 = c1.getId();
        System.out.println(id1);
        id1 = computerDao.updateName(c1);
        System.out.println(c1.toString());
        System.out.println("id updated computer:" +id1);

        /* Delete a computer */
        id1 = computerDao.Remove(c1);
        System.out.println("id removed computer:" +id1);

        /* List all computers */
        List<Computer> list_computer = computerDao.GetAll();
        System.out.println("list_computer:" +list_computer);
        /* List all companies */
        /* Show computer details */
    }
}
