package com.ebiz.cdb.persistence.dao;



import com.ebiz.cdb.persistence.Computer;

import java.util.List;

public interface ComputerDao {

    /**
     * Create computer.
     *
     * @param computer (required) computer.
     * @return computer ids.
     */
    Long create(Computer computer);

    /**
     * Find computer by id.
     *
     * @param id (required) computer id.
     * @return computer.
     */
    Computer findById(Long id);

    /**
     * Find computers by name.
     *
     * @param name             computer name.
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     */
    List<Computer> findByName(String name, int page, int nbComputerByPage);

    /**
     * Update computer.
     *
     * @param computer (required) computer.
     * @return computer id.
     */
    Long update(Computer computer);

    /**
     * Delete computer.
     *
     * @param computer (required) computer.
     * @return previous computer id.
     */
    Long remove(Computer computer);

    /**
     * Get all computers.
     *
     * @return list of all computers.
     */
    List<Computer> getAll();

    /**
     * Get all computers by page.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers by page.
     */
    List<Computer> getPageList(int page, int nbComputerByPage);

    /**
     * Count number of computers.
     *
     * @return number of computers.
     */
    Long count();

    /**
     * Count computer with given name.
     *
     * @param name computer name.
     * @return list of computers.
     */
    Long countByName(String name);

    /**
     * Delete all computers that belong to one given company.
     *
     * @param companyId company id.
     */
    void deleteByCompanyId(Long companyId);

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
     * @param columnName       colmun name in db.
     * @param orderBy          order by "ASC" or "DESC".
     * @return list of computers.
     */
    List<Computer> getPageListOrderBy(int page, int nbComputerByPage, String columnName, String orderBy);

    /**
     * Get all computers by page, order by column name.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @param name             search filter: company or computer name.
     * @param columnName       colmun name in db.
     * @param orderBy          order by "ASC" or "DESC".
     * @return list of computers.
     */
    List<Computer> getPageListNameOrderBy(int page, int nbComputerByPage, String name, String columnName, String orderBy);
}