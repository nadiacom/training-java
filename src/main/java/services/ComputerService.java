package services;

import models.Computer;
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
public enum ComputerService {

    INSTANCE;

    /**
     * Default constructor.
     */
    ComputerService() {
    }

    private static ComputerDao computerDao = ComputerDaoImpl.INSTANCE;
    private static CompanyDao companyDao = CompanyDaoImpl.INSTANCE;

    protected DAOFactory daoFactory = DAOFactory.INSTANCE;

    /**
     * Get all computers.
     *
     * @return all computers.s
     */
    public List<Computer> getAll() {
        daoFactory.open();
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
        daoFactory.open();
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
    public Long update(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        /* Retieve */
        daoFactory.open();
        Computer c1 = computerDao.findById(Long.valueOf(id));
        /* Update */
        c1.setName(name);
        c1.setIntroduced(introduced);
        c1.setDiscontinued(discontinued);
         /* Get and set company */
        c1.setCompany(companyDao.findById(Long.valueOf(companyId)));
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
        return computerId;
    }

    /**
     * Delete all computers that belong to one given company.
     *
     * @param id company id.
     */
    public void deleteByCompanyId(int id) {
        daoFactory.open();
        computerDao.deleteByCompanyId(Long.valueOf(id));
        daoFactory.close();
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
        daoFactory.open();
        List<Computer> computers = computerDao.findByName(name, page, nbComputerByPage);
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
        daoFactory.open();
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
    public Computer create(String name, LocalDate introduced, LocalDate discontinued, Integer companyId) {
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
        daoFactory.open();
        Long id = computerDao.create(computer);
        Computer c = computerDao.findById(id);
        daoFactory.close();
        /* Return computer */
        return c;
    }

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     * @return computer.
     */
    public Computer get(int id) {
        daoFactory.open();
        Computer c = computerDao.findById(Long.valueOf(id));
        return c;
    }

}
