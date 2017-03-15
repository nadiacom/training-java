package main.java.services;

import main.java.daos.ComputerDao;;
import main.java.models.Computer;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ebiz on 15/03/17.
 */
public class ComputerService {

    private static final String ATT_DAO_FACTORY = "daofactory";
    /* Instantiation of our DAOFactory */
    private static DAOFactory daoFactory =  DAOFactory.getInstance();
    /* Instantiation of companyDao and computerDao */
    private static ComputerDao computerDao = daoFactory.getComputerDao();

    public void printAllComputers(){
        System.out.println("Here is the list of the registered computers :");
        List<Computer> list_computers = computerDao.GetAll();
        for(int i=0; i< list_computers.size(); i++) {
            System.out.println(list_computers.get(i));
        }
    }

    public void updateNameComputer(int id, String name, String introduced, String discontinued, int company_id){
        /* Retieve computer */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        System.out.println("id computer :"+ id);
        /* Update computer */
        c1.setName(name);
        c1.setIntroduced(Timestamp.valueOf(introduced));
        c1.setDiscontinued(Timestamp.valueOf(discontinued));
        c1.setCompany_id(Long.valueOf(company_id));
        Long id1 = computerDao.update(c1);
        c1 = computerDao.findById(Long.valueOf(id));
        System.out.println("updated computer: id = " +id1+ " name = "+c1.getName());
    }

    public void deleteComputer(int id){
        /* Retieve computer */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Delete computer */
        Long id1 = computerDao.Remove(c1);
        System.out.println("Removed computer with id: " +id1);
    }

    public void createComputer(String name, String introduced, String discontinued, int company_id){
         /* Create a computer */
        Computer computer = new Computer(name, Timestamp.valueOf(introduced),Timestamp.valueOf(discontinued), Long.valueOf(company_id));
        Long id_computer = computerDao.create(computer);
        Computer c1 = computerDao.findById(id_computer);
        System.out.println("You created computer : "+c1.toString());
    }

    public void showComputerDetail(int id){
        System.out.println(id);
        Computer c1 = computerDao.findById(Long.valueOf(id));
        System.out.println("Computer: "+c1.toString());
    }
}
