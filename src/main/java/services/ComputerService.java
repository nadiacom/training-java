package services;

import models.Computer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.DAOFactory;
import persistence.daos.CompanyDao;
import persistence.daos.ComputerDao;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ComputerService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("services.ComputerService");
    private ComputerDao computerDao;
    private CompanyDao companyDao;
    private DAOFactory daoFactory;

    /**
     * ComputerService constructor.
     *
     * @param computerDao autowired computerDao
     * @param companyDao  autowired companyDao
     * @param daoFactory  autowired daoFactory
     */
    @Autowired
    public ComputerService(ComputerDao computerDao, CompanyDao companyDao, DAOFactory daoFactory) {
        this.computerDao = computerDao;
        this.companyDao = companyDao;
        this.daoFactory = daoFactory;
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
     * @param computer (required) computer.
     * @return computer id.
     */
    public Long update(Computer computer) {
        Long computerId = computerDao.update(computer);
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
        List<Computer> computers = computerDao.getPageListNameOrderBy(page, nbComputerByPage, name, colmunName, orderBy);
        daoFactory.close();
        return computers;
    }

    /**
     * Find computers by page and order by column.
     *
     * @param colmunName       column name.
     * @param orderBy          order by : "ASC" or "DESC".
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     */
    public List<Computer> findByOrder(String colmunName, String orderBy, int page, int nbComputerByPage) {
        List<Computer> computers = computerDao.getPageListOrderBy(page, nbComputerByPage, colmunName, orderBy);
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
     * @param computer (required) computer.
     * @return computer id.
     */
    public Long create(Computer computer) {
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

    public void setComputerDao(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
