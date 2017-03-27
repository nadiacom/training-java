package persistence.daos;

import exceptions.daos.DAOException;
import models.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ebiz on 14/03/17.
 */
public interface CompanyDao {

    /**
     * Find company by id.
     *
     * @param id (required) company id.
     * @throws DAOException exception dao.
     * @return company.
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
     * @throws DAOException exception dao.
     * @return list of all companies.
     */
    List<Company> getAll() throws DAOException;

    /**
     * Get companies by page.
     *
     * @param page (required) page number.
     * @throws DAOException exception dao.
     * @return list of companies by page.
     */
    List<Company> getPageList(int page) throws DAOException;

    /**
     * Delete company by id.
     *
     * @param company (required) company.
     * @throws DAOException exception dao.
     * @return deleted comany id.
     */
    Long delete(Company company) throws DAOException;

}
