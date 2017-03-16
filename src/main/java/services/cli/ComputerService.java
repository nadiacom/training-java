package main.java.services.cli;

import main.java.persistence.daos.CompanyDao;
import main.java.persistence.daos.CompanyDaoImpl;
import main.java.persistence.daos.ComputerDao;;
import main.java.models.Company;
import main.java.models.Computer;
import main.java.persistence.DAOFactory;
import main.java.persistence.daos.ComputerDaoImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ebiz on 15/03/17.
 */
public class ComputerService {

    private static final String ATT_DAO_FACTORY = "daofactory";
    /* Instantiation of our DAOFactory */
    private static DAOFactory daoFactory =  DAOFactory.getInstance();
    /* Instantiation of companyDao and computerDao */
    private static ComputerDao computerDao = ComputerDaoImpl.getInstance();
    private static CompanyDao companyDao = CompanyDaoImpl.getInstance();

    public void printAllComputers(){
        System.out.println("Here is the list of the registered computers :");
        List<Computer> list_computers = computerDao.GetAll();
        for(int i=0; i< list_computers.size(); i++) {
            System.out.println(list_computers.get(i));
        }
    }

    public void printComputersByPage(int page){
        List<Computer> list_computers;
        System.out.println("Here is the list of the registered computers :");
        list_computers = computerDao.getPageList(page);
        for(int i=0; i< list_computers.size(); i++) {
            System.out.println(list_computers.get(i));
        }
     }

    public void updateNameComputer(int id, String name, LocalDateTime introduced, LocalDateTime discontinued, int company_id){
         /* Get company from input company_id */
        Company c = companyDao.findById(Long.valueOf(company_id));
        /* Retieve computer */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        System.out.println("id computer :"+ id);
        /* Update computer */
        c1.setName(name);
        c1.setIntroduced(introduced);
        c1.setDiscontinued(discontinued);
        c1.setCompany(c);
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

    public void createComputer(String name, LocalDateTime introduced, LocalDateTime discontinued, int company_id){
        /* Get company from input company_id */
        Company c = companyDao.findById(Long.valueOf(company_id));
        /* Create a computer */
        Computer computer = new
                 Computer.ComputerBuilder()
                 .name(name)
                 .introduced(introduced)
                 .discontinued(discontinued)
                 .company(c)
                 .build();
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
