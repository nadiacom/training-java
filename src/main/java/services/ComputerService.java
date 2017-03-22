package services;

import models.Computer;
import persistence.daos.CompanyDao;
import persistence.daos.CompanyDaoImpl;
import persistence.daos.ComputerDao;
import persistence.daos.ComputerDaoImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by ebiz on 20/03/17.
 */
public class ComputerService {

    /**
     * Default constructor.
     */
    private ComputerService() {
    }

    private static class SingletonHelper {
        private static final ComputerService INSTANCE = new ComputerService();
    }

    public static ComputerService getInstance() {
        return ComputerService.SingletonHelper.INSTANCE;
    }

    /* Instantiation of companyDao and computerDao */
    private static ComputerDao computerDao = ComputerDaoImpl.getInstance();
    private static CompanyDao companyDao = CompanyDaoImpl.getInstance();

    /**
     * Get all computers.
     *
     * @return all computers.s
     */
    public List<Computer> getAllComputers() {
        return computerDao.getAll();
    }

    /**
     * Get all computers by page.
     *
     * @param page (required) page number.
     * @return computer list by page.
     */
    public List<Computer> getComputersByPage(int page) {
        return computerDao.getPageList(page);
    }

    /**
     * Update computer.
     *
     * @param id           (required) computer id.
     * @param name         (required) computer name
     * @param introduced   (required) date when computer was introduced.
     * @param discontinued (required) date when computer was discontinued.
     * @param companyId    (required) company id of the computer.
     * @return computer id.
     */
    public Long updateComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        /* Retieve computer */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Update computer */
        c1.setName(name);
        c1.setIntroduced(introduced);
        c1.setDiscontinued(discontinued);
         /* Get and set company from input company_id */
        c1.setCompany(companyDao.findById(Long.valueOf(companyId)));
        /* Update and return computer id */
        return computerDao.update(c1);
    }

    /**
     * Delete computer.
     *
     * @param id (required) computer id.
     * @return computer id.
     */
    public Long deleteComputer(int id) {
        /* Retieve computer */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Delete computer and return id */
        return computerDao.remove(c1);
    }

    /**
     * Create computer.
     *
     * @param name         (required) computer name.
     * @param introduced   (required) date when computer was introduced.
     * @param discontinued (required) date when computer was discontinued.
     * @param companyId    (required) company id of the computer.
     * @return computer id.
     */
    public Computer createComputer(String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        /* Build a new computer from model builder */
        Computer computer = new
                Computer.ComputerBuilder()
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued)
                /* Get and set company from input company_id */
                .company(companyDao.findById(Long.valueOf(companyId)))
                .build();
        /* Create computer */
        Long id = computerDao.create(computer);
        /* Return computer */
        return computerDao.findById(id);
    }

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     * @return computer.
     */
    public Computer getComputer(int id) {
        return computerDao.findById(Long.valueOf(id));
    }

    /**
     * Get number of computers.
     *
     * @throws SQLException SQL exception.
     * @return number of computers.
     */
    public int getNumberComputers() throws SQLException {
        return computerDao.getNumberComputers();
    }

}
