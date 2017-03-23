package cli;

import models.Computer;
import services.ComputerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ebiz on 15/03/17.
 */
public class ComputerCli {

    private static ComputerService  computerService = ComputerService.getInstance();

    /**
     * List and print all computers.
     */
    public void printAllComputers() {
        System.out.println("Here is the list of the registered computers :");
        List<Computer> listComputers = computerService.getAllComputers();
        for (int i = 0; i < listComputers.size(); i++) {
            System.out.println(listComputers.get(i));
        }
    }

    /**
     * List and print all computers by page.
     *
     * @param page (required) page number.
     */
    public void printComputersByPage(int page, int nbComputerByPage) {
        System.out.println("Here is the list of the registered computers :");
        List<Computer> listComputers = computerService.getComputersByPage(page, nbComputerByPage);
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
     * @param companyId   (required) company id of the computer.
     */
    public void updateComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        System.out.println("Updated computer: id = " + computerService.updateComputer(id, name, introduced, discontinued, companyId));
    }

    /**
     * Delete computer.
     *
     * @param id (required) computer id.
     */
    public void deleteComputer(int id) {
        System.out.println("Removed computer with id: " + computerService.deleteComputer(id));
    }

    /**
     * Create computer.
     *
     * @param name         (required) computer name.
     * @param introduced   (required) date when computer was introduced.
     * @param discontinued (required) date when computer was discontinued.
     * @param companyId   (required) company id of the computer.
     */
    public void createComputer(String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        System.out.println("You created computer : " + computerService.createComputer(name, introduced, discontinued, companyId));
    }

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     */
    public void showComputerDetails(int id) {
        System.out.println("Computer: " + computerService.getComputer(id));
    }
}
