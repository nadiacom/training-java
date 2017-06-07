package com.ebiz.cdb.console.cli;

import com.ebiz.cdb.binding.utils.DatePatternUtils;
import com.ebiz.cdb.console.services.CompanyCliService;
import com.ebiz.cdb.console.services.ComputerCliService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);
    static Scanner input = new Scanner(System.in);
    static int itemNum;
    private static CompanyCliService companyService;
    private static ComputerCliService computerService;
    private static DatePatternUtils datePatternUtilsCli;

    /**
     * Main constructor.
     *
     * @param companyService  autowired companyService
     * @param computerService autowired computerService
     */
    @Autowired
    public Main(CompanyCliService companyService, ComputerCliService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    /**
     * Command-line interface.
     *
     * @param args CLI input.
     * @throws SQLException SQL exception.
     */
    public static void main(String[] args) throws SQLException {

        datePatternUtilsCli = new DatePatternUtils();

        do {
            System.out.println("What do you want to do ?");
            System.out.println("1: List computers");
            System.out.println("2: List companies");
            System.out.println("3: Show computer details (the detailed information of only one computer)");
            System.out.println("4: Create a computer");
            System.out.println("5: Update a computer");
            System.out.println("6: Delete a computer");
            System.out.println("7: Delete a company (will delete all computers linked to this company)");
            System.out.println("8: exit");

            itemNum = input.nextInt();
            input.nextLine();
            switch (itemNum) {
                case 1: //computerService.printAllComputers();
                    boolean quit = false;
                    int currentPage = 0;
                    do {
                        computerService.printComputersByPage(currentPage, 10);
                        System.out.print("Show more computers (y|n) ? ");
                        if (input.nextLine().equals("y")) {
                            currentPage++;
                        } else {
                            quit = true;
                        }
                    } while (!quit);
                    break;
                case 2: //companyService.printAllCompanies();
                    quit = false;
                    currentPage = 1;
                    do {
                        companyService.printCompaniesByPage(currentPage);
                        System.out.print("Show more companies (y|n) ? ");
                        if (input.nextLine().equals("y")) {
                            currentPage++;
                        } else {
                            quit = true;
                        }
                    } while (!quit);
                    break;
                case 3:
                    System.out.print("Enter the id of the computer you want to display:");
                    itemNum = input.nextInt();
                    input.nextLine();
                    computerService.showComputerDetails(itemNum);
                    break;
                case 4:
                    System.out.println("You choose to create a computer:");
                    System.out.print("Please enter a name:");
                    String name = input.nextLine();
                    System.out.print("Please enter the date where computer was introduced with the following format : YYYY-MM-DD:");
                    String introduced;
                    while (!datePatternUtilsCli.isPatternValid(introduced = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }
                    System.out.print("Please enter the date where computer was discontinued with the following format : YYYY-MM-DD:");
                    String discontinued;
                    while (!datePatternUtilsCli.isPatternValid(discontinued = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }

                    System.out.print("Please enter the id of the company:");
                    int companyId = input.nextInt();
                    input.nextLine();
                    //TODO call updated create method cli
                    //computerService.createComputer(name, datePatternUtilsCli.getLocalDate(introduced), datePatternUtilsCli.getLocalDate(discontinued), companyId);
                    break;
                case 5:
                    System.out.println("You choose to update a computer:");
                    System.out.print("Please enter the id of the computer:");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.print("Please enter the new name of the computer:");
                    name = input.nextLine();
                    System.out.print("Please enter the date where computer was introduced with the following format : YYYY-MM-DD:");
                    while (!datePatternUtilsCli.isPatternValid(introduced = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }
                    System.out.print("Please enter the date where computer was discontinued with the following format : YYYY-MM-DD:");
                    while (!datePatternUtilsCli.isPatternValid(discontinued = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }
                    System.out.print("Please enter the id of the company:");
                    companyId = input.nextInt();
                    //TODO call updated update method cli
                    //computerService.updateComputer(id, name, datePatternUtilsCli.getLocalDate(introduced), datePatternUtilsCli.getLocalDate(discontinued), companyId);
                    break;
                case 6:
                    System.out.println("You choose to delete a computer:");
                    System.out.print("Please enter the id of the computer:");
                    id = input.nextInt();
                    input.nextLine();
                    computerService.deleteComputer(id);
                    break;
                case 7:
                    System.out.println("You choose to delete a company:");
                    System.out.print("Please enter the id of the company:");
                    id = input.nextInt();
                    input.nextLine();
                    companyService.delete(id);
                    break;
                default:
                    break;
            }
        } while (!(input.nextLine().equals("8")));
        input.close();
    }

}