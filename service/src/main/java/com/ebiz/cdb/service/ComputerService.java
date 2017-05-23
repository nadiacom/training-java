package com.ebiz.cdb.service;

import com.ebiz.cdb.core.models.Computer;

import java.util.List;

public interface ComputerService {

    /**
     * Get all computers.
     *
     * @return all computers.s
     */
    List<Computer> getAll();

    /**
     * Get all computers by page.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return computer list by page.
     */
    List<Computer> getByPage(int page, int nbComputerByPage);

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
     * @param id (required) computer id.
     * @return computer id.
     */
    Long delete(int id);

    /**
     * Delete all computers that belong to one given company.
     *
     * @param id company id.
     */
    void deleteByCompanyId(int id);

    /**
     * Find computer by id.
     *
     * @param id computer id.
     * @return computer.
     */
    Computer findById(Long id);

    /**
     * Find computers by name.
     *
     * @param name             computer name.
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     */
    List<Computer> findByName(String name, int page, int nbComputerByPage);

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
    List<Computer> findByNameAndOrder(String name, String colmunName, String orderBy, int page, int nbComputerByPage);

    /**
     * Find computers by page and order by column.
     *
     * @param colmunName       column name.
     * @param orderBy          order by : "ASC" or "DESC".
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of computers.
     */
    List<Computer> findByOrder(String colmunName, String orderBy, int page, int nbComputerByPage);

    /**
     * Retrieve all computers that belong to a given company.
     *
     * @param id company id.
     * @return list of computers.
     */
    List<Computer> findByCompanyId(int id);

    /**
     * Create computer.
     *
     * @param computer (required) computer.
     * @return computer id.
     */
    Long create(Computer computer);

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     * @return computer.
     */
    Computer get(int id);

    /**
     * Count all computers.
     *
     * @return nb of computers.
     */
    Long count();

    /**
     * Count computers by name.
     *
     * @param name computer name.
     * @return nb of computers.
     */
    Long countByName(String name);
}
