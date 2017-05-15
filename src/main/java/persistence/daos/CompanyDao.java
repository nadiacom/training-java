package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;

import java.util.List;

public interface CompanyDao {

    /**
     * Find company by id.
     *
     * @param id (required) company id.
     * @return company.
     * @throws DAOException exception dao.
     */
    Company findById(Long id) throws DAOException;

    /**
     * Find companies by name.
     *
     * @param name             company name.
     * @param page             (required) page number.
     * @param nbComputerByPage number of companies displayed by page.
     * @return list of companies.
     * @throws DAOException DAOException.
     */
    List<Company> findByName(String name, int page, int nbComputerByPage) throws DAOException;

    /**
     * Get all companies.
     *
     * @return list of all companies.
     * @throws DAOException exception dao.
     */
    List<Company> getAll() throws DAOException;

    /**
     * Get companies by page.
     *
     * @param page (required) page number.
     * @return list of companies by page.
     * @throws DAOException exception dao.
     */
    List<Company> getPageList(int page) throws DAOException;

    /**
     * Delete company by id.
     *
     * @param company (required) company.
     * @return deleted comany id.
     * @throws DAOException exception dao.
     */
    Long delete(Company company) throws DAOException;

}
