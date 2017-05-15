package cli;

import models.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.ComputerService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComputerCli {
    @Autowired
    private ComputerService computerService;

    /**
     * List and print all computers.
     */
    public void printAllComputers() {
        System.out.println("Here is the list of the registered computers :");
        List<Computer> listComputers = computerService.getAll();
        for (int i = 0; i < listComputers.size(); i++) {
            System.out.println(listComputers.get(i));
        }
    }

    /**
     * List and print all computers by page.
     *
     * @param page             (required) page number.
     * @param nbComputerByPage (required) number of computer user want to show by page.
     */
    public void printComputersByPage(int page, int nbComputerByPage) {
        System.out.println("Here is the list of the registered computers :");
        List<Computer> listComputers = computerService.getByPage(page, nbComputerByPage);
        for (int i = 0; i < listComputers.size(); i++) {
            System.out.println(listComputers.get(i));
        }
    }

    /**
     * Update computer.
     *
     * @param id           (required) computer id.
     * @param name         (required) computer name
     * @param introduced   (required) date when computer was introduced.
     * @param discontinued (required) date when computer was discontinued.
     * @param companyId    (required) company id of the computer.
     */
    public void updateComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        System.out.println("Updated computer: id = " + computerService.update(id, name, introduced, discontinued, companyId));
    }

    /**
     * Delete computer.
     *
     * @param id (required) computer id.
     */
    public void deleteComputer(int id) {
        if (computerService.get(id) != null) {
            System.out.println("Removed computer with id: " + computerService.delete(id));
        } else {
            System.out.println("No computer exists with the given id. Try another one.");
        }
    }

    /**
     * Create computer.
     *
     * @param name         (required) computer name.
     * @param introduced   (required) date when computer was introduced.
     * @param discontinued (required) date when computer was discontinued.
     * @param companyId    (required) company id of the computer.
     */
    public void createComputer(String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        System.out.println("You created computer : " + computerService.create(name, introduced, discontinued, companyId));
    }

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     */
    public void showComputerDetails(int id) {
        if (computerService.get(id) != null) {
            System.out.println("Computer: " + computerService.get(id));
        } else {
            System.out.println("No computer exists with the given id. Try another one.");
        }
    }
}
