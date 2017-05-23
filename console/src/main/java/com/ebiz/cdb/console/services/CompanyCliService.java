package com.ebiz.cdb.console.services;


import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.service.impl.CompanyServiceImpl;
import com.ebiz.cdb.service.impl.ComputerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyCliService {

    private final CompanyServiceImpl companyServiceImpl;
    private final ComputerServiceImpl computerServiceImpl;

    /**
     * CompanyCliService constructor.
     *
     * @param companyServiceImpl  autowired companyService
     * @param computerServiceImpl autowired computerService
     */
    @Autowired
    public CompanyCliService(CompanyServiceImpl companyServiceImpl, ComputerServiceImpl computerServiceImpl) {
        this.companyServiceImpl = companyServiceImpl;
        this.computerServiceImpl = computerServiceImpl;
    }

    /**
     * List and print all companies.
     */
    public void printAllCompanies() {
        System.out.println("List of the companies :");
        List<Company> listCompanies = companyServiceImpl.getAll();
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
        List<Company> listCompanies = companyServiceImpl.getPageList(page);
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
        return companyServiceImpl.findById(id);
    }

    /**
     * Delete computer.
     *
     * @param id (required) company id.
     */
    public void delete(int id) {
        Company c = companyServiceImpl.findById(Long.valueOf(id));
        companyServiceImpl.delete(c);
    }
}
