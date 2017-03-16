package main.java.controllers;

import main.java.services.cli.CompanyService;
import main.java.services.cli.ComputerService;
import main.java.services.cli.InputCLIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;


/**
 * Created by ebiz on 14/03/17.
 */
public class Main {

    private static CompanyService companyService;
    private static ComputerService computerService;
    private static InputCLIService inputCliService;

    static Scanner input = new Scanner(System.in);
    static int ItemNum;

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String [ ] args) throws SQLException {

        companyService = new CompanyService();
        computerService = new ComputerService();

        do{
            System.out.println("What do you want to do ?");
            System.out.println("1: List computers");
            System.out.println("2: List companies");
            System.out.println("3: Show computer details (the detailed information of only one computer)");
            System.out.println("4: Create a computer");
            System.out.println("5: Update a computer");
            System.out.println("6: Delete a computer");
            System.out.println("7: exit");

            ItemNum = input.nextInt();
            input.nextLine();
            switch(ItemNum){
                case 1: //computerService.printAllComputers();
                        boolean quit = false;
                        int currentPage = 0;
                        do{
                            computerService.printComputersByPage(currentPage);
                            System.out.print( "Show more computers (y|n) ? " );
                            if(input.nextLine().equals("y")) {
                                currentPage ++;
                            } else {
                                quit = true;
                            }
                        }while (!quit);
                        break;
                case 2: //companyService.printAllCompanies();
                        quit = false;
                        currentPage = 0;
                        do{
                            companyService.printCompaniesByPage(currentPage);
                            System.out.print( "Show more companies (y|n) ? " );
                            if(input.nextLine().equals("y")) {
                                currentPage ++;
                            } else {
                                quit = true;
                            }
                        }while (!quit);
                        break;
                case 3: System.out.print("Enter the id of the computer you want to display:");
                        ItemNum = input.nextInt();
                        input.nextLine();
                        computerService.showComputerDetail(ItemNum);
                        break;
                case 4: System.out.println("You choose to create a computer:");
                        System.out.print("Please enter a name:");
                        String name = input.nextLine();
                        System.out.print("Please enter the date where computer was introduced with the following format : YYYY-MM-DD hh:mm:ss:");
                        String introduced;
                        while(!inputCliService.isTimeStampValid(introduced = input.nextLine())){
                            System.out.print("Please enter a date with the following format : YYYY-MM-DD hh:mm:ss:");
                        }
                        System.out.print("Please enter the date where computer was discontinued with the following format : YYYY-MM-DD hh:mm:ss:");
                        String discontinued;
                        while(!inputCliService.isTimeStampValid(discontinued = input.nextLine())){
                            System.out.print("Please enter a date with the following format : YYYY-MM-DD hh:mm:ss:");
                        }

                        System.out.print("Please enter the id of the company:");
                        int company_id = input.nextInt();
                        input.nextLine();
                        computerService.createComputer(name, inputCliService.GetLocalDateTime(introduced), inputCliService.GetLocalDateTime(discontinued), company_id);
                        break;
                case 5: System.out.println("You choose to update a computer:");
                        System.out.print("Please enter the id of the computer:");
                        int id = input.nextInt();
                        System.out.print("Please enter the new name of the computer:");
                        name = input.nextLine();
                        System.out.print("Please enter the date where computer was introduced with the following format : YYYY-MM-DD hh:mm:ss:");
                        while(!inputCliService.isTimeStampValid(introduced = input.nextLine())){
                            System.out.print("Please enter a date with the following format : YYYY-MM-DD hh:mm:ss:");
                        }
                        System.out.print("Please enter the date where computer was discontinued with the following format : YYYY-MM-DD hh:mm:ss:");
                        while(!inputCliService.isTimeStampValid(discontinued = input.nextLine())){
                            System.out.print("Please enter a date with the following format : YYYY-MM-DD hh:mm:ss:");
                        }
                        System.out.print("Please enter the id of the company:");
                        company_id = input.nextInt();
                        computerService.updateNameComputer(id, name, inputCliService.GetLocalDateTime(introduced), inputCliService.GetLocalDateTime(discontinued), company_id);
                        break;
                case 6: System.out.println("You choose to delete a computer:");
                        System.out.print("Please enter the id of the computer:");
                        id = input.nextInt();
                        input.nextLine();
                        computerService.deleteComputer(id);
                        break;
                default: break;
            }
        }while (!(input.nextLine().equals("7")));
        input.close();
    }
}
