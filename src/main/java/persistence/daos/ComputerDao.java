package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;
import models.Computer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
public interface ComputerDao {

    /**
     * Create computer.
     *
     * @param computer (required) computer.
     * @throws DAOException exception dao
     * @return computer ids.
     */
    Long create(Computer computer) throws DAOException;

    /**
     * Find computer by id.
     *
     * @param id (required) computer id.
     * @throws DAOException exception dao.
     * @return computer.
     */
    Computer findById(Long id) throws DAOException;

    /**
     * Update computer.
     *
     * @param computer (required) computer.
     * @throws DAOException exception dao.
     * @return computer id.
     */
    Long update(Computer computer) throws DAOException;

    /**
     * Delete computer.
     *
     * @param computer (required) computer.
     * @throws DAOException exception dao.
     * @return previous computer id.
     */
    Long remove(Computer computer) throws DAOException;

    /**
     * Get all computers.
     *
     * @throws DAOException exception dao.
     * @return list of all computers.
     */
    List<Computer> getAll() throws DAOException;

    /**
     * Get all computers by page.
     *
     * @param page (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @throws DAOException exception dao.
     * @return list of computers by page.
     */
    List<Computer> getPageList(int page, int nbComputerByPage) throws DAOException;

    /**
     * Get number of computers.
     *
     * @throws DAOException exception dao.
     * @throws SQLException SQL exception.
     * @return number of computers.
     */
    int getNumberComputers() throws DAOException;

}
