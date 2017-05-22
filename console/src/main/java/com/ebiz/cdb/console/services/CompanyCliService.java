package com.ebiz.cdb.console.services;


import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.service.CompanyService;
import com.ebiz.cdb.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyCliService {

    private final CompanyService companyService;
    private final ComputerService computerService;

    /**
     * CompanyCliService constructor.
     *
     * @param companyService  autowired companyService
     * @param computerService autowired computerService
     */
    @Autowired
    public CompanyCliService(CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    /**
     * List and print all companies.
     */
    public void printAllCompanies() {
        System.out.println("List of the companies :");
        List<Company> listCompanies = companyService.getAll();
        for (int i = 0; i < listCompanies.size(); i++) {
            System.out.println(listCompanies.get(i));
        }
    }

    /**
     * List and print all companies by page.
     *
     * @param page (required) page number.
     */
    public void printCompaniesByPage(int page) {
        System.out.println("Here is the list of the registered companies :");
        List<Company> listCompanies = companyService.getPageList(page);
        for (int i = 0; i < listCompanies.size(); i++) {
            System.out.println(listCompanies.get(i));
        }
    }

    /**
     * Get company by id.
     *
     * @param id (required) company id.
     * @return company
     */
    public Company getCompanyById(Long id) {
        return companyService.findById(id);
    }

    /**
     * Delete computer.
     *
     * @param id (required) company id.
     */
    public void delete(int id) {
        Company c = companyService.findById(Long.valueOf(id));
        companyService.delete(c);
    }
}
