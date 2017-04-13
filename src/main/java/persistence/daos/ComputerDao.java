package persistence.daos;

import exceptions.daos.DAOException;
import models.Computer;

import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
public interface ComputerDao {

    /**
     * Create computer.
     *
     * @param computer (required) computer.
     * @return computer ids.
     * @throws DAOException exception dao
     */
    Long create(Computer computer) throws DAOException;

    /**
     * Find computer by id.
     *
     * @param id (required) computer id.
     * @return computer.
     * @throws DAOException exception dao.
     */
    Computer findById(Long id) throws DAOException;

    /**
     * Find computers by name.
     *
     * @param name             computer name.
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     * @throws DAOException DAOException.
     */
    List<Computer> findByName(String name, int page, int nbComputerByPage) throws DAOException;

    /**
     * Update computer.
     *
     * @param computer (required) computer.
     * @return computer id.
     * @throws DAOException exception dao.
     */
    Long update(Computer computer) throws DAOException;

    /**
     * Delete computer.
     *
     * @param computer (required) computer.
     * @return previous computer id.
     * @throws DAOException exception dao.
     */
    Long remove(Computer computer) throws DAOException;

    /**
     * Get all computers.
     *
     * @return list of all computers.
     * @throws DAOException exception dao.
     */
    List<Computer> getAll() throws DAOException;

    /**
     * Get all computers by page.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers by page.
     * @throws DAOException exception dao.
     */
    List<Computer> getPageList(int page, int nbComputerByPage) throws DAOException;

    /**
     * Count number of computers.
     *
     * @return number of computers.
     * @throws DAOException exception dao.
     */
    int count() throws DAOException;

    /**
     * Count computer with given name.
     *
     * @param name computer name.
     * @return list of computers.
     * @throws DAOException DAOException.
     */
    int countByName(String name) throws DAOException;

    /**
     * Delete all computers that belong to one given company.
     *
     * @param companyId company id.
     * @throws DAOException DAOException.
     */
    void deleteByCompanyId(Long companyId) throws DAOException;

    /**
     * Retrieve all computers that belong to a given company.
     *
     * @param companyId company id.
     * @return list of computers.
     */
    List<Computer> findByCompanyId(Long companyId);

    /**
     * Get all computers by page, order by column name.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @param name             search filter: company or computer name.
     * @param columnName       colmun name in db.
     * @param orderBy          order by "ASC" or "DESC".
     * @return list of computers.
     * @throws DAOException exception dao.
     */
    List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String name, String columnName, String orderBy) throws DAOException;
}