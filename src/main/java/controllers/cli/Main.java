package controllers.cli;

import cli.CompanyCli;
import cli.ComputerCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.validators.inputs.Input;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);
    static Scanner input = new Scanner(System.in);
    static int itemNum;
    @Autowired
    private static CompanyCli companyService;
    @Autowired
    private static ComputerCli computerService;
    private static Input inputCli;

    /**
     * Main method.
     *
     * @param companyService  companyService.
     * @param computerService computerService.
     */
    private Main(CompanyCli companyService, ComputerCli computerService) {
        Main.companyService = companyService;
        Main.computerService = computerService;
    }

    /**
     * Command-line interface.
     *
     * @param args CLI input.
     * @throws SQLException SQL exception.
     */
    public static void main(String[] args) throws SQLException {


        inputCli = new Input();

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
                    while (!inputCli.isDatePatternValid(introduced = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }
                    System.out.print("Please enter the date where computer was discontinued with the following format : YYYY-MM-DD:");
                    String discontinued;
                    while (!inputCli.isDatePatternValid(discontinued = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }

                    System.out.print("Please enter the id of the company:");
                    int companyId = input.nextInt();
                    input.nextLine();
                    computerService.createComputer(name, inputCli.getLocalDate(introduced), inputCli.getLocalDate(discontinued), companyId);
                    break;
                case 5:
                    System.out.println("You choose to update a computer:");
                    System.out.print("Please enter the id of the computer:");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.print("Please enter the new name of the computer:");
                    name = input.nextLine();
                    System.out.print("Please enter the date where computer was introduced with the following format : YYYY-MM-DD:");
                    while (!inputCli.isDatePatternValid(introduced = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }
                    System.out.print("Please enter the date where computer was discontinued with the following format : YYYY-MM-DD:");
                    while (!inputCli.isDatePatternValid(discontinued = input.nextLine())) {
                        System.out.print("Please enter a date with the following format : YYYY-MM-DD:");
                    }
                    System.out.print("Please enter the id of the company:");
                    companyId = input.nextInt();
                    computerService.updateComputer(id, name, inputCli.getLocalDate(introduced), inputCli.getLocalDate(discontinued), companyId);
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
