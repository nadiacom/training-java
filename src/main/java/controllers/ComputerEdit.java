package controllers;

import models.Company;
import models.Computer;
import services.CompanyService;
import services.ComputerService;
import services.validators.Input;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class ComputerEdit extends javax.servlet.http.HttpServlet {

    private int NB_COMPUTER_PAGE = 50;
    private static Input inputValidator = new Input();

    /**
     * doGet method.
     *
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException javax servlet exception.
     * @throws IOException                    IOException.
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        if (request.getParameter("computer") != null) {
            //Get computer id
            int id = Integer.valueOf(request.getParameter("computer"));
            //Get computer from id
            Computer c = ComputerService.getInstance().getComputer(id);
            //Get companies ids and names
            List<Company> companies = CompanyService.getInstance().getAllCompanies();
            //Set view parameters
            request.setAttribute("computer", c);
            request.setAttribute("companies", companies);
        }
        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/editComputer.jsp");
        rd.include(request, response);
    }

    /**
     * doPost method.
     *
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException javax servlet exception.
     * @throws IOException                    IOException.
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        if (request.getParameter("computer") != null) {
            //Update computer
            Long computerId = ComputerService.getInstance().updateComputer(Integer.valueOf(request.getParameter("id")), request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), Integer.valueOf(request.getParameter("companyId")));
            //Redirect to dashboard
            response.sendRedirect("/dashboard");
        }
    }
}
