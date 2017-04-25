package services;

import models.Computer;
import org.slf4j.LoggerFactory;
import persistence.DAOFactory;
import persistence.daos.CompanyDao;
import persistence.daos.CompanyDaoImpl;
import persistence.daos.ComputerDao;
import persistence.daos.ComputerDaoImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ebiz on 20/03/17.
 */
public class ComputerService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("services.ComputerService");
    private static ComputerDao computerDao;
    private static CompanyDao companyDao;
    private static DAOFactory daoFactory;

    /**
     * Default constructor.
     */
    public ComputerService() {
    }

    /**
     * Get all computers.
     *
     * @return all computers.s
     */
    public List<Computer> getAll() {
        List<Computer> computers = computerDao.getAll();
        daoFactory.close();
        return computers;
    }

    /**
     * Get all computers by page.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return computer list by page.
     */
    public List<Computer> getByPage(int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.getPageList(page, nbComputerByPage);
        daoFactory.close();
        return computers;
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
    public Long update(int id, String name, LocalDate introduced, LocalDate discontinued, Integer companyId) {
        /* Retieve */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Update */
        c1.setName(name);
        c1.setIntroduced(introduced);
        c1.setDiscontinued(discontinued);
         /* Get and set company */
        c1.setCompany(companyId != null ? companyDao.findById(Long.valueOf(companyId)) : null);
        /* Update and return computer id */
        Long computerId = computerDao.update(c1);
        daoFactory.close();
        return computerId;
    }

    /**
     * Delete computer.
     *
     * @param id (required) computer id.
     * @return computer id.
     */
    public Long delete(int id) {
        /* Retieve */
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Delete and return id */
        Long computerId = computerDao.remove(c1);
        daoFactory.close();
        return computerId;
    }

    /**
     * Delete all computers that belong to one given company.
     *
     * @param id company id.
     */
    public void deleteByCompanyId(int id) {
        computerDao.deleteByCompanyId(Long.valueOf(id));
        daoFactory.close();
    }

    /**
     * Find computer by id.
     *
     * @param id computer id.
     * @return computer.
     */
    public Computer findById(Long id) {
        Computer c = computerDao.findById(id);
        daoFactory.close();
        return c;
    }

    /**
     * Find computers by name.
     *
     * @param name             computer name.
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     */
    public List<Computer> findByName(String name, int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.findByName(name, page, nbComputerByPage);
        daoFactory.close();
        return computers;
    }

    /**
     * Find computers by name, page and order by column.
     *
     * @param name             computer name.
     * @param colmunName       column name.
     * @param orderBy          order by : "ASC" or "DESC".
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     */
    public List<Computer> findByNameAndOrder(String name, String colmunName, String orderBy, int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.getPageListOrderBy(page, nbComputerByPage, name, colmunName, orderBy);
        daoFactory.close();
        return computers;
    }

    /**
     * Retrieve all computers that belong to a given company.
     *
     * @param id company id.
     * @return list of computers.
     */
    public List<Computer> findByCompanyId(int id) {
        List<Computer> computers = computerDao.findByCompanyId(Long.valueOf(id));
        daoFactory.close();
        return computers;
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
    public Long create(String name, LocalDate introduced, LocalDate discontinued, Integer companyId) {
        /* Build a new computer from model builder */
        Computer computer = new
                Computer.ComputerBuilder()
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued)
                /* Get and set company from input company_id */
                .company(companyId != null ? companyDao.findById(Long.valueOf(companyId)) : null)
                .build();
        /* Create computer */
        Long id = computerDao.create(computer);
        daoFactory.close();
        /* Return computer */
        return id;
    }

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     * @return computer.
     */
    public Computer get(int id) {
        Computer c = computerDao.findById(Long.valueOf(id));
        daoFactory.close();
        return c;
    }

    /**
     * Count all computers.
     *
     * @return nb of computers.
     */
    public int count() {
        int count = computerDao.count();
        daoFactory.close();
        return count;
    }

    /**
     * Count computers by name.
     *
     * @param name computer name.
     * @return nb of computers.
     */
    public int countByName(String name) {
        int count = computerDao.countByName(name);
        daoFactory.close();
        return count;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public void setComputerDao(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }

    public ComputerDao getComputerDao() {
        return computerDao;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }
}
