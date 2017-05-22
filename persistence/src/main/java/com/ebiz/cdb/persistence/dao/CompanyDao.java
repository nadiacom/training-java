package com.ebiz.cdb.persistence.dao;

import com.ebiz.cdb.core.models.Company;

import java.util.List;

public interface CompanyDao {

    /**
     * Find company by id.
     *
     * @param id (required) company id.
     * @return company.
     */
    Company findById(Long id);

    /**
     * Find companies by name.
     *
     * @param name             company name.
     * @param page             (required) page number.
     * @param nbComputerByPage number of companies displayed by page.
     * @return list of companies.
     */
    List<Company> findByName(String name, int page, int nbComputerByPage);

    /**
     * Get all companies.
     *
     * @return list of all companies.
     */
    List<Company> getAll();

    /**
     * Get companies by page.
     *
     * @param page (required) page number.
     * @return list of companies by page.
     */
    List<Company> getPageList(int page);

    /**
     * Delete company by id.
     *
     * @param company (required) company.
     * @return deleted comany id.
     */
    Long delete(Company company);

}
