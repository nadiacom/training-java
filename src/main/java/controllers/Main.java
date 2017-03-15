package main.java.controllers;

import main.java.daos.CompanyDao;
import main.java.daos.ComputerDao;
import main.java.models.Company;
import main.java.models.Computer;
import main.java.services.CompanyService;
import main.java.services.ComputerService;
import main.java.services.DAOFactory;
import main.java.services.InputCLIService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
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

    public static void main(String [ ] args) throws SQLException {
        
        companyService = new CompanyService();
        computerService = new ComputerService();

        while(true){
            System.out.println("What do you want to do ?");
            System.out.println("1: List computers");
            System.out.println("2: List companies");
            System.out.println("3: Show computer details (the detailed information of only one computer)");
            System.out.println("4: Create a computer");
            System.out.println("5: Update a computer");
            System.out.println("6: Delete a computer");

            ItemNum = input.nextInt();
            input.nextLine();
            switch(ItemNum){
                case 1: computerService.printAllComputers();
                        break;
                case 2: companyService.printAllCompanies();
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
                        computerService.createComputer(name, introduced, discontinued, company_id);
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
                        computerService.updateNameComputer(id, name, introduced, discontinued, company_id);
                        break;
                case 6: System.out.println("You choose to delete a computer:");
                        System.out.print("Please enter the id of the computer:");
                        id = input.nextInt();
                        input.nextLine();
                        computerService.deleteComputer(id);
                        break;
                default: break;
            }
        }
    }
}
