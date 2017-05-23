package com.ebiz.cdb.service;

import com.ebiz.cdb.core.models.Company;

import java.util.List;

public interface CompanyService {

    /**
     * Get all companies.
     *
     * @return all companies.
     */
    List<Company> getAll();


    /**
     * Get all companies by page.
     *
     * @param page (required) page number.
     * @return companies list by page.
     */
    List<Company> getPageList(int page);

    /**
     * Get company by id.
     *
     * @param id (required) company id.
     * @return company.
     */
    Company findById(Long id);

    /**
     * Find companies by name.
     *
     * @param name             company name.
     * @param page             page number.
     * @param nbComputerByPage number of companies displayed by page.
     * @return list of companies.
     */
    List<Company> findByName(String name, int page, int nbComputerByPage);

    /**
     * Delete company by id.
     *
     * @param id (required) company id.
     * @return deleted company id.
     */
    Long delete(int id);

    /**
     * Delete company : used by CLI only.
     *
     * @param company (required) company.
     * @return deleted company id.
     */
    Long delete(Company company);
}
