package cli;

import models.Company;
import services.CompanyService;
import services.ComputerService;

import java.util.List;

/**
 * Created by ebiz on 15/03/17.
 */
public class CompanyCli {

    private static CompanyService companyService = CompanyService.getInstance();
    private static ComputerService computerService = ComputerService.getInstance();

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
        List<Company> listCompanies = companyService.getByPage(page);
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
        return companyService.getById(id);
    }

    /**
     * Delete computer.
     *
     * @param id (required) company id.
     */
    public void delete(int id) {
        //Check if company exists
        if (companyService.getById(Long.valueOf(id)) != null) {
            //Remove company
            System.out.println("Removed company with id: " + companyService.delete(id));
            //Check if company owns computers
            if (!computerService.findByCompanyId(id).isEmpty()) {
                //Remove computers that belong to given company
                System.out.println("Removed following computer: " + computerService.findByCompanyId(id));
                computerService.deleteByCompanyId(id);
            } else {
                System.out.println("No computer belong to this company. No computer removed");
            }
        } else {
            System.out.println("No company exists with the given id. Try another one.");
        }

    }
}
