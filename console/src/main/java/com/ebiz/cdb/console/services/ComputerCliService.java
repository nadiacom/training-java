package com.ebiz.cdb.console.services;

import com.ebiz.cdb.core.models.Computer;
import com.ebiz.cdb.service.impl.ComputerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputerCliService {
    private final ComputerServiceImpl computerServiceImpl;

    /**
     * ComputerCliService constructor.
     *
     * @param computerServiceImpl autowired computerService
     */
    @Autowired
    public ComputerCliService(ComputerServiceImpl computerServiceImpl) {
        this.computerServiceImpl = computerServiceImpl;
    }

    /**
     * List and print all computers.
     */
    public void printAllComputers() {
        System.out.println("Here is the list of the registered computers :");
        List<Computer> listComputers = computerServiceImpl.getAll();
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
        List<Computer> listComputers = computerServiceImpl.getByPage(page, nbComputerByPage);
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
    //TODO update cli for create method
    /*
    public void updateComputer(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        System.out.println("Updated computer: id = " + computerService.update(id, name, introduced, discontinued, companyId));
    } */

    /**
     * Delete computer.
     *
     * @param id (required) computer id.
     */
    public void deleteComputer(int id) {
        if (computerServiceImpl.findById(Long.valueOf(id)) != null) {
            System.out.println("Removed computer with id: " + computerServiceImpl.delete(id));
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
    //TODO update cli for create method
    /*
    public void createComputer(String name, LocalDate introduced, LocalDate discontinued, int companyId) {
        System.out.println("You created computer : " + computerService.create(name, introduced, discontinued, companyId));
    } */

    /**
     * Display computer details.
     *
     * @param id (required) computer id.
     */
    public void showComputerDetails(int id) {
        if (computerServiceImpl.findById(Long.valueOf(id)) != null) {
            System.out.println("Computer: " + computerServiceImpl.findById(Long.valueOf(id)));
        } else {
            System.out.println("No computer exists with the given id. Try another one.");
        }
    }
}
